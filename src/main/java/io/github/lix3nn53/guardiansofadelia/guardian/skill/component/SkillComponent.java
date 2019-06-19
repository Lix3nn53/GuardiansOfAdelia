package io.github.lix3nn53.guardiansofadelia.guardian.skill.component;

import java.util.ArrayList;
import java.util.List;

public abstract class SkillComponent {

    private final List<SkillComponent> children = new ArrayList<>();

    public abstract boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets);

    public boolean executeChildren(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
        if (targets.isEmpty()) return false;

        boolean worked = false;
        for (SkillComponent child : children) {
            boolean passed = child.execute(caster, skillLevel, targets);
            worked = passed || worked;
        }
        return worked;
    }

    public void addChildren(SkillComponent child) {
        children.add(child);
    }
}
