package io.github.lix3nn53.guardiansofadelia.guardian.skill.list;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition.FlagCondition;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition.HealthCondition;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.*;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityRemoveMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.InvincibleMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.ProjectileMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.SpreadType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.DisarmMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.AreaTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SelfTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.InitializeTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.LandTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.TookMagicalDamageTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.TookPhysicalDamageTrigger;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ArrangementParticle;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class PaladinSkills {

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
        description.add(ChatColor.GRAY + "Deal damage and push nearby enemies");

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
        reqPoints.add(2);
        reqPoints.add(2);
        reqPoints.add(3);
        reqPoints.add(3);

        List<Integer> manaCosts = new ArrayList<>();
        manaCosts.add(8);
        manaCosts.add(9);
        manaCosts.add(10);
        manaCosts.add(11);
        manaCosts.add(12);
        manaCosts.add(14);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(14);
        cooldowns.add(13);
        cooldowns.add(12);
        cooldowns.add(11);
        cooldowns.add(10);
        cooldowns.add(8);

        Skill skill = new Skill("Hammerblow", 6, Material.IRON_HOE, 46, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> areas = new ArrayList<>();
        areas.add(3D);
        areas.add(3.25D);
        areas.add(3.5D);
        areas.add(3.75D);
        areas.add(4D);
        areas.add(4.5D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, areas);

        List<Double> damages = new ArrayList<>();
        damages.add(90D);
        damages.add(160D);
        damages.add(220D);
        damages.add(290D);
        damages.add(360D);
        damages.add(480D);
        DamageMechanic damageMechanic = new DamageMechanic(damages, DamageMechanic.DamageType.MELEE);

        List<Double> speeds = new ArrayList<>();
        speeds.add(1.2D);
        speeds.add(1.3D);
        speeds.add(1.4D);
        speeds.add(1.5D);
        speeds.add(1.6D);
        speeds.add(1.8D);
        PushMechanic pushMechanic = new PushMechanic(PushMechanic.PushType.FIXED, speeds, true);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(areaTarget);
        areaTarget.addChildren(damageMechanic);
        areaTarget.addChildren(pushMechanic);

        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.SWEEP_ATTACK, ArrangementParticle.CIRCLE, 3.4, 7, 0, 0, 0, 0, 1, 0, 0, null);

        SelfTarget selfTargetForSound = new SelfTarget();
        selfTargetForSound.addChildren(particleMechanic);
        areaTarget.addChildren(selfTargetForSound);
        selfTargetForSound.addChildren(new SoundMechanic(GoaSound.SKILL_WIND_PUSH));

        return skill;
    }

    private static Skill getTwo() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Heal yourself and shoot a projectile");
        description.add(ChatColor.GRAY + "that heals an ally on hit");

        List<Integer> reqLevels = new ArrayList<>();
        reqLevels.add(5);
        reqLevels.add(12);
        reqLevels.add(22);
        reqLevels.add(35);
        reqLevels.add(48);
        reqLevels.add(55);

        List<Integer> reqPoints = new ArrayList<>();
        reqPoints.add(2);
        reqPoints.add(2);
        reqPoints.add(3);
        reqPoints.add(3);
        reqPoints.add(4);
        reqPoints.add(4);

        List<Integer> manaCosts = new ArrayList<>();
        manaCosts.add(12);
        manaCosts.add(13);
        manaCosts.add(14);
        manaCosts.add(15);
        manaCosts.add(16);
        manaCosts.add(18);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(24);
        cooldowns.add(22);
        cooldowns.add(20);
        cooldowns.add(18);
        cooldowns.add(16);
        cooldowns.add(14);

        Skill skill = new Skill("Heal", 6, Material.IRON_HOE, 37, description, reqLevels, reqPoints, manaCosts, cooldowns);

        List<Integer> projectileAmounts = new ArrayList<>();
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 1.9, projectileAmounts, 30,
                0, 1, 0, 200, false, SmallFireball.class, Particle.HEART, ArrangementParticle.SPHERE, 0.5, 4, null, true);

        List<Integer> amounts = new ArrayList<>();
        amounts.add(50);
        amounts.add(250);
        amounts.add(500);
        amounts.add(1500);
        amounts.add(2500);
        amounts.add(3500);
        projectileMechanic.addChildren(new HealMechanic(amounts, new ArrayList<>()));

        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.HEART, ArrangementParticle.CIRCLE, 1.4, 8, 0, 0, 0, 0, 0.5, 0, 0, null);

        SelfTarget selfTarget = new SelfTarget();
        skill.addTrigger(selfTarget);
        selfTarget.addChildren(projectileMechanic);

        List<Double> areas = new ArrayList<>();
        areas.add(3D);
        areas.add(3.25D);
        areas.add(3.5D);
        areas.add(3.75D);
        areas.add(4D);
        areas.add(4.5D);
        AreaTarget areaTarget = new AreaTarget(true, false, true, 999, areas);

        projectileMechanic.addChildren(areaTarget);
        areaTarget.addChildren(particleMechanic);
        projectileMechanic.addChildren(new SoundMechanic(GoaSound.SKILL_HEAL));


        return skill;
    }

    private static Skill getThree() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Transform nearby enemies into pigs!");

        List<Integer> reqLevels = new ArrayList<>();
        reqLevels.add(10);
        reqLevels.add(18);
        reqLevels.add(26);
        reqLevels.add(38);
        reqLevels.add(50);
        reqLevels.add(64);

        List<Integer> reqPoints = new ArrayList<>();
        reqPoints.add(2);
        reqPoints.add(2);
        reqPoints.add(3);
        reqPoints.add(3);
        reqPoints.add(4);
        reqPoints.add(4);

        List<Integer> manaCosts = new ArrayList<>();
        manaCosts.add(18);
        manaCosts.add(20);
        manaCosts.add(22);
        manaCosts.add(24);
        manaCosts.add(26);
        manaCosts.add(28);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(32);
        cooldowns.add(30);
        cooldowns.add(28);
        cooldowns.add(26);
        cooldowns.add(24);
        cooldowns.add(20);

        Skill skill = new Skill("Polymorph", 6, Material.IRON_HOE, 55, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> areas = new ArrayList<>();
        areas.add(4D);
        areas.add(5D);
        areas.add(6D);
        areas.add(7D);
        areas.add(8D);
        areas.add(10D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, areas);

        List<Integer> ticks = new ArrayList<>();
        ticks.add(80);
        ticks.add(100);
        ticks.add(120);
        ticks.add(140);
        ticks.add(160);
        ticks.add(200);
        DisguiseMechanic disguiseMechanic = new DisguiseMechanic(DisguiseType.PIG, false, ticks);

        List<Integer> amplifiers = new ArrayList<>();
        amplifiers.add(2);
        amplifiers.add(3);
        amplifiers.add(4);
        amplifiers.add(5);
        amplifiers.add(6);
        amplifiers.add(7);
        PotionEffectMechanic slow = new PotionEffectMechanic(PotionEffectType.SLOW, ticks, amplifiers);

        DisarmMechanic disarmMechanic = new DisarmMechanic(ticks);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(areaTarget);
        areaTarget.addChildren(disguiseMechanic);
        areaTarget.addChildren(slow);
        areaTarget.addChildren(disarmMechanic);

        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.SPELL_WITCH, ArrangementParticle.CIRCLE, 6.7, 27, 0, 0, 0, 0, 0.5, 0, 0, null);
        SelfTarget selfTargetForSound = new SelfTarget();
        selfTargetForSound.addChildren(particleMechanic);
        areaTarget.addChildren(selfTargetForSound);
        selfTargetForSound.addChildren(new SoundMechanic(GoaSound.SKILL_PIG));

        return skill;
    }

    private static Skill getPassive() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "If you take a damage that leaves you with less than 15% health,");
        description.add(ChatColor.GRAY + "fly into the sky like an angel and heal yourself.");

        List<Integer> reqLevels = new ArrayList<>();
        reqLevels.add(20);
        reqLevels.add(30);
        reqLevels.add(40);
        reqLevels.add(50);
        reqLevels.add(60);
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
        cooldowns.add(45);
        cooldowns.add(45);
        cooldowns.add(45);
        cooldowns.add(45);
        cooldowns.add(45);
        cooldowns.add(45);

        Skill skill = new Skill("Resurrection", 6, Material.IRON_HOE, 35, description, reqLevels, reqPoints, manaCosts, cooldowns);

        InitializeTrigger initializeTrigger = new InitializeTrigger();

        TookPhysicalDamageTrigger tookPhysicalDamageTrigger = new TookPhysicalDamageTrigger(900L);
        TookMagicalDamageTrigger tookMagicalDamageTrigger = new TookMagicalDamageTrigger(900L);

        HealthCondition healthCondition = new HealthCondition(0.0, 0.15);

        List<Integer> flagTicks = new ArrayList<>();
        flagTicks.add(20 * 45);
        flagTicks.add(20 * 45);
        flagTicks.add(20 * 45);
        flagTicks.add(20 * 45);
        flagTicks.add(20 * 45);
        flagTicks.add(20 * 45);
        FlagSetMechanic flagSetMechanic = new FlagSetMechanic("passiveCooldown", flagTicks);
        FlagCondition flagCondition = new FlagCondition("passiveCooldown", false);

        skill.addTrigger(initializeTrigger);

        initializeTrigger.addChildren(tookPhysicalDamageTrigger);
        tookPhysicalDamageTrigger.addChildren(flagCondition);
        flagCondition.addChildren(healthCondition);
        healthCondition.addChildren(flagSetMechanic);
        List<Double> percents = new ArrayList<>();
        percents.add(0.3);
        percents.add(0.35);
        percents.add(0.4);
        percents.add(0.5);
        percents.add(0.6);
        percents.add(0.8);
        HealMechanic healMechanic = new HealMechanic(new ArrayList<>(), percents);
        healthCondition.addChildren(healMechanic);

        initializeTrigger.addChildren(tookMagicalDamageTrigger);
        tookMagicalDamageTrigger.addChildren(flagCondition);

        List<Integer> ticks = new ArrayList<>();
        ticks.add(120);
        ticks.add(120);
        ticks.add(120);
        ticks.add(120);
        ticks.add(120);
        ticks.add(120);
        List<Integer> amplifiers = new ArrayList<>();
        amplifiers.add(6);
        amplifiers.add(6);
        amplifiers.add(6);
        amplifiers.add(6);
        amplifiers.add(6);
        amplifiers.add(6);
        PotionEffectMechanic levitation = new PotionEffectMechanic(PotionEffectType.LEVITATION, ticks, amplifiers);
        PotionEffectMechanic invis = new PotionEffectMechanic(PotionEffectType.INVISIBILITY, ticks, amplifiers);
        PotionEffectMechanic glowing = new PotionEffectMechanic(PotionEffectType.GLOWING, ticks, amplifiers);
        List<Integer> ticksImmunity = new ArrayList<>();
        ticksImmunity.add(240);
        ticksImmunity.add(240);
        ticksImmunity.add(240);
        ticksImmunity.add(240);
        ticksImmunity.add(240);
        ticksImmunity.add(240);
        ImmunityMechanic immunityMechanic = new ImmunityMechanic(EntityDamageEvent.DamageCause.FALL, ticksImmunity);

        healthCondition.addChildren(levitation);
        healthCondition.addChildren(invis);
        healthCondition.addChildren(glowing);
        healthCondition.addChildren(immunityMechanic);
        healthCondition.addChildren(new SoundMechanic(GoaSound.SKILL_BUFF));

        return skill;
    }

    private static Skill getUltimate() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Jump into the sky, give invincible effect");
        description.add(ChatColor.GRAY + "to yourself and nearby allies when you land.");

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
        manaCosts.add(24);
        manaCosts.add(26);
        manaCosts.add(28);
        manaCosts.add(30);
        manaCosts.add(32);
        manaCosts.add(34);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(52);
        cooldowns.add(48);
        cooldowns.add(44);
        cooldowns.add(40);
        cooldowns.add(36);
        cooldowns.add(32);

        Skill skill = new Skill("Cosmic Radiance", 6, Material.IRON_HOE, 18, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> forwards = new ArrayList<>();
        forwards.add(1.4);
        forwards.add(1.5);
        forwards.add(1.6);
        forwards.add(1.7);
        forwards.add(1.8);
        forwards.add(2D);
        List<Double> upwards = new ArrayList<>();
        upwards.add(1.7D);
        upwards.add(1.75D);
        upwards.add(1.8);
        upwards.add(1.85);
        upwards.add(1.9);
        upwards.add(2D);
        List<Double> right = new ArrayList<>();
        right.add(0D);
        right.add(0D);
        right.add(0D);
        right.add(0D);
        right.add(0D);
        right.add(0D);
        LaunchMechanic launchMechanic = new LaunchMechanic(LaunchMechanic.Relative.TARGET, forwards, upwards, right);

        LandTrigger landTrigger = new LandTrigger();

        ImmunityMechanic immunityMechanic = new ImmunityMechanic(EntityDamageEvent.DamageCause.FALL, new ArrayList<>());//0 for infinite
        ImmunityRemoveMechanic immunityRemoveMechanic = new ImmunityRemoveMechanic(EntityDamageEvent.DamageCause.FALL, 5);

        List<Integer> repeatAmounts = new ArrayList<>();
        repeatAmounts.add(10);
        repeatAmounts.add(12);
        repeatAmounts.add(14);
        repeatAmounts.add(16);
        repeatAmounts.add(18);
        repeatAmounts.add(20);
        ParticleAnimationMechanic particleAnimationMechanic = new ParticleAnimationMechanic(Particle.REDSTONE, ArrangementParticle.CIRCLE, 1, 3, 0,
                0, 0, 0, 0, 0, 1, 5L, repeatAmounts, new Particle.DustOptions(Color.YELLOW, 2));

        List<Double> radiuses = new ArrayList<>();
        radiuses.add(8D);
        radiuses.add(9D);
        radiuses.add(10D);
        radiuses.add(11D);
        radiuses.add(12D);
        radiuses.add(14D);
        AreaTarget areaTarget = new AreaTarget(true, false, true, 999, radiuses);

        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.REDSTONE, ArrangementParticle.CIRCLE, 8, 3, -0.6, 0.4, 0.1, 0, 0, 0, 1, new Particle.DustOptions(Color.YELLOW, 2));

        skill.addTrigger(selfTarget);

        selfTarget.addChildren(launchMechanic);
        selfTarget.addChildren(immunityMechanic);
        selfTarget.addChildren(particleAnimationMechanic);
        selfTarget.addChildren(landTrigger);

        landTrigger.addChildren(particleMechanic);
        landTrigger.addChildren(new SoundMechanic(GoaSound.SKILL_BUFF));
        landTrigger.addChildren(immunityRemoveMechanic);
        landTrigger.addChildren(areaTarget);
        List<Integer> ticks = new ArrayList<>();
        ticks.add(120);
        ticks.add(130);
        ticks.add(140);
        ticks.add(150);
        ticks.add(160);
        ticks.add(180);
        areaTarget.addChildren(new InvincibleMechanic(ticks));

        List<Integer> repeatAmounts2 = new ArrayList<>();
        repeatAmounts2.add(24);
        repeatAmounts2.add(26);
        repeatAmounts2.add(28);
        repeatAmounts2.add(30);
        repeatAmounts2.add(32);
        repeatAmounts2.add(36);
        ParticleAnimationMechanic particleAnimationMechanic2 = new ParticleAnimationMechanic(Particle.REDSTONE, ArrangementParticle.CIRCLE, 1, 3,
                -0.1, 1, 0, 0, 0.5, 0, 1, 5L, repeatAmounts2, new Particle.DustOptions(Color.YELLOW, 2));

        areaTarget.addChildren(particleAnimationMechanic2);

        return skill;
    }
}
