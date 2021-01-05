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
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {

        return false;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }

    public void startEffects(Player caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        executeChildren(caster, skillLevel, targets, castCounter);
    }


    public void stopEffects(Player caster) {
        caster.sendMessage("stopEffects");
        SkillDataManager.onPlayerQuit(caster);
        TriggerListener.onPlayerQuit(caster);
    }
}
