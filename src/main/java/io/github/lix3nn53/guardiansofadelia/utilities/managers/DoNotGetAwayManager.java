package io.github.lix3nn53.guardiansofadelia.utilities.managers;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class DoNotGetAwayManager {

    private static final HashMap<Player, DoNotGetAwayData> playerToData = new HashMap<>();

    public static void addPlayer(Player player, Location center, float distance, String onLeave) {
        playerToData.put(player, new DoNotGetAwayData(center, distance, onLeave));
    }

    public static void removePlayer(Player player) {
        playerToData.remove(player);
    }

    public static void onMove(Player player, Location to) {
        if (playerToData.containsKey(player)) {
            DoNotGetAwayData doNotGetAwayData = playerToData.get(player);

            float distance = doNotGetAwayData.distance;
            if (doNotGetAwayData.center.distanceSquared(to) > distance * distance) {
                player.teleport(doNotGetAwayData.center);
            }
        }
    }

    private static class DoNotGetAwayData {
        public final Location center;
        public final float distance;
        public final String onLeave;

        private DoNotGetAwayData(Location center, float distance, String onLeave) {
            this.center = center;
            this.distance = distance;
            this.onLeave = onLeave;
        }
    }
}
