package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TriggerComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Must be in first skill component list
 */
public class InitializeTrigger extends TriggerComponent {

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {

        return false;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }

    public void startEffects(Player caster, int skillLevel, List<LivingEntity> targets) {
        executeChildren(caster, skillLevel, targets);
    }


    public void stopEffects(Player caster) {
        SkillDataManager.onPlayerQuit(caster);
        TriggerListener.onPlayerQuit(caster);
    }
}
