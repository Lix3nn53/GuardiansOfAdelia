package io.github.lix3nn53.guardiansofadelia.guardian.skill.list;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition.ValueCondition;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.*;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityRemoveMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.ProjectileMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.SpreadType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.AreaTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SelfTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SingleTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.InitializeTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.LandTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.RangedAttackTrigger;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ArrangementParticle;
import org.bukkit.ChatColor;
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
        description.add(ChatColor.GRAY + "Shoot an arrow that poisons target");

        List<Integer> reqLevels = new ArrayList<>();
        reqLevels.add(1);
        reqLevels.add(8);
        reqLevels.add(16);
        reqLevels.add(24);
        reqLevels.add(32);
        reqLevels.add(48);

        List<Integer> reqPoints = new ArrayList<>();
        reqPoints.add(1);
        reqPoints.add(1);
        reqPoints.add(1);
        reqPoints.add(2);
        reqPoints.add(2);
        reqPoints.add(2);

        List<Integer> manaCosts = new ArrayList<>();
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(12);
        cooldowns.add(11);
        cooldowns.add(10);
        cooldowns.add(9);
        cooldowns.add(8);
        cooldowns.add(7);

        Skill skill = new Skill("Poison Arrow", 6, Material.IRON_HOE, 27, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Integer> projectileAmounts = new ArrayList<>();
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 2.7, projectileAmounts, 30,
                0, 1, 0, 200, true, Arrow.class, Particle.REDSTONE,
                ArrangementParticle.CIRCLE, 0.5, 4, new Particle.DustOptions(Color.GREEN, 2), false);

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
        ticks.add(80);
        ticks.add(100);
        ticks.add(120);
        ticks.add(140);
        ticks.add(160);
        List<Integer> amplifiers = new ArrayList<>();
        amplifiers.add(1);
        amplifiers.add(1);
        amplifiers.add(1);
        amplifiers.add(1);
        amplifiers.add(2);
        amplifiers.add(2);
        PotionEffectMechanic potionEffectMechanic = new PotionEffectMechanic(PotionEffectType.POISON, ticks, amplifiers);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(projectileMechanic);
        selfTarget.addChildren(new SoundMechanic(GoaSound.SKILL_POISON_ARROW));
        projectileMechanic.addChildren(damageMechanic);
        projectileMechanic.addChildren(potionEffectMechanic);

        return skill;
    }

    private static Skill getTwo() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Root nearby targets and gain movement speed");

        List<Integer> reqLevels = new ArrayList<>();
        reqLevels.add(5);
        reqLevels.add(12);
        reqLevels.add(22);
        reqLevels.add(35);
        reqLevels.add(48);
        reqLevels.add(55);

        List<Integer> reqPoints = new ArrayList<>();
        reqPoints.add(1);
        reqPoints.add(2);
        reqPoints.add(2);
        reqPoints.add(3);
        reqPoints.add(3);
        reqPoints.add(3);

        List<Integer> manaCosts = new ArrayList<>();
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(16);
        cooldowns.add(16);
        cooldowns.add(16);
        cooldowns.add(16);
        cooldowns.add(16);
        cooldowns.add(16);

        Skill skill = new Skill("Zephyr", 6, Material.IRON_HOE, 33, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> radiuses = new ArrayList<>();
        radiuses.add(3D);
        radiuses.add(3.5D);
        radiuses.add(4D);
        radiuses.add(4.5D);
        radiuses.add(5D);
        radiuses.add(6D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, radiuses);

        List<Integer> ccTicks = new ArrayList<>();
        ccTicks.add(40);
        ccTicks.add(50);
        ccTicks.add(60);
        ccTicks.add(70);
        ccTicks.add(80);
        ccTicks.add(90);
        List<Integer> ccAmplifiers = new ArrayList<>();
        ccAmplifiers.add(999);
        ccAmplifiers.add(999);
        ccAmplifiers.add(999);
        ccAmplifiers.add(999);
        ccAmplifiers.add(999);
        ccAmplifiers.add(999);
        areaTarget.addChildren(new PotionEffectMechanic(PotionEffectType.SLOW, ccTicks, ccAmplifiers));
        areaTarget.addChildren(new PotionEffectMechanic(PotionEffectType.JUMP, ccTicks, ccAmplifiers));

        skill.addTrigger(selfTarget);

        List<Integer> ticks = new ArrayList<>();
        ticks.add(80);
        ticks.add(100);
        ticks.add(120);
        ticks.add(140);
        ticks.add(160);
        ticks.add(200);
        List<Integer> amplifiers = new ArrayList<>();
        amplifiers.add(1);
        amplifiers.add(2);
        amplifiers.add(3);
        amplifiers.add(4);
        amplifiers.add(5);
        amplifiers.add(6);
        selfTarget.addChildren(areaTarget);
        selfTarget.addChildren(new PotionEffectMechanic(PotionEffectType.SPEED, ticks, amplifiers));
        List<Integer> repeatAmount = new ArrayList<>();
        repeatAmount.add(16);
        repeatAmount.add(20);
        repeatAmount.add(24);
        repeatAmount.add(28);
        repeatAmount.add(32);
        repeatAmount.add(40);
        ParticleAnimationMechanic particleAnimationMechanic = new ParticleAnimationMechanic(Particle.REDSTONE, ArrangementParticle.CIRCLE, 1, 4, 0, 0, 0,
                0, 0.5, 0, 0, 5, repeatAmount, new Particle.DustOptions(Color.AQUA, 8));
        selfTarget.addChildren(particleAnimationMechanic);
        selfTarget.addChildren(new SoundMechanic(GoaSound.SKILL_SONIC_BOOM));

        return skill;
    }

    private static Skill getThree() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Jump backwards into the sky and");
        description.add(ChatColor.GRAY + "gain resistance to fall damage");

        List<Integer> reqLevels = new ArrayList<>();
        reqLevels.add(10);
        reqLevels.add(18);
        reqLevels.add(26);
        reqLevels.add(38);
        reqLevels.add(50);
        reqLevels.add(64);

        List<Integer> reqPoints = new ArrayList<>();
        reqPoints.add(1);
        reqPoints.add(2);
        reqPoints.add(2);
        reqPoints.add(3);
        reqPoints.add(3);
        reqPoints.add(3);

        List<Integer> manaCosts = new ArrayList<>();
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(24);
        cooldowns.add(24);
        cooldowns.add(24);
        cooldowns.add(24);
        cooldowns.add(24);
        cooldowns.add(24);

        Skill skill = new Skill("Purple Wings", 6, Material.IRON_HOE, 41, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> forwards = new ArrayList<>();
        forwards.add(-1.25);
        forwards.add(-1.3);
        forwards.add(-1.35);
        forwards.add(-1.4);
        forwards.add(-1.45);
        forwards.add(-1.5);
        List<Double> upwards = new ArrayList<>();
        upwards.add(1.2);
        upwards.add(1.35);
        upwards.add(1.5);
        upwards.add(1.65);
        upwards.add(1.8);
        upwards.add(2D);
        List<Double> right = new ArrayList<>();
        right.add(0D);
        right.add(0D);
        right.add(0D);
        right.add(0D);
        right.add(0D);
        right.add(0D);
        LaunchMechanic launchMechanic = new LaunchMechanic(LaunchMechanic.Relative.CASTER, forwards, upwards, right);

        List<Integer> repeatAmounts = new ArrayList<>();
        repeatAmounts.add(20);
        repeatAmounts.add(25);
        repeatAmounts.add(30);
        repeatAmounts.add(35);
        repeatAmounts.add(40);
        repeatAmounts.add(50);
        ParticleAnimationMechanic particleAnimationMechanic = new ParticleAnimationMechanic(Particle.SPELL_WITCH, ArrangementParticle.SPHERE, 1.2,
                8, 0, 0, 0, 0, 0, 0, 0, 5, repeatAmounts, null);

        ImmunityMechanic immunityMechanic = new ImmunityMechanic(EntityDamageEvent.DamageCause.FALL, new ArrayList<>()); //send empty list for infinite

        LandTrigger landTrigger = new LandTrigger();

        ImmunityRemoveMechanic immunityRemoveMechanic = new ImmunityRemoveMechanic(EntityDamageEvent.DamageCause.FALL);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(launchMechanic);
        selfTarget.addChildren(particleAnimationMechanic);
        selfTarget.addChildren(immunityMechanic);
        selfTarget.addChildren(new SoundMechanic(GoaSound.SKILL_VOID_SIGNAL));

        selfTarget.addChildren(landTrigger);
        landTrigger.addChildren(immunityRemoveMechanic);

        return skill;
    }

    private static Skill getPassive() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Your every third successful projectile hit");
        description.add(ChatColor.GRAY + "gives you bonus critical chance");
        description.add(ChatColor.GRAY + "(This can exceed the critical chance cap)");

        List<Integer> reqLevels = new ArrayList<>();
        reqLevels.add(20);
        reqLevels.add(30);
        reqLevels.add(40);
        reqLevels.add(50);
        reqLevels.add(60);
        reqLevels.add(80);

        List<Integer> reqPoints = new ArrayList<>();
        reqPoints.add(2);
        reqPoints.add(4);
        reqPoints.add(4);
        reqPoints.add(6);
        reqPoints.add(6);
        reqPoints.add(6);

        List<Integer> manaCosts = new ArrayList<>();
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(1);
        cooldowns.add(1);
        cooldowns.add(1);
        cooldowns.add(1);
        cooldowns.add(1);
        cooldowns.add(1);

        Skill skill = new Skill("Sharpshooter", 6, Material.IRON_HOE, 21, description, reqLevels, reqPoints, manaCosts, cooldowns);

        InitializeTrigger initializeTrigger = new InitializeTrigger();

        RangedAttackTrigger rangedAttackTrigger = new RangedAttackTrigger(cooldowns);

        List<Integer> ticks = new ArrayList<>();
        ticks.add(100);
        ticks.add(120);
        ticks.add(140);
        ticks.add(160);
        ticks.add(200);
        ticks.add(220);
        List<Double> multipliers = new ArrayList<>();
        multipliers.add(0.7);
        multipliers.add(0.8);
        multipliers.add(0.9);
        multipliers.add(0.1);
        multipliers.add(0.12);
        multipliers.add(0.14);
        BuffMechanic buffMechanic = new BuffMechanic(BuffType.CRIT_CHANCE, multipliers, ticks);

        ValueCondition activateCondition = new ValueCondition(3, 3);
        ValueSetMechanic valueSetMechanic = new ValueSetMechanic(0);

        ValueCondition upValueCondition = new ValueCondition(0, 2);
        ValueAddMechanic valueAddMechanic = new ValueAddMechanic(1);

        skill.addTrigger(initializeTrigger);
        initializeTrigger.addChildren(rangedAttackTrigger);

        SelfTarget selfTargetForActivate = new SelfTarget();
        rangedAttackTrigger.addChildren(selfTargetForActivate);
        selfTargetForActivate.addChildren(activateCondition);
        activateCondition.addChildren(buffMechanic);
        activateCondition.addChildren(valueSetMechanic);
        activateCondition.addChildren(new SoundMechanic(GoaSound.SKILL_BUFF));

        SelfTarget selfTargetForUp = new SelfTarget();
        rangedAttackTrigger.addChildren(selfTargetForUp);
        selfTargetForUp.addChildren(upValueCondition);
        upValueCondition.addChildren(valueAddMechanic);

        return skill;
    }

    private static Skill getUltimate() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Reveal an area with a flurry of arrows,");
        description.add(ChatColor.GRAY + "dealing waves of damage to opponents");

        List<Integer> reqLevels = new ArrayList<>();
        reqLevels.add(40);
        reqLevels.add(50);
        reqLevels.add(60);
        reqLevels.add(70);
        reqLevels.add(80);
        reqLevels.add(90);

        List<Integer> reqPoints = new ArrayList<>();
        reqPoints.add(4);
        reqPoints.add(5);
        reqPoints.add(6);
        reqPoints.add(7);
        reqPoints.add(8);
        reqPoints.add(9);

        List<Integer> manaCosts = new ArrayList<>();
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

        Skill skill = new Skill("Make It Rain", 6, Material.IRON_HOE, 48, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> ranges = new ArrayList<>();
        ranges.add(24D);
        ranges.add(24D);
        ranges.add(24D);
        ranges.add(24D);
        ranges.add(24D);
        ranges.add(24D);
        SingleTarget singleTarget = new SingleTarget(false, true, false, 1, ranges, 4);

        List<Integer> projectileAmounts = new ArrayList<>();
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.RAIN, 8, 12, 1, projectileAmounts, 0,
                0, 0, 200, false, Arrow.class);

        List<Integer> projectileAmountsVisual = new ArrayList<>();
        projectileAmountsVisual.add(12);
        projectileAmountsVisual.add(12);
        projectileAmountsVisual.add(12);
        projectileAmountsVisual.add(12);
        projectileAmountsVisual.add(12);
        projectileAmountsVisual.add(12);
        ProjectileMechanic projectileMechanicVisual = new ProjectileMechanic(SpreadType.RAIN, 8, 12, 1, projectileAmountsVisual, 0,
                0, 0, 200, true, Arrow.class);

        List<Integer> repeatAmounts = new ArrayList<>();
        repeatAmounts.add(3);
        repeatAmounts.add(3);
        repeatAmounts.add(4);
        repeatAmounts.add(4);
        repeatAmounts.add(5);
        repeatAmounts.add(6);
        RepeatMechanic repeatMechanic = new RepeatMechanic(20, repeatAmounts);

        List<Double> areas = new ArrayList<>();
        areas.add(8D);
        areas.add(8D);
        areas.add(8D);
        areas.add(8D);
        areas.add(8D);
        areas.add(8D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, areas);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(singleTarget);
        singleTarget.addChildren(repeatMechanic);
        repeatMechanic.addChildren(projectileMechanicVisual);
        repeatMechanic.addChildren(projectileMechanic);
        repeatMechanic.addChildren(new SoundMechanic(GoaSound.SKILL_ARROW_RAIN));

        projectileMechanic.addChildren(areaTarget);
        List<Double> damages = new ArrayList<>();
        damages.add(16D);
        damages.add(16D);
        damages.add(16D);
        damages.add(16D);
        damages.add(16D);
        damages.add(16D);
        areaTarget.addChildren(new DamageMechanic(damages, DamageMechanic.DamageType.RANGED));

        return skill;
    }
}
