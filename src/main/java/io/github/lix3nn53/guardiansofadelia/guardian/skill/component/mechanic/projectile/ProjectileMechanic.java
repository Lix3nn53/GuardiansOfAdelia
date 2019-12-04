package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.ProjectileRepeatProtector;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.TemporaryEntity;
import io.github.lix3nn53.guardiansofadelia.utilities.packetwrapper.WrapperPlayServerEntityDestroy;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ArrangementParticle;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.Direction;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleUtil;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProjectileMechanic extends MechanicComponent {

    private final Class<? extends Projectile> projectileType;
    private final SpreadType spreadType;
    private final double radius;
    private final double height;
    private final double speed;
    private final List<Integer> amount;
    private final double angle;
    private final double right;
    private final double upward;
    private final double forward;
    private final double range;
    private final boolean mustHitToWork;
    private LivingEntity caster;
    //Particle projectile
    private Particle particle;
    private ArrangementParticle arrangement;
    private double radiusParticle;
    private int amountParticle;
    private Particle.DustOptions dustOptions;
    private boolean isProjectileInvisible = true;

    //Piercing

    //very custom things
    private boolean addCasterAsFirstTargetIfHitSuccess;
    private boolean addCasterAsSecondTargetIfHitFail;

    /**
     * For spread types Cone and Horizontal_Cone, normal projectile
     */
    public ProjectileMechanic(SpreadType spreadType, double speed,
                              List<Integer> amount, double angle, double right, double upward, double forward,
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

    /**
     * For spread types Cone and Horizontal_Cone, particle projectile
     */
    public ProjectileMechanic(SpreadType spreadType, double speed,
                              List<Integer> amount, double angle, double right, double upward, double forward,
                              double range, boolean mustHitToWork, Class<? extends Projectile> projectileType,
                              Particle particle, ArrangementParticle arrangement, double radiusParticle,
                              int amountParticle, Particle.DustOptions dustOptions, boolean isProjectileInvisible) {
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
        this.isProjectileInvisible = isProjectileInvisible;

        this.radius = 0;
        this.height = 0;

        setParticle(particle, arrangement, radiusParticle, amountParticle, dustOptions);
    }

    /**
     * For spread type Rain, normal projectile
     */
    public ProjectileMechanic(SpreadType spreadType, double radius, double height, double speed,
                              List<Integer> amount, double right, double upward, double forward,
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
    }

    /**
     * For spread type Rain, particle projectile
     */
    public ProjectileMechanic(SpreadType spreadType, double radius, double height, double speed,
                              List<Integer> amount, double right, double upward, double forward,
                              double range, boolean mustHitToWork, Class<? extends Projectile> projectileType,
                              Particle particle, ArrangementParticle arrangement, double radiusParticle,
                              int amountParticle, Particle.DustOptions dustOptions, boolean isProjectileInvisible) {
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
        this.isProjectileInvisible = isProjectileInvisible;

        setParticle(particle, arrangement, radiusParticle, amountParticle, dustOptions);
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
        if (targets.isEmpty()) return false;

        this.caster = caster;
        UUID skillKey = UUID.randomUUID(); //skill key to put into projectile

        // Fire from each target
        ArrayList<Entity> projectiles = new ArrayList<Entity>();
        for (LivingEntity target : targets) {
            // Apply the spread type
            if (spreadType.equals(SpreadType.RAIN)) {
                ArrayList<Location> locs = ProjectileUtil.calcRain(target.getLocation(), radius, height, amount.get(skillLevel - 1));

                for (Location loc : locs) {
                    Projectile p = caster.launchProjectile(Arrow.class);

                    PersistentDataContainerUtil.putString("skillCastKey", skillKey.toString(), p); //put skill key

                    //Disguise projectile since only Arrow works with Rain type
                    if (!this.isProjectileInvisible) {
                        MiscDisguise disguise = null;
                        if (projectileType == Fireball.class) {
                            disguise = new MiscDisguise(DisguiseType.FIREBALL);
                        } else if (projectileType == SmallFireball.class) {
                            disguise = new MiscDisguise(DisguiseType.SMALL_FIREBALL);
                        } else if (projectileType == Egg.class) {
                            disguise = new MiscDisguise(DisguiseType.EGG);
                        }
                        if (disguise != null) {
                            DisguiseAPI.disguiseToAll(p, disguise);
                        }
                    }

                    p.setVelocity(new Vector(0, -speed, 0));
                    p.teleport(loc);
                    projectiles.add(p);

                    changeToParticleProjectile(p);
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

                ArrayList<Vector> dirs = ProjectileUtil.calcSpread(dir, angle, amount.get(skillLevel - 1));
                for (Vector d : dirs) {
                    Projectile p = caster.launchProjectile(projectileType);

                    PersistentDataContainerUtil.putString("skillCastKey", skillKey.toString(), p); //put skill key
                    changeToParticleProjectile(p);

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

        return true;
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

            hit = new TemporaryEntity(projectile.getLocation());
        } else if (addCasterAsFirstTargetIfHitSuccess) {
            if (CitizensAPI.getNPCRegistry().isNPC(hit)) return;

            hitSuccess = true;
            targets.add(caster);
        }

        if (hit instanceof LivingEntity) {
            boolean b = ProjectileRepeatProtector.shouldSkillWorkOnProjectileHitToEntity(hit, projectile);

            if (b) {
                targets.add((LivingEntity) hit);
            }
        }

        if (!hitSuccess && addCasterAsSecondTargetIfHitFail) {
            targets.add(caster);
        }

        int skillLevel = 1;
        if (PersistentDataContainerUtil.hasInteger(projectile, "skillLevel")) {
            skillLevel = PersistentDataContainerUtil.getInteger(projectile, "skillLevel");
        }

        executeChildren((LivingEntity) projectile.getShooter(), skillLevel, targets);

        if (projectile instanceof Arrow) {
            if (((Arrow) projectile).getPierceLevel() > 0) return;
        }

        projectile.remove();
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (skillLevel == 0) {
            additions.add(ChatColor.YELLOW + "Projectile amount: " + amount.get(skillLevel));
        } else if (skillLevel == amount.size()) {
            additions.add(ChatColor.YELLOW + "Projectile amount: " + amount.get(skillLevel - 1));
        } else {
            additions.add(ChatColor.YELLOW + "Projectile amount: " + amount.get(skillLevel - 1) + " -> " + amount.get(skillLevel));
        }
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
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

    private void changeToParticleProjectile(Projectile p) {
        if (particle != null) {
            if (isProjectileInvisible) {
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
            }

            startParticleAnimation(p);
        }
    }
}
