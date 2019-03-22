package io.github.lix3nn53.guardiansofadelia.towns;

import org.bukkit.Location;

public class Town {

    private final Location location;
    private final String name;
    private final int no;

    public Town(String name, int no, Location location) {
        this.name = name;
        this.no = no;
        this.location = location;
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

    public int getNo() {
        return no;
    }
}
