package io.github.lix3nn53.guardiansofadelia.minigames;

import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.Dungeon;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MiniGameManager {

    private static final HashMap<String, Dungeon> codeToDungeon = new HashMap<>();
    private static final HashMap<Location, DungeonTheme> gateLocations = new HashMap<>();

    private static final HashMap<Player, Minigame> playerToMinigame = new HashMap<>();

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

    public static void onQuit(Player player) {
        if (playerToMinigame.containsKey(player)) {
            Minigame minigame = playerToMinigame.get(player);
            minigame.leave(player);
            playerToMinigame.remove(player);
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
}
