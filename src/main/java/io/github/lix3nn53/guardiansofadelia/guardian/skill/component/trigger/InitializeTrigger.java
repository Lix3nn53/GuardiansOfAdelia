package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TriggerComponent;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class InitializeTrigger extends TriggerComponent {

    LivingEntity caster;
    int skillLevel;
    List<LivingEntity> targets;
    String castKey;

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {
        List<LivingEntity> targetCaster = new ArrayList<>();
        targetCaster.add(caster);

        return executeChildren(caster, skillLevel, targetCaster, castKey);
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        return new ArrayList<>();
    }

    public void startEffects() {
        executeChildren(caster, skillLevel, targets, castKey);
    }

    public void stopEffects() {
        SkillDataManager.stopRepeatTasksOfCast(castKey);
        SkillDataManager.clearFlags(castKey);
    }
}
