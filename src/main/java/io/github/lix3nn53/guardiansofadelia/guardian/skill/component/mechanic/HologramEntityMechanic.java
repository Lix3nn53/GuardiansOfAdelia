package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class HologramEntityMechanic extends MechanicComponent {

    private final EntityType entityType;
    private final Material HELMET;
    private final int CUSTOM_MODEL_DATA;
    private final List<Integer> DURATION;
    private final String DISPLAY_TEXT;
    private final boolean GRAVITY;
    private final boolean SAVE;
    private final double speed;
    private final double right;
    private final double upward;
    private final double forward;

    public HologramEntityMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("entityType")) {
            configLoadError("entityType");
        }

        if (!configurationSection.contains("helmetType")) {
            configLoadError("helmetType");
        }

        if (!configurationSection.contains("customModelData")) {
            configLoadError("customModelData");
        }

        if (!configurationSection.contains("durations")) {
            configLoadError("durations");
        }

        this.entityType = EntityType.valueOf(configurationSection.getString("entityType"));
        this.HELMET = Material.valueOf(configurationSection.getString("helmetType"));
        this.CUSTOM_MODEL_DATA = configurationSection.getInt("customModelData");
        this.DURATION = configurationSection.getIntegerList("durations");

        if (configurationSection.contains("displayText")) {
            this.DISPLAY_TEXT = configurationSection.getString("displayText");
        } else {
            this.DISPLAY_TEXT = null;
        }

        if (configurationSection.contains("gravity")) {
            this.GRAVITY = configurationSection.getBoolean("gravity");
        } else {
            this.GRAVITY = false;
        }

        if (configurationSection.contains("speed")) {
            this.speed = configurationSection.getDouble("speed");
        } else {
            this.speed = 0;
        }
        if (configurationSection.contains("right")) {
            this.right = configurationSection.getDouble("right");
        } else {
            this.right = 0;
        }
        if (configurationSection.contains("upward")) {
            this.upward = configurationSection.getDouble("upward");
        } else {
            this.upward = 0;
        }
        if (configurationSection.contains("forward")) {
            this.forward = configurationSection.getDouble("forward");
        } else {
            this.forward = 0;
        }

        if (configurationSection.contains("save")) {
            this.SAVE = configurationSection.getBoolean("save");
        } else {
            this.SAVE = false;
        }
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
            Location baseLocation = target.getLocation().clone();
            GuardiansOfAdelia.getInstance().getLogger().info("baseLocation: " + baseLocation.toString());

            Vector looking = baseLocation.getDirection().setY(0).normalize();
            Vector normal = looking.clone().crossProduct(UP);

            looking.multiply(forward).add(normal.multiply(right));
            baseLocation.add(looking).add(0, upward + 0.5, 0);

            GuardiansOfAdelia.getInstance().getLogger().info("next baseLocation: " + baseLocation.toString());
            LivingEntity model = (LivingEntity) baseLocation.getWorld().spawnEntity(baseLocation, entityType);
            model.setAI(false);
            model.setInvulnerable(true);
            model.setGravity(GRAVITY);

            if (HELMET != null) {
                ItemStack itemStack = new ItemStack(HELMET);
                ItemMeta itemMeta = itemStack.getItemMeta();

                itemMeta.setCustomModelData(CUSTOM_MODEL_DATA);
                itemStack.setItemMeta(itemMeta);

                EntityEquipment equipment = model.getEquipment();
                equipment.setHelmet(itemStack);
            }

            if (DISPLAY_TEXT != null) {
                final String text = DISPLAY_TEXT.replaceAll("%caster%", caster.getName());
                model.setCustomName(text);
                model.setCustomNameVisible(true);
            }

            if (speed != 0) {
                Vector dir = target.getLocation().getDirection();
                model.setVelocity(dir.multiply(speed));
            }

            if (SAVE) {
                SkillDataManager.onSkillEntityCreateWithSaveOption(caster, model, castCounter);
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
