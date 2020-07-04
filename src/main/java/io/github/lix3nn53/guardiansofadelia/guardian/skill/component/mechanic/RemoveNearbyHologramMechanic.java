package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class RemoveNearbyHologramMechanic extends MechanicComponent {

    private final List<Double> ranges;
    private final String displayName;

    public RemoveNearbyHologramMechanic(List<Double> ranges, String displayName) {
        this.ranges = ranges;
        this.displayName = displayName;
    }

    public RemoveNearbyHologramMechanic(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("ranges")) {
            configLoadError("ranges");
        }

        if (!configurationSection.contains("displayName")) {
            configLoadError("displayName");
        }

        this.displayName = configurationSection.getString("displayName");
        this.ranges = configurationSection.getDoubleList("ranges");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        double range = ranges.get(skillLevel - 1);
        final String text = displayName.replaceAll("%caster%", caster.getName());

        boolean isRemoved = false;

        for (LivingEntity target : targets) {
            List<Entity> nearbyEntities = target.getNearbyEntities(range, range, range);

            for (Entity entity : nearbyEntities) {
                if (entity instanceof ArmorStand) {
                    ArmorStand armorStand = (ArmorStand) entity;
                    if (armorStand.isCustomNameVisible()) {
                        if (armorStand.getCustomName().equals(text)) {
                            armorStand.remove();
                            isRemoved = true;
                            break;
                        }
                    }
                }
            }

            if (isRemoved) {
                break;
            }
        }

        return isRemoved;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
