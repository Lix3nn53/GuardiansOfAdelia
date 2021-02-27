package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class SavedEntitiesTarget extends TargetComponent {

    public SavedEntitiesTarget(ConfigurationSection configurationSection) {
        super(configurationSection);
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        List<LivingEntity> savedEntities = new ArrayList<>();

        for (LivingEntity target : targets) {
            List<LivingEntity> savedEntitiesOfTarget = SkillDataManager.getSavedEntitiesOfCaster(target);

            savedEntities.addAll(savedEntitiesOfTarget);
        }

        if (savedEntities.isEmpty()) return false;

        List<LivingEntity> targetsNew;
        if (super.isKeepCurrent()) {
            if (super.isAddToBeginning()) {
                savedEntities.addAll(targets);
                targetsNew = savedEntities;
            } else {
                targetsNew = targets;
                targetsNew.addAll(savedEntities);
            }
        } else {
            targetsNew = savedEntities;
        }

        return executeChildren(caster, skillLevel, targetsNew, castCounter);
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
