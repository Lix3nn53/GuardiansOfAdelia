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
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.AreaTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SelfTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.InitializeTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.LandTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.TookMagicalDamageTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.TookPhysicalDamageTrigger;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ArrangementParticle;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
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
        description.add("Deal damage and push nearby enemies");

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
        cooldowns.add(8);
        cooldowns.add(8);
        cooldowns.add(8);
        cooldowns.add(8);
        cooldowns.add(8);
        cooldowns.add(8);

        Skill skill = new Skill("Hammerblow", Material.IRON_HOE, 46, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> areas = new ArrayList<>();
        areas.add(3D);
        areas.add(3D);
        areas.add(3D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, areas);

        List<Double> damages = new ArrayList<>();
        damages.add(3D);
        damages.add(3D);
        damages.add(3D);
        damages.add(3D);
        DamageMechanic damageMechanic = new DamageMechanic(damages, DamageMechanic.DamageType.MELEE);

        List<Double> speeds = new ArrayList<>();
        speeds.add(1.2D);
        speeds.add(1.4D);
        speeds.add(1.6D);
        PushMechanic pushMechanic = new PushMechanic(PushMechanic.PushType.FIXED, speeds, true);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(areaTarget);
        areaTarget.addChildren(damageMechanic);
        areaTarget.addChildren(pushMechanic);

        return skill;
    }

    private static Skill getTwo() {
        List<String> description = new ArrayList<>();
        description.add("Heal yourself and shoot a projectile");
        description.add("that heals allies in area on hit");

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

        Skill skill = new Skill("Heal", Material.IRON_HOE, 37, description, reqLevels, reqPoints, manaCosts, cooldowns);

        List<Integer> projectileAmounts = new ArrayList<>();
        projectileAmounts.add(1);
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 1.9, projectileAmounts, 30,
                0, 1, 0, 200, false, SmallFireball.class, Particle.HEART, ArrangementParticle.SPHERE, 0.5, 4, null);

        List<Integer> amounts = new ArrayList<>();
        amounts.add(50);
        amounts.add(50);
        amounts.add(50);
        projectileMechanic.addChildren(new HealMechanic(amounts, new ArrayList<>()));

        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.HEART, ArrangementParticle.CIRCLE, 6, 20, 0, 0, 0, 0, 0.5, 0, 0, null);

        skill.addTrigger(projectileMechanic);
        projectileMechanic.addChildren(particleMechanic);


        return skill;
    }

    private static Skill getThree() {
        List<String> description = new ArrayList<>();
        description.add("Transform nearby enemies into pigs!");

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

        Skill skill = new Skill("Polymorph", Material.IRON_HOE, 55, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> areas = new ArrayList<>();
        areas.add(3D);
        areas.add(3D);
        areas.add(3D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, areas);

        List<Integer> ticks = new ArrayList<>();
        ticks.add(80);
        ticks.add(80);
        ticks.add(80);
        ticks.add(80);
        DisguiseMechanic disguiseMechanic = new DisguiseMechanic(DisguiseType.PIG, false, ticks);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(areaTarget);
        areaTarget.addChildren(disguiseMechanic);

        return skill;
    }

    private static Skill getPassive() {
        List<String> description = new ArrayList<>();
        description.add("If you take a damage that kills you,");
        description.add("block the damage and heal yourself.");

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
        cooldowns.add(64);
        cooldowns.add(64);
        cooldowns.add(64);
        cooldowns.add(64);
        cooldowns.add(64);
        cooldowns.add(64);

        Skill skill = new Skill("Resurrection", Material.IRON_HOE, 35, description, reqLevels, reqPoints, manaCosts, cooldowns);

        InitializeTrigger initializeTrigger = new InitializeTrigger();

        TookPhysicalDamageTrigger tookPhysicalDamageTrigger = new TookPhysicalDamageTrigger(20L * 30);
        TookMagicalDamageTrigger tookMagicalDamageTrigger = new TookMagicalDamageTrigger(20L * 30);

        HealthCondition healthCondition = new HealthCondition(0.0, 0.1);

        List<Integer> flagTicks = new ArrayList<>();
        flagTicks.add(20 * 30);
        flagTicks.add(20 * 30);
        flagTicks.add(20 * 30);
        FlagSetMechanic flagSetMechanic = new FlagSetMechanic("passiveCooldown", flagTicks);
        FlagCondition flagCondition = new FlagCondition("passiveCooldown", false);

        skill.addTrigger(initializeTrigger);

        initializeTrigger.addChildren(tookPhysicalDamageTrigger);
        tookPhysicalDamageTrigger.addChildren(flagCondition);
        flagCondition.addChildren(healthCondition);
        healthCondition.addChildren(flagSetMechanic);
        List<Double> percents = new ArrayList<>();
        percents.add(0.3);
        percents.add(0.5);
        percents.add(0.6);
        HealMechanic healMechanic = new HealMechanic(new ArrayList<>(), percents);
        healthCondition.addChildren(healMechanic);

        initializeTrigger.addChildren(tookMagicalDamageTrigger);
        tookMagicalDamageTrigger.addChildren(flagCondition);

        List<Integer> ticks = new ArrayList<>();
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        List<Integer> amplifiers = new ArrayList<>();
        amplifiers.add(999);
        amplifiers.add(999);
        amplifiers.add(999);
        amplifiers.add(999);
        PotionEffectMechanic levitation = new PotionEffectMechanic(PotionEffectType.LEVITATION, ticks, amplifiers);
        PotionEffectMechanic invis = new PotionEffectMechanic(PotionEffectType.INVISIBILITY, ticks, amplifiers);
        PotionEffectMechanic glowing = new PotionEffectMechanic(PotionEffectType.GLOWING, ticks, amplifiers);
        ImmunityMechanic immunityMechanic = new ImmunityMechanic(EntityDamageEvent.DamageCause.FALL, ticks);

        healthCondition.addChildren(levitation);
        healthCondition.addChildren(invis);
        healthCondition.addChildren(glowing);
        healthCondition.addChildren(immunityMechanic);

        return skill;
    }

    private static Skill getUltimate() {
        List<String> description = new ArrayList<>();
        description.add("Jump into the air, give invincible effect");
        description.add("to yourself and nearby allies when you land.");

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
        cooldowns.add(64);
        cooldowns.add(64);
        cooldowns.add(64);
        cooldowns.add(64);
        cooldowns.add(64);
        cooldowns.add(64);

        Skill skill = new Skill("Cosmic Radiance", Material.IRON_HOE, 18, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> forwards = new ArrayList<>();
        forwards.add(1.5);
        List<Double> upwards = new ArrayList<>();
        upwards.add(1D);
        upwards.add(1.8);
        upwards.add(1.85);
        List<Double> right = new ArrayList<>();
        right.add(0D);
        LaunchMechanic launchMechanic = new LaunchMechanic(LaunchMechanic.Relative.TARGET, forwards, upwards, right);

        LandTrigger landTrigger = new LandTrigger();

        ImmunityMechanic immunityMechanic = new ImmunityMechanic(EntityDamageEvent.DamageCause.FALL, new ArrayList<>());//0 for infinite
        ImmunityRemoveMechanic immunityRemoveMechanic = new ImmunityRemoveMechanic(EntityDamageEvent.DamageCause.FALL);

        List<Integer> repeatAmounts = new ArrayList<>();
        repeatAmounts.add(15);
        ParticleAnimationMechanic particleAnimationMechanic = new ParticleAnimationMechanic(Particle.REDSTONE, ArrangementParticle.CIRCLE, 1, 3, -0.1, 1, 0, 0, 0, 0, 1, 4L, repeatAmounts, new Particle.DustOptions(Color.FUCHSIA, 2));

        List<Double> radiuses = new ArrayList<>();
        radiuses.add(9D);
        AreaTarget areaTarget = new AreaTarget(true, false, true, 999, radiuses);

        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.REDSTONE, ArrangementParticle.CIRCLE, 8, 3, -0.6, 0.4, 0.1, 0, 0, 0, 1, new Particle.DustOptions(Color.YELLOW, 2));

        skill.addTrigger(selfTarget);

        selfTarget.addChildren(launchMechanic);
        selfTarget.addChildren(immunityMechanic);
        selfTarget.addChildren(particleAnimationMechanic);
        selfTarget.addChildren(landTrigger);

        landTrigger.addChildren(particleMechanic);
        landTrigger.addChildren(immunityRemoveMechanic);
        landTrigger.addChildren(areaTarget);
        List<Integer> ticks = new ArrayList<>();
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        areaTarget.addChildren(new InvincibleMechanic(ticks));

        List<Integer> repeatAmounts2 = new ArrayList<>();
        repeatAmounts2.add(15);
        ParticleAnimationMechanic particleAnimationMechanic2 = new ParticleAnimationMechanic(Particle.REDSTONE, ArrangementParticle.CIRCLE, 1, 3, -0.1, 1, 0, 0, 0, 0, 1, 4L, repeatAmounts2, new Particle.DustOptions(Color.FUCHSIA, 2));

        areaTarget.addChildren(particleAnimationMechanic2);

        return skill;
    }
}
