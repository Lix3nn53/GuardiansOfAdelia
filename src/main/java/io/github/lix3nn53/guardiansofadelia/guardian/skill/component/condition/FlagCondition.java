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
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
        if (targets.isEmpty()) return false;

        boolean success = false;
        for (LivingEntity target : targets) {
            boolean hasFlag = SkillDataManager.hasFlag(target, key);

            if (hasFlag == isSet) {
                success = executeChildren(caster, skillLevel, targets) || success;
            }
        }

        return success;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        return new ArrayList<>();
    }
}
