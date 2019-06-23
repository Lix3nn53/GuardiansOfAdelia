package io.github.lix3nn53.guardiansofadelia.guardian.skill.component;

import org.bukkit.entity.LivingEntity;

import java.util.List;

public abstract class TriggerComponent extends SkillComponent {

    private boolean isRunning = false;

    public boolean trigger(final LivingEntity caster, final int skillLevel, final List<LivingEntity> targets) {

        return execute(caster, skillLevel, targets);
    }

    @Override
    public boolean execute(final LivingEntity caster, final int skillLevel, final List<LivingEntity> targets) {

        try {

            isRunning = true;

            return executeChildren(caster, skillLevel, targets);

        } finally {

            isRunning = false;

        }

    }

}
