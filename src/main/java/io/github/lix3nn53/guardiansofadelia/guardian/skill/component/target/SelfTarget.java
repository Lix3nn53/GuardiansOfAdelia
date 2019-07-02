package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class SelfTarget extends TargetComponent {

    public SelfTarget() {
        super(false, false, true, 1);
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {
        List<LivingEntity> targetCaster = new ArrayList<>();
        targetCaster.add(caster);

        return executeChildren(caster, skillLevel, targetCaster, castKey);
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        return new ArrayList<>();
    }
}
