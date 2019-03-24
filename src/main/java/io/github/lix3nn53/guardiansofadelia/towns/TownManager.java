package io.github.lix3nn53.guardiansofadelia.towns;

import org.bukkit.Location;

import java.util.*;

public class TownManager {

    private static final List<Town> towns = new ArrayList<>();

    public static void addTown(Town town) {
        towns.add(town);
    }

    public static List<Town> getTowns() {
        return towns;
    }

    public static Town getNearestTown(Location location) {
        Town nearestTown = Collections.min(towns, Comparator.comparingDouble(town -> town.getDistanceSquared(location)));
        return nearestTown;
    }

    public static Town getTown(int i) {
        Optional<Town> optionalTown = towns.stream()
                .filter(item -> item.getNo() == i)
                .findAny();
        return optionalTown.orElse(null);
    }
}
