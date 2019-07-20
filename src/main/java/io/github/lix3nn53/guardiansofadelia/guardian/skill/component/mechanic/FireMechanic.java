package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class FireMechanic extends MechanicComponent {

    private final List<Integer> ticks;

    public FireMechanic(List<Integer> ticks) {
        this.ticks = ticks;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
        if (targets.isEmpty()) return false;

        for (LivingEntity target : targets) {
            int newTicks = ticks.get(skillLevel - 1) <= 0 ? 0 : Math.max(ticks.get(skillLevel - 1), target.getFireTicks());

            target.setFireTicks(newTicks);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (skillLevel == 0 || skillLevel == ticks.size()) {
            additions.add(ChatColor.RED + "Burn duration: " + (ticks.get(skillLevel) / 20));
        } else {
            additions.add(ChatColor.RED + "Burn duration: " + (ticks.get(skillLevel - 1) / 20) + " -> " + (ticks.get(skillLevel) / 20));
        }
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
