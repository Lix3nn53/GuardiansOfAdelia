package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class TargetHelper {

    public static LivingEntity getLookingTarget(LivingEntity source, double range, double tolerance) {
        List<Entity> list = source.getNearbyEntities(range, range, range);
        if (list.isEmpty()) return null;

        Vector facing = source.getLocation().getDirection();
        double fLengthSq = facing.lengthSquared();

        LivingEntity target = null;
        double minDistance = 99999;
        for (Entity entity : list) {

            if (!isInFront(source, entity) || !(entity instanceof LivingEntity))
                continue;
            Vector relative = entity.getLocation().subtract(source.getLocation()).toVector();
            double dot = relative.dot(facing);
            double rLengthSq = relative.lengthSquared();
            double cosSquared = dot * dot / rLengthSq * fLengthSq;
            double sinSquared = 1.0D - cosSquared;
            double dSquared = rLengthSq * sinSquared;


            if (dSquared < tolerance) {
                // valid target
                double distance = entity.getLocation().distanceSquared(source.getLocation());
                if (distance < minDistance) {
                    minDistance = distance;
                    target = (LivingEntity) entity;
                }
            }

        }
        return target;
    }

    public static boolean isInFront(Entity source, Entity recipient) {
        // Get the necessary vectors
        Vector facing = source.getLocation().getDirection();
        Vector relative = recipient.getLocation().subtract(source.getLocation()).toVector();

        // If the dot product is positive, the recipient is in front
        return facing.dot(relative) >= 0;
    }

    public static Location getOpenLocation(Location loc1, Location loc2, boolean throughWall) {
        if (loc1.getX() == loc2.getX() && loc1.getY() == loc2.getY() && loc1.getZ() == loc2.getZ()) {
            return loc1;
        }


        Vector slope = loc2.clone().subtract(loc1).toVector();
        int steps = (int) (slope.length() * 4.0D) + 1;
        slope.multiply(1.0D / steps);


        if (throughWall) {

            Location temp = loc2.clone();
            while (temp.getBlock().getType().isSolid() && steps > 0) {

                temp.subtract(slope);
                steps--;
            }
            temp.setX(temp.getBlockX() + 0.5D);
            temp.setZ(temp.getBlockZ() + 0.5D);
            temp.setY((temp.getBlockY() + 1));
            return temp;
        }


        Location temp = loc1.clone();
        while (!temp.getBlock().getType().isSolid() && steps > 0) {

            temp.add(slope);
            steps--;
        }
        temp.subtract(slope);
        temp.setX(temp.getBlockX() + 0.5D);
        temp.setZ(temp.getBlockZ() + 0.5D);
        temp.setY((temp.getBlockY() + 1));
        return temp;
    }

    /**
     * Gets entities nearby a location using a given radius
     *
     * @param loc    location centered around
     * @param radius radius to get within
     * @return nearby entities
     */
    public static List<LivingEntity> getNearbySphere(Location loc, double radius) {
        List<LivingEntity> result = new ArrayList<>();

        int minX = (int) (loc.getX() - radius) >> 4;
        int maxX = (int) (loc.getX() + radius) >> 4;
        int minZ = (int) (loc.getZ() - radius) >> 4;
        int maxZ = (int) (loc.getZ() + radius) >> 4;

        double radiusSquare = radius * radius;

        for (int i = minX; i <= maxX; i++)
            for (int j = minZ; j <= maxZ; j++)
                for (Entity entity : loc.getWorld().getChunkAt(i, j).getEntities())
                    if (entity instanceof LivingEntity && entity.getLocation().distanceSquared(loc) < radiusSquare)
                        result.add((LivingEntity) entity);

        return result;
    }

    /**
     * Gets entities nearby a location using a given radius
     *
     * @param loc    location centered around
     * @param radius radius to get within
     * @return nearby entities
     */
    public static List<Entity> getNearbySphereNonLiving(Location loc, double radius) {
        List<Entity> result = new ArrayList<>();

        int minX = (int) (loc.getX() - radius) >> 4;
        int maxX = (int) (loc.getX() + radius) >> 4;
        int minZ = (int) (loc.getZ() - radius) >> 4;
        int maxZ = (int) (loc.getZ() + radius) >> 4;

        double radiusSquare = radius * radius;

        for (int i = minX; i <= maxX; i++)
            for (int j = minZ; j <= maxZ; j++)
                for (Entity entity : loc.getWorld().getChunkAt(i, j).getEntities())
                    if (entity.getLocation().distanceSquared(loc) < radiusSquare)
                        result.add(entity);

        return result;
    }

    public static List<LivingEntity> getBoxTargets(Location cubeCenter, Vector corner, Vector corner2, float yaw, float pitch) {
        List<LivingEntity> result = new ArrayList<>();

        World world = cubeCenter.getWorld();
        Vector center = cubeCenter.toVector();

        for (Entity entity : corner.toLocation(world).getChunk().getEntities()) {
            if (entity instanceof LivingEntity) {
                Location location = entity.getLocation();
                Vector vector = location.toVector();

                Vector subtract = vector.subtract(center);

            }
        }

        /*for (Entity entity : corner.toLocation().getWorld().getChunkAt(i, j).getEntities())
            if (entity instanceof LivingEntity && boxDistance(entity.getLocation(), loc) < radius)
                result.add((LivingEntity) entity);*/

        return result;
    }

    public static List<LivingEntity> getNearbyBox(Location loc, double radius) {
        List<LivingEntity> result = new ArrayList<>();

        int minX = (int) (loc.getX() - radius) >> 4;
        int maxX = (int) (loc.getX() + radius) >> 4;
        int minZ = (int) (loc.getZ() - radius) >> 4;
        int maxZ = (int) (loc.getZ() + radius) >> 4;

        for (int i = minX; i <= maxX; i++)
            for (int j = minZ; j <= maxZ; j++)
                for (Entity entity : loc.getWorld().getChunkAt(i, j).getEntities())
                    if (entity instanceof LivingEntity && boxDistance(entity.getLocation(), loc) < radius)
                        result.add((LivingEntity) entity);

        return result;
    }

    public static double boxDistance(Location loc1, Location loc2) {
        return Math.max(Math.max(Math.abs(loc1.getX() - loc2.getX()), Math.abs(loc1.getY() - loc2.getY())), Math.abs(loc1.getZ() - loc2.getZ()));
    }

    public static List<LivingEntity> getConeTargets(LivingEntity source, double arc, double range) {
        List<LivingEntity> targets = new ArrayList<LivingEntity>();
        List<Entity> list = source.getNearbyEntities(range, range, range);
        if (arc <= 0.0D) return targets;


        Vector dir = source.getLocation().getDirection();
        dir.setY(0);
        double cos = Math.cos(arc * Math.PI / 180.0D);
        double cosSq = cos * cos;


        for (Entity entity : list) {

            if (entity instanceof LivingEntity) {


                if (arc >= 360.0D) {

                    targets.add((LivingEntity) entity);


                    continue;
                }

                Vector relative = entity.getLocation().subtract(source.getLocation()).toVector();
                relative.setY(0);
                double dot = relative.dot(dir);
                double value = dot * dot / relative.lengthSquared();
                if (arc < 180.0D && dot > 0.0D && value >= cosSq) {
                    targets.add((LivingEntity) entity);
                    continue;
                }
                if (arc >= 180.0D && (dot > 0.0D || dot <= cosSq)) targets.add((LivingEntity) entity);

            }
        }

        return targets;
    }
}
