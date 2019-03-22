package io.github.lix3nn53.guardiansofadelia.towns;

import org.bukkit.Location;

import java.util.*;

public class TownManager {

    private final List<Town> towns = new ArrayList<>();

    public void addTown(Town town) {
        towns.add(town);
    }

    public List<Town> getTowns() {
        return towns;
    }

    public Town getNearestTown(Location location) {
        Town nearestTown = Collections.min(towns, Comparator.comparingDouble(town -> town.getDistanceSquared(location)));
        return nearestTown;
    }

    public Town getTown(int i) {
        Optional<Town> optionalTown = this.towns.stream()
                .filter(item -> item.getNo() == i)
                .findAny();
        return optionalTown.orElse(null);
    }
}
