package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.particles;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ParticleAnimationMechanic extends ParticleMechanic {

    private final boolean playback;

    private final float yawIncrease;
    private final float pitchIncrease;
    private final double upwardIncrease;

    private final long frequency;
    private final List<Integer> repeatAmount;

    private final List<Double> dataIncreaseList = new ArrayList<>();

    //conditions
    private final String valueConditionKey;
    private final int valueConditionMinValue;
    private final int valueConditionMaxValue;

    private final String flagConditionKey;
    private final boolean flagIsSet;

    private final boolean dontStop; // Run animation until a condition fails

    private final String multiplyDurationValue;

    public ParticleAnimationMechanic(ConfigurationSection configurationSection) {
        super(configurationSection);

        for (int i = 1; i < 10; i++) {
            if (!configurationSection.contains("dataIncrease" + i)) break;
            this.dataIncreaseList.add(configurationSection.getDouble("dataIncrease" + i));
        }

        this.playback = configurationSection.contains("playback") && configurationSection.getBoolean("playback");

        this.yawIncrease = configurationSection.contains("yawIncrease") ? (float) configurationSection.getDouble("yawIncrease") : 0;
        this.pitchIncrease = configurationSection.contains("pitchIncrease") ? (float) configurationSection.getDouble("pitchIncrease") : 0;
        this.upwardIncrease = configurationSection.contains("upwardIncrease") ? configurationSection.getDouble("upwardIncrease") : 0;

        this.frequency = configurationSection.getInt("frequency");
        this.repeatAmount = configurationSection.getIntegerList("repeatAmount");

        this.valueConditionKey = configurationSection.contains("valueConditionKey") ? configurationSection.getString("valueConditionKey") : null;
        this.valueConditionMinValue = configurationSection.contains("valueConditionMinValue") ? configurationSection.getInt("valueConditionMinValue") : 0;
        this.valueConditionMaxValue = configurationSection.contains("valueConditionMaxValue") ? configurationSection.getInt("valueConditionMaxValue") : 0;

        this.flagConditionKey = configurationSection.contains("flagConditionKey") ? configurationSection.getString("flagConditionKey") : null;
        this.flagIsSet = configurationSection.contains("flagIsSet") && configurationSection.getBoolean("flagIsSet");

        this.dontStop = configurationSection.contains("dontStop") && configurationSection.getBoolean("dontStop");

        this.multiplyDurationValue = configurationSection.contains("multiplyDurationValue") ? configurationSection.getString("multiplyDurationValue") : null;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        int repeat = repeatAmount != null ? repeatAmount.get(skillLevel - 1) : 0;
        for (LivingEntity target : targets) {
            int value = SkillDataManager.getValue(target, multiplyDurationValue);
            int repeatAmountCurrent = value > 0 ? repeat * value : repeat;

            new BukkitRunnable() {

                int counter = 0;
                boolean playingBack = false;

                @Override
                public void run() {
                    // Add increments
                    float currentYaw = yaw + (yawIncrease * counter);
                    float currentPitch = pitch + (pitchIncrease * counter);

                    double currentUpward = upward + (upwardIncrease * counter);

                    // Play particle
                    playParticle(target, skillLevel, centerEye, resetY, forwardList, currentUpward, right, dataIndexToDataList, particleArrangement, rotation,
                            rotationMatchEye, currentYaw, currentPitch, offsetx, offsety, offsetz, dataIncreaseList, counter);

                    if (this.playingBack) {
                        counter--;
                    } else {
                        counter++;
                    }

                    if (!this.playingBack && counter >= repeatAmountCurrent) {
                        if (playback) {
                            this.playingBack = true;
                        } else {
                            if (dontStop) {
                                counter = 0; // no playback so start from zero
                            } else {
                                cancel();
                            }
                        }
                    } else if (this.playingBack && counter <= 0) {
                        if (dontStop) {
                            counter = 0;
                            this.playingBack = false;
                        } else {
                            cancel();
                        }
                    }

                    if (valueConditionKey != null) {
                        int value = SkillDataManager.getValue(target, valueConditionKey);

                        if (value < valueConditionMinValue || value > valueConditionMaxValue) {
                            cancel();
                        }
                    }

                    if (flagConditionKey != null) {
                        boolean b = SkillDataManager.hasFlag(target, flagConditionKey);
                        if (b != flagIsSet) {
                            cancel();
                        }
                    }
                }
            }.runTaskTimer(GuardiansOfAdelia.getInstance(), 0L, frequency);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
