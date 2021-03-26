package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.particles;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ParticleAnimationMechanic extends ParticleMechanic {

    private final float yawIncrease;
    private final float pitchIncrease;

    private final long frequency;
    private final List<Integer> repeatAmount;

    private final List<Double> dataIncreaseList = new ArrayList<>();

    //conditions
    private final String valueConditionKey;
    private final int valueConditionMinValue;
    private final int valueConditionMaxValue;


    public ParticleAnimationMechanic(ConfigurationSection configurationSection) {
        super(configurationSection);

        for (int i = 1; i < 10; i++) {
            if (!configurationSection.contains("dataIncrease" + i)) break;
            this.dataIncreaseList.add(configurationSection.getDouble("dataIncrease" + i));
        }

        this.yawIncrease = configurationSection.contains("yawIncrease") ? (float) configurationSection.getDouble("yawIncrease") : 0;
        this.pitchIncrease = configurationSection.contains("pitchIncrease") ? (float) configurationSection.getDouble("pitchIncrease") : 0;

        this.frequency = configurationSection.getInt("frequency");
        this.repeatAmount = configurationSection.getIntegerList("repeatAmount");

        this.valueConditionKey = configurationSection.contains("valueConditionKey") ? configurationSection.getString("valueConditionKey") : null;
        this.valueConditionMinValue = configurationSection.contains("valueConditionMinValue") ? configurationSection.getInt("valueConditionMinValue") : 0;
        this.valueConditionMaxValue = configurationSection.contains("valueConditionMaxValue") ? configurationSection.getInt("valueConditionMaxValue") : 0;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        int repeatAmountCurrent = repeatAmount != null ? repeatAmount.get(skillLevel - 1) : 0;
        for (LivingEntity ent : targets) {
            new BukkitRunnable() {

                int counter = 0;

                @Override
                public void run() {
                    // Add increments
                    float currentYaw = yaw + (yawIncrease * counter);
                    float currentPitch = pitch + (pitchIncrease * counter);

                    // Play particle
                    playParticle(ent, skillLevel, centerEye, resetY, forwardList, upward, right, dataIndexToDataList, particleArrangement, rotation,
                            rotationMatchEye, currentYaw, currentPitch, offsetx, offsety, offsetz, dataIncreaseList, counter);

                    counter++;
                    if (counter >= repeatAmountCurrent) {
                        cancel();
                    }

                    if (valueConditionKey != null) {
                        int value = SkillDataManager.getValue(ent, valueConditionKey);

                        if (value < valueConditionMinValue || value > valueConditionMaxValue) {
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
