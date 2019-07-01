package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

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
}
