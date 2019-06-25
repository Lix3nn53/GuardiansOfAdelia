package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.EntityUtils;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ProjectileMechanic extends MechanicComponent {

    private final Vector UP = new Vector(0, 1, 0);

    private final SpreadType spreadType;
    private final double radius;
    private final double height;
    private final double speed;
    private final int amount;
    private final double angle;
    private final double right;
    private final double upward;
    private final double forward;
    private final double range;

    private final Class<? extends Projectile> projectileType;

    public ProjectileMechanic(SpreadType spreadType, double speed,
                              int amount, double angle, double right, double upward, double forward,
                              double range, Class<? extends Projectile> projectileType) {
        this.spreadType = spreadType;
        this.speed = speed;
        this.amount = amount;
        this.angle = angle;
        this.right = right;
        this.upward = upward;
        this.forward = forward;
        this.range = range;
        this.projectileType = projectileType;

        this.radius = 0;
        this.height = 0;
    }

    public ProjectileMechanic(SpreadType spreadType, double radius, double height, double speed,
                              int amount, double right, double upward, double forward,
                              double range, Class<? extends Projectile> projectileType) {
        this.spreadType = spreadType;

        this.radius = radius;
        this.height = height;

        this.speed = speed;
        this.amount = amount;

        this.angle = 0;

        this.right = right;
        this.upward = upward;
        this.forward = forward;
        this.range = range;
        this.projectileType = projectileType;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {

        // Fire from each target
        ArrayList<Entity> projectiles = new ArrayList<Entity>();
        for (LivingEntity target : targets)
        {
            // Apply the spread type
            if (spreadType.equals(SpreadType.RAIN)) {
                ArrayList<Location> locs = ProjectileUtil.calcRain(target.getLocation(), radius, height, amount);

                for (Location loc : locs)
                {
                    Projectile p = caster.launchProjectile(projectileType);
                    p.setVelocity(new Vector(0, speed, 0));
                    p.teleport(loc);
                    projectiles.add(p);
                }
            }
            else
            {
                Vector dir = target.getLocation().getDirection();
                if (spreadType.equals(SpreadType.HORIZONTAL_CONE))
                {
                    dir.setY(0);
                    dir.normalize();
                }

                Vector looking = target.getLocation().getDirection().setY(0).normalize();
                Vector normal = looking.clone().crossProduct(UP);
                looking.multiply(forward).add(normal.multiply(right));

                ArrayList<Vector> dirs = ProjectileUtil.calcSpread(dir, angle, amount);
                for (Vector d : dirs)
                {
                    Projectile p = caster.launchProjectile(projectileType);
                    if (projectileType != Arrow.class)
                    {
                        p.teleport(target.getLocation().add(looking).add(0, upward + 0.5, 0).add(p.getVelocity()).setDirection(d));
                    }
                    p.setVelocity(d.multiply(speed));
                    projectiles.add(p);
                }
            }
        }
        EntityUtils.delayedRemove(projectiles, (int) Math.ceil(range / Math.abs(speed)));

        return targets.size() > 0;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        //TODO
        return null;
    }
}
