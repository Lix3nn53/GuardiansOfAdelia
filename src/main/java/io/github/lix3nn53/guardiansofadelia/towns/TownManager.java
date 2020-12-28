package io.github.lix3nn53.guardiansofadelia.towns;

import org.bukkit.Location;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class TownManager {

    private static final HashMap<Integer, Town> townNoToTown = new HashMap<>();

    public static void addTown(Town town, int townNo) {
        townNoToTown.put(townNo, town);
    }

    public static HashMap<Integer, Town> getTowns() {
        return townNoToTown;
    }

    public static Town getNearestTown(Location location) {
        Town nearestTown = Collections.min(townNoToTown.values(), Comparator.comparingDouble(town -> town.getDistanceSquared(location)));
        return nearestTown;
    }

    public static Town getTown(int i) {
        return townNoToTown.get(i);
    }
}
