package io.github.lix3nn53.guardiansofadelia.guardian.skill.list;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition.FlagCondition;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.*;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.SilenceMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.AreaTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SelfTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SingleTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.MeleeAttackTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.RangedAttackTrigger;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ArrangementParticle;
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
        description.add("Push your target with a powerful fist.");

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

        Skill skill = new Skill("Iron Fist", Material.IRON_HOE, 21, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> ranges = new ArrayList<>();
        ranges.add(3.5D);
        ranges.add(4D);
        ranges.add(4.5D);
        SingleTarget singleTarget = new SingleTarget(false, true, false, 999, ranges, 4);

        List<Double> speeds = new ArrayList<>();
        speeds.add(1.2D);
        speeds.add(1.4D);
        speeds.add(1.6D);
        PushMechanic pushMechanic = new PushMechanic(PushMechanic.PushType.FIXED, speeds, true);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(singleTarget);
        singleTarget.addChildren(pushMechanic);

        return skill;
    }

    private static Skill getTwo() {
        List<String> description = new ArrayList<>();
        description.add("Dash to one side.");
        description.add("Direction changes each time.");

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
        cooldowns.add(2);
        cooldowns.add(2);
        cooldowns.add(2);
        cooldowns.add(2);
        cooldowns.add(2);
        cooldowns.add(2);
        cooldowns.add(2);

        Skill skill = new Skill("Tumble", Material.IRON_HOE, 33, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        //mechanics depending on flag
        FlagCondition ifGoLeftIsSet = new FlagCondition("goLeft", true);

        List<Double> upward = new ArrayList<>();
        upward.add(0D);
        upward.add(0D);
        upward.add(0D);
        List<Double> forward = new ArrayList<>();
        forward.add(0D);
        forward.add(0D);
        forward.add(0D);
        List<Double> right = new ArrayList<>();
        right.add(0.8);
        right.add(1D);
        right.add(1.2D);
        LaunchMechanic rightLaunch = new LaunchMechanic(LaunchMechanic.Relative.CASTER, forward, upward, right);

        List<Double> left = new ArrayList<>();
        left.add(-0.8);
        left.add(-1D);
        left.add(-1.2D);
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

        return skill;
    }

    private static Skill getThree() {
        List<String> description = new ArrayList<>();
        description.add("Make yourself invincible and start");
        description.add("healing over time. You can't");
        description.add("can't make any moves during this effect.");

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

        Skill skill = new Skill("Serenity", Material.IRON_HOE, 35, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Integer> ccTicks = new ArrayList<>();
        ccTicks.add(60);
        ccTicks.add(60);
        ccTicks.add(60);
        ccTicks.add(60);
        List<Integer> ccAmplifiers = new ArrayList<>();
        ccAmplifiers.add(999);
        ccAmplifiers.add(999);
        ccAmplifiers.add(999);
        selfTarget.addChildren(new PotionEffectMechanic(PotionEffectType.SLOW, ccTicks, ccAmplifiers));
        selfTarget.addChildren(new PotionEffectMechanic(PotionEffectType.JUMP, ccTicks, ccAmplifiers));
        selfTarget.addChildren(new SilenceMechanic(ccTicks));

        List<Integer> repetitions = new ArrayList<>();
        repetitions.add(15);
        repetitions.add(18);
        repetitions.add(22);
        RepeatMechanic repeatMechanic = new RepeatMechanic(40L, repetitions);


        List<Double> percents = new ArrayList<>();
        percents.add(0.3);
        percents.add(0.5);
        percents.add(0.6);
        repeatMechanic.addChildren(new HealMechanic(new ArrayList<>(), percents));

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(repeatMechanic);

        List<Integer> ticks = new ArrayList<>();
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        List<Double> multipliers = new ArrayList<>();
        multipliers.add(0.1);
        multipliers.add(0.12);
        multipliers.add(0.15);
        multipliers.add(0.18);
        BuffMechanic phyDef = new BuffMechanic(BuffType.PHYSICAL_DEFENSE, multipliers, ticks);
        BuffMechanic mgcDef = new BuffMechanic(BuffType.MAGIC_DEFENSE, multipliers, ticks);
        selfTarget.addChildren(phyDef);
        selfTarget.addChildren(mgcDef);

        return skill;
    }

    private static Skill getPassive() {
        List<String> description = new ArrayList<>();
        description.add("Marks the target you hit by throwing your spear.");
        description.add("Melee attacks on marked targets will deal");
        description.add("bonus damage and slow them.");

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

        Skill skill = new Skill("Mark of Ocean", Material.IRON_HOE, 60, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        RangedAttackTrigger rangedAttackTrigger = new RangedAttackTrigger(cooldowns);

        List<Integer> flagTicks = new ArrayList<>();
        flagTicks.add(120);
        flagTicks.add(130);
        flagTicks.add(140);
        flagTicks.add(150);
        flagTicks.add(160);
        FlagSetMechanic flagSetMechanic = new FlagSetMechanic("oceanMark", flagTicks);

        MeleeAttackTrigger meleeAttackTrigger = new MeleeAttackTrigger(cooldowns);
        FlagCondition flagCondition = new FlagCondition("oceanMark", true);

        List<Double> damages = new ArrayList<>();
        damages.add(12D);
        damages.add(13D);
        damages.add(14D);
        damages.add(15D);
        DamageMechanic damageMechanic = new DamageMechanic(damages, DamageMechanic.DamageType.MAGIC);

        List<Integer> ticks = new ArrayList<>();
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        List<Integer> amplifiers = new ArrayList<>();
        amplifiers.add(2);
        amplifiers.add(3);
        amplifiers.add(4);
        amplifiers.add(5);
        PotionEffectMechanic potionEffectMechanic = new PotionEffectMechanic(PotionEffectType.SLOW, ticks, amplifiers);

        FlagRemoveMechanic flagRemoveMechanic = new FlagRemoveMechanic("oceanMark");

        skill.addTrigger(selfTarget);

        selfTarget.addChildren(rangedAttackTrigger);
        rangedAttackTrigger.addChildren(flagSetMechanic);

        selfTarget.addChildren(meleeAttackTrigger);
        meleeAttackTrigger.addChildren(flagCondition);
        flagCondition.addChildren(damageMechanic);
        flagCondition.addChildren(potionEffectMechanic);
        flagCondition.addChildren(flagRemoveMechanic);

        return skill;
    }

    private static Skill getUltimate() {
        List<String> description = new ArrayList<>();
        description.add("Launch nearby enemies upwards and");
        description.add("&5stun them in the air");

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

        Skill skill = new Skill("Aqua Prison", Material.IRON_HOE, 25, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> radiuses = new ArrayList<>();
        radiuses.add(3D);
        radiuses.add(3.5D);
        radiuses.add(4D);
        radiuses.add(5D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, radiuses);

        List<Double> upward = new ArrayList<>();
        upward.add(3D);
        upward.add(4D);
        upward.add(5D);
        List<Double> forward = new ArrayList<>();
        forward.add(0D);
        forward.add(0D);
        forward.add(0D);
        List<Double> right = new ArrayList<>();
        right.add(0D);
        right.add(0D);
        right.add(0D);
        LaunchMechanic launchMechanic = new LaunchMechanic(LaunchMechanic.Relative.TARGET, forward, upward, right);

        List<Integer> ticks = new ArrayList<>();
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        List<Integer> amplifiers = new ArrayList<>();
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
        repeatAmount.add(40);
        repeatAmount.add(50);
        repeatAmount.add(60);
        repeatAmount.add(70);
        ParticleAnimationMechanic particleAnimationMechanic = new ParticleAnimationMechanic(Particle.WATER_BUBBLE, ArrangementParticle.CIRCLE, 1.4, 4, 0, 0, 0,
                0, 0.5, 0, 0, 5, repeatAmount, null);
        selfTarget.addChildren(particleAnimationMechanic);

        return skill;
    }
}
