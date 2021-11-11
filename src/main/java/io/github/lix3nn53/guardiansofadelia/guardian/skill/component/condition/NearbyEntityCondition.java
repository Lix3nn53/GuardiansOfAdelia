package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.ConditionComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.TargetHelper;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.Collections;
import java.util.List;

public class NearbyEntityCondition extends ConditionComponent {

    private final EntityType entityType;
    private final String displayNameContains;
    private final float radius;
    private final boolean mustExist;

    public NearbyEntityCondition(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

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
        this.radius = (float) configurationSection.getDouble("radius");
        this.mustExist = configurationSection.getBoolean("mustExist");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        boolean success = false;
        for (LivingEntity target : targets) {
            List<LivingEntity> nearby = TargetHelper.getNearbySphere(target.getLocation(), radius);

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
                success = executeChildren(caster, skillLevel, Collections.singletonList(target), castCounter, skillIndex) || success;
            }
        }

        return success;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
