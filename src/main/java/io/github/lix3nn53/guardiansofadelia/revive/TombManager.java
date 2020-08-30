package io.github.lix3nn53.guardiansofadelia.revive;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.creatures.spawners.SpawnerManager;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TombManager {

    private static final HashMap<Player, Tomb> playerToTomb = new HashMap<>();
    private static final HashMap<String, List<Tomb>> chunkKeyToTomb = new HashMap<>();
    private static final List<Player> playersInTombCooldown = new ArrayList<>();

    public static boolean hasTomb(Player player) {
        return playerToTomb.containsKey(player);
    }

    public static void onDeath(Player player, Location deathLocation) {
        Town town = TownManager.getNearestTown(deathLocation);
        player.teleport(town.getLocation());

        if (playersInTombCooldown.contains(player)) {
            player.sendMessage(ChatColor.RED + "You can't search for your tomb again. Your soul needs 5 minutes between deaths.");
            return;
        }

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
        playersInTombCooldown.add(player);

        openDeathGui(player);

        new BukkitRunnable() {

            int count = 0;
            final int timeLimitIn10Seconds = 12;

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

        //remove from playersInTombCooldown after 5 minutes
        new BukkitRunnable() {

            @Override
            public void run() {
                playersInTombCooldown.remove(player);
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), 20 * 300L);
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
        itemMeta.setCustomModelData(18);
        itemMeta.setDisplayName(ChatColor.GREEN + "Respawn");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "You have respawn in nearest town");
        lore.add(ChatColor.GRAY + "to your death location.");
        lore.add(ChatColor.GRAY + "Close your inventory to continue.");
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        respawn.setItemMeta(itemMeta);

        reviveGui.setItem(3, respawn);

        ItemStack soul = new ItemStack(Material.IRON_HOE);
        itemMeta = soul.getItemMeta();
        itemMeta.setCustomModelData(10);
        itemMeta.setDisplayName(ChatColor.AQUA + "Search your tomb in soul mode");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "Find your tomb and left");
        lore.add(ChatColor.GRAY + "click near it to respawn");
        lore.add(ChatColor.RED + "Time limit is 2 minutes after your death");
        lore.add(ChatColor.GRAY + "You will respawn here if you cant");
        lore.add(ChatColor.GRAY + "find your tomb in time");
        itemMeta.setLore(lore);
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

    public static void cancelSearch(Player player) {
        if (player.getGameMode().equals(GameMode.ADVENTURE)) {
            if (playerToTomb.containsKey(player)) {
                Tomb tomb = playerToTomb.get(player);
                tomb.remove();
                player.sendMessage(ChatColor.GREEN + "Spawned in nearest town.");
            }
        }
    }

    public static void startSearch(Player player) {
        if (playerToTomb.containsKey(player)) {
            player.setGameMode(GameMode.SPECTATOR);
        } else {
            player.sendMessage(ChatColor.RED + "You don't have a tomb");
        }
    }
}
