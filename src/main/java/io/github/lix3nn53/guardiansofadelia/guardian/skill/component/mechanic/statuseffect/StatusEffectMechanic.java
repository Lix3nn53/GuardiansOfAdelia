package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class StatusEffectMechanic extends MechanicComponent {

    private final List<StatusEffectType> statusEffectTypes = new ArrayList<>();
    private final List<Integer> durations;

    public StatusEffectMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("durations")) {
            configLoadError("durations");
        }

        this.durations = configurationSection.getIntegerList("durations");
        List<String> statusEffectTypesStr = configurationSection.getStringList("statusEffectTypes");
        for (String str : statusEffectTypesStr) {
            StatusEffectType byName = StatusEffectType.valueOf(str);
            this.statusEffectTypes.add(byName);
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        int duration = durations.get(skillLevel - 1);

        for (LivingEntity target : targets) {
            for (StatusEffectType effectType : statusEffectTypes) {
                StatusEffectManager.addStatus(target, effectType);

                // Add freeze effect
                if (effectType.equals(StatusEffectType.ROOT) || effectType.equals(StatusEffectType.STUN)) {
                    int maxFreezeTicks = target.getMaxFreezeTicks();
                    int freezeTicks = Math.min(duration, maxFreezeTicks);

                    target.setFreezeTicks(freezeTicks);

                    // Custom code for mobs
                    if (!(target instanceof Player)) {
                        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration, 5));
                    }
                }
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
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), duration);

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        for (StatusEffectType type : statusEffectTypes) {
            if (skillLevel == 0) {
                additions.add(ChatColor.RED + type.toString() + " duration: " + (int) ((durations.get(skillLevel) / 20) + 0.5) + " seconds");
            } else if (skillLevel == durations.size()) {
                additions.add(ChatColor.RED + type.toString() + " duration: " + (int) ((durations.get(skillLevel - 1) / 20) + 0.5) + " seconds");
            } else {
                additions.add(ChatColor.RED + type.toString() + " duration: " + (int) ((durations.get(skillLevel - 1) / 20) + 0.5) + " seconds -> " + (int) ((durations.get(skillLevel) / 20) + 0.5));
            }
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
