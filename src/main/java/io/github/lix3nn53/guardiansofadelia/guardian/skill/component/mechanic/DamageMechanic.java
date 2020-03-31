package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class DamageMechanic extends MechanicComponent {

    private List<Double> damageList;
    private DamageType damageType;

    public DamageMechanic(List<Double> damageList, DamageType damageType) {
        this.damageList = damageList;
        this.damageType = damageType;
    }

    public DamageMechanic(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("damageType")) {
            configLoadError("damageType");
        }

        if (!configurationSection.contains("damageList")) {
            configLoadError("damageList");
        }

        this.damageType = DamageType.valueOf(configurationSection.getString("damageType"));
        this.damageList = configurationSection.getDoubleList("damageList");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        double calcDamage = damageList.get(skillLevel - 1);

        for (LivingEntity ent : targets) {
            SkillUtils.setDamageType(damageType);
            ent.setNoDamageTicks(0);
            ent.damage(calcDamage, caster);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (skillLevel == 0) {
            additions.add(damageType.toString() + ": " + (int) (damageList.get(skillLevel) + 0.5));
        } else if (skillLevel == damageList.size()) {
            additions.add(damageType.toString() + ": " + (int) (damageList.get(skillLevel - 1) + 0.5));
        } else {
            additions.add(damageType.toString() + ": " + (int) (damageList.get(skillLevel - 1) + 0.5) + " -> " + (int) (damageList.get(skillLevel) + 0.5));
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }


    public enum DamageType {
        MAGIC, MELEE, RANGED;

        @Override
        public String toString() {
            switch (this) {
                case MAGIC:
                    return ChatColor.BLUE + "Magic Damage";
                case MELEE:
                    return ChatColor.RED + "Melee Damage";
                case RANGED:
                    return ChatColor.GOLD + "Ranged Damage";
            }
            return "NULL";
        }
    }
}
