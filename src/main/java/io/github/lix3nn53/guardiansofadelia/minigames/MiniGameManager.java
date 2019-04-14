package io.github.lix3nn53.guardiansofadelia.minigames;

import io.github.lix3nn53.guardiansofadelia.minigames.arenas.LastOneStanding;
import io.github.lix3nn53.guardiansofadelia.minigames.arenas.WinByMostKills;
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
    private static final List<WinByMostKills> winByMostKillsList = new ArrayList<>();

    private static final HashMap<String, Dungeon> codeToDungeon = new HashMap<>();
    private static final HashMap<Location, DungeonTheme> gateLocations = new HashMap<>();

    private static final HashMap<Player, Minigame> playerToMinigame = new HashMap<>();

    public static void initMinigames() {
        //Last One Standing
        List<Location> lastOneStandingStartLocations1 = new ArrayList<>();
        lastOneStandingStartLocations1.add(new Location(Bukkit.getWorld("arena"), -22.5, 68.5, 181.5, -80, -2));
        lastOneStandingStartLocations1.add(new Location(Bukkit.getWorld("arena"), 60.5, 68.5, 196.5, 100, -2));
        LastOneStanding lastOneStandingRoom1 = new LastOneStanding("Royal Duel", 1, 3, 1, lastOneStandingStartLocations1, 1, 2, 2, 2);
        lastOneStandingList.add(lastOneStandingRoom1);

        List<Location> lastOneStandingStartLocations2 = new ArrayList<>();
        lastOneStandingStartLocations2.add(new Location(Bukkit.getWorld("arena"), -22.5, 68.5, 303.5, -80, -2));
        lastOneStandingStartLocations2.add(new Location(Bukkit.getWorld("arena"), 60.5, 68.5, 318.5, 100, -2));
        LastOneStanding lastOneStandingRoom2 = new LastOneStanding("Royal Duel", 1, 3, 2, lastOneStandingStartLocations2, 1, 2, 2, 2);
        lastOneStandingList.add(lastOneStandingRoom2);

        List<Location> lastOneStandingStartLocations3 = new ArrayList<>();
        lastOneStandingStartLocations3.add(new Location(Bukkit.getWorld("arena"), 80.5, 90.5, 577.5, -164, -4));
        lastOneStandingStartLocations3.add(new Location(Bukkit.getWorld("arena"), -15.5, 87.5, 445.5, -33, -4));
        LastOneStanding lastOneStandingRoom3 = new LastOneStanding("Conquer the village", 1, 3, 3, lastOneStandingStartLocations3, 2, 2, 2, 4);
        lastOneStandingList.add(lastOneStandingRoom3);

        List<Location> lastOneStandingStartLocations4 = new ArrayList<>();
        lastOneStandingStartLocations4.add(new Location(Bukkit.getWorld("arena"), 91.5, 90.5, 808.5, -164, -4));
        lastOneStandingStartLocations4.add(new Location(Bukkit.getWorld("arena"), -5.5, 87.5, 676.5, -33, -4));
        LastOneStanding lastOneStandingRoom4 = new LastOneStanding("Conquer the village", 1, 3, 4, lastOneStandingStartLocations4, 2, 2, 2, 4);
        lastOneStandingList.add(lastOneStandingRoom4);

        //Win By Most Kills
        List<Location> winByMostKillsStartLocations1 = new ArrayList<>();
        winByMostKillsStartLocations1.add(new Location(Bukkit.getWorld("arena"), -71.5, 145.5, -305.5, -135, -2));
        winByMostKillsStartLocations1.add(new Location(Bukkit.getWorld("arena"), 56.5, 145.5, -305.5, 135, -2));
        winByMostKillsStartLocations1.add(new Location(Bukkit.getWorld("arena"), 56.5, 145.5, -433.5, 45, -2));
        WinByMostKills winByMostKillsRoom1 = new WinByMostKills("Space Jump", 1, 1, 1, winByMostKillsStartLocations1, 1, 3,3);
        winByMostKillsList.add(winByMostKillsRoom1);

        List<Location> winByMostKillsStartLocations2 = new ArrayList<>();
        winByMostKillsStartLocations2.add(new Location(Bukkit.getWorld("arena"), -60.5, 141.5, 22.5, -135, -2));
        winByMostKillsStartLocations2.add(new Location(Bukkit.getWorld("arena"), 67.5, 141.5, 22.5, 135, -2));
        winByMostKillsStartLocations2.add(new Location(Bukkit.getWorld("arena"), 67.5, 141.5, -105.5, 48, -2));
        WinByMostKills winByMostKillsRoom2 = new WinByMostKills("Space Jump", 1, 1, 2, winByMostKillsStartLocations2, 1, 3,3);
        winByMostKillsList.add(winByMostKillsRoom2);
    }

    public static LastOneStanding getLastOneStanding(int roomNo) {
        return lastOneStandingList.get(roomNo - 1);
    }

    public static WinByMostKills getWinByMostKills(int roomNo) {
        return winByMostKillsList.get(roomNo - 1);
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

    public static void onPlayerDeath(Player player) {
        if (playerToMinigame.containsKey(player)) {
            Minigame minigame = playerToMinigame.get(player);
            minigame.onPlayerDeath(player);
        }
    }

    public static void onPlayerKill(Player killer) {
        if (playerToMinigame.containsKey(killer)) {
            Minigame minigame = playerToMinigame.get(killer);
            minigame.addScore(killer, 1);
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

    public static GuiGeneric getWinByMostKillsJoinGui() {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatColor.GOLD + "Join Win By Most Kills", 0);

        int i = 9;
        for (WinByMostKills winByMostKills : winByMostKillsList) {
            ItemStack room = new ItemStack(Material.LIME_WOOL);
            ItemMeta itemMeta = room.getItemMeta();
            itemMeta.setDisplayName(ChatColor.GREEN + winByMostKills.getMinigameName() + " (" + winByMostKills.getPlayersInGameSize() + "/" + winByMostKills.getMaxPlayerSize() + ")");
            itemMeta.setLore(new ArrayList() {{
                add("");
                add(ChatColor.GREEN + "Map: " + ChatColor.WHITE + winByMostKills.getMapName());
                add(ChatColor.YELLOW + "Level req: " + ChatColor.WHITE + winByMostKills.getLevelReq());
                add(ChatColor.GOLD + "Team amount: " + ChatColor.WHITE + winByMostKills.getTeamAmount());
                add(ChatColor.GOLD + "Team size: " + ChatColor.WHITE + winByMostKills.getTeamSize());
                add(ChatColor.LIGHT_PURPLE + "Game time: " + ChatColor.WHITE + winByMostKills.getTimeLimitInMinutes() + " minute(s)");
                add("");
                add(ChatColor.GRAY + "Click to join this room!");
            }});
            room.setItemMeta(itemMeta);
            if (winByMostKills.isInGame()) {
                room.setType(Material.RED_WOOL);
            }
            room.setItemMeta(itemMeta);
            guiGeneric.setItem(i, room);
            i += 2;
        }
        return guiGeneric;
    }
}
