package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.ConditionComponent;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class FlagCondition extends ConditionComponent {

    private final String key;
    private final boolean isSet;

    /**
     * @param key
     * @param isSet check to true or false
     */
    public FlagCondition(String key, boolean isSet) {
        this.key = key;
        this.isSet = isSet;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {
        boolean hasFlag = SkillDataManager.hasFlag(castKey, key);

        if (hasFlag == isSet) {
            return executeChildren(caster, skillLevel, targets, castKey);
        }

        return false;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        return new ArrayList<>();
    }
}
