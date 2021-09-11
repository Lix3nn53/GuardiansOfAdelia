package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.commands.admin.CommandAdmin;
import io.github.lix3nn53.guardiansofadelia.creatures.custom.TemporaryEntity;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.packetwrapper.WrapperPlayServerEntityDestroy;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleArrangementLoader;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement.ParticleArrangement;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.ArmorStandWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.LivingWatcher;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProjectileMechanic extends MechanicComponent {

    private final Class<? extends Projectile> projectileType;
    private final SpreadType spreadType;
    private final double radius;
    private final double height;
    private final double speed;
    private final List<Integer> amountList;
    private final String amountValueKey;
    private final double angle;
    private final double upward;
    private final double range;
    private final boolean mustHitToWork;
    private LivingEntity caster;
    private int castCounter;
    //Particle projectile
    private final ParticleArrangement particleArrangement;
    private final boolean isProjectileInvisible;

    // Disguise
    private Optional<Material> disguiseMaterial = Optional.empty();
    private int disguiseCustomModelData = -1;

    //Piercing

    //very custom things
    private boolean addCasterAsFirstTargetIfHitSuccess;
    private boolean addCasterAsSecondTargetIfHitFail; // First target is empty entity at hit location
    private int skillIndex;

    /*
    // For spread types Cone and Horizontal_Cone, normal projectile
    public ProjectileMechanic(SpreadType spreadType, double speed,
                              List<Integer> amountList, double angle, double right, double upward, double forward,
                              double range, boolean mustHitToWork, Class<? extends Projectile> projectileType) {
        this.spreadType = spreadType;
        this.speed = speed;
        this.amountList = amountList;
        this.angle = angle;
        this.upward = upward;
        this.range = range;
        this.mustHitToWork = mustHitToWork;
        this.projectileType = projectileType;

        this.radius = 0;
        this.height = 0;
    }

    // For spread types Cone and Horizontal_Cone, particle projectile
    public ProjectileMechanic(SpreadType spreadType, double speed,
                              List<Integer> amountList, double angle, double right, double upward, double forward,
                              double range, boolean mustHitToWork, Class<? extends Projectile> projectileType,
                              Particle particleType, ParticleArrangementType particleArrangementType, double particleRadius,
                              int particleAmount, Particle.DustOptions dustOptions, boolean isProjectileInvisible) {
        this.spreadType = spreadType;
        this.speed = speed;
        this.amountList = amountList;
        this.angle = angle;
        this.upward = upward;
        this.range = range;
        this.mustHitToWork = mustHitToWork;
        this.projectileType = projectileType;
        this.isProjectileInvisible = isProjectileInvisible;

        this.radius = 0;
        this.height = 0;

        setParticle(particleType, particleArrangementType, particleRadius, particleAmount, dustOptions);
    }

    // For spread type Rain, normal projectile
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

        this.upward = upward;
        this.range = range;
        this.projectileType = projectileType;
    }

    // For spread type Rain, particle projectile
    public ProjectileMechanic(SpreadType spreadType, double radius, double height, double speed,
                              List<Integer> amountList, double right, double upward, double forward,
                              double range, boolean mustHitToWork, Class<? extends Projectile> projectileType,
                              Particle particleType, ParticleArrangementType particleArrangementType, double particleRadius,
                              int particleAmount, Particle.DustOptions dustOptions, boolean isProjectileInvisible) {
        this.spreadType = spreadType;

        this.radius = radius;
        this.height = height;

        this.speed = speed;
        this.amountList = amountList;
        this.mustHitToWork = mustHitToWork;

        this.angle = 0;

        this.upward = upward;
        this.range = range;
        this.projectileType = projectileType;
        this.isProjectileInvisible = isProjectileInvisible;

        setParticle(particleType, particleArrangementType, particleRadius, particleAmount, dustOptions);
    }
    */

    public ProjectileMechanic(ConfigurationSection configurationSection) throws ClassNotFoundException {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        String projectileClass = configurationSection.getString("projectileClass");
        this.projectileType = (Class<? extends Projectile>) Class.forName("org.bukkit.entity." + projectileClass);

        spreadType = SpreadType.valueOf(configurationSection.getString("spreadType"));
        speed = configurationSection.getDouble("speed");
        amountList = configurationSection.getIntegerList("amountList");
        amountValueKey = configurationSection.getString("amountValueKey");
        angle = configurationSection.getDouble("angle");
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
        if (configurationSection.contains("particle")) {
            ConfigurationSection particle = configurationSection.getConfigurationSection("particle");
            this.particleArrangement = ParticleArrangementLoader.load(particle);
        } else {
            this.particleArrangement = null;
        }

        if (configurationSection.contains("upward")) {
            this.upward = configurationSection.getDouble("upward");
        } else {
            this.upward = 0;
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
        } else {
            isProjectileInvisible = false;
        }

        // Disguise
        if (configurationSection.contains("disguiseMaterial")) {
            disguiseMaterial = Optional.of(Material.valueOf(configurationSection.getString("disguiseMaterial")));
        }
        if (configurationSection.contains("disguiseCustomModelData")) {
            disguiseCustomModelData = configurationSection.getInt("disguiseCustomModelData");
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        this.skillIndex = skillIndex;
        this.caster = caster;
        this.castCounter = castCounter;
        UUID skillKey = UUID.randomUUID(); //skill key to put into projectile

        // Fire from each target
        ArrayList<Entity> projectiles = new ArrayList<>();
        int i = 0;
        for (LivingEntity target : targets) {
            // Apply the spread type
            int amount = amountList.get(skillLevel - 1);
            if (amountValueKey != null) {
                amount += SkillDataManager.getValue(caster, amountValueKey);
            }
            if (spreadType.equals(SpreadType.RAIN)) {
                ArrayList<Location> locs = ProjectileUtil.calcRain(target.getLocation(), radius, height, amount);

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
                        } else if (projectileType == Snowball.class) {
                            disguise = new MiscDisguise(DisguiseType.SNOWBALL);
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

                /*Vector looking = null;
                if (right != 0 || upward != 0 || forward != 0) {
                    looking = target.getLocation().getDirection().setY(0).normalize();
                    Vector normal = looking.clone().crossProduct(UP);
                    looking.multiply(forward).add(normal.multiply(right));
                }*/

                ArrayList<Vector> dirs = ProjectileUtil.calcSpread(dir, angle, amount);
                for (Vector d : dirs) {
                    Projectile projectile = target.launchProjectile(projectileType);

                    PersistentDataContainerUtil.putString("skillCastKey", skillKey.toString(), projectile); //put skillCastKey to projectile entity
                    changeToParticleProjectile(projectile);

                    if (upward != 0) {
                        /*projectile.teleport(target.getLocation()
                                .add(looking)
                                .add(0, upward, 0)
                                .add(projectile.getVelocity())
                                .setDirection(d));*/
                        projectile.teleport(projectile.getLocation().add(0, upward, 0));
                    }

                    if (i < targets.size() - 1) { // size 2, index 0 works, index 1 does not work
                        if (projectileType.equals(ShulkerBullet.class)) {
                            ShulkerBullet shulkerBullet = (ShulkerBullet) projectile;

                            LivingEntity livingEntity = targets.get(targets.size() - 1);

                            shulkerBullet.setTarget(livingEntity); // all projectiles target last entity
                            if (target instanceof Player) {
                                Player player = (Player) target;
                                if (CommandAdmin.DEBUG_MODE)
                                    player.sendMessage("ShulkerBullet target: " + livingEntity.getCustomName());
                            }
                        }
                    }

                    /* else if (piercing > 0) {
                        //TODO arrow does not pierce

                        NBTEntity ent = new NBTEntity(p);
                        ent.setByte("ShotFromCrossbow", (byte) 1);
                        ent.setByte("crit", (byte) 1);
                        ent.setString("SoundEvent", "minecraft:item.crossbow.hit");

                        GuardiansOfAdelia.getInstance().getLogger().info("piercing");
                        ((Arrow) p).setPierceLevel(piercing);
                    }*/
                    /*if (projectile instanceof Arrow) {
                        ((Arrow) projectile).setPierceLevel(5);
                        ((Arrow) projectile).setShotFromCrossbow(true);
                        if (caster instanceof Player) {
                            Player player = (Player) caster;
                            player.sendMessage("TEST setPierceLevel");
                        }
                    }*/

                    projectile.setVelocity(d.clone().multiply(speed));
                    projectiles.add(projectile);

                    if (disguiseMaterial.isPresent()) {
                        ItemStack itemStack = new ItemStack(disguiseMaterial.get());
                        if (disguiseCustomModelData != -1) {
                            ItemMeta itemMeta = itemStack.getItemMeta();
                            itemMeta.setCustomModelData(disguiseCustomModelData);
                            itemStack.setItemMeta(itemMeta);
                        }

                        MobDisguise mobDisguiseBase = new MobDisguise(DisguiseType.ARMOR_STAND, false);
                        MobDisguise mobDisguise = mobDisguiseBase.setModifyBoundingBox(false);
                        LivingWatcher livingWatcher = mobDisguise.getWatcher();
                        livingWatcher.setInvisible(true);
                        livingWatcher.setNoGravity(true);
                        livingWatcher.setHelmet(itemStack);

                        ArmorStandWatcher armorStandWatcher = (ArmorStandWatcher) livingWatcher;
                        armorStandWatcher.setSmall(true);
                        armorStandWatcher.setMarker(true);

                        DisguiseAPI.disguiseToAll(projectile, mobDisguise);
                    }
                }
            }
            i++;
        }
        int delayTicks = (int) Math.ceil(range / Math.abs(speed));
        if (projectileType.equals(ShulkerBullet.class)) {
            delayTicks = delayTicks * 10;
        }

        ProjectileListener.onSkillProjectileShoot(projectiles, this, skillLevel, delayTicks);

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
        } else if (addCasterAsFirstTargetIfHitSuccess) {
            if (CitizensAPI.getNPCRegistry().isNPC(hit)) return;

            hitSuccess = true;
            targets.add(caster);
        }

        //add hit to target list
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

        ProjectileSource shooter = projectile.getShooter();

        if (shooter instanceof LivingEntity) {
            LivingEntity shooterLiving = (LivingEntity) shooter;

            if (PetManager.isCompanion(shooterLiving)) {
                Player owner = PetManager.getOwner(shooterLiving);

                executeChildren(owner, skillLevel, targets, castCounter, skillIndex);
            } else if (SkillDataManager.isSavedEntity(shooterLiving)) {
                LivingEntity owner = SkillDataManager.getOwner(shooterLiving);

                executeChildren(owner, skillLevel, targets, castCounter, skillIndex);
            } else {
                executeChildren(shooterLiving, skillLevel, targets, castCounter, skillIndex);
            }
        }

        if (projectile instanceof Arrow) {
            if (((Arrow) projectile).getPierceLevel() > 0) return;
        }

        projectile.remove();
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        if (skillLevel == 0) {
            String s = ChatColor.YELLOW + "Projectile amount: " + amountList.get(skillLevel);

            if (amountValueKey != null) {
                s += " x[" + amountValueKey + "]";
            }

            additions.add(s);
        } else if (skillLevel == amountList.size()) {
            String s = ChatColor.YELLOW + "Projectile amount: " + amountList.get(skillLevel - 1);

            if (amountValueKey != null) {
                s += " x[" + amountValueKey + "]";
            }

            additions.add(s);
        } else {
            String s = ChatColor.YELLOW + "Projectile amount: " + amountList.get(skillLevel - 1) + " -> " + amountList.get(skillLevel);

            if (amountValueKey != null) {
                s += " x[" + amountValueKey + "]";
            }

            additions.add(s);
        }
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }

    /*public void setPiercing(int piercing) {
        this.piercing = piercing;
    }*/

    private void startParticleAnimation(Projectile projectile) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (projectile.isValid()) {
                    Location location = projectile.getLocation();

                    particleArrangement.play(location, new Vector());
                } else {
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(GuardiansOfAdelia.getInstance(), 1L, 1L);
    }

    public void setAddCasterAsFirstTargetIfHitSuccess(boolean addCasterAsFirstTargetIfHitSuccess) {
        this.addCasterAsFirstTargetIfHitSuccess = addCasterAsFirstTargetIfHitSuccess;
    }

    public void setAddCasterAsSecondTargetIfHitFail(boolean addCasterAsSecondTargetIfHitFail) {
        this.addCasterAsSecondTargetIfHitFail = addCasterAsSecondTargetIfHitFail;
    }

    private void changeToParticleProjectile(Projectile p) {
        if (particleArrangement != null) {
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
