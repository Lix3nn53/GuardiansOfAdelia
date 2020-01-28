package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class FlagRemoveMechanic extends MechanicComponent {

    private final String key;
    private final boolean isUniqueCast;

    public FlagRemoveMechanic(String key, boolean isUniqueCast) {
        this.key = key;
        this.isUniqueCast = isUniqueCast;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        for (LivingEntity target : targets) {
            if (isUniqueCast) {
                SkillDataManager.removeFlag(target, key + castCounter);
            } else {
                SkillDataManager.removeFlag(target, key);
            }
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
