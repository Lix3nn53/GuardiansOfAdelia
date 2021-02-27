package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Applies child components to the closest all nearby entities around
 * each of the current targets.
 */
public class ClearDeadTargets extends TargetComponent {

    public ClearDeadTargets() {
        super(true, true, true, 10, true, true, false);
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        List<LivingEntity> aliveTargets = new ArrayList<>();
        for (LivingEntity target : targets) {
            if (!target.isDead()) {
                aliveTargets.add(target);
            }
        }

        return executeChildren(caster, skillLevel, aliveTargets, castCounter);
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
