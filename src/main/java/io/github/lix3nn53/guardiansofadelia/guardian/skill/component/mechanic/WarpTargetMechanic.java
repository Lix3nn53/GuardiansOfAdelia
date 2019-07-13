package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class WarpTargetMechanic extends MechanicComponent {

    private final boolean toCaster;

    public WarpTargetMechanic(boolean toCaster) {
        this.toCaster = toCaster;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
        if (targets.isEmpty()) return false;

        for (LivingEntity target : targets) {
            if (toCaster) {
                target.teleport(caster);
            } else {
                caster.teleport(target);
                break;
            }
        }
        return targets.size() > 0;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        return lore;
    }
}
