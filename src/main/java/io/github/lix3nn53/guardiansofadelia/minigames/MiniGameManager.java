package io.github.lix3nn53.guardiansofadelia.minigames;

import io.github.lix3nn53.guardiansofadelia.minigames.arenas.LastOneStanding;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.Dungeon;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MiniGameManager {

    private static final List<LastOneStanding> lastOneStandingList = new ArrayList<>();

    private static final HashMap<String, Dungeon> codeToDungeon = new HashMap<>();
    private static final HashMap<Location, DungeonTheme> gateLocations = new HashMap<>();

    private static final HashMap<Player, Minigame> playerToMinigame = new HashMap<>();

    public static void initMinigames() {
        List<Location> startLocations = new ArrayList<>();
        startLocations.add(new Location(Bukkit.getWorld("arena"), -71.5, 145.5, -305.5, -135, -2));
        startLocations.add(new Location(Bukkit.getWorld("arena"), 56.5, 145.5, -305.5, 135, -2));
        startLocations.add(new Location(Bukkit.getWorld("arena"), 56.5, 145.5, -433.5, 45, -2));
        LastOneStanding room = new LastOneStanding("Space Jump", 1, 5, 1, startLocations, 1, 3, 2, 3);
        lastOneStandingList.add(room);

        List<Location> startLocations2 = new ArrayList<>();
        startLocations2.add(new Location(Bukkit.getWorld("arena"), -60.5, 141.5, 22.5, -135, -2));
        startLocations2.add(new Location(Bukkit.getWorld("arena"), 67.5, 141.5, 22.5, 135, -2));
        startLocations2.add(new Location(Bukkit.getWorld("arena"), 67.5, 141.5, -105.5, 48, -2));
        LastOneStanding room2 = new LastOneStanding("Space Jump", 1, 5, 2, startLocations2, 1, 3, 2, 3);
        lastOneStandingList.add(room2);

        List<Location> startLocations3 = new ArrayList<>();
        startLocations3.add(new Location(Bukkit.getWorld("arena"), -22.5, 68.5, 181.5, -80, -2));
        startLocations3.add(new Location(Bukkit.getWorld("arena"), 60.5, 68.5, 196.5, 100, -2));
        LastOneStanding room3 = new LastOneStanding("Royal Duel", 1, 5, 3, startLocations3, 1, 2, 2, 2);
        lastOneStandingList.add(room3);

        List<Location> startLocations4 = new ArrayList<>();
        startLocations4.add(new Location(Bukkit.getWorld("arena"), -22.5, 68.5, 303.5, -80, -2));
        startLocations4.add(new Location(Bukkit.getWorld("arena"), 60.5, 68.5, 318.5, 100, -2));
        LastOneStanding room4 = new LastOneStanding("Royal Duel", 1, 5, 4, startLocations4, 1, 2, 2, 2);
        lastOneStandingList.add(room4);

        List<Location> startLocations5 = new ArrayList<>();
        startLocations5.add(new Location(Bukkit.getWorld("arena"), 80.5, 90.5, 577.5, -164, -4));
        startLocations5.add(new Location(Bukkit.getWorld("arena"), -15.5, 87.5, 445.5, -33, -4));
        LastOneStanding room5 = new LastOneStanding("Conquer the village", 1, 5, 5, startLocations5, 2, 2, 2, 4);
        lastOneStandingList.add(room5);

        List<Location> startLocations6 = new ArrayList<>();
        startLocations6.add(new Location(Bukkit.getWorld("arena"), 91.5, 90.5, 808.5, -164, -4));
        startLocations6.add(new Location(Bukkit.getWorld("arena"), -5.5, 87.5, 676.5, -33, -4));
        LastOneStanding room6 = new LastOneStanding("Conquer the village", 1, 5, 6, startLocations6, 2, 2, 2, 4);
        lastOneStandingList.add(room6);
    }

    public static LastOneStanding getLastOneStanding(int roomNo) {
        return lastOneStandingList.get(roomNo - 1);
    }

    public static void addDungeon(DungeonTheme dungeonTheme, int roomNo, Dungeon dungeon) {
        codeToDungeon.put(dungeonTheme.toString() + roomNo, dungeon);
    }

    public static Dungeon getDungeon(DungeonTheme dungeonTheme, int roomNo) {
        return codeToDungeon.get(dungeonTheme.toString() + roomNo);
    }

    public static Dungeon getDungeon(String code) {
        return codeToDungeon.get(code);
    }

    public static boolean dungeonExists(DungeonTheme dungeonTheme, int roomNo) {
        return codeToDungeon.containsKey(dungeonTheme.toString() + roomNo);
    }

    public static Set<String> getDungeons() {
        return codeToDungeon.keySet();
    }

    public static DungeonTheme getDungeonFromGate(Location location) {
        for (Location gateLocation : gateLocations.keySet()) {
            if (gateLocation.distanceSquared(location) < 16D) {
                return gateLocations.get(gateLocation);
            }
        }
        return null;
    }

    public static void addGate(Location location, DungeonTheme dungeonTheme) {
        gateLocations.put(location, dungeonTheme);
    }

    public static Set<Location> getDungeonGates() {
        return gateLocations.keySet();
    }

    public static boolean isInMinigame(Player player) {
        return playerToMinigame.containsKey(player);
    }

    public static void addPlayer(Player player, Minigame minigame) {
        playerToMinigame.put(player, minigame);
    }

    public static void removePlayer(Player player) {
        playerToMinigame.remove(player);
    }

    public static void onPlayerDeath(Player player, Location deathLocation) {
        if (playerToMinigame.containsKey(player)) {
            Minigame minigame = playerToMinigame.get(player);
            minigame.onPlayerDeath(player, deathLocation);
        }
    }

    public static void onQuit(Player player) {
        if (playerToMinigame.containsKey(player)) {
            Minigame minigame = playerToMinigame.get(player);
            minigame.leave(player);
        }
    }

    public static void onMobKillDungeon(Player player, LivingEntity livingEntity) {
        if (playerToMinigame.containsKey(player)) {
            if (playerToMinigame.get(player) instanceof Dungeon) {
                Dungeon dungeon = (Dungeon) playerToMinigame.get(player);
                String mobName = livingEntity.getCustomName();
                dungeon.onBossKill(mobName);
            }
        }
    }

    public static GuiGeneric getLastOneStandingJoinGui() {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatColor.GOLD + "Join Last One Standing", 0);

        int i = 9;
        for (LastOneStanding lastOneStanding : lastOneStandingList) {
            ItemStack room = new ItemStack(Material.LIME_WOOL);
            ItemMeta itemMeta = room.getItemMeta();
            itemMeta.setDisplayName(ChatColor.GREEN + lastOneStanding.getMinigameName() + " (" + lastOneStanding.getPlayersInGameSize() + "/" + lastOneStanding.getMaxPlayerSize() + ")");
            itemMeta.setLore(new ArrayList() {{
                add("");
                add(ChatColor.GREEN + "Map: " + ChatColor.WHITE + lastOneStanding.getMapName());
                add(ChatColor.YELLOW + "Level req: " + ChatColor.WHITE + lastOneStanding.getLevelReq());
                add(ChatColor.GOLD + "Team amount: " + ChatColor.WHITE + lastOneStanding.getTeamAmount());
                add(ChatColor.GOLD + "Team size: " + ChatColor.WHITE + lastOneStanding.getTeamSize());
                add(ChatColor.LIGHT_PURPLE + "Game time: " + ChatColor.WHITE + lastOneStanding.getTimeLimitInMinutes() + " minute(s)");
                add("");
                add(ChatColor.GRAY + "Click to join this room!");
            }});
            room.setItemMeta(itemMeta);
            if (lastOneStanding.isInGame()) {
                room.setType(Material.RED_WOOL);
            }
            room.setItemMeta(itemMeta);
            guiGeneric.setItem(i, room);
            i += 2;
        }
        return guiGeneric;
    }
}
