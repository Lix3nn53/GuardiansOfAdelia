package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class RepeatMechanic extends MechanicComponent {

    private final long period;
    private final int repetitions;

    /**
     * @param period
     * @param repetitions 0 for infinite
     */
    public RepeatMechanic(long period, int repetitions) {
        this.period = period;
        this.repetitions = repetitions;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {
        if (targets.isEmpty()) return false;

        BukkitTask bukkitTask = new BukkitRunnable() {

            int counter;

            @Override
            public void run() {
                executeChildren(caster, skillLevel, targets, castKey);

                if (repetitions != 0) {
                    counter++;
                    if (counter >= repetitions) {
                        cancel();
                    }
                }
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, period);

        SkillDataManager.onRepeatTaskCreate(castKey, bukkitTask);

        new BukkitRunnable() { //remove task from cast data holder after it stops

            @Override
            public void run() {
                SkillDataManager.removeRepeatTask(castKey, bukkitTask);
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), period * repetitions);

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        lore.add("Repeat every " + (int) (period / 20 + 0.5) + " seconds, " + repetitions + " times");
        return lore;
    }
}