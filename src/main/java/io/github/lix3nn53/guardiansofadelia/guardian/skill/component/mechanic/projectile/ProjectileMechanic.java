package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.TempEntity;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ArrangementParticle;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.Direction;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleUtil;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.metadata.LazyMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

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
    private final boolean mustHitToWork;
    //Particle projectile
    Particle particle;

    private final Class<? extends Projectile> projectileType;
    ArrangementParticle arrangement;
    double radiusParticle;
    int amountParticle;
    private int piercing = 0;

    public ProjectileMechanic(SpreadType spreadType, double speed,
                              int amount, double angle, double right, double upward, double forward,
                              double range, boolean mustHitToWork, Class<? extends Projectile> projectileType) {
        this.spreadType = spreadType;
        this.speed = speed;
        this.amount = amount;
        this.angle = angle;
        this.right = right;
        this.upward = upward;
        this.forward = forward;
        this.range = range;
        this.mustHitToWork = mustHitToWork;
        this.projectileType = projectileType;

        this.radius = 0;
        this.height = 0;
    }

    public ProjectileMechanic(SpreadType spreadType, double speed,
                              int amount, double angle, double right, double upward, double forward,
                              double range, boolean mustHitToWork, Class<? extends Projectile> projectileType,
                              Particle particle, ArrangementParticle arrangement, double radiusParticle, int amountParticle) {
        this.spreadType = spreadType;
        this.speed = speed;
        this.amount = amount;
        this.angle = angle;
        this.right = right;
        this.upward = upward;
        this.forward = forward;
        this.range = range;
        this.mustHitToWork = mustHitToWork;
        this.projectileType = projectileType;

        this.radius = 0;
        this.height = 0;

        setParticle(particle, arrangement, radiusParticle, amountParticle);
    }

    /**
     * For rain type projectile mechanics
     *
     */
    public ProjectileMechanic(SpreadType spreadType, double radius, double height, double speed,
                              int amount, double right, double upward, double forward,
                              double range, boolean mustHitToWork, Class<? extends Projectile> projectileType,
                              Particle particle, ArrangementParticle arrangement, double radiusParticle, int amountParticle) {
        this.spreadType = spreadType;

        this.radius = radius;
        this.height = height;

        this.speed = speed;
        this.amount = amount;
        this.mustHitToWork = mustHitToWork;

        this.angle = 0;

        this.right = right;
        this.upward = upward;
        this.forward = forward;
        this.range = range;
        this.projectileType = projectileType;

    }

    /**
     * For rain type projectile mechanics
     */
    public ProjectileMechanic(SpreadType spreadType, double radius, double height, double speed,
                              int amount, double right, double upward, double forward,
                              double range, boolean mustHitToWork, Class<? extends Projectile> projectileType) {
        this.spreadType = spreadType;

        this.radius = radius;
        this.height = height;

        this.speed = speed;
        this.amount = amount;
        this.mustHitToWork = mustHitToWork;

        this.angle = 0;

        this.right = right;
        this.upward = upward;
        this.forward = forward;
        this.range = range;
        this.projectileType = projectileType;

        setParticle(particle, arrangement, radiusParticle, amountParticle);
    }

    public void onPierce() {
        this.piercing--;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {

        // Fire from each target
        ArrayList<Entity> projectiles = new ArrayList<Entity>();
        for (LivingEntity target : targets) {
            // Apply the spread type
            if (spreadType.equals(SpreadType.RAIN)) {
                ArrayList<Location> locs = ProjectileUtil.calcRain(target.getLocation(), radius, height, amount);

                for (Location loc : locs) {
                    Projectile p = caster.launchProjectile(projectileType);
                    p.setVelocity(new Vector(0, speed, 0));
                    p.teleport(loc);
                    projectiles.add(p);
                }
            } else {
                Vector dir = target.getLocation().getDirection();
                if (spreadType.equals(SpreadType.HORIZONTAL_CONE)) {
                    dir.setY(0);
                    dir.normalize();
                }

                Vector looking = target.getLocation().getDirection().setY(0).normalize();
                Vector normal = looking.clone().crossProduct(UP);
                looking.multiply(forward).add(normal.multiply(right));

                ArrayList<Vector> dirs = ProjectileUtil.calcSpread(dir, angle, amount);
                for (Vector d : dirs) {
                    Projectile p = caster.launchProjectile(projectileType);

                    if (particle != null) {
                        //TODO disguise console warn spam
                        MiscDisguise disguise = new MiscDisguise(DisguiseType.UNKNOWN);
                        DisguiseAPI.disguiseToAll(p, disguise);
                        startParticleAnimation(p);
                    }

                    if (projectileType != Arrow.class) {
                        p.teleport(target.getLocation().add(looking).add(0, upward + 0.5, 0).add(p.getVelocity()).setDirection(d));
                    } else if (piercing > 0) {
                        //TODO arrow does not pierce
                        Callable integerCallable = () -> piercing;
                        p.setMetadata("PierceLevel", new LazyMetadataValue(GuardiansOfAdelia.getInstance(), LazyMetadataValue.CacheStrategy.CACHE_AFTER_FIRST_EVAL, integerCallable));
                    }
                    p.setVelocity(d.multiply(speed));
                    projectiles.add(p);
                }
            }
        }
        ProjectileListener.onSkillProjectileShoot(projectiles, this, skillLevel, (int) Math.ceil(range / Math.abs(speed)));

        return targets.size() > 0;
    }

    /**
     * The callback for the projectiles that applies child components
     *
     * @param projectile projectile calling back for
     * @param hit        the entity hit by the projectile, if any
     */
    public void callback(Projectile projectile, Entity hit) {
        if (hit == null) {
            if (projectile.isValid()) projectile.remove();
            if (mustHitToWork) return;

            hit = new TempEntity(projectile.getLocation());
        }


        ArrayList<LivingEntity> targets = new ArrayList<>();
        if (hit instanceof LivingEntity) {
            targets.add((LivingEntity) hit);
        }

        int skillLevel = 1;
        if (PersistentDataContainerUtil.hasInteger(projectile, "skillLevel")) {
            skillLevel = PersistentDataContainerUtil.getInteger(projectile, "skillLevel");
        }

        executeChildren((LivingEntity) projectile.getShooter(), skillLevel, targets);

        if (getPiercing() <= 0) {
            projectile.remove();
        }
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        ArrayList<String> lore = new ArrayList<>();

        lore.add("Projectile amount: " + amount);

        //TODO
        return new ArrayList<>();
    }

    public void setPiercing(int piercing) {
        this.piercing = piercing;
    }

    public int getPiercing() {
        return piercing;
    }

    public void setParticle(Particle particle, ArrangementParticle arrangement, double radiusParticle, int amountParticle) {
        this.particle = particle;
        this.arrangement = arrangement;
        this.radiusParticle = radiusParticle;
        this.amountParticle = amountParticle;
    }

    private void startParticleAnimation(Projectile projectile) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (projectile.isValid()) {
                    Location location = projectile.getLocation();

                    ParticleUtil.play(location, particle, arrangement, radiusParticle, amountParticle, Direction.XZ);
                } else {
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(GuardiansOfAdelia.getInstance(), 1L, 3L);
    }
}
