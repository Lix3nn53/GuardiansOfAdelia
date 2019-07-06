package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.TempEntity;
import io.github.lix3nn53.guardiansofadelia.utilities.packetwrapper.WrapperPlayServerEntityDestroy;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ArrangementParticle;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.Direction;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ProjectileMechanic extends MechanicComponent {

    private LivingEntity caster;

    private final Class<? extends Projectile> projectileType;
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
    private Particle particle;
    private ArrangementParticle arrangement;
    private double radiusParticle;
    private int amountParticle;
    private Particle.DustOptions dustOptions;

    //Piercing

    //very custom things
    private boolean addCasterAsFirstTargetIfHitSuccess;
    private boolean addCasterAsSecondTargetIfHitFail;

    private String castKey;

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
                              Particle particle, ArrangementParticle arrangement, double radiusParticle,
                              int amountParticle, Particle.DustOptions dustOptions) {
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

        setParticle(particle, arrangement, radiusParticle, amountParticle, dustOptions);
    }

    /**
     * For rain type projectile mechanics
     */
    public ProjectileMechanic(SpreadType spreadType, double radius, double height, double speed,
                              int amount, double right, double upward, double forward,
                              double range, boolean mustHitToWork, Class<? extends Projectile> projectileType,
                              Particle particle, ArrangementParticle arrangement, double radiusParticle,
                              int amountParticle, Particle.DustOptions dustOptions) {
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

        setParticle(particle, arrangement, radiusParticle, amountParticle, dustOptions);
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

        setParticle(particle, arrangement, radiusParticle, amountParticle, dustOptions);
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {
        this.castKey = castKey;
        this.caster = caster;

        // Fire from each target
        ArrayList<Entity> projectiles = new ArrayList<Entity>();
        for (LivingEntity target : targets) {
            // Apply the spread type
            if (spreadType.equals(SpreadType.RAIN)) {
                //TODO test projectile rain
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
                        WrapperPlayServerEntityDestroy destroy = new WrapperPlayServerEntityDestroy();
                        destroy.setEntityIds(new int[]{p.getEntityId()});
                        try {
                            ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

                            List<Player> players = p.getWorld().getPlayers();
                            for (Player player : players) {
                                protocolManager.sendServerPacket(player, destroy.getHandle());
                            }
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }

                        startParticleAnimation(p);
                    }

                    if (projectileType != Arrow.class) {
                        p.teleport(target.getLocation().add(looking).add(0, upward + 0.5, 0).add(p.getVelocity()).setDirection(d));
                    }/* else if (piercing > 0) {
                        //TODO arrow does not pierce

                        NBTEntity ent = new NBTEntity(p);
                        ent.setByte("ShotFromCrossbow", (byte) 1);
                        ent.setByte("crit", (byte) 1);
                        ent.setString("SoundEvent", "minecraft:item.crossbow.hit");

                        GuardiansOfAdelia.getInstance().getLogger().info("piercing");
                        ((Arrow) p).setPierceLevel(piercing);
                    }*/

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
        ArrayList<LivingEntity> targets = new ArrayList<>();

        boolean hitSuccess = false;
        if (hit == null) {
            if (projectile.isValid()) projectile.remove();
            if (mustHitToWork) return;

            hit = new TempEntity(projectile.getLocation());
        } else if (addCasterAsFirstTargetIfHitSuccess) {
            hitSuccess = true;
            targets.add(caster);
        }

        if (hit instanceof LivingEntity) {
            targets.add((LivingEntity) hit);
        }

        if (!hitSuccess && addCasterAsSecondTargetIfHitFail) {
            targets.add(caster);
        }

        int skillLevel = 1;
        if (PersistentDataContainerUtil.hasInteger(projectile, "skillLevel")) {
            skillLevel = PersistentDataContainerUtil.getInteger(projectile, "skillLevel");
        }

        executeChildren((LivingEntity) projectile.getShooter(), skillLevel, targets, castKey);

        if (projectile instanceof Arrow) {
            if (((Arrow) projectile).getPierceLevel() > 0) return;
        }

        projectile.remove();
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        ArrayList<String> lore = new ArrayList<>();

        lore.add("Projectile amount: " + amount);

        //TODO
        return new ArrayList<>();
    }

    /*public void setPiercing(int piercing) {
        this.piercing = piercing;
    }*/

    public void setParticle(Particle particle, ArrangementParticle arrangement, double radiusParticle, int amountParticle, Particle.DustOptions dustOptions) {
        this.particle = particle;
        this.arrangement = arrangement;
        this.radiusParticle = radiusParticle;
        this.amountParticle = amountParticle;
        this.dustOptions = dustOptions;
    }

    private void startParticleAnimation(Projectile projectile) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (projectile.isValid()) {
                    Location location = projectile.getLocation();

                    ParticleUtil.play(location, particle, arrangement, radiusParticle, amountParticle, Direction.XZ, 0, 0, 0, 0, dustOptions);
                } else {
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(GuardiansOfAdelia.getInstance(), 1L, 3L);
    }

    public void setAddCasterAsFirstTargetIfHitSuccess(boolean addCasterAsFirstTargetIfHitSuccess) {
        this.addCasterAsFirstTargetIfHitSuccess = addCasterAsFirstTargetIfHitSuccess;
    }

    public void setAddCasterAsSecondTargetIfHitFail(boolean addCasterAsSecondTargetIfHitFail) {
        this.addCasterAsSecondTargetIfHitFail = addCasterAsSecondTargetIfHitFail;
    }
}
