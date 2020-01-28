package io.github.lix3nn53.guardiansofadelia.guardian.skill.component;

import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class SkillComponent {

    private final List<SkillComponent> children = new ArrayList<>();

    public abstract boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter);

    /**
     * Use this in #execute method of SkillComponents
     *
     * @param caster
     * @param skillLevel
     * @param targets
     * @return
     */
    protected boolean executeChildren(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        boolean worked = false;
        for (SkillComponent child : children) {
            boolean passed = child.execute(caster, skillLevel, targets, castCounter);
            worked = passed || worked;
        }
        return worked;
    }

    public void addChildren(SkillComponent child) {
        children.add(child);
    }

    public abstract List<String> getSkillLoreAdditions(List<String> additions, int skillLevel);

    public List<String> getSkillLoreAdditionsOfChildren(List<String> additions, int skillLevel) {
        if (children.isEmpty()) return additions;

        for (SkillComponent child : children) {
            additions = child.getSkillLoreAdditions(additions, skillLevel);
        }

        return additions;
    }
}
