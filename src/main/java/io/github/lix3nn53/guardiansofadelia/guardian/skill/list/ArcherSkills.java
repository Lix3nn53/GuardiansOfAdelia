package io.github.lix3nn53.guardiansofadelia.guardian.skill.list;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.*;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityRemoveMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.ProjectileMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.SpreadType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.AreaTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SelfTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SingleTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.InitializeTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.LandTrigger;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ArrangementParticle;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class ArcherSkills {

    public static List<Skill> getSet() {
        List<Skill> skills = new ArrayList<>();

        skills.add(getOne());
        skills.add(getTwo());
        skills.add(getThree());
        skills.add(getPassive());
        skills.add(getUltimate());

        return skills;
    }

    private static Skill getOne() {
        List<String> description = new ArrayList<>();
        description.add("Shoot an arrow that blinds target");

        List<Integer> reqLevels = new ArrayList<>();
        reqLevels.add(1);
        reqLevels.add(5);
        reqLevels.add(10);
        reqLevels.add(15);
        reqLevels.add(20);
        reqLevels.add(25);
        reqLevels.add(30);

        List<Integer> reqPoints = new ArrayList<>();
        reqPoints.add(1);
        reqPoints.add(2);
        reqPoints.add(3);
        reqPoints.add(4);
        reqPoints.add(5);
        reqPoints.add(6);
        reqPoints.add(7);

        List<Integer> manaCosts = new ArrayList<>();
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);

        Skill skill = new Skill("Blind Shot", Material.IRON_HOE, 27, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Integer> projectileAmounts = new ArrayList<>();
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 1.9, projectileAmounts, 30,
                0, 1, 0, 200, true, Arrow.class);

        List<Double> damageAmounts = new ArrayList<>();
        damageAmounts.add(1D);
        damageAmounts.add(1D);
        damageAmounts.add(1D);
        damageAmounts.add(1D);
        damageAmounts.add(1D);
        damageAmounts.add(1D);
        DamageMechanic damageMechanic = new DamageMechanic(damageAmounts, DamageMechanic.DamageType.RANGED);

        List<Integer> ticks = new ArrayList<>();
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        List<Integer> amplifiers = new ArrayList<>();
        ticks.add(2);
        ticks.add(2);
        ticks.add(2);
        ticks.add(2);
        PotionEffectMechanic potionEffectMechanic = new PotionEffectMechanic(PotionEffectType.BLINDNESS, ticks, amplifiers);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(projectileMechanic);
        projectileMechanic.addChildren(damageMechanic);
        projectileMechanic.addChildren(potionEffectMechanic);

        return skill;
    }

    private static Skill getTwo() {
        List<String> description = new ArrayList<>();
        description.add("Root nearby targets and gain");
        description.add("movement speed.");

        List<Integer> reqLevels = new ArrayList<>();
        reqLevels.add(1);
        reqLevels.add(5);
        reqLevels.add(10);
        reqLevels.add(15);
        reqLevels.add(20);
        reqLevels.add(25);
        reqLevels.add(30);

        List<Integer> reqPoints = new ArrayList<>();
        reqPoints.add(1);
        reqPoints.add(2);
        reqPoints.add(3);
        reqPoints.add(4);
        reqPoints.add(5);
        reqPoints.add(6);
        reqPoints.add(7);

        List<Integer> manaCosts = new ArrayList<>();
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);

        Skill skill = new Skill("Zephyr", Material.IRON_HOE, 33, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> radiuses = new ArrayList<>();
        radiuses.add(3D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, radiuses);

        List<Integer> ccTicks = new ArrayList<>();
        ccTicks.add(60);
        ccTicks.add(60);
        ccTicks.add(60);
        ccTicks.add(60);
        List<Integer> ccAmplifiers = new ArrayList<>();
        ccTicks.add(999);
        areaTarget.addChildren(new PotionEffectMechanic(PotionEffectType.SLOW, ccTicks, ccAmplifiers));
        areaTarget.addChildren(new PotionEffectMechanic(PotionEffectType.JUMP, ccTicks, ccAmplifiers));

        skill.addTrigger(selfTarget);

        List<Integer> ticks = new ArrayList<>();
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        List<Integer> amplifiers = new ArrayList<>();
        ticks.add(2);
        ticks.add(2);
        ticks.add(2);
        ticks.add(2);
        selfTarget.addChildren(areaTarget);
        selfTarget.addChildren(new PotionEffectMechanic(PotionEffectType.SPEED, ticks, amplifiers));
        List<Integer> repeatAmount = new ArrayList<>();
        repeatAmount.add(8);
        repeatAmount.add(2);
        repeatAmount.add(2);
        repeatAmount.add(2);
        ParticleAnimationMechanic particleAnimationMechanic = new ParticleAnimationMechanic(Particle.REDSTONE, ArrangementParticle.CIRCLE, 1.4, 4, 0, 0, 0,
                0, 0.5, 0, 0, 5, repeatAmount, new Particle.DustOptions(Color.AQUA, 8));
        selfTarget.addChildren(particleAnimationMechanic);

        return skill;
    }

    private static Skill getThree() {
        List<String> description = new ArrayList<>();
        description.add("Jump backwards into the air and");
        description.add("gain resistance to fall damage.");

        List<Integer> reqLevels = new ArrayList<>();
        reqLevels.add(1);
        reqLevels.add(5);
        reqLevels.add(10);
        reqLevels.add(15);
        reqLevels.add(20);
        reqLevels.add(25);
        reqLevels.add(30);

        List<Integer> reqPoints = new ArrayList<>();
        reqPoints.add(1);
        reqPoints.add(2);
        reqPoints.add(3);
        reqPoints.add(4);
        reqPoints.add(5);
        reqPoints.add(6);
        reqPoints.add(7);

        List<Integer> manaCosts = new ArrayList<>();
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);

        Skill skill = new Skill("Purple Wings", Material.IRON_HOE, 41, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> forwards = new ArrayList<>();
        forwards.add(-1.25);
        List<Double> upwards = new ArrayList<>();
        upwards.add(1.2);
        upwards.add(1.35);
        upwards.add(1.5);
        List<Double> right = new ArrayList<>();
        right.add(0D);
        LaunchMechanic launchMechanic = new LaunchMechanic(LaunchMechanic.Relative.CASTER, forwards, upwards, right);

        List<Integer> repeatAmounts = new ArrayList<>();
        repeatAmounts.add(10);
        ParticleAnimationMechanic particleAnimationMechanic = new ParticleAnimationMechanic(Particle.SPELL_WITCH, ArrangementParticle.SPHERE, 1.2,
                4, 0, 0, 0, 0, 0, 0, 0, 4, repeatAmounts, null);

        ImmunityMechanic immunityMechanic = new ImmunityMechanic(EntityDamageEvent.DamageCause.FALL, new ArrayList<>()); //send empty list for infinite

        LandTrigger landTrigger = new LandTrigger();

        ImmunityRemoveMechanic immunityRemoveMechanic = new ImmunityRemoveMechanic(EntityDamageEvent.DamageCause.FALL);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(launchMechanic);
        selfTarget.addChildren(particleAnimationMechanic);
        selfTarget.addChildren(immunityMechanic);

        selfTarget.addChildren(landTrigger);
        landTrigger.addChildren(immunityRemoveMechanic);

        return skill;
    }

    private static Skill getPassive() {
        List<String> description = new ArrayList<>();
        description.add("Jump boost");

        List<Integer> reqLevels = new ArrayList<>();
        reqLevels.add(1);
        reqLevels.add(5);
        reqLevels.add(10);
        reqLevels.add(15);
        reqLevels.add(20);
        reqLevels.add(25);
        reqLevels.add(30);

        List<Integer> reqPoints = new ArrayList<>();
        reqPoints.add(1);
        reqPoints.add(2);
        reqPoints.add(3);
        reqPoints.add(4);
        reqPoints.add(5);
        reqPoints.add(6);
        reqPoints.add(7);

        List<Integer> manaCosts = new ArrayList<>();
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);

        Skill skill = new Skill("Hop Hop", Material.IRON_HOE, 44, description, reqLevels, reqPoints, manaCosts, cooldowns);

        InitializeTrigger initializeTrigger = new InitializeTrigger();

        RepeatMechanic repeatMechanic = new RepeatMechanic(240, new ArrayList<>()); //empty list for infinite

        List<Integer> ticks = new ArrayList<>();
        ticks.add(240);
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        List<Integer> amplifiers = new ArrayList<>();
        ticks.add(2);
        ticks.add(2);
        ticks.add(2);
        ticks.add(2);
        PotionEffectMechanic potionEffectMechanic = new PotionEffectMechanic(PotionEffectType.JUMP, ticks, amplifiers);

        skill.addTrigger(initializeTrigger);
        initializeTrigger.addChildren(repeatMechanic);

        repeatMechanic.addChildren(potionEffectMechanic);
        return skill;
    }

    private static Skill getUltimate() {
        List<String> description = new ArrayList<>();
        description.add("Reveal an area with a flurry of arrows,");
        description.add("dealing waves of damage to opponents.");

        List<Integer> reqLevels = new ArrayList<>();
        reqLevels.add(1);
        reqLevels.add(5);
        reqLevels.add(10);
        reqLevels.add(15);
        reqLevels.add(20);
        reqLevels.add(25);
        reqLevels.add(30);

        List<Integer> reqPoints = new ArrayList<>();
        reqPoints.add(1);
        reqPoints.add(2);
        reqPoints.add(3);
        reqPoints.add(4);
        reqPoints.add(5);
        reqPoints.add(6);
        reqPoints.add(7);

        List<Integer> manaCosts = new ArrayList<>();
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);

        Skill skill = new Skill("Make It Rain", Material.IRON_HOE, 48, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> ranges = new ArrayList<>();
        ranges.add(12D);
        SingleTarget singleTarget = new SingleTarget(false, true, false, 1, ranges, 4);

        List<Integer> projectileAmounts = new ArrayList<>();
        projectileAmounts.add(16);
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.RAIN, 8, 12, 1.9, projectileAmounts, 0,
                0, 0, 200, true, Arrow.class);

        List<Integer> repeatAmounts = new ArrayList<>();
        repeatAmounts.add(2);
        repeatAmounts.add(2);
        repeatAmounts.add(2);
        RepeatMechanic repeatMechanic = new RepeatMechanic(20, repeatAmounts);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(singleTarget);
        singleTarget.addChildren(repeatMechanic);
        repeatMechanic.addChildren(projectileMechanic);
        List<Double> damages = new ArrayList<>();
        damages.add(16D);
        projectileMechanic.addChildren(new DamageMechanic(damages, DamageMechanic.DamageType.RANGED));

        return skill;
    }
}
