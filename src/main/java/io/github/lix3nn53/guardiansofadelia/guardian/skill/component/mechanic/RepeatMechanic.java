package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

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

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
        if (targets.isEmpty()) return false;

        BukkitTask bukkitTask = new BukkitRunnable() {

            int counter;

            @Override
            public void run() {
                executeChildren(caster, skillLevel, targets);

                if (!repetitions.isEmpty()) {
                    counter++;
                    if (counter >= repetitions.get(skillLevel - 1)) {
                        cancel();
                    }
                }
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, period);

        SkillDataManager.onRepeatTaskCreate(caster, bukkitTask);

        new BukkitRunnable() { //remove task from cast data holder after it stops

            @Override
            public void run() {
                SkillDataManager.removeRepeatTask(caster, bukkitTask);
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), period * repetitions.get(skillLevel - 1));

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (skillLevel == 0 || skillLevel == repetitions.size()) {
            additions.add(ChatColor.LIGHT_PURPLE + "Repeat every " + (int) (period / 20 + 0.5) + " seconds for " + repetitions.get(skillLevel) + " times");
        } else {
            additions.add(ChatColor.LIGHT_PURPLE + "Repeat every " + (int) (period / 20 + 0.5) + " seconds for " + repetitions.get(skillLevel - 1) + " times -> " + repetitions.get(skillLevel) + " times");
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}