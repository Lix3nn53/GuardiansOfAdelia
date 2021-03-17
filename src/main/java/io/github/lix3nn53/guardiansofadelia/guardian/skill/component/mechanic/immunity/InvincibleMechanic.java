package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class InvincibleMechanic extends MechanicComponent {

    private final List<Integer> ticks;

    public InvincibleMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (configurationSection.contains("ticks")) {
            this.ticks = configurationSection.getIntegerList("ticks");
        } else {
            this.ticks = new ArrayList<>();
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        for (LivingEntity ent : targets) {
            ImmunityListener.addInvincible(ent);
        }

        if (!ticks.isEmpty()) {
            new BukkitRunnable() { //remove buffs from buffed players

                @Override
                public void run() {
                    for (LivingEntity ent : targets) {
                        ImmunityListener.removeInvincible(ent);
                    }
                }
            }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), ticks.get(skillLevel - 1));
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        if (ticks.isEmpty()) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        if (skillLevel == 0) {
            additions.add(ChatColor.GOLD + "Invincible duration: " + (ticks.get(skillLevel) / 20));
        } else if (skillLevel == ticks.size()) {
            additions.add(ChatColor.GOLD + "Invincible duration: " + (ticks.get(skillLevel - 1) / 20));
        } else {
            additions.add(ChatColor.GOLD + "Invincible duration: " + (ticks.get(skillLevel - 1) / 20) + " -> " + (ticks.get(skillLevel) / 20));
        }
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
