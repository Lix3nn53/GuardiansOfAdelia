package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.ConditionComponent;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class HealthCondition extends ConditionComponent {

    double minPercent;
    double maxPercent;

    public HealthCondition(double minPercent, double maxPercent) {
        this.minPercent = minPercent;
        this.maxPercent = maxPercent;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {

        for (LivingEntity target : targets) {
            double currentHealth = target.getHealth();
            double maxHealth = target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            double value = currentHealth / maxHealth;

            if (value > minPercent && value <= maxPercent) {
                executeChildren(caster, skillLevel, targets, castKey);
            }
        }

        return false;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        return new ArrayList<>();
    }
}
