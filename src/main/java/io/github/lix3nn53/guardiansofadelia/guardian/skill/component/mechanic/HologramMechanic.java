package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.DroppedItemWatcher;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class HologramMechanic extends MechanicComponent {

    private final Material HELMET;
    private final boolean DISGUISE;
    private final boolean FOLLOW;
    private final int CUSTOM_MODEL_DATA;
    private final List<Integer> DURATION;
    private final String DISPLAY_TEXT;
    private final boolean SMALL;
    private final boolean GRAVITY;
    private final boolean MARKER;
    private final boolean SAVE;
    private final double SPEED;

    private final Vector OFFSET;
    private final EulerAngle ANGLE;

    public HologramMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("helmetType")) {
            configLoadError("helmetType");
        }

        if (!configurationSection.contains("customModelData")) {
            configLoadError("customModelData");
        }

        if (!configurationSection.contains("durations")) {
            configLoadError("durations");
        }

        this.HELMET = Material.valueOf(configurationSection.getString("helmetType"));
        this.DISGUISE = configurationSection.contains("disguise") && configurationSection.getBoolean("disguise");
        this.FOLLOW = configurationSection.contains("follow") && configurationSection.getBoolean("follow");
        this.CUSTOM_MODEL_DATA = configurationSection.getInt("customModelData");
        this.DURATION = configurationSection.getIntegerList("durations");

        if (configurationSection.contains("displayText")) {
            this.DISPLAY_TEXT = configurationSection.getString("displayText");
        } else {
            this.DISPLAY_TEXT = null;
        }

        if (configurationSection.contains("small")) {
            this.SMALL = configurationSection.getBoolean("small");
        } else {
            this.SMALL = true;
        }

        if (configurationSection.contains("gravity")) {
            this.GRAVITY = configurationSection.getBoolean("gravity");
        } else {
            this.GRAVITY = false;
        }

        if (configurationSection.contains("marker")) {
            this.MARKER = configurationSection.getBoolean("marker");
        } else {
            this.MARKER = true;
        }

        if (configurationSection.contains("speed")) {
            this.SPEED = configurationSection.getDouble("speed");
        } else {
            this.SPEED = 0;
        }
        this.SAVE = configurationSection.contains("save") && configurationSection.getBoolean("save");

        double upward = configurationSection.contains("upward") ? configurationSection.getDouble("upward") : 0;
        double forward = configurationSection.contains("forward") ? configurationSection.getDouble("forward") : 0;
        double right = configurationSection.contains("right") ? configurationSection.getDouble("right") : 0;

        this.OFFSET = new Vector(forward, upward, right);

        double anglex = configurationSection.contains("anglex") ? configurationSection.getDouble("anglex") : 0;
        double angley = configurationSection.contains("angley") ? configurationSection.getDouble("angley") : 0;
        double anglez = configurationSection.contains("anglez") ? configurationSection.getDouble("anglez") : 0;

        this.ANGLE = new EulerAngle(anglex, angley, anglez);
    }

    /**
     * passes created hologram to children
     *
     * @param caster
     * @param skillLevel
     * @param targets
     * @param skillIndex
     * @return
     */
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        List<LivingEntity> armorStandList = new ArrayList<>();

        for (LivingEntity target : targets) {
            Location location = target.getLocation().add(this.OFFSET);
            ArmorStand model = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
            model.setVisible(false);
            model.setInvulnerable(true);
            model.setSmall(SMALL);
            model.setGravity(GRAVITY);
            model.setMarker(MARKER);
            model.setHeadPose(this.ANGLE);

            if (HELMET != null) {
                ItemStack itemStack = new ItemStack(HELMET);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(CUSTOM_MODEL_DATA);
                itemStack.setItemMeta(itemMeta);

                if (DISGUISE) {
                    MiscDisguise disguise = new MiscDisguise(DisguiseType.DROPPED_ITEM);
                    DroppedItemWatcher watcher = (DroppedItemWatcher) disguise.getWatcher();
                    watcher.setItemStack(itemStack);

                    DisguiseAPI.disguiseToAll(model, disguise);
                } else {
                    EntityEquipment equipment = model.getEquipment();
                    equipment.setHelmet(itemStack);
                }
            }

            if (DISPLAY_TEXT != null) {
                final String text = DISPLAY_TEXT.replaceAll("%caster%", caster.getName());
                model.setCustomName(text);
                model.setCustomNameVisible(true);
            }

            if (SPEED != 0) {
                Vector dir = target.getLocation().getDirection();
                model.setVelocity(dir.multiply(SPEED));
            }

            if (SAVE) {
                SkillDataManager.onSkillEntityCreateWithSaveOption(caster, model, castCounter);
            }

            if (FOLLOW) {
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (!model.isValid()) {
                            cancel();
                            model.remove();
                        } else {
                            Location location = target.getLocation().clone().add(OFFSET);
                            model.teleport(location);
                        }
                    }
                }.runTaskTimer(GuardiansOfAdelia.getInstance(), 0L, 1L);
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (SAVE) {
                        SkillDataManager.removeSavedEntity(caster, castCounter, model);
                    } else {
                        model.remove();
                    }
                }
            }.runTaskLater(GuardiansOfAdelia.getInstance(), DURATION.get(skillLevel - 1));

            //pass ArmorStand to children as target
            armorStandList.add(model);
        }

        return executeChildren(caster, skillLevel, armorStandList, castCounter, skillIndex);
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
