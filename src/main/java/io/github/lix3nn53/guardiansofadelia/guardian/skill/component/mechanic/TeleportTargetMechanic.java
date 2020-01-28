package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class TeleportTargetMechanic extends MechanicComponent {

    private final boolean toTarget;
    private final boolean toCaster;

    public TeleportTargetMechanic(boolean toTarget, boolean toCaster) {
        this.toTarget = toTarget;
        this.toCaster = toCaster;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        Location casterStartLocation = caster.getLocation();

        if (toTarget) {
            LivingEntity target = targets.get(0);
            caster.teleport(target);
        }

        if (toCaster) {
            for (LivingEntity target : targets) {
                target.teleport(casterStartLocation);
            }
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
