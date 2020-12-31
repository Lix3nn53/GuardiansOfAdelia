package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.List;

public class HealMechanic extends MechanicComponent {

    private final List<Integer> healAmountList;
    private final List<Double> healPercentList;
    private final String multiplyWithValue;

    public HealMechanic(List<Integer> healAmountList, List<Double> healPercentList, @Nullable String multiplyWithValue) {
        this.healAmountList = healAmountList;
        this.healPercentList = healPercentList;
        this.multiplyWithValue = multiplyWithValue;
    }

    public HealMechanic(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("healAmountList")) {
            configLoadError("healAmountList");
        }

        if (!configurationSection.contains("healPercentList")) {
            configLoadError("healPercentList");
        }

        if (configurationSection.contains("multiplyWithValue")) {
            this.multiplyWithValue = configurationSection.getString("multiplyWithValue");
        } else {
            this.multiplyWithValue = null;
        }

        this.healAmountList = configurationSection.getIntegerList("healAmountList");
        this.healPercentList = configurationSection.getDoubleList("healPercentList");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        boolean healed = false;
        for (LivingEntity ent : targets) {
            double currentHealth = ent.getHealth();

            AttributeInstance attribute = ent.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if (attribute == null) continue;

            double maxHealth = attribute.getValue();

            if (currentHealth == maxHealth) continue;

            int healAmount = 0;
            if (!healAmountList.isEmpty()) {
                healAmount = healAmountList.get(skillLevel - 1);
            }
            if (!healPercentList.isEmpty()) {
                healAmount = (int) (maxHealth * healPercentList.get(skillLevel - 1) + 0.5);
            }
            if (multiplyWithValue != null) {
                int multiply = SkillDataManager.getValue(caster, multiplyWithValue);
                healAmount *= multiply;
            }

            double nextHealth = currentHealth + healAmount;

            if (nextHealth > maxHealth) {
                nextHealth = maxHealth;
            }

            ent.setHealth(nextHealth);
            healed = true;

            //update partyboard
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
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!healAmountList.isEmpty()) {
            if (skillLevel == 0) {
                String lore = ChatColor.GREEN + "Heal: " + healAmountList.get(skillLevel);

                if (multiplyWithValue != null) {
                    lore = lore + "x[" + multiplyWithValue + "]";
                }

                additions.add(lore);
            } else if (skillLevel == healAmountList.size()) {
                String lore = ChatColor.GREEN + "Heal: " + healAmountList.get(skillLevel - 1);

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

                additions.add(ChatColor.GREEN + lore1 + " -> " + lore2);
            }
        }
        if (!healPercentList.isEmpty()) {
            if (skillLevel == 0) {
                String lore = ChatColor.GREEN + "Heal: " + healAmountList.get(skillLevel) + "%";

                if (multiplyWithValue != null) {
                    lore = lore + "x[" + multiplyWithValue + "]";
                }

                additions.add(lore);
            } else if (skillLevel == healAmountList.size()) {
                String lore = ChatColor.GREEN + "Heal: " + healAmountList.get(skillLevel - 1) + "%";

                if (multiplyWithValue != null) {
                    lore = lore + "x[" + multiplyWithValue + "]";
                }

                additions.add(lore);
            } else {
                String lore1 = "Heal: " + healAmountList.get(skillLevel - 1) + "%";
                String lore2 = healAmountList.get(skillLevel) + "%";

                if (multiplyWithValue != null) {
                    lore1 = lore1 + "x[" + multiplyWithValue + "]";
                    lore2 = lore2 + "x[" + multiplyWithValue + "]";
                }

                additions.add(ChatColor.GREEN + lore1 + " -> " + lore2);
            }
        }
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
