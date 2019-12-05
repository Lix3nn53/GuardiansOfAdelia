package io.github.lix3nn53.guardiansofadelia.creatures.entitySkills;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.*;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.AreaTarget;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ArrangementParticle;
import org.bukkit.Particle;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class EntitySkillComponents {

    public static ParticleMechanic getComponentParticleMechanic(Particle particle, double radius, int amount, Particle.DustOptions dustOptions) {
        List<Double> list = new ArrayList<>();
        list.add(2.5D);
        list.add(2.75D);
        list.add(3D);
        list.add(3.25D);
        list.add(3.5D);
        list.add(4D);
        list.add(4D);
        list.add(4D);
        list.add(4D);
        list.add(4D);

        return new ParticleMechanic(particle, ArrangementParticle.CIRCLE, radius, amount, 0, 0, 0, 0, 0.5, 0, 0, dustOptions);
    }

    public static PushMechanic getComponentPushMechanic() {
        List<Double> list = new ArrayList<>();
        list.add(2.5D);
        list.add(2.75D);
        list.add(3D);
        list.add(3.25D);
        list.add(3.5D);
        list.add(4D);
        list.add(4D);
        list.add(4D);
        list.add(4D);
        list.add(4D);

        return new PushMechanic(PushMechanic.PushType.FIXED, list, true);
    }

    public static PotionEffectMechanic getComponentPotionEffectMechanic(PotionEffectType potionEffectType, int duration, int amplifier) {
        List<Integer> durations = new ArrayList<>();
        durations.add(duration);
        durations.add(duration);
        durations.add(duration);
        durations.add(duration);
        durations.add(duration);
        durations.add(duration);
        durations.add(duration);
        durations.add(duration);
        durations.add(duration);
        durations.add(duration);

        List<Integer> amplifiers = new ArrayList<>();
        amplifiers.add(amplifier);
        amplifiers.add(amplifier);
        amplifiers.add(amplifier);
        amplifiers.add(amplifier);
        amplifiers.add(amplifier);
        amplifiers.add(amplifier);
        amplifiers.add(amplifier);
        amplifiers.add(amplifier);
        amplifiers.add(amplifier);
        amplifiers.add(amplifier);

        return new PotionEffectMechanic(potionEffectType, durations, amplifiers);
    }

    public static PushMechanic getComponentPullMechanic() {
        List<Double> speeds = new ArrayList<>();
        speeds.add(-2D);
        speeds.add(-2.5D);
        speeds.add(-3D);
        speeds.add(-3.25D);
        speeds.add(-3.5D);
        speeds.add(-3.75D);
        speeds.add(-4D);
        speeds.add(-4.5D);
        speeds.add(-5D);
        speeds.add(-5.5D);

        return new PushMechanic(PushMechanic.PushType.FIXED, speeds, true);
    }

    public static LaunchMechanic getComponentLaunchMechanic() {
        List<Double> upward = new ArrayList<>();
        upward.add(1.2D);
        upward.add(1.4D);
        upward.add(1.6D);
        upward.add(1.8D);
        upward.add(2D);
        upward.add(2.2D);
        upward.add(2.4D);
        upward.add(2.6D);
        upward.add(2.8D);
        upward.add(3D);
        List<Double> forward = new ArrayList<>();
        forward.add(0D);
        forward.add(0D);
        forward.add(0D);
        forward.add(0D);
        forward.add(0D);
        forward.add(0D);
        List<Double> right = new ArrayList<>();
        right.add(0D);
        right.add(0D);
        right.add(0D);
        right.add(0D);
        right.add(0D);
        right.add(0D);

        return new LaunchMechanic(LaunchMechanic.Relative.TARGET, forward, upward, right);
    }

    public static HealMechanic getComponentHealByAmount() {
        List<Integer> amounts = new ArrayList<>();
        amounts.add(50);
        amounts.add(100);
        amounts.add(200);
        amounts.add(500);
        amounts.add(750);
        amounts.add(1000);
        amounts.add(1250);
        amounts.add(1600);
        amounts.add(2200);
        amounts.add(3600);

        return new HealMechanic(amounts, new ArrayList<>());
    }

    public static AreaTarget getComponentAreaTarget(double radius) {
        List<Double> radiusList = new ArrayList<>();
        radiusList.add(radius);
        radiusList.add(radius);
        radiusList.add(radius);
        radiusList.add(radius);
        radiusList.add(radius);
        radiusList.add(radius);
        radiusList.add(radius);
        radiusList.add(radius);
        radiusList.add(radius);
        radiusList.add(radius);
        return new AreaTarget(false, true, false, 99, radiusList);
    }

    public static DamageMechanic getComponentDamageMechanic(DamageMechanic.DamageType type) {
        List<Double> damages = new ArrayList<>();
        damages.add(180D);
        damages.add(292D);
        damages.add(405D);
        damages.add(540D);
        damages.add(675D);
        damages.add(900D);
        damages.add(900D);
        damages.add(900D);
        damages.add(900D);
        damages.add(900D);

        return new DamageMechanic(damages, type);
    }
}
