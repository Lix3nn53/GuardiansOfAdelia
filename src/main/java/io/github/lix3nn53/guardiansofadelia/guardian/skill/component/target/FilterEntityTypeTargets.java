package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Applies child components to the closest all nearby entities around
 * each of the current targets.
 */
public class FilterEntityTypeTargets extends TargetComponent {

    private final List<EntityType> entityTypes;

    public FilterEntityTypeTargets(ConfigurationSection configurationSection) {
        super(false, true, true, true, 99, true, false, false);

        if (!configurationSection.contains("entityTypes")) {
            configLoadError("entityTypes");
        }

        entityTypes = new ArrayList<>();

        List<String> types = configurationSection.getStringList("entityTypes");
        for (String type : types) {
            EntityType entityType = EntityType.valueOf(type);

            entityTypes.add(entityType);
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        List<LivingEntity> targetsNew = new ArrayList<>();

        for (LivingEntity target : targets) {
            if (entityTypes.contains(target.getType())) {
                targetsNew.add(target);
            }
        }

        if (targetsNew.isEmpty()) return false;

        return executeChildren(caster, skillLevel, targetsNew, castCounter, skillIndex);
    }

    @Override
    public List<String> getSkillLoreAdditions(String lang, List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);
    }
}
