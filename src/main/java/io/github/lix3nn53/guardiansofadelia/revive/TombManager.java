package io.github.lix3nn53.guardiansofadelia.revive;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.creatures.spawners.SpawnerManager;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
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
import java.util.List;

public class TombManager {

    private static HashMap<Player, Tomb> playerToTomb = new HashMap<>();
    private static HashMap<String, List<Tomb>> chunkKeyToTomb = new HashMap<>();

    public static boolean hasTomb(Player player) {
        return playerToTomb.containsKey(player);
    }

    public static void onDeath(Player player, Location deathLocation) {
        Town town = TownManager.getNearestTown(deathLocation);
        player.teleport(town.getLocation());

        Tomb tomb = new Tomb(player, deathLocation);
        String chunkKey = SpawnerManager.getChunkKey(deathLocation);
        if (chunkKeyToTomb.containsKey(chunkKey)) {
            List<Tomb> tombs = chunkKeyToTomb.get(chunkKey);
            tombs.add(tomb);
            chunkKeyToTomb.put(chunkKey, tombs);
        } else {
            List<Tomb> tombs = new ArrayList<>();
            tombs.add(tomb);
            chunkKeyToTomb.put(chunkKey, tombs);
        }
        playerToTomb.put(player, tomb);

        openDeathGui(player);

        new BukkitRunnable() {

            int count = 0;
            int timeLimitIn10Seconds = 12;

            @Override
            public void run() {
                if (playerToTomb.containsKey(player)) {
                    if (count == timeLimitIn10Seconds) {
                        tomb.remove();
                        player.teleport(town.getLocation());
                        player.setGameMode(GameMode.ADVENTURE);
                        player.sendMessage(ChatColor.RED + "Tomb search timeout");
                        cancel();
                    } else {
                        player.sendMessage(ChatColor.AQUA.toString() + ((timeLimitIn10Seconds * 10) - (10 * count)) + " seconds left for your soul to give up");
                        count++;
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, 20 * 10L);
    }

    public static void onChunkLoad(String chunkKey) {
        if (chunkKeyToTomb.containsKey(chunkKey)) {
            List<Tomb> tombs = chunkKeyToTomb.get(chunkKey);
            for (Tomb tomb : tombs) {
                tomb.createModel();
            }
        }
    }

    public static void onTombRemove(Tomb tomb) {
        String chunkKey = SpawnerManager.getChunkKey(tomb.getBaseLocation());
        if (chunkKeyToTomb.containsKey(chunkKey)) {
            List<Tomb> tombs = chunkKeyToTomb.get(chunkKey);
            tombs.remove(tomb);
            if (tombs.isEmpty()) {
                chunkKeyToTomb.remove(chunkKey);
            } else {
                chunkKeyToTomb.put(chunkKey, tombs);
            }
        }
        playerToTomb.remove(tomb.getOwner());
    }

    private static void openDeathGui(Player player) {
        GuiGeneric reviveGui = new GuiGeneric(9, ChatColor.AQUA + "Revive Gui", 0);

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

        ItemStack soul = new ItemStack(Material.IRON_HOE);
        if (itemMeta instanceof Damageable) {
            Damageable damageable = (Damageable) itemMeta;
            damageable.setDamage(73);
        }
        itemMeta.setDisplayName(ChatColor.AQUA + "Search your tomb in soul mode");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Find your tomb and left");
            add(ChatColor.GRAY + "click near it to respawn");
            add(ChatColor.RED + "Time limit is 2 minutes after your death");
            add(ChatColor.GRAY + "You will respawn here if you cant");
            add(ChatColor.GRAY + "find your tomb in time");
        }});
        soul.setItemMeta(itemMeta);

        reviveGui.setItem(5, soul);

        reviveGui.openInventory(player);
    }

    public static void onReachToTomb(Player player) {
        if (playerToTomb.containsKey(player)) {
            Tomb tomb = playerToTomb.get(player);
            if (tomb.isNear()) {
                tomb.remove();
                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(ChatColor.GREEN + "You have reached your tomb in time!");
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 240, 20));
                player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 40, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 60, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 60, 1));
            }
        }
    }

    public static void startSearch(Player player) {
        if (playerToTomb.containsKey(player)) {
            player.closeInventory();
            player.setGameMode(GameMode.SPECTATOR);
        } else {
            player.sendMessage(ChatColor.RED + "You don't have a tomb");
        }
    }
}
