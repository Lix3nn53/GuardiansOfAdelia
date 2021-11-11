package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.List;

public class ManaMechanic extends MechanicComponent {

    private final List<Integer> manaAmount;
    private final List<Float> manaPercent;
    private final String multiplyWithValue;

    public ManaMechanic(List<Integer> manaAmount, List<Float> manaPercent, @Nullable String multiplyWithValue) {
        super(false);
        this.manaAmount = manaAmount;
        this.manaPercent = manaPercent;
        this.multiplyWithValue = multiplyWithValue;
    }

    public ManaMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("manaAmountList")) {
            configLoadError("manaAmountList");
        }

        if (!configurationSection.contains("manaPercentList")) {
            configLoadError("manaPercentList");
        }

        if (configurationSection.contains("multiplyWithValue")) {
            this.multiplyWithValue = configurationSection.getString("multiplyWithValue");
        } else {
            this.multiplyWithValue = null;
        }

        this.manaAmount = configurationSection.getIntegerList("manaAmountList");
        this.manaPercent = configurationSection.getFloatList("manaPercentList");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        boolean manaFilled = false;

        for (LivingEntity ent : targets) {
            if (ent instanceof Player) {
                Player player = (Player) ent;

                if (GuardianDataManager.hasGuardianData(player)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                    if (guardianData.hasActiveCharacter()) {
                        RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                        RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();
                        int currentMana = rpgCharacterStats.getCurrentMana();

                        float maxMana = rpgCharacterStats.getTotalMaxMana();

                        if (currentMana == maxMana) continue;

                        int fillAmount = 0;
                        if (!manaAmount.isEmpty()) {
                            fillAmount = manaAmount.get(skillLevel - 1);
                        }
                        if (!manaPercent.isEmpty()) {
                            fillAmount = (int) (maxMana * manaPercent.get(skillLevel - 1) + 0.5);
                        }
                        if (multiplyWithValue != null) {
                            int multiply = SkillDataManager.getValue(caster, multiplyWithValue);
                            fillAmount *= multiply;
                        }

                        if (fillAmount <= 0) {
                            return false;
                        }

                        int nextMana = currentMana + fillAmount;

                        if (nextMana > maxMana) {
                            nextMana = (int) maxMana;
                        }

                        rpgCharacterStats.setCurrentMana(nextMana);
                        manaFilled = true;
                    }
                }
            }
        }

        return manaFilled;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        if (!manaAmount.isEmpty()) {
            if (skillLevel == 0) {
                additions.add(ChatPalette.BLUE_LIGHT + "Mana regen: " + manaAmount.get(skillLevel));
            } else if (skillLevel == manaAmount.size()) {
                additions.add(ChatPalette.BLUE_LIGHT + "Mana regen: " + manaAmount.get(skillLevel - 1));
            } else {
                additions.add(ChatPalette.BLUE_LIGHT + "Mana regen: " + manaAmount.get(skillLevel - 1) + " -> " + manaAmount.get(skillLevel));
            }
        }
        if (!manaPercent.isEmpty()) {
            if (skillLevel == 0) {
                additions.add(ChatPalette.BLUE_LIGHT + "Mana regen: " + manaPercent.get(skillLevel) + "%");
            } else if (skillLevel == manaPercent.size()) {
                additions.add(ChatPalette.BLUE_LIGHT + "Mana regen: " + manaPercent.get(skillLevel - 1) + "%");
            } else {
                additions.add(ChatPalette.BLUE_LIGHT + "Mana regen: " + manaPercent.get(skillLevel - 1) + "%" + " -> " + manaPercent.get(skillLevel) + "%");
            }
        }
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
