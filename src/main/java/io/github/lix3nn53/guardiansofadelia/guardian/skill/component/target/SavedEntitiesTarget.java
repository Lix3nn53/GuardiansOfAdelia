package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Collections;
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

        if (super.isKeepCurrent()) {
            if (super.isAddToBeginning()) {
                Collections.reverse(savedEntities);
                for (LivingEntity single : savedEntities) {
                    targets.add(0, single);
                }
            } else {
                targets.addAll(savedEntities);
            }
        } else {
            targets = savedEntities;
        }

        return executeChildren(caster, skillLevel, targets, castCounter);
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
