package io.github.lix3nn53.guardiansofadelia.guardian.skill.component;

import org.bukkit.entity.LivingEntity;

import java.util.List;

public class EmptyComponent extends SkillComponent {

    public EmptyComponent(boolean addLore) {
        super(addLore);
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        return executeChildren(caster, skillLevel, targets, castCounter, skillIndex);
    }

    @Override
    public List<String> getSkillLoreAdditions(String lang, List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);
    }
}
