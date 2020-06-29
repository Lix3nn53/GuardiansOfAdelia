package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.List;

/**
 * Applies child components to the closest all nearby entities around
 * each of the current targets.
 */
public class FilterCurrentTargets extends TargetComponent {

    public FilterCurrentTargets(boolean allies, boolean enemy, boolean self, int max) {
        super(allies, enemy, self, max);
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        targets = determineTargets(caster, targets);

        if (targets.isEmpty()) return false;

        return executeChildren(caster, skillLevel, targets, castCounter);
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }

    public FilterCurrentTargets(ConfigurationSection configurationSection) {
        super(configurationSection.getBoolean("allies"), configurationSection.getBoolean("enemy"), configurationSection.getBoolean("self"), configurationSection.getInt("max"));

        if (!configurationSection.contains("allies")) {
            configLoadError("allies");
        }

        if (!configurationSection.contains("enemy")) {
            configLoadError("enemy");
        }

        if (!configurationSection.contains("self")) {
            configLoadError("self");
        }

        if (!configurationSection.contains("max")) {
            configLoadError("max");
        }
    }
}
