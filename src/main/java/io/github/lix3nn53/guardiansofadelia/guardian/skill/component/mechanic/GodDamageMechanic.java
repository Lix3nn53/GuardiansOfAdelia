package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class GodDamageMechanic extends MechanicComponent {

    private final List<Double> damageList;
    private final List<Double> damagePercentList;

    public GodDamageMechanic(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("damageList") && !configurationSection.contains("damagePercentList")) {
            configLoadError("damageList");
            configLoadError("damagePercentList");
        }

        if (configurationSection.contains("damageList")) {
            this.damageList = configurationSection.getDoubleList("damageList");
        } else {
            this.damageList = new ArrayList<>();
        }

        if (configurationSection.contains("damagePercentList")) {
            this.damagePercentList = configurationSection.getDoubleList("damagePercentList");
        } else {
            this.damagePercentList = new ArrayList<>();
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;


        for (LivingEntity ent : targets) {
            double damage = 0;
            if (!damagePercentList.isEmpty()) {
                AttributeInstance attribute = ent.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if (attribute == null) continue;

                double damagePercent = damagePercentList.get(skillLevel - 1);
                double value = attribute.getValue();
                damage = value * damagePercent;
            }
            if (!damageList.isEmpty()) {
                double damageAmount = damageList.get(skillLevel - 1);
                damage += damageAmount;
            }

            ent.setNoDamageTicks(0);
            ent.damage(damage);
            ent.setNoDamageTicks(0);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
