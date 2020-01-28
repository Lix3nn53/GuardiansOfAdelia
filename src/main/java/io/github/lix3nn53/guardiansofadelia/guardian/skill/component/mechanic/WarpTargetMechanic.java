package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class WarpTargetMechanic extends MechanicComponent {

    private final boolean toCaster;

    public WarpTargetMechanic(boolean toCaster) {
        this.toCaster = toCaster;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        for (LivingEntity target : targets) {
            if (toCaster) {
                target.teleport(caster);
            } else {
                caster.teleport(target);
                break;
            }
        }
        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
