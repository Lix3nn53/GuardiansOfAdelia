package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.ConditionComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValueCondition extends ConditionComponent {

    private final String key;
    private final int minValue;

    private final int maxValue;
    private final List<Integer> maxValueList;

    private final boolean checkCasterOnly;

    public ValueCondition(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("key")) {
            configLoadError("key");
        }

        if (!configurationSection.contains("minValue")) {
            configLoadError("minValue");
        }

        if (!configurationSection.contains("maxValue") && !configurationSection.contains("maxValueList")) {
            configLoadError("maxValue OR maxValueList");
        }

        if (configurationSection.contains("checkCasterOnly")) {
            checkCasterOnly = configurationSection.getBoolean("checkCasterOnly");
        } else {
            checkCasterOnly = false;
        }

        this.key = configurationSection.getString("key");
        this.minValue = configurationSection.getInt("minValue");

        this.maxValue = configurationSection.contains("maxValue") ? configurationSection.getInt("maxValue") : 0;
        this.maxValueList = configurationSection.contains("maxValueList") ? configurationSection.getIntegerList("maxValueList") : new ArrayList<>();
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        boolean success = false;

        int currentMax = maxValue;
        if (!maxValueList.isEmpty()) {
            currentMax = maxValueList.get(skillLevel - 1);
        }

        if (checkCasterOnly) {
            int value = SkillDataManager.getValue(caster, key);

            if (value >= minValue && value <= currentMax) {
                success = executeChildren(caster, skillLevel, targets, castCounter);
            }
        } else {
            for (LivingEntity target : targets) {
                int value = SkillDataManager.getValue(target, key);

                if (value >= minValue && value <= currentMax) {
                    success = executeChildren(caster, skillLevel, Collections.singletonList(target), castCounter) || success;
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
