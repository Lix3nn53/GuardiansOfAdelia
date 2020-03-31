package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class HealMechanic extends MechanicComponent {

    private final List<Integer> healAmountList;
    private final List<Double> healPercentList;

    public HealMechanic(List<Integer> healAmountList, List<Double> healPercentList) {
        this.healAmountList = healAmountList;
        this.healPercentList = healPercentList;
    }

    public HealMechanic(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("healAmountList")) {
            configLoadError("healAmountList");
        }

        if (!configurationSection.contains("healPercentList")) {
            configLoadError("healPercentList");
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

            double nextHealth = currentHealth;

            if (!healAmountList.isEmpty()) {
                nextHealth = currentHealth + healAmountList.get(skillLevel - 1);
            }

            if (!healPercentList.isEmpty()) {
                nextHealth = nextHealth + (maxHealth * healPercentList.get(skillLevel - 1));
            }

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
                additions.add(ChatColor.GREEN + "Health regen: " + healAmountList.get(skillLevel));
            } else if (skillLevel == healAmountList.size()) {
                additions.add(ChatColor.GREEN + "Health regen: " + healAmountList.get(skillLevel - 1));
            } else {
                additions.add(ChatColor.GREEN + "Health regen: " + healAmountList.get(skillLevel - 1) + " -> " + healAmountList.get(skillLevel));
            }
        }
        if (!healPercentList.isEmpty()) {
            if (skillLevel == 0) {
                additions.add(ChatColor.GREEN + "Health regen: " + healPercentList.get(skillLevel) + "%");
            } else if (skillLevel == healPercentList.size()) {
                additions.add(ChatColor.GREEN + "Health regen: " + healPercentList.get(skillLevel - 1) + "%");
            } else {
                additions.add(ChatColor.GREEN + "Health regen: " + healPercentList.get(skillLevel - 1) + "%" + " -> " + healPercentList.get(skillLevel) + "%");
            }
        }
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
