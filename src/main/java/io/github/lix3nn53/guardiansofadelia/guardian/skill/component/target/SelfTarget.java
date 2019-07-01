package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Applies child components to the closest all nearby entities around
 * each of the current targets.
 */
public class SelfTarget extends TargetComponent {

    protected SelfTarget(boolean allies, boolean enemy, boolean self, int max) {
        super(allies, enemy, self, max);
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {

        List<LivingEntity> newTargets = new ArrayList<>();

        newTargets.add(caster);

        return executeChildren(caster, skillLevel, newTargets, castKey);
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        return new ArrayList<>();
    }
}
