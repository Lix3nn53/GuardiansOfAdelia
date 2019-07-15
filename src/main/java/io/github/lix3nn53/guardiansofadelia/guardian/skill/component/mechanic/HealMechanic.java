package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class HealMechanic extends MechanicComponent {

    private final List<Integer> healAmount;
    private final List<Double> healPercent;

    public HealMechanic(List<Integer> healAmount, List<Double> healPercent) {
        this.healAmount = healAmount;
        this.healPercent = healPercent;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
        if (targets.isEmpty()) return false;

        boolean healed = false;
        for (LivingEntity ent : targets) {
            double currentHealth = ent.getHealth();
            double maxHealth = ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

            if (currentHealth == maxHealth) continue;

            double nextHealth = currentHealth;

            if (!healAmount.isEmpty()) {
                if (healAmount.get(skillLevel - 1) > 0) {
                    nextHealth = currentHealth + healAmount.get(skillLevel - 1);
                }
            }

            if (!healPercent.isEmpty()) {
                if (healPercent.get(skillLevel - 1) > 0) {
                    nextHealth = nextHealth + (maxHealth * healPercent.get(skillLevel - 1));
                }
            }

            if (nextHealth > maxHealth) {
                nextHealth = maxHealth;
            }

            ent.setHealth(nextHealth);
            healed = true;
        }

        return healed;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        if (healAmount.get(skillLevel - 1) > 0) {
            if (skillLevel == 0 || skillLevel == healAmount.size()) {
                lore.add(ChatColor.GREEN + "Health regen: " + healAmount.get(skillLevel));
            } else {
                lore.add(ChatColor.GREEN + "Health regen: " + healAmount.get(skillLevel - 1) + " -> " + healAmount.get(skillLevel));
            }
        }
        if (healPercent.get(skillLevel - 1) > 0) {
            if (skillLevel == 0 || skillLevel == healPercent.size()) {
                lore.add(ChatColor.GREEN + "Health regen: " + healPercent.get(skillLevel) + "%");
            } else {
                lore.add(ChatColor.GREEN + "Health regen: " + healPercent.get(skillLevel - 1) + "%" + " -> " + healPercent.get(skillLevel) + "%");
            }
        }
        return lore;
    }
}
