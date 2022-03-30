package io.github.lix3nn53.guardiansofadelia.utilities;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class LocationUtils {

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

        return availableLocations.get(GuardiansOfAdelia.RANDOM.nextInt(availableLocations.size()));
    }

    public static Location getBlockCenter(Location loc) {
        return loc.add(0.5, 0.5, 0.5);
    }

    /*
    Returns a random point of a sphere, evenly distributed over the sphere.
    The sphere is centered at (x0,y0,z0) with the passed in radius.
    The returned point is returned as a three element array [x,y,z].
    */
    public static Location randomSpherePoint(Location baseLocation, float radius) {
        float u = (float) Math.random();
        float v = (float) Math.random();
        float theta = 2 * (float) Math.PI * u;
        float phi = (float) Math.acos(2 * v - 1);
        float x1 = (radius * (float) Math.sin(phi) * (float) Math.cos(theta));
        float y1 = (radius * (float) Math.sin(phi) * (float) Math.sin(theta));
        float z1 = (radius * (float) Math.cos(phi));

        return baseLocation.clone().add(x1, y1, z1);
    }

    public static Location randomPointNoY(Location baseLocation, float radius) {
        float x1 = ((float) Math.random() * (radius * 2)) - radius;
        float z1 = ((float) Math.random() * (radius * 2)) - radius;

        return baseLocation.clone().add(x1, Math.random() + 0.8 - 2, z1);
    }

    public static String getChunkKey(Location location) {
        World world = location.getWorld();
        if (world == null) return null;
        return world.getName() + "|" + (location.getBlockX() >> 4) + "," + (location.getBlockZ() >> 4);
    }
}
