package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import javax.annotation.Nullable;
import java.util.List;

public class DamageMechanic extends MechanicComponent {

    private final List<Double> damageList;
    private final DamageType damageType;
    private final String multiplyWithValue;

    public DamageMechanic(List<Double> damageList, DamageType damageType, @Nullable String multiplyWithValue) {
        this.damageList = damageList;
        this.damageType = damageType;
        this.multiplyWithValue = multiplyWithValue;
    }

    public DamageMechanic(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("damageType")) {
            configLoadError("damageType");
        }

        if (!configurationSection.contains("damageList")) {
            configLoadError("damageList");
        }

        if (configurationSection.contains("multiplyWithValue")) {
            this.multiplyWithValue = configurationSection.getString("multiplyWithValue");
        } else {
            this.multiplyWithValue = null;
        }

        this.damageType = DamageType.valueOf(configurationSection.getString("damageType"));
        this.damageList = configurationSection.getDoubleList("damageList");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        double calcDamage = damageList.get(skillLevel - 1);

        if (multiplyWithValue != null) {
            int value = SkillDataManager.getValue(caster, multiplyWithValue);
            calcDamage *= value;
        }

        if (calcDamage > 0) {
            for (LivingEntity ent : targets) {
                SkillUtils.setDamageType(damageType);
                ent.setNoDamageTicks(0);
                ent.damage(calcDamage, caster);
            }
        } else {
            return false;
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (skillLevel == 0) {
            String lore = damageType.toString() + ": " + (int) (damageList.get(skillLevel) + 0.5);

            if (multiplyWithValue != null) {
                lore = lore + "x[" + multiplyWithValue + "]";
            }

            additions.add(lore);
        } else if (skillLevel == damageList.size()) {
            String lore = damageType.toString() + ": " + (int) (damageList.get(skillLevel - 1) + 0.5);

            if (multiplyWithValue != null) {
                lore = lore + "x[" + multiplyWithValue + "]";
            }

            additions.add(lore);
        } else {
            String lore1 = damageType.toString() + ": " + (int) (damageList.get(skillLevel - 1) + 0.5);
            String lore2 = (int) (damageList.get(skillLevel) + 0.5) + "";

            if (multiplyWithValue != null) {
                lore1 = lore1 + "x[" + multiplyWithValue + "]";
                lore2 = lore2 + "x[" + multiplyWithValue + "]";
            }

            additions.add(lore1 + " -> " + lore2);
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
