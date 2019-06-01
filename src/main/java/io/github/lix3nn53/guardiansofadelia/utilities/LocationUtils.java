package io.github.lix3nn53.guardiansofadelia.utilities;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LocationUtils {

    private static final Random RANDOM = new Random();

    public static Location getRandomSafeLocationNearPoint(Location location, int radius) {
        Block playerBlock = location.getBlock();
        List<Location> availableLocations = new ArrayList<>();

        World world = location.getWorld();
        for (int x = playerBlock.getX() - radius; x < playerBlock.getX() + radius; x++) {
            for (int y = playerBlock.getY() - radius; y < playerBlock.getY() + radius; y++) {
                for (int z = playerBlock.getZ() - radius; z < playerBlock.getZ() + radius; z++) {
                    Location loc = getBlockCenter(new Location(world, x, y, z));
                    if (loc.getBlock().isEmpty()) {
                        Block underBlock = loc.clone().subtract(0, 1, 0).getBlock();
                        if (!underBlock.isEmpty() && !underBlock.isLiquid()) {
                            availableLocations.add(loc);
                        }
                    }
                }
            }
        }

        if (availableLocations.size() == 0) {
            return getBlockCenter(playerBlock.getLocation().clone());
        }

        return availableLocations.get(LocationUtils.RANDOM.nextInt(availableLocations.size()));
    }

    public static Location getBlockCenter(Location loc) {
        return loc.add(0.5, 0, 0.5);
    }

    /*
    Returns a random point of a sphere, evenly distributed over the sphere.
    The sphere is centered at (x0,y0,z0) with the passed in radius.
    The returned point is returned as a three element array [x,y,z].
    */
    public static Location randomSpherePoint(Location baseLocation, double radius) {
        double u = Math.random();
        double v = Math.random();
        double theta = 2 * Math.PI * u;
        double phi = Math.acos(2 * v - 1);
        double x1 = (radius * Math.sin(phi) * Math.cos(theta));
        double y1 = (radius * Math.sin(phi) * Math.sin(theta));
        double z1 = (radius * Math.cos(phi));

        return baseLocation.clone().add(x1, y1, z1);
    }

    public static Location randomPointNoY(Location baseLocation, double radius) {
        double x1 = (Math.random() * (radius * 2)) - radius;
        double z1 = (Math.random() * (radius * 2)) - radius;

        return baseLocation.clone().add(x1, Math.random() + 0.8 - 4, z1);
    }

}
