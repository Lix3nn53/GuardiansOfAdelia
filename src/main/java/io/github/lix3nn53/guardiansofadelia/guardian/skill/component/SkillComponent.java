package io.github.lix3nn53.guardiansofadelia.guardian.skill.component;

import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class SkillComponent {

    private final List<SkillComponent> children = new ArrayList<>();

    public abstract boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets);

    /**
     * Use this in #execute method of SkillComponents
     *
     * @param caster
     * @param skillLevel
     * @param targets
     * @return
     */
    boolean executeChildren(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
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

    public abstract List<String> getSkillLoreAdditions(int skillLevel);

    /**
     * Use this in #getSkillLoreAdditions method of SkillComponents
     *
     * @param skillLevel
     * @return
     */
    List<String> getSkillLoreAdditionsOfChildren(int skillLevel) {
        List<String> additions = new ArrayList<>();
        for (SkillComponent child : children) {
            additions.addAll(child.getSkillLoreAdditions(skillLevel));
        }

        return additions;
    }
}
