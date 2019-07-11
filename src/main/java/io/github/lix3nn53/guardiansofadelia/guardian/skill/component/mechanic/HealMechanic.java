package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class HealMechanic extends MechanicComponent {

    private final List<Integer> healAmount;
    private final List<Double> healPercent;

    public HealMechanic(List<Integer> healAmount, List<Double> healPercent) {
        this.healAmount = healAmount;
        this.healPercent = healPercent;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
        if (targets.isEmpty()) return false;

        for (LivingEntity ent : targets) {
            double currentHealth = ent.getHealth();
            double maxHealth = ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

            double nextHealth = currentHealth;

            if (!healAmount.isEmpty()) {
                if (healAmount.get(skillLevel - 1) > 0) {
                    nextHealth = currentHealth + healAmount.get(skillLevel - 1);
                }
            }

            if (!healPercent.isEmpty()) {
                if (healPercent.get(skillLevel - 1) > 0) {
                    nextHealth = nextHealth + (maxHealth * healPercent.get(skillLevel - 1));
                }
            }

            if (nextHealth > maxHealth) {
                nextHealth = maxHealth;
            }

            ent.setHealth(nextHealth);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        return lore;
    }
}
