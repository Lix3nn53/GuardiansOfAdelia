package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.ConditionComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class TargetsEmptyCondition extends ConditionComponent {

    private final boolean empty;

    public TargetsEmptyCondition(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("empty")) {
            configLoadError("empty");
        }

        this.empty = configurationSection.getBoolean("empty");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        boolean empty = targets.isEmpty();

        boolean success = false;
        if (this.empty == empty) {
            success = executeChildren(caster, skillLevel, targets, castCounter, skillIndex);
        }

        return success;
    }

    @Override
    public List<String> getSkillLoreAdditions(String lang, List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);
    }
}
