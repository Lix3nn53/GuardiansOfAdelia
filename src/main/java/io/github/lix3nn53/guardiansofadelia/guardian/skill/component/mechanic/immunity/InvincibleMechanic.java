package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class InvincibleMechanic extends MechanicComponent {

    private final List<Integer> ticks;

    private final String multiplyDurationValue;

    public InvincibleMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (configurationSection.contains("ticks")) {
            this.ticks = configurationSection.getIntegerList("ticks");
        } else {
            this.ticks = new ArrayList<>();
        }

        this.multiplyDurationValue = configurationSection.contains("multiplyDurationValue") ? configurationSection.getString("multiplyDurationValue") : null;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        for (LivingEntity ent : targets) {
            ImmunityListener.addInvincible(ent);
        }

        if (!ticks.isEmpty()) {
            for (LivingEntity target : targets) {
                int ticksCurrent = ticks.get(skillLevel - 1);
                if (multiplyDurationValue != null) {
                    int value = SkillDataManager.getValue(target, multiplyDurationValue);
                    if (value > 0) {
                        ticksCurrent *= value;
                    }
                }
                new BukkitRunnable() { //remove buffs from buffed players

                    @Override
                    public void run() {
                        ImmunityListener.removeInvincible(target);
                    }
                }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), ticksCurrent);
            }
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        if (ticks.isEmpty()) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        if (skillLevel == 0) {
            String s = ChatColor.GOLD + "Invincible duration: " + (ticks.get(skillLevel) / 20);

            if (multiplyDurationValue != null) {
                s += " x[" + multiplyDurationValue + "]";
            }

            additions.add(s);
        } else if (skillLevel == ticks.size()) {
            String s = ChatColor.GOLD + "Invincible duration: " + (ticks.get(skillLevel - 1) / 20);

            if (multiplyDurationValue != null) {
                s += " x[" + multiplyDurationValue + "]";
            }

            additions.add(s);
        } else {
            String s = ChatColor.GOLD + "Invincible duration: " + (ticks.get(skillLevel - 1) / 20) + " -> " + (ticks.get(skillLevel) / 20);

            if (multiplyDurationValue != null) {
                s += " x[" + multiplyDurationValue + "]";
            }

            additions.add(s);
        }
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
