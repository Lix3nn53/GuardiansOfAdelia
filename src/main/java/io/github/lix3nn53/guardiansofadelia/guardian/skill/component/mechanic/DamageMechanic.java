package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DamageMechanic extends MechanicComponent {

    private final List<Double> damageList;
    private final List<Double> damageMultiplyList; // Multiply this value with according attribute of attacker to determine damage value
    private final DamageType damageType;
    private final String multiplyWithValue;

    public DamageMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("damageType")) {
            configLoadError("damageType");
        }

        if (!configurationSection.contains("damageList") && !configurationSection.contains("damageMultiplyList")) {
            configLoadError("damageList OR damageMultiplyList");
        }

        if (configurationSection.contains("multiplyWithValue")) {
            this.multiplyWithValue = configurationSection.getString("multiplyWithValue");
        } else {
            this.multiplyWithValue = null;
        }

        this.damageType = DamageType.valueOf(configurationSection.getString("damageType"));
        this.damageList = configurationSection.contains("damageList") ? configurationSection.getDoubleList("damageList") : new ArrayList<>();
        this.damageMultiplyList = configurationSection.contains("damageMultiplyList") ? configurationSection.getDoubleList("damageMultiplyList") : new ArrayList<>();

    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        double calcDamage = 0;

        if (!damageMultiplyList.isEmpty()) {
            if (caster instanceof Player) {
                Player player = (Player) caster;
                if (GuardianDataManager.hasGuardianData(player)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                    if (guardianData.hasActiveCharacter()) {
                        RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                        RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();
                        String rpgClassStr = activeCharacter.getRpgClassStr();

                        double statValue = 0;
                        if (damageType.equals(DamageMechanic.DamageType.MAGIC)) {
                            statValue = rpgCharacterStats.getTotalMagicDamage(player, rpgClassStr);
                        } else if (damageType.equals(DamageMechanic.DamageType.RANGED)) {
                            statValue = rpgCharacterStats.getTotalRangedDamage(player, rpgClassStr);
                        } else { //melee
                            statValue = rpgCharacterStats.getTotalMeleeDamage(player, rpgClassStr);
                        }

                        double multiply = damageMultiplyList.get(skillLevel - 1);

                        calcDamage += statValue * multiply;
                    }
                }
            }
        }

        if (!damageList.isEmpty()) {
            calcDamage += damageList.get(skillLevel - 1);
        }

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
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        if (!damageList.isEmpty()) {
            if (!damageMultiplyList.isEmpty()) {
                if (skillLevel == 0) {
                    String lore = damageType.toString() + ": " + (int) (damageList.get(skillLevel) + 0.5) + " + " +
                            (int) (100 * damageMultiplyList.get(skillLevel) + 0.5) + "% of your damage";

                    if (multiplyWithValue != null) {
                        lore = lore + "x[" + multiplyWithValue + "]";
                    }

                    additions.add(lore);
                } else if (skillLevel == damageMultiplyList.size()) {
                    String lore = damageType.toString() + ": " + (int) (damageList.get(skillLevel - 1) + 0.5) + " + " +
                            (int) (100 * damageMultiplyList.get(skillLevel - 1) + 0.5) + "% of your damage";

                    if (multiplyWithValue != null) {
                        lore = lore + "x[" + multiplyWithValue + "]";
                    }

                    additions.add(lore);
                } else {
                    String lore1 = damageType.toString() + ": " + (int) (damageList.get(skillLevel - 1) + 0.5) + " + " +
                            (int) (100 * damageMultiplyList.get(skillLevel - 1) + 0.5) + "% of your damage";
                    String lore2 = (int) (damageList.get(skillLevel) + 0.5) + " + " +
                            (int) (100 * damageMultiplyList.get(skillLevel) + 0.5) + "% of your damage";

                    if (multiplyWithValue != null) {
                        lore1 = lore1 + "x[" + multiplyWithValue + "]";
                        lore2 = lore2 + "x[" + multiplyWithValue + "]";
                    }

                    additions.add(lore1 + " -> " + lore2);
                }
            } else {
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
            }
        } else if (!damageMultiplyList.isEmpty()) {
            if (skillLevel == 0) {
                String lore = damageType.toString() + ": " + (int) (100 * damageMultiplyList.get(skillLevel) + 0.5) + "% of your damage";

                if (multiplyWithValue != null) {
                    lore = lore + "x[" + multiplyWithValue + "]";
                }

                additions.add(lore);
            } else if (skillLevel == damageMultiplyList.size()) {
                String lore = damageType.toString() + ": " + (int) (100 * damageMultiplyList.get(skillLevel - 1) + 0.5) + "% of your damage";

                if (multiplyWithValue != null) {
                    lore = lore + "x[" + multiplyWithValue + "]";
                }

                additions.add(lore);
            } else {
                String lore1 = damageType.toString() + ": " + (int) (100 * damageMultiplyList.get(skillLevel - 1) + 0.5) + "% of your damage";
                String lore2 = (int) (100 * damageMultiplyList.get(skillLevel) + 0.5) + "% of your damage";

                if (multiplyWithValue != null) {
                    lore1 = lore1 + "x[" + multiplyWithValue + "]";
                    lore2 = lore2 + "x[" + multiplyWithValue + "]";
                }

                additions.add(lore1 + " -> " + lore2);
            }
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
