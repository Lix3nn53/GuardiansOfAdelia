package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.ConditionComponent;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class HealthCondition extends ConditionComponent {

    private final double minPercent;
    private final double maxPercent;

    public HealthCondition(double minPercent, double maxPercent) {
        this.minPercent = minPercent;
        this.maxPercent = maxPercent;
    }

    public HealthCondition(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("minPercent")) {
            configLoadError("minPercent");
        }

        if (!configurationSection.contains("maxPercent")) {
            configLoadError("maxPercent");
        }

        this.minPercent = configurationSection.getDouble("minPercent");
        this.maxPercent = configurationSection.getDouble("maxPercent");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        boolean success = false;
        for (LivingEntity target : targets) {
            double currentHealth = target.getHealth();
            double maxHealth = target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            double value = currentHealth / maxHealth;

            if (value > minPercent && value <= maxPercent) {
                success = executeChildren(caster, skillLevel, targets, castCounter) || success;
            }
        }

        return success;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
