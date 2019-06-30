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

    private final double amount;
    private final long period;
    private final int repetations;

    public RepeatMechanic(double amount, long period, int repetations) {
        this.amount = amount;
        this.period = period;
        this.repetations = repetations;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {
        if (targets.isEmpty()) return false;

        BukkitTask bukkitTask = new BukkitRunnable() {

            int counter;

            @Override
            public void run() {
                executeChildren(caster, skillLevel, targets, castKey);

                counter++;
                if (counter >= repetations) {
                    cancel();
                }
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, period);

        SkillDataManager.onRepeatTaskCreate(castKey, bukkitTask);

        new BukkitRunnable() { //remove task from cast data holder after it stops

            @Override
            public void run() {
                SkillDataManager.removeRepeatTask(castKey, bukkitTask);
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), period * repetations);

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        lore.add("Repeat: " + amount);
        return lore;
    }
}