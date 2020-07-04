package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class DisarmMechanic extends MechanicComponent {

    private final List<Integer> duration;

    public DisarmMechanic(List<Integer> duration) {
        this.duration = duration;
    }

    public DisarmMechanic(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("durations")) {
            configLoadError("durations");
        }

        this.duration = configurationSection.getIntegerList("durations");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        for (LivingEntity target : targets) {
            StatusEffectManager.setDisarmed(target);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                for (LivingEntity target : targets) {
                    StatusEffectManager.removeDisarmed(target);
                }
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), duration.get(skillLevel - 1));

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (skillLevel == 0) {
            additions.add(ChatColor.RED + "Disarm duration: " + (int) ((duration.get(skillLevel) / 20) + 0.5) + " seconds");
        } else if (skillLevel == duration.size()) {
            additions.add(ChatColor.RED + "Disarm duration: " + (int) ((duration.get(skillLevel - 1) / 20) + 0.5) + " seconds");
        } else {
            additions.add(ChatColor.RED + "Disarm duration: " + (int) ((duration.get(skillLevel - 1) / 20) + 0.5) + " seconds -> " + (int) ((duration.get(skillLevel) / 20) + 0.5));
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
