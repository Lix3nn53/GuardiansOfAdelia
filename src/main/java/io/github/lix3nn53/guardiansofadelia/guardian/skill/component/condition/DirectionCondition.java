package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.ConditionComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.TargetHelper;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class DirectionCondition extends ConditionComponent {

    private final boolean isInFront;

    public DirectionCondition(boolean isInFront) {
        this.isInFront = isInFront;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
        if (targets.isEmpty()) return false;

        boolean success = false;
        for (LivingEntity target : targets) {
            if (TargetHelper.isInFront(caster, target) == isInFront) {
                success = executeChildren(caster, skillLevel, targets) || success;
            }
        }

        return success;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        return new ArrayList<>();
    }
}
