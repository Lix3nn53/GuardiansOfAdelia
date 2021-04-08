package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class StatusEffectMechanic extends MechanicComponent {

    private final List<StatusEffectType> statusEffectTypes = new ArrayList<>();
    private final List<Integer> duration;

    public StatusEffectMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("durations")) {
            configLoadError("durations");
        }

        this.duration = configurationSection.getIntegerList("durations");
        List<String> statusEffectTypesStr = configurationSection.getStringList("statusEffectTypes");
        for (String str : statusEffectTypesStr) {
            StatusEffectType byName = StatusEffectType.valueOf(str);
            this.statusEffectTypes.add(byName);
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        for (LivingEntity target : targets) {
            for (StatusEffectType effectType : statusEffectTypes) {
                StatusEffectManager.addStatus(target, effectType);
            }
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                for (LivingEntity target : targets) {
                    for (StatusEffectType effectType : statusEffectTypes) {
                        StatusEffectManager.removeStatus(target, effectType);
                    }
                }
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), duration.get(skillLevel - 1));

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        for (StatusEffectType type : statusEffectTypes) {
            if (skillLevel == 0) {
                additions.add(ChatColor.RED + type.toString() + " duration: " + (int) ((duration.get(skillLevel) / 20) + 0.5) + " seconds");
            } else if (skillLevel == duration.size()) {
                additions.add(ChatColor.RED + type.toString() + " duration: " + (int) ((duration.get(skillLevel - 1) / 20) + 0.5) + " seconds");
            } else {
                additions.add(ChatColor.RED + type.toString() + " duration: " + (int) ((duration.get(skillLevel - 1) / 20) + 0.5) + " seconds -> " + (int) ((duration.get(skillLevel) / 20) + 0.5));
            }
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
