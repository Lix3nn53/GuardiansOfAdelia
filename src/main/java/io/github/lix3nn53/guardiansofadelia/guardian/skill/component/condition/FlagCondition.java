package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.ConditionComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.Collections;
import java.util.List;

public class FlagCondition extends ConditionComponent {

    private final String key;
    private final boolean isSet;
    private final boolean isUnique;

    public FlagCondition(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("key")) {
            configLoadError("key");
        }

        if (!configurationSection.contains("isSet")) {
            configLoadError("isSet");
        }

        this.key = configurationSection.getString("key");
        this.isSet = configurationSection.getBoolean("isSet");
        this.isUnique = configurationSection.contains("isUnique") && configurationSection.getBoolean("isUnique");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        boolean success = false;
        for (LivingEntity target : targets) {
            boolean hasFlag;

            if (isUnique) {
                hasFlag = SkillDataManager.hasFlag(target, key + castCounter);
            } else {
                hasFlag = SkillDataManager.hasFlag(target, key);
            }

            if (hasFlag == isSet) {
                success = executeChildren(caster, skillLevel, Collections.singletonList(target), castCounter, skillIndex) || success;
            }
        }

        return success;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
