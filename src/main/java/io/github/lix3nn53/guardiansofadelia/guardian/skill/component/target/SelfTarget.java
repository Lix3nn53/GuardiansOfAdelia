package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class SelfTarget extends TargetComponent {

    public SelfTarget() {
        super(false, false, false, true, 1, false, false, false);
    }

    public SelfTarget(ConfigurationSection configurationSection) {
        super(false, false, false, true,
                configurationSection.contains("max") ? configurationSection.getInt("max") : 1,
                false,
                configurationSection.contains("keepCurrent") && configurationSection.getBoolean("keepCurrent"),
                configurationSection.contains("addToBeginning") && configurationSection.getBoolean("addToBeginning"));
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        List<LivingEntity> targetsNew = new ArrayList<>();
        if (super.isKeepCurrent()) {
            if (super.isAddToBeginning()) {
                targetsNew.add(caster);
                targetsNew.addAll(targets);
            } else {
                targetsNew.addAll(targets);
                targetsNew.add(caster);
            }
        } else {
            targetsNew.add(caster);
        }

        return executeChildren(caster, skillLevel, targetsNew, castCounter, skillIndex);
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
