package io.github.lix3nn53.guardiansofadelia.guardian.skill.list;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition.DirectionCondition;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition.FlagCondition;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.*;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityRemoveMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.ProjectileMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.SpreadType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.FilterCurrentTargets;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SelfTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SingleTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.InitializeTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.LandTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.MeleeAttackTrigger;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ArrangementParticle;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Egg;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class RogueSkills {

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
        description.add(ChatColor.GRAY + "Jump towards your target and deal damage.");
        description.add(ChatColor.GRAY + "If you are behind your target also apply slow.");

        List<Integer> reqLevels = new ArrayList<>();
        reqLevels.add(1);
        reqLevels.add(13);
        reqLevels.add(25);
        reqLevels.add(37);
        reqLevels.add(49);
        reqLevels.add(61);

        List<Integer> reqPoints = new ArrayList<>();
        reqPoints.add(1);
        reqPoints.add(1);
        reqPoints.add(2);
        reqPoints.add(2);
        reqPoints.add(3);
        reqPoints.add(3);

        List<Integer> manaCosts = new ArrayList<>();
        manaCosts.add(5);
        manaCosts.add(7);
        manaCosts.add(9);
        manaCosts.add(11);
        manaCosts.add(13);
        manaCosts.add(15);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(12);
        cooldowns.add(12);
        cooldowns.add(12);
        cooldowns.add(12);
        cooldowns.add(12);
        cooldowns.add(12);

        Skill skill = new Skill("Claw Swipe", 6, Material.IRON_HOE, 32, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        FlagCondition phantomCondition = new FlagCondition("phantom", false);

        FlagCondition phantomCondition2 = new FlagCondition("phantom", true);

        List<Double> ranges = new ArrayList<>();
        ranges.add(3D);
        ranges.add(3.5D);
        ranges.add(4D);
        ranges.add(4.5D);
        ranges.add(5D);
        ranges.add(6D);
        SingleTarget singleTarget = new SingleTarget(false, true, false, 1, ranges, 4);

        List<Double> ranges2 = new ArrayList<>();
        ranges2.add(5D);
        ranges2.add(5.5D);
        ranges2.add(6D);
        ranges2.add(6.5D);
        ranges2.add(7D);
        ranges2.add(9D);
        SingleTarget singleTarget2 = new SingleTarget(false, true, false, 1, ranges2, 4);

        DirectionCondition directionCondition = new DirectionCondition(false);

        List<Integer> ticks = new ArrayList<>();
        ticks.add(60);
        ticks.add(65);
        ticks.add(70);
        ticks.add(75);
        ticks.add(80);
        ticks.add(100);
        List<Integer> amplifiers = new ArrayList<>();
        amplifiers.add(2);
        amplifiers.add(3);
        amplifiers.add(4);
        amplifiers.add(5);
        amplifiers.add(6);
        amplifiers.add(7);
        PotionEffectMechanic slow = new PotionEffectMechanic(PotionEffectType.SLOW, ticks, amplifiers);

        WarpTargetMechanic warpTargetMechanic = new WarpTargetMechanic(false);

        List<Double> damages = new ArrayList<>();
        damages.add(100D);
        damages.add(170D);
        damages.add(240D);
        damages.add(320D);
        damages.add(400D);
        damages.add(530D);
        DamageMechanic damageMechanic = new DamageMechanic(damages, DamageMechanic.DamageType.MELEE);

        skill.addTrigger(selfTarget);

        selfTarget.addChildren(phantomCondition);
        phantomCondition.addChildren(singleTarget);

        selfTarget.addChildren(phantomCondition2);
        phantomCondition2.addChildren(singleTarget2);

        singleTarget.addChildren(directionCondition);
        singleTarget2.addChildren(directionCondition);
        directionCondition.addChildren(slow);

        singleTarget.addChildren(warpTargetMechanic);
        singleTarget2.addChildren(warpTargetMechanic);
        singleTarget.addChildren(damageMechanic);
        singleTarget2.addChildren(damageMechanic);
        singleTarget.addChildren(new SoundMechanic(GoaSound.SKILL_POISON_SLASH));
        singleTarget2.addChildren(new SoundMechanic(GoaSound.SKILL_POISON_SLASH));
        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.SWEEP_ATTACK, ArrangementParticle.CIRCLE, 1.2, 7, 0, 0, 0, 0, 1, 0, 0, null);
        singleTarget.addChildren(particleMechanic);
        singleTarget2.addChildren(particleMechanic);

        return skill;
    }

    private static Skill getTwo() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Dash forward and gain");
        description.add(ChatColor.GRAY + "resistance to fall damage");

        List<Integer> reqLevels = new ArrayList<>();
        reqLevels.add(8);
        reqLevels.add(20);
        reqLevels.add(32);
        reqLevels.add(44);
        reqLevels.add(56);
        reqLevels.add(68);

        List<Integer> reqPoints = new ArrayList<>();
        reqPoints.add(2);
        reqPoints.add(2);
        reqPoints.add(3);
        reqPoints.add(3);
        reqPoints.add(4);
        reqPoints.add(4);

        List<Integer> manaCosts = new ArrayList<>();
        manaCosts.add(10);
        manaCosts.add(12);
        manaCosts.add(14);
        manaCosts.add(16);
        manaCosts.add(18);
        manaCosts.add(20);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(8);
        cooldowns.add(8);
        cooldowns.add(8);
        cooldowns.add(8);
        cooldowns.add(8);
        cooldowns.add(8);

        Skill skill = new Skill("Void Dash", 6, Material.IRON_HOE, 10, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        FlagCondition phantomCondition = new FlagCondition("phantom", false);

        List<Double> forwards = new ArrayList<>();
        forwards.add(4D);
        forwards.add(5D);
        forwards.add(6D);
        forwards.add(7D);
        forwards.add(8D);
        forwards.add(10D);
        List<Double> upwards = new ArrayList<>();
        upwards.add(1.5);
        upwards.add(1.5);
        upwards.add(1.7);
        upwards.add(1.7);
        upwards.add(1.8);
        upwards.add(2D);
        List<Double> right = new ArrayList<>();
        right.add(0D);
        right.add(0D);
        right.add(0D);
        right.add(0D);
        right.add(0D);
        right.add(0D);
        WarpMechanic warpMechanic = new WarpMechanic(false, forwards, upwards, right);

        FlagCondition phantomCondition2 = new FlagCondition("phantom", true);

        List<Double> forwards2 = new ArrayList<>();
        forwards2.add(6D);
        forwards2.add(7D);
        forwards2.add(8D);
        forwards2.add(10D);
        forwards2.add(12D);
        forwards2.add(14D);
        WarpMechanic warpMechanic2 = new WarpMechanic(false, forwards2, upwards, right);

        LandTrigger landTrigger = new LandTrigger();

        ImmunityMechanic immunityMechanic = new ImmunityMechanic(EntityDamageEvent.DamageCause.FALL, new ArrayList<>());//0 for infinite
        ImmunityRemoveMechanic immunityRemoveMechanic = new ImmunityRemoveMechanic(EntityDamageEvent.DamageCause.FALL, 5);

        skill.addTrigger(selfTarget);

        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.REDSTONE, ArrangementParticle.CIRCLE, 1.2, 4, 0, 0, 0, 0, 0.5, 0, 0, new Particle.DustOptions(Color.FUCHSIA, 2));
        selfTarget.addChildren(particleMechanic);

        selfTarget.addChildren(phantomCondition);
        phantomCondition.addChildren(warpMechanic);

        selfTarget.addChildren(phantomCondition2);
        phantomCondition2.addChildren(warpMechanic2);

        selfTarget.addChildren(immunityMechanic);
        selfTarget.addChildren(landTrigger);
        selfTarget.addChildren(new SoundMechanic(GoaSound.SKILL_PROJECTILE_VOID));

        selfTarget.addChildren(particleMechanic);

        landTrigger.addChildren(immunityRemoveMechanic);

        return skill;
    }

    private static Skill getThree() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Throw shrunkens one by one");

        List<Integer> reqLevels = new ArrayList<>();
        reqLevels.add(12);
        reqLevels.add(24);
        reqLevels.add(36);
        reqLevels.add(48);
        reqLevels.add(60);
        reqLevels.add(72);

        List<Integer> reqPoints = new ArrayList<>();
        reqPoints.add(2);
        reqPoints.add(2);
        reqPoints.add(3);
        reqPoints.add(3);
        reqPoints.add(4);
        reqPoints.add(4);

        List<Integer> manaCosts = new ArrayList<>();
        manaCosts.add(20);
        manaCosts.add(22);
        manaCosts.add(24);
        manaCosts.add(26);
        manaCosts.add(28);
        manaCosts.add(30);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(30);
        cooldowns.add(30);
        cooldowns.add(30);
        cooldowns.add(30);
        cooldowns.add(30);
        cooldowns.add(30);

        Skill skill = new Skill("Shrunkens", 6, Material.IRON_HOE, 56, description, reqLevels, reqPoints, manaCosts, cooldowns);

        List<Integer> repetitions = new ArrayList<>();
        repetitions.add(2);
        repetitions.add(3);
        repetitions.add(4);
        repetitions.add(5);
        repetitions.add(6);
        repetitions.add(7);
        RepeatMechanic repeatMechanic = new RepeatMechanic(10L, repetitions);

        List<Integer> projectileAmounts = new ArrayList<>();
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 2.7, projectileAmounts, 30, 0, 1, 0, 90,
                true, Egg.class, Particle.REDSTONE, ArrangementParticle.CIRCLE, 0.5, 4, new Particle.DustOptions(Color.PURPLE, 0.8f), false);

        FlagCondition phantomCondition = new FlagCondition("phantom", true);

        List<Integer> ticks = new ArrayList<>();
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        List<Integer> amplifiers = new ArrayList<>();
        amplifiers.add(2);
        amplifiers.add(3);
        amplifiers.add(4);
        amplifiers.add(5);
        amplifiers.add(6);
        amplifiers.add(7);
        PotionEffectMechanic slow = new PotionEffectMechanic(PotionEffectType.SLOW, ticks, amplifiers);

        SelfTarget selfTarget = new SelfTarget();
        skill.addTrigger(selfTarget);
        selfTarget.addChildren(repeatMechanic);
        repeatMechanic.addChildren(projectileMechanic);
        repeatMechanic.addChildren(new SoundMechanic(GoaSound.SKILL_THROW_SMALL_OBJECT));

        List<Double> damages = new ArrayList<>();
        damages.add(24D);
        damages.add(60D);
        damages.add(120D);
        damages.add(200D);
        damages.add(270D);
        damages.add(450D);
        DamageMechanic damageMechanic = new DamageMechanic(damages, DamageMechanic.DamageType.RANGED);

        FilterCurrentTargets filterCurrentTargets = new FilterCurrentTargets(false, true, false, 999);
        projectileMechanic.addChildren(filterCurrentTargets);
        filterCurrentTargets.addChildren(damageMechanic);
        filterCurrentTargets.addChildren(phantomCondition);
        phantomCondition.addChildren(slow);

        return skill;
    }

    private static Skill getPassive() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Deal bonus damage when you hit");
        description.add(ChatColor.GRAY + "targets from behind with melee attacks");

        List<Integer> reqLevels = new ArrayList<>();
        reqLevels.add(20);
        reqLevels.add(35);
        reqLevels.add(50);
        reqLevels.add(60);
        reqLevels.add(70);
        reqLevels.add(80);

        List<Integer> reqPoints = new ArrayList<>();
        reqPoints.add(2);
        reqPoints.add(3);
        reqPoints.add(3);
        reqPoints.add(4);
        reqPoints.add(4);
        reqPoints.add(5);

        List<Integer> manaCosts = new ArrayList<>();
        manaCosts.add(0);
        manaCosts.add(0);
        manaCosts.add(0);
        manaCosts.add(0);
        manaCosts.add(0);
        manaCosts.add(0);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(4);
        cooldowns.add(4);
        cooldowns.add(4);
        cooldowns.add(4);
        cooldowns.add(4);
        cooldowns.add(3);

        Skill skill = new Skill("Backstab", 6, Material.IRON_HOE, 17, description, reqLevels, reqPoints, manaCosts, cooldowns);

        InitializeTrigger initializeTrigger = new InitializeTrigger();

        MeleeAttackTrigger meleeAttackTrigger = new MeleeAttackTrigger(cooldowns);

        DirectionCondition directionCondition = new DirectionCondition(false);

        skill.addTrigger(initializeTrigger);
        initializeTrigger.addChildren(meleeAttackTrigger);
        meleeAttackTrigger.addChildren(directionCondition);

        List<Double> damages = new ArrayList<>();
        damages.add(70.0);
        damages.add(140.0);
        damages.add(280.0);
        damages.add(450.0);
        damages.add(600.0);
        damages.add(1000.0);
        DamageMechanic damageMechanic = new DamageMechanic(damages, DamageMechanic.DamageType.MELEE);

        directionCondition.addChildren(damageMechanic);
        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.CRIT, ArrangementParticle.CIRCLE, 0.8, 4, 0, 0, 0, 0, 0.5, 0, 0, null);
        directionCondition.addChildren(particleMechanic);

        return skill;
    }

    private static Skill getUltimate() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Become a phantom assassin:");
        description.add(ChatColor.GRAY + "- Increase your critical chance");
        description.add(ChatColor.GRAY + "(This can exceed the critical chance cap)");
        description.add(ChatColor.GRAY + "- Increase your critical damage");
        description.add(ChatColor.GRAY + "- Increase range of Claw Swipe");
        description.add(ChatColor.GRAY + "- Increase range of Void Dash");
        description.add(ChatColor.GRAY + "- Your shurikens slows the target");

        List<Integer> reqLevels = new ArrayList<>();
        reqLevels.add(40);
        reqLevels.add(50);
        reqLevels.add(60);
        reqLevels.add(70);
        reqLevels.add(80);
        reqLevels.add(90);

        List<Integer> reqPoints = new ArrayList<>();
        reqPoints.add(5);
        reqPoints.add(6);
        reqPoints.add(7);
        reqPoints.add(8);
        reqPoints.add(9);
        reqPoints.add(10);

        List<Integer> manaCosts = new ArrayList<>();
        manaCosts.add(40);
        manaCosts.add(45);
        manaCosts.add(50);
        manaCosts.add(55);
        manaCosts.add(60);
        manaCosts.add(60);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(50);
        cooldowns.add(50);
        cooldowns.add(50);
        cooldowns.add(50);
        cooldowns.add(50);
        cooldowns.add(50);

        Skill skill = new Skill("Phantom Assassin", 6, Material.IRON_HOE, 45, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Integer> ticks = new ArrayList<>();
        ticks.add(180);
        ticks.add(200);
        ticks.add(220);
        ticks.add(240);
        ticks.add(260);
        ticks.add(300);
        List<Double> damageMultipliers = new ArrayList<>();
        damageMultipliers.add(0.2);
        damageMultipliers.add(0.3);
        damageMultipliers.add(0.4);
        damageMultipliers.add(0.5);
        damageMultipliers.add(0.6);
        damageMultipliers.add(0.8);
        BuffMechanic critDamageBuff = new BuffMechanic(BuffType.CRIT_DAMAGE, damageMultipliers, ticks);
        List<Double> chanceMultipliers = new ArrayList<>();
        chanceMultipliers.add(0.05);
        chanceMultipliers.add(0.06);
        chanceMultipliers.add(0.07);
        chanceMultipliers.add(0.08);
        chanceMultipliers.add(0.1);
        chanceMultipliers.add(0.12);
        BuffMechanic critChanceBuff = new BuffMechanic(BuffType.CRIT_CHANCE, chanceMultipliers, ticks);
        FlagSetMechanic flagSetMechanic = new FlagSetMechanic("phantom", ticks);

        List<Integer> amplifiers = new ArrayList<>();
        amplifiers.add(1);
        amplifiers.add(1);
        amplifiers.add(1);
        amplifiers.add(1);
        amplifiers.add(1);
        amplifiers.add(1);
        PotionEffectMechanic invisibility = new PotionEffectMechanic(PotionEffectType.INVISIBILITY, ticks, amplifiers);
        PotionEffectMechanic glowing = new PotionEffectMechanic(PotionEffectType.GLOWING, ticks, amplifiers);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(critDamageBuff);
        selfTarget.addChildren(critChanceBuff);
        selfTarget.addChildren(flagSetMechanic);
        selfTarget.addChildren(invisibility);
        selfTarget.addChildren(glowing);

        List<Integer> repeatAmount = new ArrayList<>();
        repeatAmount.add(36);
        repeatAmount.add(40);
        repeatAmount.add(44);
        repeatAmount.add(48);
        repeatAmount.add(52);
        repeatAmount.add(60);
        ParticleAnimationMechanic particleAnimationMechanic = new ParticleAnimationMechanic(Particle.REDSTONE, ArrangementParticle.CIRCLE, 1, 4, 0, 0, 0,
                0, 0.5, 0, 0, 5, repeatAmount, new Particle.DustOptions(Color.PURPLE, 4));
        selfTarget.addChildren(particleAnimationMechanic);
        selfTarget.addChildren(new SoundMechanic(GoaSound.SKILL_BUFF));

        return skill;
    }
}
