package io.github.lix3nn53.guardiansofadelia.guardian.skill.component;

import org.bukkit.entity.LivingEntity;

import java.util.List;

public abstract class TriggerComponent extends SkillComponent {

    private boolean isRunning = false;

    public boolean trigger(final LivingEntity caster, final int skillLevel, final List<LivingEntity> targets, String castKey) {

        return execute(caster, skillLevel, targets, castKey);
    }

    @Override
    public boolean execute(final LivingEntity caster, final int skillLevel, final List<LivingEntity> targets, String castKey) {

        try {

            isRunning = true;

            return executeChildren(caster, skillLevel, targets, castKey);

        } finally {

            isRunning = false;

        }

    }

}
