package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TriggerComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Must be in first skill component list
 */
public class InitializeTrigger extends TriggerComponent {

    private int lastCastCounter;

    public InitializeTrigger(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        this.skillIndex = skillIndex;

        return false;
    }

    @Override
    public List<String> getSkillLoreAdditions(String lang, List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);
    }

    public void startEffects(Player caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        this.skillIndex = skillIndex;

        executeChildren(caster, skillLevel, targets, castCounter, skillIndex);
        lastCastCounter = castCounter;
    }

    public void stopPreviousEffects(Player caster) {
        SkillDataManager.stopRepeatTasksOfCast(caster, lastCastCounter);
        SkillDataManager.removeSavedEntities(caster, lastCastCounter);
    }
}
