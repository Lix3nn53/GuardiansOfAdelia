package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class TeleportTargetMechanic extends MechanicComponent {

    private final boolean toTarget;

    public TeleportTargetMechanic(boolean toTarget) {
        this.toTarget = toTarget;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
        if (targets.isEmpty()) return false;

        if (toTarget) {
            LivingEntity target = targets.get(0);
            caster.teleport(target);
        } else {
            for (LivingEntity target : targets) {
                target.teleport(caster);
            }
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
