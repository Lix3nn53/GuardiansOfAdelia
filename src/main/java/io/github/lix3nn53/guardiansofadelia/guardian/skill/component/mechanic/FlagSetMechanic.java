package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class FlagSetMechanic extends MechanicComponent {

    private final String key;
    private final List<Integer> ticks;
    private final boolean isUnique;

    private final String multiplyDurationValue;

    public FlagSetMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("key")) {
            configLoadError("key");
        }

        this.key = configurationSection.getString("key");
        this.isUnique = configurationSection.contains("isUnique") && configurationSection.getBoolean("isUnique");

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

        for (LivingEntity target : targets) {
            if (isUnique) {
                SkillDataManager.addFlag(target, key + castCounter);
            } else {
                SkillDataManager.addFlag(target, key);
            }


            if (!ticks.isEmpty()) {
                int ticksCurrent = ticks.get(skillLevel - 1);

                if (multiplyDurationValue != null) {
                    int value = SkillDataManager.getValue(target, multiplyDurationValue);
                    if (value > 0) {
                        ticksCurrent *= value;
                    }
                }

                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (isUnique) {
                            SkillDataManager.removeFlag(target, key + castCounter);
                        } else {
                            SkillDataManager.removeFlag(target, key);
                        }
                    }
                }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), ticksCurrent);
            }
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
