package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class HealMechanic extends MechanicComponent {

    private final double healAmount;
    private final double healPercent;

    public HealMechanic(double healAmount, double healPercent) {
        this.healAmount = healAmount;
        this.healPercent = healPercent;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {
        if (targets.isEmpty()) return false;

        for (LivingEntity ent : targets) {
            double currentHealth = ent.getHealth();
            double maxHealth = ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

            double nextHealth = currentHealth + healAmount;

            if (healPercent > 0) {
                nextHealth = nextHealth + (maxHealth * healPercent);
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
