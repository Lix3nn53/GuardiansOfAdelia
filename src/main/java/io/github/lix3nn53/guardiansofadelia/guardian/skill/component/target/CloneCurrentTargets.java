package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.creatures.custom.TemporaryEntity;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Applies child components to the closest all nearby entities around
 * each of the current targets.
 */
public class CloneCurrentTargets extends TargetComponent {

    public CloneCurrentTargets(ConfigurationSection configurationSection) {
        super(configurationSection);

    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        List<LivingEntity> temporaryEntities = new ArrayList<>();

        for (LivingEntity target : targets) {
            Location location = target.getLocation();

            TemporaryEntity temporaryEntity = new TemporaryEntity(location.clone().add(0, 0.5, 0), caster);

            temporaryEntities.add(temporaryEntity);
        }

        List<LivingEntity> targetsNew = new ArrayList<>();
        if (super.isKeepCurrent()) {
            if (super.isAddToBeginning()) {
                temporaryEntities.addAll(targets);
                targetsNew = temporaryEntities;
            } else {
                targetsNew.addAll(targets);
                targetsNew.addAll(temporaryEntities);
            }
        } else {
            targetsNew = temporaryEntities;
        }

        return executeChildren(caster, skillLevel, targetsNew, castCounter, skillIndex);
    }

    @Override
    public List<String> getSkillLoreAdditions(String lang, List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);
    }
}
