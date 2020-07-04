package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.ProjectileRepeatProtector;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.TemporaryEntity;
import io.github.lix3nn53.guardiansofadelia.utilities.packetwrapper.WrapperPlayServerEntityDestroy;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.Direction;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleArrangement;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleUtil;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
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
    private final List<Integer> amountList;
    private final double angle;
    private final double right;
    private final double upward;
    private final double forward;
    private final double range;
    private final boolean mustHitToWork;
    private LivingEntity caster;
    private int castCounter;
    //Particle projectile
    private Particle particleType;
    private ParticleArrangement particleArrangement;
    private double particleRadius;
    private int particleAmount;
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
                              List<Integer> amountList, double angle, double right, double upward, double forward,
                              double range, boolean mustHitToWork, Class<? extends Projectile> projectileType) {
        this.spreadType = spreadType;
        this.speed = speed;
        this.amountList = amountList;
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
                              List<Integer> amountList, double angle, double right, double upward, double forward,
                              double range, boolean mustHitToWork, Class<? extends Projectile> projectileType,
                              Particle particleType, ParticleArrangement particleArrangement, double particleRadius,
                              int particleAmount, Particle.DustOptions dustOptions, boolean isProjectileInvisible) {
        this.spreadType = spreadType;
        this.speed = speed;
        this.amountList = amountList;
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

        setParticle(particleType, particleArrangement, particleRadius, particleAmount, dustOptions);
    }

    /**
     * For spread type Rain, normal projectile
     */
    public ProjectileMechanic(SpreadType spreadType, double radius, double height, double speed,
                              List<Integer> amountList, double right, double upward, double forward,
                              double range, boolean mustHitToWork, Class<? extends Projectile> projectileType) {
        this.spreadType = spreadType;

        this.radius = radius;
        this.height = height;

        this.speed = speed;
        this.amountList = amountList;
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
                              List<Integer> amountList, double right, double upward, double forward,
                              double range, boolean mustHitToWork, Class<? extends Projectile> projectileType,
                              Particle particleType, ParticleArrangement particleArrangement, double particleRadius,
                              int particleAmount, Particle.DustOptions dustOptions, boolean isProjectileInvisible) {
        this.spreadType = spreadType;

        this.radius = radius;
        this.height = height;

        this.speed = speed;
        this.amountList = amountList;
        this.mustHitToWork = mustHitToWork;

        this.angle = 0;

        this.right = right;
        this.upward = upward;
        this.forward = forward;
        this.range = range;
        this.projectileType = projectileType;
        this.isProjectileInvisible = isProjectileInvisible;

        setParticle(particleType, particleArrangement, particleRadius, particleAmount, dustOptions);
    }

    public ProjectileMechanic(ConfigurationSection configurationSection) throws ClassNotFoundException {
        String projectileClass = configurationSection.getString("projectileClass");
        this.projectileType = (Class<? extends Projectile>) Class.forName("org.bukkit.entity." + projectileClass);

        spreadType = SpreadType.valueOf(configurationSection.getString("spreadType"));
        speed = configurationSection.getDouble("speed");
        amountList = configurationSection.getIntegerList("amountList");
        angle = configurationSection.getDouble("angle");
        right = configurationSection.getDouble("right");
        upward = configurationSection.getDouble("upward");
        forward = configurationSection.getDouble("forward");
        range = configurationSection.getDouble("range");
        mustHitToWork = configurationSection.getBoolean("mustHitToWork");

        if (spreadType.equals(SpreadType.RAIN)) {
            radius = configurationSection.getDouble("radius");
            height = configurationSection.getDouble("height");
        } else {
            radius = 0;
            height = 0;
        }

        //Particle projectile
        if (configurationSection.contains("particleType")) {
            Particle particleType = Particle.valueOf(configurationSection.getString("particleType"));
            ParticleArrangement particleArrangement = ParticleArrangement.valueOf(configurationSection.getString("particleArrangement"));
            double particleRadius = configurationSection.getDouble("particleRadius");
            int particleAmount = configurationSection.getInt("particleAmount");

            if (configurationSection.contains("dustColor")) {
                int dustColor = configurationSection.getInt("dustColor");
                int dustSize = configurationSection.getInt("dustSize");

                Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(dustColor), dustSize);

                setParticle(particleType, particleArrangement, particleRadius, particleAmount, dustOptions);
            } else {
                setParticle(particleType, particleArrangement, particleRadius, particleAmount, null);
            }
        }

        //custom options
        if (configurationSection.contains("addCasterAsFirstTargetIfHitSuccess")) {
            this.addCasterAsFirstTargetIfHitSuccess = configurationSection.getBoolean("addCasterAsFirstTargetIfHitSuccess");
        }
        if (configurationSection.contains("addCasterAsSecondTargetIfHitFail")) {
            this.addCasterAsSecondTargetIfHitFail = configurationSection.getBoolean("addCasterAsSecondTargetIfHitFail");
        }

        if (configurationSection.contains("isProjectileInvisible")) {
            isProjectileInvisible = configurationSection.getBoolean("isProjectileInvisible");
        }

        //Piercing

        //very custom things
        if (configurationSection.contains("isProjectileInvisible")) {
            addCasterAsFirstTargetIfHitSuccess = configurationSection.getBoolean("isProjectileInvisible");
        }

        if (configurationSection.contains("isProjectileInvisible")) {
            addCasterAsSecondTargetIfHitFail = configurationSection.getBoolean("isProjectileInvisible");
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        this.caster = caster;
        this.castCounter = castCounter;
        UUID skillKey = UUID.randomUUID(); //skill key to put into projectile

        // Fire from each target
        ArrayList<Entity> projectiles = new ArrayList<Entity>();
        for (LivingEntity target : targets) {
            // Apply the spread type
            if (spreadType.equals(SpreadType.RAIN)) {
                ArrayList<Location> locs = ProjectileUtil.calcRain(target.getLocation(), radius, height, amountList.get(skillLevel - 1));

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
                        } else if (projectileType == DragonFireball.class) {
                            disguise = new MiscDisguise(DisguiseType.DRAGON_FIREBALL);
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

                ArrayList<Vector> dirs = ProjectileUtil.calcSpread(dir, angle, amountList.get(skillLevel - 1));
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

            hit = new TemporaryEntity(projectile.getLocation(), caster);

            if (projectile instanceof Player) {
                ((Player) projectile.getShooter()).sendMessage("3");
            }
        } else if (addCasterAsFirstTargetIfHitSuccess) {
            if (CitizensAPI.getNPCRegistry().isNPC(hit)) return;

            hitSuccess = true;
            targets.add(caster);
        }

        //add hit to target list
        if (hit instanceof LivingEntity) {

            if (projectile instanceof Player) {
                ((Player) projectile.getShooter()).sendMessage("4");
            }
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
            if (projectile instanceof Player) {
                ((Player) projectile.getShooter()).sendMessage("5");
            }
            skillLevel = PersistentDataContainerUtil.getInteger(projectile, "skillLevel");
        }

        executeChildren((LivingEntity) projectile.getShooter(), skillLevel, targets, castCounter);

        if (projectile instanceof Arrow) {
            if (((Arrow) projectile).getPierceLevel() > 0) return;
        }

        projectile.remove();
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (skillLevel == 0) {
            additions.add(ChatColor.YELLOW + "Projectile amount: " + amountList.get(skillLevel));
        } else if (skillLevel == amountList.size()) {
            additions.add(ChatColor.YELLOW + "Projectile amount: " + amountList.get(skillLevel - 1));
        } else {
            additions.add(ChatColor.YELLOW + "Projectile amount: " + amountList.get(skillLevel - 1) + " -> " + amountList.get(skillLevel));
        }
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }

    /*public void setPiercing(int piercing) {
        this.piercing = piercing;
    }*/

    public void setParticle(Particle particle, ParticleArrangement arrangement, double radiusParticle, int amountParticle, Particle.DustOptions dustOptions) {
        this.particleType = particle;
        this.particleArrangement = arrangement;
        this.particleRadius = radiusParticle;
        this.particleAmount = amountParticle;
        this.dustOptions = dustOptions;
    }

    private void startParticleAnimation(Projectile projectile) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (projectile.isValid()) {
                    Location location = projectile.getLocation();

                    ParticleUtil.play(location, particleType, particleArrangement, particleRadius, particleAmount, Direction.XZ, 0, 0, 0, 0, dustOptions);
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
        if (particleType != null) {
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
