package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.ConditionComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class TargetCountCondition extends ConditionComponent {

    private final int count;

    public TargetCountCondition(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("count")) {
            configLoadError("count");
        }

        this.count = configurationSection.getInt("count");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        int size = targets.size();

        boolean success = false;
        GuardiansOfAdelia.getInstance().getLogger().info("this.count " + this.count);
        GuardiansOfAdelia.getInstance().getLogger().info("size " + size);
        if (size >= this.count) {
            success = executeChildren(caster, skillLevel, targets, castCounter);
        }

        return success;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
