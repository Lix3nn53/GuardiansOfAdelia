package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class TargetHelper {

    public static List<LivingEntity> getLivingTargets(LivingEntity source, double range, double tolerance) {
        List<Entity> list = source.getNearbyEntities(range, range, range);
        List<LivingEntity> targets = new ArrayList<LivingEntity>();

        Vector facing = source.getLocation().getDirection();
        double fLengthSq = facing.lengthSquared();

        for (Entity entity : list) {

            if (!isInFront(source, entity) || !(entity instanceof LivingEntity))
                continue;
            Vector relative = entity.getLocation().subtract(source.getLocation()).toVector();
            double dot = relative.dot(facing);
            double rLengthSq = relative.lengthSquared();
            double cosSquared = dot * dot / rLengthSq * fLengthSq;
            double sinSquared = 1.0D - cosSquared;
            double dSquared = rLengthSq * sinSquared;


            if (dSquared < tolerance) targets.add((LivingEntity) entity);

        }
        return targets;
    }

    public static LivingEntity getLivingTarget(LivingEntity source, double range, double tolerance) {
        List<LivingEntity> targets = getLivingTargets(source, range, tolerance);
        if (targets.size() == 0) return null;
        LivingEntity target = targets.get(0);
        double minDistance = target.getLocation().distanceSquared(source.getLocation());
        for (LivingEntity entity : targets) {

            double distance = entity.getLocation().distanceSquared(source.getLocation());
            if (distance < minDistance) {

                minDistance = distance;
                target = entity;
            }
        }
        return target;
    }

    public static boolean isInFront(Entity entity, Entity target) {
        Vector facing = entity.getLocation().getDirection();
        Vector relative = target.getLocation().subtract(entity.getLocation()).toVector();


        return (facing.dot(relative) >= 0.0D);
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
}
