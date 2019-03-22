package io.github.lix3nn53.guardiansofadelia.utilities;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LocationUtils {

    public static Location getRandomSafeLocationNearPoint(Location paramLocation, int range) {
        Block block = paramLocation.getBlock();
        List<Location> localArrayList = new ArrayList<>();

        World world = paramLocation.getWorld();
        for (int i = block.getX() - range; i < block.getX() + range; i++) {
            for (int j = block.getY() - range; j < block.getY() + range; j++) {
                for (int k = block.getZ() - range; k < block.getZ() + range; k++) {
                    Location newLocation = new Location(world, i, j, k).getBlock().getLocation();
                    if (newLocation.getBlock().isPassable()) {
                        Block newLocationDownBlock = newLocation.clone().subtract(0.0D, 1.0D, 0.0D).getBlock();
                        if (!newLocationDownBlock.isEmpty() && !newLocationDownBlock.isLiquid()) {
                            newLocation.setYaw(-180.0F + new Random().nextFloat() * 360.0F);
                            localArrayList.add(newLocation);
                        }
                    }
                }
            }
        }
        if (localArrayList.size() == 0) {
            return block.getLocation().clone();
        }
        return localArrayList.get(new Random().nextInt(localArrayList.size()));
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

        return baseLocation.clone().add(x1, Math.random() + 0.8, z1);
    }

}
