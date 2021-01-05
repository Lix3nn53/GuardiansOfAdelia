package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class RepeatMechanic extends MechanicComponent {

    private final long period;
    private final List<Integer> repetitions;

    /**
     * @param period
     * @param repetitions 0 for infinite
     */
    public RepeatMechanic(long period, List<Integer> repetitions) {
        this.period = period;
        this.repetitions = repetitions;
    }

    public RepeatMechanic(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("period")) {
            configLoadError("period");
        }

        if (configurationSection.contains("repetitions")) {
            this.repetitions = configurationSection.getIntegerList("repetitions");
        } else {
            this.repetitions = new ArrayList<>();
        }

        this.period = configurationSection.getInt("period");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        BukkitTask bukkitTask;
        if (!repetitions.isEmpty()) {
            bukkitTask = new BukkitRunnable() {

                int counter;

                @Override
                public void run() {
                    executeChildren(caster, skillLevel, targets, castCounter);

                    counter++;
                    if (counter >= repetitions.get(skillLevel - 1)) {
                        cancel();
                    }
                }
            }.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, period);

            new BukkitRunnable() { //remove task from cast data holder after it stops

                @Override
                public void run() {
                    SkillDataManager.removeRepeatTask(caster, castCounter);
                }
            }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), period * repetitions.get(skillLevel - 1));
        } else {
            bukkitTask = new BukkitRunnable() {

                @Override
                public void run() {
                    executeChildren(caster, skillLevel, targets, castCounter);
                }
            }.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, period);
        }

        SkillDataManager.onRepeatTaskCreate(caster, bukkitTask, castCounter);

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (skillLevel == 0) {
            String repeat = "";
            if (!repetitions.isEmpty()) {
                repeat = " for " + repetitions.get(skillLevel) + " times";
            }

            additions.add(ChatColor.LIGHT_PURPLE + "Repeat every " + (int) (period / 20 + 0.5) + " seconds" + repeat);
        } else if (skillLevel == repetitions.size()) {
            String repeat = " for " + repetitions.get(skillLevel - 1) + " times";

            additions.add(ChatColor.LIGHT_PURPLE + "Repeat every " + (int) (period / 20 + 0.5) + " seconds " + repeat);
        } else {
            String repeat = "";
            if (!repetitions.isEmpty()) {
                repeat = " for " + repetitions.get(skillLevel - 1) + " times -> " + repetitions.get(skillLevel) + " times";
            }

            additions.add(ChatColor.LIGHT_PURPLE + "Repeat every " + (int) (period / 20 + 0.5) + " seconds " + repeat);
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}