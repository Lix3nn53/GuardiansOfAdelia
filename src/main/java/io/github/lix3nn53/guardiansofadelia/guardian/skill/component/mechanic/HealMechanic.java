package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;

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

            AttributeInstance attribute = ent.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if (attribute == null) continue;

            double maxHealth = attribute.getValue();

            if (currentHealth == maxHealth) continue;

            double nextHealth = currentHealth;

            if (!healAmount.isEmpty()) {
                nextHealth = currentHealth + healAmount.get(skillLevel - 1);
            }

            if (!healPercent.isEmpty()) {
                nextHealth = nextHealth + (maxHealth * healPercent.get(skillLevel - 1));
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
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!healAmount.isEmpty()) {
            if (skillLevel == 0) {
                additions.add(ChatColor.GREEN + "Health regen: " + healAmount.get(skillLevel));
            } else if (skillLevel == healAmount.size()) {
                additions.add(ChatColor.GREEN + "Health regen: " + healAmount.get(skillLevel - 1));
            } else {
                additions.add(ChatColor.GREEN + "Health regen: " + healAmount.get(skillLevel - 1) + " -> " + healAmount.get(skillLevel));
            }
        }
        if (!healPercent.isEmpty()) {
            if (skillLevel == 0) {
                additions.add(ChatColor.GREEN + "Health regen: " + healPercent.get(skillLevel) + "%");
            } else if (skillLevel == healPercent.size()) {
                additions.add(ChatColor.GREEN + "Health regen: " + healPercent.get(skillLevel - 1) + "%");
            } else {
                additions.add(ChatColor.GREEN + "Health regen: " + healPercent.get(skillLevel - 1) + "%" + " -> " + healPercent.get(skillLevel) + "%");
            }
        }
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
