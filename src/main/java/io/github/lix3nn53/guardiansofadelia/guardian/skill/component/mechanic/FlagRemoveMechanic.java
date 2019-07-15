package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class FlagRemoveMechanic extends MechanicComponent {

    private final String key;

    public FlagRemoveMechanic(String key) {
        this.key = key;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
        if (targets.isEmpty()) return false;

        for (LivingEntity target : targets) {
            SkillDataManager.removeFlag(target, key);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        return new ArrayList<>();
    }
}
