package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.commands.admin.CommandAdmin;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class RepeatMechanic extends MechanicComponent {

    private final long period;
    private final List<Integer> repetitions;

    //conditions
    private final String valueConditionKey;
    private final int valueConditionMinValue;
    private final int valueConditionMaxValue;

    /**
     * @param period
     * @param repetitions 0 for infinite
     */
    public RepeatMechanic(long period, List<Integer> repetitions) {
        super(false);

        this.period = period;
        this.repetitions = repetitions;
        this.valueConditionKey = null;
        this.valueConditionMaxValue = 0;
        this.valueConditionMinValue = 0;
    }

    public RepeatMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("period")) {
            configLoadError("period");
        }

        if (configurationSection.contains("repetitions")) {
            this.repetitions = configurationSection.getIntegerList("repetitions");
        } else {
            this.repetitions = new ArrayList<>();
        }

        this.period = configurationSection.getInt("period");

        this.valueConditionKey = configurationSection.contains("valueConditionKey") ? configurationSection.getString("valueConditionKey") : null;
        this.valueConditionMinValue = configurationSection.contains("valueConditionMinValue") ? configurationSection.getInt("valueConditionMinValue") : 0;
        this.valueConditionMaxValue = configurationSection.contains("valueConditionMaxValue") ? configurationSection.getInt("valueConditionMaxValue") : 0;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        BukkitTask bukkitTask;
        if (!repetitions.isEmpty()) {
            bukkitTask = new BukkitRunnable() {

                int counter;

                @Override
                public void run() {
                    if (valueConditionKey != null) {
                        int value = SkillDataManager.getValue(caster, valueConditionKey);

                        if (value < valueConditionMinValue || value > valueConditionMaxValue) {
                            cancel();
                            SkillDataManager.removeRepeatTask(caster, castCounter);
                            return;
                        }
                    }

                    executeChildren(caster, skillLevel, targets, castCounter, skillIndex);
                    counter++;

                    if (counter >= repetitions.get(skillLevel - 1)) {
                        cancel();
                        SkillDataManager.removeRepeatTask(caster, castCounter);
                    }
                }
            }.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, period);
        } else { // Without counter limit
            bukkitTask = new BukkitRunnable() {

                @Override
                public void run() {
                    if (valueConditionKey != null) {
                        int value = SkillDataManager.getValue(caster, valueConditionKey);

                        if (value < valueConditionMinValue || value > valueConditionMaxValue) {
                            if (caster instanceof Player) {
                                Player player = (Player) caster;
                                if (CommandAdmin.DEBUG_MODE) player.sendMessage("cancel repeat cuz value condition");
                            }

                            cancel();
                            SkillDataManager.removeRepeatTask(caster, castCounter);
                            return;
                        }
                    }

                    executeChildren(caster, skillLevel, targets, castCounter, skillIndex);
                }
            }.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, period);
        }

        SkillDataManager.onRepeatTaskCreate(caster, bukkitTask, castCounter);

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        if (skillLevel == 0) {
            String repeat = "";
            if (!repetitions.isEmpty()) {
                repeat = " for " + repetitions.get(skillLevel) + " times";
            }

            additions.add(ChatPalette.PURPLE_LIGHT + "Repeat every " + (int) (period / 20 + 0.5) + " seconds" + repeat);
        } else if (skillLevel == repetitions.size()) {
            String repeat = " for " + repetitions.get(skillLevel - 1) + " times";

            additions.add(ChatPalette.PURPLE_LIGHT + "Repeat every " + (int) (period / 20 + 0.5) + " seconds" + repeat);
        } else {
            String repeat = "";
            if (!repetitions.isEmpty()) {
                repeat = " for " + repetitions.get(skillLevel - 1) + " times -> " + repetitions.get(skillLevel) + " times";
            }

            additions.add(ChatPalette.PURPLE_LIGHT + "Repeat every " + (int) (period / 20 + 0.5) + " seconds" + repeat);
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}