package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.commands.admin.CommandAdmin;
import io.github.lix3nn53.guardiansofadelia.creatures.custom.TemporaryEntity;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProjectileMechanicBase {

    private final Class<? extends Projectile> projectileType;
    private final SpreadType spreadType;
    private final float radius;
    private final float height;
    private final float speed;
    private final List<Integer> amountList;
    private final String amountValueKey;
    private final float angle;
    private final float upward;
    private final float range;
    private final boolean mustHitToWork;
    //Particle projectile
    private final ParticleArrangement particleArrangement;
    private final boolean isProjectileInvisible;
    // Disguise
    private final Optional<Material> disguiseMaterial;
    private final int disguiseCustomModelData;
    //very custom things
    private final boolean addCasterAsFirstTargetIfHitSuccess;
    private final boolean addCasterAsSecondTargetIfHitFail; // First target is empty entity at hit location

    //Piercing
    private LivingEntity caster;
    private int skillIndex;

    public ProjectileMechanicBase(Class<? extends Projectile> projectileType, SpreadType spreadType, float radius,
                                  float height, float speed, List<Integer> amountList, String amountValueKey,
                                  float angle, float upward, float range, boolean mustHitToWork,
                                  ParticleArrangement particleArrangement, boolean isProjectileInvisible, Optional<Material> disguiseMaterial, int disguiseCustomModelData, boolean addCasterAsFirstTargetIfHitSuccess, boolean addCasterAsSecondTargetIfHitFail) {
        this.projectileType = projectileType;
        this.spreadType = spreadType;
        this.radius = radius;
        this.height = height;
        this.speed = speed;
        this.amountList = amountList;
        this.amountValueKey = amountValueKey;
        this.angle = angle;
        this.upward = upward;
        this.range = range;
        this.mustHitToWork = mustHitToWork;
        this.particleArrangement = particleArrangement;
        this.isProjectileInvisible = isProjectileInvisible;
        this.disguiseMaterial = disguiseMaterial;
        this.disguiseCustomModelData = disguiseCustomModelData;
        this.addCasterAsFirstTargetIfHitSuccess = addCasterAsFirstTargetIfHitSuccess;
        this.addCasterAsSecondTargetIfHitFail = addCasterAsSecondTargetIfHitFail;
    }

    public ProjectileMechanicBase(ConfigurationSection configurationSection) throws ClassNotFoundException {
        String projectileClass = configurationSection.getString("projectileClass");
        this.projectileType = (Class<? extends Projectile>) Class.forName("org.bukkit.entity." + projectileClass);

        spreadType = SpreadType.valueOf(configurationSection.getString("spreadType"));
        speed = (float) configurationSection.getDouble("speed");
        amountList = configurationSection.getIntegerList("amountList");
        amountValueKey = configurationSection.getString("amountValueKey");
        angle = (float) configurationSection.getDouble("angle");
        range = (float) configurationSection.getDouble("range");
        mustHitToWork = configurationSection.getBoolean("mustHitToWork");

        if (spreadType.equals(SpreadType.RAIN)) {
            radius = (float) configurationSection.getDouble("radius");
            height = (float) configurationSection.getDouble("height");
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
            this.upward = (float) configurationSection.getDouble("upward");
        } else {
            this.upward = 0;
        }

        //custom options
        if (configurationSection.contains("addCasterAsFirstTargetIfHitSuccess")) {
            this.addCasterAsFirstTargetIfHitSuccess = configurationSection.getBoolean("addCasterAsFirstTargetIfHitSuccess");
        } else {
            this.addCasterAsFirstTargetIfHitSuccess = false;
        }
        if (configurationSection.contains("addCasterAsSecondTargetIfHitFail")) {
            this.addCasterAsSecondTargetIfHitFail = configurationSection.getBoolean("addCasterAsSecondTargetIfHitFail");
        } else {
            this.addCasterAsSecondTargetIfHitFail = false;
        }

        if (configurationSection.contains("isProjectileInvisible")) {
            isProjectileInvisible = configurationSection.getBoolean("isProjectileInvisible");
        } else {
            isProjectileInvisible = false;
        }

        // Disguise
        if (configurationSection.contains("disguiseMaterial")) {
            disguiseMaterial = Optional.of(Material.valueOf(configurationSection.getString("disguiseMaterial")));
        } else {
            disguiseMaterial = Optional.empty();
        }
        if (configurationSection.contains("disguiseCustomModelData")) {
            disguiseCustomModelData = configurationSection.getInt("disguiseCustomModelData");
        } else {
            disguiseCustomModelData = 1;
        }
    }

    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int skillIndex, ProjectileCallback projectileCallback) {
        if (targets.isEmpty()) return false;

        this.skillIndex = skillIndex;
        this.caster = caster;
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

        ProjectileListener.onSkillProjectileShoot(projectiles, projectileCallback, skillLevel, delayTicks);

        return true;
    }

    public ArrayList<LivingEntity> callback(Projectile projectile, Entity hit) {
        ArrayList<LivingEntity> targets = new ArrayList<>();

        boolean hitSuccess = false;
        if (hit == null) {
            if (projectile.isValid()) projectile.remove();
            if (mustHitToWork) return targets;

            hit = new TemporaryEntity(projectile.getLocation(), caster);
        } else if (addCasterAsFirstTargetIfHitSuccess) {
            if (CitizensAPI.getNPCRegistry().isNPC(hit)) return targets;

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

        if (projectile instanceof Arrow) {
            if (((Arrow) projectile).getPierceLevel() > 0) return targets;
        }

        projectile.remove();

        return targets;
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

    public Class<? extends Projectile> getProjectileType() {
        return projectileType;
    }

    public SpreadType getSpreadType() {
        return spreadType;
    }

    public float getRadius() {
        return radius;
    }

    public float getHeight() {
        return height;
    }

    public float getSpeed() {
        return speed;
    }

    public List<Integer> getAmountList() {
        return amountList;
    }

    public String getAmountValueKey() {
        return amountValueKey;
    }

    public float getAngle() {
        return angle;
    }

    public float getUpward() {
        return upward;
    }

    public float getRange() {
        return range;
    }

    public boolean isMustHitToWork() {
        return mustHitToWork;
    }

    public LivingEntity getCaster() {
        return caster;
    }

    public void setCaster(LivingEntity caster) {
        this.caster = caster;
    }

    public ParticleArrangement getParticleArrangement() {
        return particleArrangement;
    }

    public boolean isProjectileInvisible() {
        return isProjectileInvisible;
    }

    public Optional<Material> getDisguiseMaterial() {
        return disguiseMaterial;
    }

    public int getDisguiseCustomModelData() {
        return disguiseCustomModelData;
    }

    public boolean isAddCasterAsFirstTargetIfHitSuccess() {
        return addCasterAsFirstTargetIfHitSuccess;
    }

    public boolean isAddCasterAsSecondTargetIfHitFail() {
        return addCasterAsSecondTargetIfHitFail;
    }

    public int getSkillIndex() {
        return skillIndex;
    }

    public void setSkillIndex(int skillIndex) {
        this.skillIndex = skillIndex;
    }
}
