package io.github.lix3nn53.guardiansofadelia.revive;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.creatures.spawners.SpawnerManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.Gui;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class TombManager {

    private static HashMap<Player, ArmorStand> playerToTomb = new HashMap<>();
    private static HashMap<Player, Location> playerToTombLocation = new HashMap<>();

    public static boolean isTomb(Entity entity) {
        return playerToTomb.values().contains(entity);
    }

    public static void onDeath(Player player, Location location) {
        playerToTombLocation.put(player, location);

        Town town = TownManager.getNearestTown(location);

        createModel(player, location);

        player.teleport(town.getLocation());
        openDeathGui(player);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.getGameMode().equals(GameMode.SPECTATOR)) {
                    removeTomb(player);
                    player.teleport(town.getLocation());
                    player.setGameMode(GameMode.ADVENTURE);
                }
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), 20 * 120L);
    }

    private static void removeTomb(Player player) {
        if (playerToTomb.containsKey(player)) {
            ArmorStand tomb = playerToTomb.get(player);
            tomb.remove();
            playerToTomb.remove(player);
            playerToTombLocation.remove(player);
        }
    }

    private static void openDeathGui(Player player) {
        String titleBase = ChatColor.AQUA + "Revive Gui";
        GuiGeneric reviveGui = new GuiGeneric(9, titleBase, 0);

        ItemStack respawn = new ItemStack(Material.IRON_HOE);
        ItemMeta itemMeta = respawn.getItemMeta();
        if (itemMeta instanceof Damageable) {
            Damageable damageable = (Damageable) itemMeta;
            damageable.setDamage(19);
        }
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(ChatColor.GREEN + "Respawn");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "You have respawn in nearest town");
            add(ChatColor.GRAY + "to your death location.");
            add(ChatColor.GRAY + "Close your inventory to continue.");

        }});
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        respawn.setItemMeta(itemMeta);

        reviveGui.setItem(3, respawn);

        ItemStack ghost = new ItemStack(Material.IRON_HOE);
        if (itemMeta instanceof Damageable) {
            Damageable damageable = (Damageable) itemMeta;
            damageable.setDamage(73);
        }
        itemMeta.setDisplayName("§bSearch your tomb in soul mode");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Find your tomb and left");
            add(ChatColor.GRAY + "click near it to respawn.");
            add(ChatColor.RED + "Time limit is 2 minutes after your death.");
            add(ChatColor.GRAY + "You will respawn here if you cant");
            add(ChatColor.GRAY + "find your tomb in time.");
        }});
        ghost.setItemMeta(itemMeta);

        reviveGui.setItem(5, ghost);

        reviveGui.openInventory(player);

        UUID uniqueId = player.getUniqueId();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (GuardianDataManager.hasGuardianData(uniqueId)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(uniqueId);
                    if (guardianData.hasActiveGui()) {
                        Gui activeGui = guardianData.getActiveGui();
                        if (activeGui instanceof GuiGeneric) {
                            String title = player.getOpenInventory().getTitle();
                            if (title.equals(titleBase)) {
                                removeTomb(player);
                                player.sendMessage(ChatColor.RED + "ReviveGui timeout");
                            }
                        }
                    }
                }
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), 20 * 30L);
    }

    public static boolean playerHasTomb(Player player) {
        return playerToTombLocation.containsKey(player);
    }

    public static Location getTombLocation(Player player) {
        return playerToTombLocation.get(player);
    }

    public static ArmorStand getTomb(Player player) {
        return playerToTomb.get(player);
    }

    public static void onReachToTomb(Player player) {
        if (playerToTomb.containsKey(player)) {
            removeTomb(player);
            player.setFlying(false);
            player.setAllowFlight(false);
            player.setGameMode(GameMode.ADVENTURE);
            player.sendMessage(ChatColor.GREEN + "You have reached your tomb in time!");
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
            player.removePotionEffect(PotionEffectType.GLOWING);
            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 120, 10));
            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 60, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 60, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 60, 1));
        }
    }

    public static void startSearch(Player player) {
        player.closeInventory();
        player.setAllowFlight(true);
        player.setFlying(true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1));
    }

    public static void onChunkLoad(Chunk chunk) {
        for (Player player : playerToTombLocation.keySet()) {
            Location location = playerToTombLocation.get(player);
            String chunkKey = SpawnerManager.getChunkKey(chunk.getBlock(0, 0, 0).getLocation());
            String chunkKey2 = SpawnerManager.getChunkKey(location.getChunk().getBlock(0, 0, 0).getLocation());
            if (chunkKey.equals(chunkKey2)) {
                createModel(player, location);
            }
        }
    }

    private static void createModel(Player player, Location location) {
        if (playerToTomb.containsKey(player)) {
            ArmorStand armorStand = playerToTomb.get(player);
            armorStand.remove();
        }
        ArmorStand tompModel = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

        ItemStack helm = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta itemMeta = helm.getItemMeta();
        if (itemMeta instanceof Damageable) {
            Damageable damageable = (Damageable) itemMeta;
            damageable.setDamage(1);
        }
        itemMeta.setUnbreakable(true);
        helm.setItemMeta(itemMeta);

        tompModel.setHelmet(helm);
        tompModel.setVisible(false);
        tompModel.setCustomName(org.bukkit.ChatColor.DARK_PURPLE + "< Tomb " + org.bukkit.ChatColor.LIGHT_PURPLE + player.getName() + org.bukkit.ChatColor.DARK_PURPLE + " >");
        tompModel.setCustomNameVisible(true);
        tompModel.setInvulnerable(true);
        tompModel.setGravity(false);
        tompModel.setGlowing(true);
        tompModel.setRemoveWhenFarAway(false);

        playerToTomb.put(player, tompModel);
    }
}
