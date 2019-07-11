package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class TauntMechanic extends MechanicComponent {

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
        if (targets.isEmpty()) return false;

        boolean taunted = false;
        for (LivingEntity ent : targets) {
            if (ent instanceof Creature && ent != caster) {
                ((Creature) ent).setTarget(caster);
                taunted = true;
            }
        }

        return taunted;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        return lore;
    }
}
