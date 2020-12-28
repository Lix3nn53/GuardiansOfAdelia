package io.github.lix3nn53.guardiansofadelia.towns;

import org.bukkit.Location;

public class Town {

    private final Location location;
    private final String name;
    private final int level;

    public Town(String name, Location location, int level) {
        this.name = name;
        this.location = location;
        this.level = level;
    }

    public double getDistanceSquared(Location location) {
        return this.location.distanceSquared(location);
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }
}
