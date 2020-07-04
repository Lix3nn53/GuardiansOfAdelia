package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.ConditionComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.Nearby;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class NearbyEntityCondition extends ConditionComponent {

    private final EntityType entityType;
    private final String displayNameContains;
    private final double radius;
    private final boolean mustExist;

    public NearbyEntityCondition(EntityType entityType, String displayNameContains, double radius, boolean mustExist) {
        this.entityType = entityType;
        this.displayNameContains = displayNameContains;
        this.radius = radius;
        this.mustExist = mustExist;
    }

    public NearbyEntityCondition(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("entityType")) {
            configLoadError("entityType");
        }

        if (!configurationSection.contains("displayNameContains")) {
            configLoadError("displayNameContains");
        }

        if (!configurationSection.contains("radius")) {
            configLoadError("radius");
        }

        if (!configurationSection.contains("mustExist")) {
            configLoadError("mustExist");
        }

        this.entityType = EntityType.valueOf(configurationSection.getString("entityType"));
        this.displayNameContains = configurationSection.getString("displayNameContains");
        this.radius = configurationSection.getDouble("radius");
        this.mustExist = configurationSection.getBoolean("mustExist");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        boolean success = false;
        for (LivingEntity target : targets) {
            List<Entity> nearby = Nearby.getNearby(target, radius);

            boolean existsNearbyOfCurrent = false;
            for (Entity entity : nearby) {
                if (!entity.getType().equals(this.entityType)) continue;

                if (!entity.isCustomNameVisible()) continue;

                String customName = entity.getCustomName();
                if (customName == null) continue;
                if (!customName.contains(displayNameContains)) continue;

                existsNearbyOfCurrent = true;
                break;
            }

            if (existsNearbyOfCurrent == mustExist) {
                success = executeChildren(caster, skillLevel, targets, castCounter) || success;
            }
        }

        return success;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
