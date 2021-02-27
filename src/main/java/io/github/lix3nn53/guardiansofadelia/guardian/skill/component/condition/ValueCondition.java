package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.ConditionComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class ValueCondition extends ConditionComponent {

    private final String key;
    private final int minValue;
    private final int maxValue;
    private final boolean checkCasterOnly;

    public ValueCondition(String key, int minValue, int maxValue, boolean checkCasterOnly) {
        this.key = key;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.checkCasterOnly = checkCasterOnly;
    }

    public ValueCondition(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("key")) {
            configLoadError("key");
        }

        if (!configurationSection.contains("minValue")) {
            configLoadError("minValue");
        }

        if (!configurationSection.contains("maxValue")) {
            configLoadError("maxValue");
        }

        if (configurationSection.contains("checkCasterOnly")) {
            checkCasterOnly = configurationSection.getBoolean("checkCasterOnly");
        } else {
            checkCasterOnly = false;
        }

        this.key = configurationSection.getString("key");
        this.minValue = configurationSection.getInt("minValue");
        this.maxValue = configurationSection.getInt("maxValue");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        boolean success = false;
        if (checkCasterOnly) {
            int value = SkillDataManager.getValue(caster, key);

            GuardiansOfAdelia.getInstance().getLogger().info("value: " + value);
            if (value >= minValue && value <= maxValue) {
                success = executeChildren(caster, skillLevel, targets, castCounter) || success;
            }
        } else {
            for (LivingEntity target : targets) {
                int value = SkillDataManager.getValue(target, key);

                GuardiansOfAdelia.getInstance().getLogger().info("value: " + value);
                if (value >= minValue && value <= maxValue) {
                    success = executeChildren(caster, skillLevel, targets, castCounter) || success;
                }
            }
        }

        return success;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
