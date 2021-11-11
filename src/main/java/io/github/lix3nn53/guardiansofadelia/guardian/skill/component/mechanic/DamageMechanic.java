package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.element.ElementType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DamageMechanic extends MechanicComponent {

    private final List<Float> damageList;
    private final List<Float> damageMultiplyList; // Multiply this value with according attribute of attacker to determine damage value
    private final ElementType damageType;
    private final String multiplyWithValue;

    public DamageMechanic(boolean addLore, List<Float> damageList, List<Float> damageMultiplyList, ElementType damageType, String multiplyWithValue) {
        super(addLore);
        this.damageList = damageList;
        this.damageMultiplyList = damageMultiplyList != null ? damageMultiplyList : new ArrayList<>();
        this.damageType = damageType;
        this.multiplyWithValue = multiplyWithValue;
    }

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

        this.damageType = ElementType.valueOf(configurationSection.getString("damageType"));
        this.damageList = configurationSection.contains("damageList") ? configurationSection.getFloatList("damageList") : new ArrayList<>();

        this.damageMultiplyList = configurationSection.contains("damageMultiplyList") ? configurationSection.getFloatList("damageMultiplyList") : new ArrayList<>();
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        float calcDamage = 0;

        if (!damageMultiplyList.isEmpty()) {
            if (caster instanceof Player) {
                Player player = (Player) caster;
                if (GuardianDataManager.hasGuardianData(player)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                    if (guardianData.hasActiveCharacter()) {
                        RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                        RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();
                        String rpgClassStr = activeCharacter.getRpgClassStr();

                        float statValue = rpgCharacterStats.getTotalElementDamage(player, rpgClassStr);
                        statValue += rpgCharacterStats.getElement(damageType).getTotal();

                        float multiply = damageMultiplyList.get(skillLevel - 1);

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
            if (value > 0) {
                calcDamage *= value;
            }
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
                    String lore = damageType.getFullName() + ": " + (int) (damageList.get(skillLevel) + 0.5) + " + " +
                            (int) (100 * damageMultiplyList.get(skillLevel) + 0.5) + "%";

                    if (multiplyWithValue != null) {
                        lore = lore + " x[" + multiplyWithValue + "]";
                    }

                    additions.add(lore);
                } else if (skillLevel == damageMultiplyList.size()) {
                    String lore = damageType.getFullName() + ": " + (int) (damageList.get(skillLevel - 1) + 0.5) + " + " +
                            (int) (100 * damageMultiplyList.get(skillLevel - 1) + 0.5) + "%";

                    if (multiplyWithValue != null) {
                        lore = lore + " x[" + multiplyWithValue + "]";
                    }

                    additions.add(lore);
                } else {
                    String lore1 = damageType.getFullName() + ": " + (int) (damageList.get(skillLevel - 1) + 0.5) + " + " +
                            (int) (100 * damageMultiplyList.get(skillLevel - 1) + 0.5) + "%";
                    String lore2 = (int) (damageList.get(skillLevel) + 0.5) + " + " +
                            (int) (100 * damageMultiplyList.get(skillLevel) + 0.5) + "%";

                    if (multiplyWithValue != null) {
                        lore2 = lore2 + " x[" + multiplyWithValue + "]";
                    }

                    additions.add(lore1 + " -> " + lore2);
                }
            } else {
                if (skillLevel == 0) {
                    String lore = damageType.getFullName() + ": " + (int) (damageList.get(skillLevel) + 0.5);

                    if (multiplyWithValue != null) {
                        lore = lore + " x[" + multiplyWithValue + "]";
                    }

                    additions.add(lore);
                } else if (skillLevel == damageList.size()) {
                    String lore = damageType.getFullName() + ": " + (int) (damageList.get(skillLevel - 1) + 0.5);

                    if (multiplyWithValue != null) {
                        lore = lore + " x[" + multiplyWithValue + "]";
                    }

                    additions.add(lore);
                } else {
                    String lore1 = damageType.getFullName() + ": " + (int) (damageList.get(skillLevel - 1) + 0.5);
                    String lore2 = (int) (damageList.get(skillLevel) + 0.5) + "";

                    if (multiplyWithValue != null) {
                        lore2 = lore2 + " x[" + multiplyWithValue + "]";
                    }

                    additions.add(lore1 + " -> " + lore2);
                }
            }
        } else if (!damageMultiplyList.isEmpty()) {
            if (skillLevel == 0) {
                String lore = damageType.getFullName() + ": " + (int) (100 * damageMultiplyList.get(skillLevel) + 0.5) + "%";

                if (multiplyWithValue != null) {
                    lore = lore + " x[" + multiplyWithValue + "]";
                }

                additions.add(lore);
            } else if (skillLevel == damageMultiplyList.size()) {
                String lore = damageType.getFullName() + ": " + (int) (100 * damageMultiplyList.get(skillLevel - 1) + 0.5) + "%";

                if (multiplyWithValue != null) {
                    lore = lore + " x[" + multiplyWithValue + "]";
                }

                additions.add(lore);
            } else {
                String lore1 = damageType.getFullName() + ": " + (int) (100 * damageMultiplyList.get(skillLevel - 1) + 0.5) + "%";
                String lore2 = (int) (100 * damageMultiplyList.get(skillLevel) + 0.5) + "%";

                if (multiplyWithValue != null) {
                    lore2 = lore2 + " x[" + multiplyWithValue + "]";
                }

                additions.add(lore1 + " -> " + lore2);
            }
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
