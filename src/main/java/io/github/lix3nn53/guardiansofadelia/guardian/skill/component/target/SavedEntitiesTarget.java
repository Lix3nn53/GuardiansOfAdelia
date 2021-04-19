package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class SavedEntitiesTarget extends TargetComponent {

    private final boolean casterOnly;

    public SavedEntitiesTarget(ConfigurationSection configurationSection) {
        super(configurationSection);

        this.casterOnly = configurationSection.contains("casterOnly") && configurationSection.getBoolean("casterOnly");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        List<LivingEntity> savedEntities = new ArrayList<>();

        if (casterOnly) {
            List<LivingEntity> savedEntitiesOfTarget = SkillDataManager.getSavedEntitiesOfCaster(caster);

            savedEntities.addAll(savedEntitiesOfTarget);
        } else {
            for (LivingEntity target : targets) {
                List<LivingEntity> savedEntitiesOfTarget = SkillDataManager.getSavedEntitiesOfCaster(target);

                savedEntities.addAll(savedEntitiesOfTarget);
            }
        }

        if (savedEntities.isEmpty()) return false;

        List<LivingEntity> targetsNew = new ArrayList<>();
        if (super.isKeepCurrent()) {
            if (super.isAddToBeginning()) {
                savedEntities.addAll(targets);
                targetsNew = savedEntities;
            } else {
                targetsNew.addAll(targets);
                targetsNew.addAll(savedEntities);
            }
        } else {
            targetsNew = savedEntities;
        }

        return executeChildren(caster, skillLevel, targetsNew, castCounter, skillIndex);
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
