package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.ConditionComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.Collections;
import java.util.List;

public class DistanceCondition extends ConditionComponent {

    private final float distance;
    private final boolean inside;

    public DistanceCondition(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("distance")) {
            configLoadError("distance");
        }

        if (!configurationSection.contains("inside")) {
            configLoadError("inside");
        }

        this.distance = configurationSection.getInt("distance");
        this.inside = configurationSection.getBoolean("inside");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        boolean success = false;
        for (LivingEntity target : targets) {
            float currentDistance = (float) caster.getLocation().distanceSquared(target.getLocation());

            float v = distance * distance;

            if (inside == currentDistance <= v) {
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
