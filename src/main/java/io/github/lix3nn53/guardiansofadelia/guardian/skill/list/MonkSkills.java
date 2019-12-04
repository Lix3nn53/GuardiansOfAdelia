package io.github.lix3nn53.guardiansofadelia.guardian.skill.list;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition.FlagCondition;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.*;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.DisarmMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.SilenceMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.AreaTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SelfTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SingleTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.InitializeTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.MeleeAttackTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.RangedAttackTrigger;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ArrangementParticle;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class MonkSkills {

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
        description.add(ChatColor.GRAY + "Push your target with a powerful fist.");

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

        Skill skill = new Skill("Iron Fist", 6, Material.IRON_HOE, 21, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> ranges = new ArrayList<>();
        ranges.add(3D);
        ranges.add(3.25D);
        ranges.add(3.5D);
        ranges.add(3.75D);
        ranges.add(4D);
        ranges.add(4.5D);
        SingleTarget singleTarget = new SingleTarget(false, true, false, 999, ranges, 4);

        List<Double> speeds = new ArrayList<>();
        speeds.add(2.4D);
        speeds.add(2.8D);
        speeds.add(3.2D);
        speeds.add(3.6D);
        speeds.add(4D);
        speeds.add(4.8D);
        PushMechanic pushMechanic = new PushMechanic(PushMechanic.PushType.FIXED, speeds, true);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(singleTarget);
        List<Double> damages = new ArrayList<>();
        damages.add(30.0);
        damages.add(70.0);
        damages.add(260.0);
        damages.add(450.0);
        damages.add(600.0);
        damages.add(1000.0);

        singleTarget.addChildren(new DamageMechanic(damages, DamageMechanic.DamageType.MAGIC));
        singleTarget.addChildren(pushMechanic);
        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.EXPLOSION_NORMAL, ArrangementParticle.CIRCLE, 1, 1, 0, 0, 0, 0, 0.5, 0, 0, null);
        singleTarget.addChildren(particleMechanic);
        singleTarget.addChildren(new SoundMechanic(GoaSound.SKILL_PROJECTILE_FIRE));

        return skill;
    }

    private static Skill getTwo() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Dash slightly back to one side.");
        description.add(ChatColor.GRAY + "Direction changes each time.");

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
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(6);
        cooldowns.add(6);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(4);
        cooldowns.add(4);

        Skill skill = new Skill("Tumble", 6, Material.IRON_HOE, 33, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        //mechanics depending on flag
        FlagCondition ifGoLeftIsSet = new FlagCondition("goLeft", true);

        List<Double> upward = new ArrayList<>();
        upward.add(0D);
        upward.add(0D);
        upward.add(0D);
        upward.add(0D);
        upward.add(0D);
        upward.add(0D);
        List<Double> forward = new ArrayList<>();
        forward.add(-0.4D);
        forward.add(-0.4D);
        forward.add(-0.4D);
        forward.add(-0.4D);
        forward.add(-0.4D);
        forward.add(-0.4D);
        List<Double> right = new ArrayList<>();
        right.add(1.4D);
        right.add(1.6D);
        right.add(1.8D);
        right.add(2D);
        right.add(2.2D);
        right.add(2.4D);
        LaunchMechanic rightLaunch = new LaunchMechanic(LaunchMechanic.Relative.CASTER, forward, upward, right);

        List<Double> left = new ArrayList<>();
        left.add(-1.4D);
        left.add(-1.6D);
        left.add(-1.8D);
        left.add(-2D);
        left.add(-2.2D);
        left.add(-2.4D);
        LaunchMechanic leftLaunch = new LaunchMechanic(LaunchMechanic.Relative.CASTER, forward, upward, left);

        skill.addTrigger(selfTarget);

        selfTarget.addChildren(ifGoLeftIsSet);
        ifGoLeftIsSet.addChildren(leftLaunch);
        FlagRemoveMechanic removeGoLeft = new FlagRemoveMechanic("goLeft");
        ifGoLeftIsSet.addChildren(removeGoLeft);
        FlagSetMechanic setDidLeftTrigger = new FlagSetMechanic("leftTrigger", new ArrayList<>());
        ifGoLeftIsSet.addChildren(setDidLeftTrigger);

        FlagCondition ifLeftDidNotTrigger = new FlagCondition("leftTrigger", false);
        selfTarget.addChildren(ifLeftDidNotTrigger);
        ifLeftDidNotTrigger.addChildren(rightLaunch);
        FlagSetMechanic setLeft = new FlagSetMechanic("goLeft", new ArrayList<>());
        ifLeftDidNotTrigger.addChildren(setLeft);

        FlagRemoveMechanic removeDidLeftTrigger = new FlagRemoveMechanic("leftTrigger");
        selfTarget.addChildren(removeDidLeftTrigger);
        selfTarget.addChildren(new SoundMechanic(GoaSound.SKILL_PROJECTILE_VOID));

        return skill;
    }

    private static Skill getThree() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Increase your defenses and start");
        description.add(ChatColor.GRAY + "healing over time. You can't");
        description.add(ChatColor.GRAY + "can't make any moves during this effect.");

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
        manaCosts.add(30);
        manaCosts.add(32);
        manaCosts.add(34);
        manaCosts.add(36);
        manaCosts.add(38);
        manaCosts.add(40);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(30);
        cooldowns.add(30);
        cooldowns.add(30);
        cooldowns.add(30);
        cooldowns.add(30);
        cooldowns.add(30);

        Skill skill = new Skill("Serenity", 6, Material.IRON_HOE, 35, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Integer> ccTicks = new ArrayList<>();
        ccTicks.add(120);
        ccTicks.add(140);
        ccTicks.add(160);
        ccTicks.add(180);
        ccTicks.add(200);
        ccTicks.add(240);
        List<Integer> ccAmplifiers = new ArrayList<>();
        ccAmplifiers.add(999);
        ccAmplifiers.add(999);
        ccAmplifiers.add(999);
        ccAmplifiers.add(999);
        ccAmplifiers.add(999);
        ccAmplifiers.add(999);
        selfTarget.addChildren(new PotionEffectMechanic(PotionEffectType.SLOW, ccTicks, ccAmplifiers));
        selfTarget.addChildren(new PotionEffectMechanic(PotionEffectType.JUMP, ccTicks, ccAmplifiers));
        selfTarget.addChildren(new SilenceMechanic(ccTicks));
        selfTarget.addChildren(new DisarmMechanic(ccTicks));

        List<Integer> repetitions = new ArrayList<>();
        repetitions.add(6);
        repetitions.add(7);
        repetitions.add(8);
        repetitions.add(9);
        repetitions.add(10);
        repetitions.add(12);
        RepeatMechanic repeatMechanic = new RepeatMechanic(20L, repetitions);


        List<Double> percents = new ArrayList<>();
        percents.add(0.5);
        percents.add(0.6);
        percents.add(0.65);
        percents.add(0.7);
        percents.add(0.75);
        percents.add(0.8);
        repeatMechanic.addChildren(new HealMechanic(new ArrayList<>(), percents));

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(repeatMechanic);

        List<Integer> ticks = new ArrayList<>();
        ticks.add(120);
        ticks.add(140);
        ticks.add(160);
        ticks.add(180);
        ticks.add(200);
        ticks.add(240);
        List<Double> multipliers = new ArrayList<>();
        multipliers.add(0.6);
        multipliers.add(0.65);
        multipliers.add(0.7);
        multipliers.add(0.75);
        multipliers.add(0.8);
        multipliers.add(0.9);
        BuffMechanic phyDef = new BuffMechanic(BuffType.PHYSICAL_DEFENSE, multipliers, ticks);
        BuffMechanic mgcDef = new BuffMechanic(BuffType.MAGIC_DEFENSE, multipliers, ticks);
        selfTarget.addChildren(phyDef);
        selfTarget.addChildren(mgcDef);
        selfTarget.addChildren(new SoundMechanic(GoaSound.SKILL_BUFF));

        List<Integer> repeatAmounts = new ArrayList<>();
        repeatAmounts.add(24);
        repeatAmounts.add(28);
        repeatAmounts.add(32);
        repeatAmounts.add(36);
        repeatAmounts.add(40);
        repeatAmounts.add(48);
        ParticleAnimationMechanic particleAnimationMechanic = new ParticleAnimationMechanic(Particle.REDSTONE, ArrangementParticle.CIRCLE, 1.8, 9, 0,
                0, 0, 0, 1, 0, 1, 5L, repeatAmounts, new Particle.DustOptions(Color.YELLOW, 2));

        selfTarget.addChildren(particleAnimationMechanic);

        return skill;
    }

    private static Skill getPassive() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Marks the target you hit by throwing your spear.");
        description.add(ChatColor.GRAY + "Melee attacks on marked targets will deal");
        description.add(ChatColor.GRAY + "bonus damage and slow them.");

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
        cooldowns.add(1);
        cooldowns.add(1);
        cooldowns.add(1);
        cooldowns.add(1);
        cooldowns.add(1);
        cooldowns.add(1);

        Skill skill = new Skill("Mark of Ocean", 6, Material.IRON_HOE, 60, description, reqLevels, reqPoints, manaCosts, cooldowns);

        InitializeTrigger initializeTrigger = new InitializeTrigger();

        RangedAttackTrigger rangedAttackTrigger = new RangedAttackTrigger(cooldowns);

        List<Integer> flagTicks = new ArrayList<>();
        flagTicks.add(200);
        flagTicks.add(200);
        flagTicks.add(200);
        flagTicks.add(200);
        flagTicks.add(200);
        flagTicks.add(200);
        FlagSetMechanic flagSetMechanic = new FlagSetMechanic("oceanMark", flagTicks);

        MeleeAttackTrigger meleeAttackTrigger = new MeleeAttackTrigger(cooldowns);
        FlagCondition flagCondition = new FlagCondition("oceanMark", true);

        List<Double> damages = new ArrayList<>();
        damages.add(30.0);
        damages.add(70.0);
        damages.add(260.0);
        damages.add(450.0);
        damages.add(600.0);
        damages.add(1000.0);
        DamageMechanic damageMechanic = new DamageMechanic(damages, DamageMechanic.DamageType.MAGIC);

        List<Integer> ticks = new ArrayList<>();
        ticks.add(60);
        ticks.add(65);
        ticks.add(70);
        ticks.add(75);
        ticks.add(80);
        ticks.add(90);
        List<Integer> amplifiers = new ArrayList<>();
        amplifiers.add(1);
        amplifiers.add(2);
        amplifiers.add(3);
        amplifiers.add(4);
        amplifiers.add(5);
        amplifiers.add(6);
        PotionEffectMechanic potionEffectMechanic = new PotionEffectMechanic(PotionEffectType.SLOW, ticks, amplifiers);

        FlagRemoveMechanic flagRemoveMechanic = new FlagRemoveMechanic("oceanMark");

        skill.addTrigger(initializeTrigger);

        initializeTrigger.addChildren(rangedAttackTrigger);
        rangedAttackTrigger.addChildren(flagSetMechanic);

        initializeTrigger.addChildren(meleeAttackTrigger);
        meleeAttackTrigger.addChildren(flagCondition);
        flagCondition.addChildren(damageMechanic);
        flagCondition.addChildren(potionEffectMechanic);
        flagCondition.addChildren(flagRemoveMechanic);
        flagCondition.addChildren(new SoundMechanic(GoaSound.SKILL_SPLASH));

        return skill;
    }

    private static Skill getUltimate() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Launch nearby enemies upwards and");
        description.add(ChatColor.GRAY + "stun them in the air");

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
        manaCosts.add(50);
        manaCosts.add(55);
        manaCosts.add(60);
        manaCosts.add(65);
        manaCosts.add(70);
        manaCosts.add(70);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(40);
        cooldowns.add(40);
        cooldowns.add(40);
        cooldowns.add(40);
        cooldowns.add(40);
        cooldowns.add(40);

        Skill skill = new Skill("Aqua Prison", 6, Material.IRON_HOE, 25, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> radiuses = new ArrayList<>();
        radiuses.add(4D);
        radiuses.add(4.25D);
        radiuses.add(4.5D);
        radiuses.add(4.75D);
        radiuses.add(5D);
        radiuses.add(5.5D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, radiuses);

        List<Double> upward = new ArrayList<>();
        upward.add(2D);
        upward.add(3D);
        upward.add(4D);
        upward.add(5D);
        upward.add(6D);
        upward.add(7D);
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
        LaunchMechanic launchMechanic = new LaunchMechanic(LaunchMechanic.Relative.TARGET, forward, upward, right);

        List<Integer> ticks = new ArrayList<>();
        ticks.add(160);
        ticks.add(170);
        ticks.add(180);
        ticks.add(190);
        ticks.add(200);
        ticks.add(240);
        List<Integer> amplifiers = new ArrayList<>();
        amplifiers.add(0);
        amplifiers.add(0);
        amplifiers.add(0);
        amplifiers.add(0);
        amplifiers.add(0);
        amplifiers.add(0);
        PotionEffectMechanic levitation = new PotionEffectMechanic(PotionEffectType.LEVITATION, ticks, amplifiers);

        SilenceMechanic silenceMechanic = new SilenceMechanic(ticks);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(areaTarget);
        areaTarget.addChildren(launchMechanic);
        areaTarget.addChildren(levitation);
        areaTarget.addChildren(silenceMechanic);

        List<Integer> repeatAmount = new ArrayList<>();
        repeatAmount.add(32);
        repeatAmount.add(34);
        repeatAmount.add(36);
        repeatAmount.add(38);
        repeatAmount.add(40);
        repeatAmount.add(48);
        ParticleAnimationMechanic particleAnimationMechanicBubble = new ParticleAnimationMechanic(Particle.WATER_BUBBLE, ArrangementParticle.SPHERE, 1.4, 4, 0, 0, 0,
                0, 0.5, 0, 0, 5, repeatAmount, null);
        areaTarget.addChildren(particleAnimationMechanicBubble);
        ParticleAnimationMechanic particleAnimationMechanic = new ParticleAnimationMechanic(Particle.REDSTONE, ArrangementParticle.CIRCLE, 1.4, 2, 0,
                0, 0, 0, 0, 0, 1, 5L, repeatAmount, new Particle.DustOptions(Color.AQUA, 2));
        areaTarget.addChildren(particleAnimationMechanic);

        return skill;
    }
}
