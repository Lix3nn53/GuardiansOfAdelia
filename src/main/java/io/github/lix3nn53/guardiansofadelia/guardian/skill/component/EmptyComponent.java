package io.github.lix3nn53.guardiansofadelia.guardian.skill.component;

import org.bukkit.entity.LivingEntity;

import java.util.List;

public class EmptyComponent extends SkillComponent {

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        return executeChildren(caster, skillLevel, targets, castCounter);
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
