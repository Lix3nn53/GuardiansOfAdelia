package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class HealMechanic extends MechanicComponent {

    private final List<Integer> healAmountList;
    private final List<Float> healPercentList;
    private final String multiplyWithValue;

    public HealMechanic(List<Integer> healAmountList, List<Float> healPercentList, @Nullable String multiplyWithValue) {
        super(false);
        this.healAmountList = healAmountList;
        this.healPercentList = healPercentList;
        this.multiplyWithValue = multiplyWithValue;
    }

    public HealMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("healAmountList") && !configurationSection.contains("healPercentList")) {
            configLoadError("healAmountList and healPercentList");
        }

        if (configurationSection.contains("healAmountList")) {
            this.healAmountList = configurationSection.getIntegerList("healAmountList");
        } else {
            this.healAmountList = new ArrayList<>();
        }

        if (configurationSection.contains("healPercentList")) {
            this.healPercentList = configurationSection.getFloatList("healPercentList");
        } else {
            this.healPercentList = new ArrayList<>();
        }

        if (configurationSection.contains("multiplyWithValue")) {
            this.multiplyWithValue = configurationSection.getString("multiplyWithValue");
        } else {
            this.multiplyWithValue = null;
        }

    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        boolean healed = false;
        for (LivingEntity ent : targets) {
            float currentHealth = (float) ent.getHealth();

            AttributeInstance attribute = ent.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if (attribute == null) continue;

            float maxHealth = (float) attribute.getValue();

            if (currentHealth == maxHealth) continue;

            int healAmount = 0;
            if (!healAmountList.isEmpty()) {
                healAmount = healAmountList.get(skillLevel - 1);
            }
            if (!healPercentList.isEmpty()) {
                healAmount = (int) (maxHealth * healPercentList.get(skillLevel - 1) + 0.5);
            }
            if (multiplyWithValue != null) {
                int value = SkillDataManager.getValue(caster, multiplyWithValue);
                if (value > 0) {
                    healAmount *= value;
                }
            }

            if (healAmount <= 0) {
                return false;
            }

            float nextHealth = currentHealth + healAmount;

            if (nextHealth > maxHealth) {
                nextHealth = maxHealth;
            }

            ent.setHealth(nextHealth);
            healed = true;

            // Update party scoreboard
            if (ent instanceof Player) {
                Player player = (Player) ent;

                if (PartyManager.inParty(player)) {
                    Party party = PartyManager.getParty(player);
                    party.getBoard().updateHP(player.getName(), (int) (nextHealth + 0.5));
                }
            }
        }

        return healed;
    }

    @Override
    public List<String> getSkillLoreAdditions(String lang, List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);

        if (!healAmountList.isEmpty()) {
            if (skillLevel == 0) {
                String lore = ChatPalette.GREEN_DARK + "Heal: " + healAmountList.get(skillLevel);

                if (multiplyWithValue != null) {
                    lore = lore + "x[" + multiplyWithValue + "]";
                }

                additions.add(lore);
            } else if (skillLevel == healAmountList.size()) {
                String lore = ChatPalette.GREEN_DARK + "Heal: " + healAmountList.get(skillLevel - 1);

                if (multiplyWithValue != null) {
                    lore = lore + "x[" + multiplyWithValue + "]";
                }

                additions.add(lore);
            } else {
                String lore1 = "Heal: " + healAmountList.get(skillLevel - 1);
                String lore2 = healAmountList.get(skillLevel) + "";

                if (multiplyWithValue != null) {
                    lore1 = lore1 + "x[" + multiplyWithValue + "]";
                    lore2 = lore2 + "x[" + multiplyWithValue + "]";
                }

                additions.add(ChatPalette.GREEN_DARK + lore1 + " -> " + lore2);
            }
        }
        if (!healPercentList.isEmpty()) {
            if (skillLevel == 0) {
                String lore = ChatPalette.GREEN_DARK + "Heal: " + healPercentList.get(skillLevel) + "%";

                if (multiplyWithValue != null) {
                    lore = lore + " x[" + multiplyWithValue + "]";
                }

                additions.add(lore);
            } else if (skillLevel == healPercentList.size()) {
                String lore = ChatPalette.GREEN_DARK + "Heal: " + healPercentList.get(skillLevel - 1) + "%";

                if (multiplyWithValue != null) {
                    lore = lore + " x[" + multiplyWithValue + "]";
                }

                additions.add(lore);
            } else {
                String lore1 = "Heal: " + healPercentList.get(skillLevel - 1) + "%";
                String lore2 = healPercentList.get(skillLevel) + "%";

                if (multiplyWithValue != null) {
                    lore2 = lore2 + " x[" + multiplyWithValue + "]";
                }

                additions.add(ChatPalette.GREEN_DARK + lore1 + " -> " + lore2);
            }
        }
        return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);
    }
}
