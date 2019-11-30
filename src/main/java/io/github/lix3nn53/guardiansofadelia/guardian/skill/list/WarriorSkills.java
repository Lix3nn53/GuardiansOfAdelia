package io.github.lix3nn53.guardiansofadelia.guardian.skill.list;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition.HealthCondition;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.*;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.hologram.HologramMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityRemoveMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.ProjectileMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.SpreadType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.AreaTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.FilterCurrentTargets;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SelfTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.InitializeTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.LandTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.TookPhysicalDamageTrigger;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ArrangementParticle;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class WarriorSkills {

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
        description.add(ChatColor.GRAY + "Deal damage to nearby enemies");

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

        Skill skill = new Skill("Power Slash", 6, Material.IRON_HOE, 15, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> radiuses = new ArrayList<>();
        radiuses.add(2.5D);
        radiuses.add(3D);
        radiuses.add(3.5D);
        radiuses.add(4D);
        radiuses.add(4.5D);
        radiuses.add(5D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, radiuses);
        SelfTarget selfTargetForSound = new SelfTarget();
        areaTarget.addChildren(selfTargetForSound);
        selfTargetForSound.addChildren(new SoundMechanic(GoaSound.SKILL_LIGHTNING_NORMAL));

        List<Double> damages = new ArrayList<>();
        damages.add(24.0);
        damages.add(60.0);
        damages.add(240.0);
        damages.add(390.0);
        damages.add(540.0);
        damages.add(900.0);

        DamageMechanic damageMechanic = new DamageMechanic(damages, DamageMechanic.DamageType.MELEE);

        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.VILLAGER_ANGRY, ArrangementParticle.CIRCLE, 3, 20, 0, 0, 0, 0, 0.5, 0, 0, null);

        skill.addTrigger(selfTarget);

        selfTarget.addChildren(areaTarget);
        selfTargetForSound.addChildren(particleMechanic);

        areaTarget.addChildren(damageMechanic);

        return skill;
    }

    private static Skill getTwo() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Shoot a flame-burst that launches");
        description.add(ChatColor.GRAY + "a target into the sky");

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
        manaCosts.add(15);
        manaCosts.add(17);
        manaCosts.add(19);
        manaCosts.add(21);
        manaCosts.add(23);
        manaCosts.add(25);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(16);
        cooldowns.add(16);
        cooldowns.add(16);
        cooldowns.add(16);
        cooldowns.add(16);
        cooldowns.add(16);

        Skill skill = new Skill("Flame Burst", 6, Material.IRON_HOE, 57, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Integer> projectileAmounts = new ArrayList<>();
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 1.9, projectileAmounts, 30,
                0, 1, 0, 200, true, SmallFireball.class, Particle.FLAME, ArrangementParticle.SPHERE, 0.5, 4, null, true);

        List<Double> damages = new ArrayList<>();
        damages.add(142D);
        damages.add(240D);
        damages.add(324D);
        damages.add(432D);
        damages.add(540D);
        damages.add(720D);
        DamageMechanic damageMechanic = new DamageMechanic(damages, DamageMechanic.DamageType.MELEE);

        List<Double> forwards = new ArrayList<>();
        forwards.add(0D);
        forwards.add(0D);
        forwards.add(0D);
        forwards.add(0D);
        forwards.add(0D);
        forwards.add(0D);
        List<Double> upwards = new ArrayList<>();
        upwards.add(1.7);
        upwards.add(1.75);
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

        FilterCurrentTargets filterCurrentTargets = new FilterCurrentTargets(false, true, false, 999);

        skill.addTrigger(selfTarget);

        selfTarget.addChildren(projectileMechanic);
        selfTarget.addChildren(new SoundMechanic(GoaSound.SKILL_FIRE_SLASH));

        projectileMechanic.addChildren(filterCurrentTargets);
        filterCurrentTargets.addChildren(damageMechanic);
        filterCurrentTargets.addChildren(launchMechanic);
        filterCurrentTargets.addChildren(new SoundMechanic(GoaSound.SKILL_STUN_HIT));

        return skill;
    }

    private static Skill getThree() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Place a flag at your location that");
        description.add(ChatColor.GRAY + "gives damage buff to nearby allies");

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
        manaCosts.add(24);
        manaCosts.add(28);
        manaCosts.add(32);
        manaCosts.add(36);
        manaCosts.add(40);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(90);
        cooldowns.add(90);
        cooldowns.add(90);
        cooldowns.add(90);
        cooldowns.add(90);
        cooldowns.add(90);

        Skill skill = new Skill("Victory Flag", 6, Material.IRON_HOE, 9, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        //Add HologramMechanic to SelfTarget's children
        List<Integer> seconds = new ArrayList<>();
        seconds.add(40);
        seconds.add(40);
        seconds.add(40);
        seconds.add(40);
        seconds.add(40);
        seconds.add(40);
        HologramMechanic hologramMechanic = new HologramMechanic(Material.IRON_PICKAXE, 10000004, seconds, ChatColor.AQUA + "< Victory-Flag %caster% >");

        //Add repeatMechanic to hologramMechanic's children
        List<Integer> repetitions = new ArrayList<>();
        repetitions.add(20);
        repetitions.add(20);
        repetitions.add(20);
        repetitions.add(20);
        repetitions.add(20);
        repetitions.add(20);
        RepeatMechanic repeatMechanic = new RepeatMechanic(40L, repetitions);

        //Add areaTarget to repeatMechanic's children
        List<Double> radiuses = new ArrayList<>();
        radiuses.add(8D);
        radiuses.add(9D);
        radiuses.add(10D);
        radiuses.add(11D);
        radiuses.add(12D);
        radiuses.add(14D);
        AreaTarget areaTarget = new AreaTarget(true, false, true, 999, radiuses);

        List<Integer> ticks = new ArrayList<>();
        ticks.add(41);
        ticks.add(41);
        ticks.add(41);
        ticks.add(41);
        ticks.add(41);
        ticks.add(41);
        List<Double> multipliers = new ArrayList<>();
        multipliers.add(0.08);
        multipliers.add(0.1);
        multipliers.add(0.12);
        multipliers.add(0.14);
        multipliers.add(0.16);
        multipliers.add(0.2);
        BuffMechanic physicalDamageBuff = new BuffMechanic(BuffType.PHYSICAL_DAMAGE, multipliers, ticks);
        BuffMechanic magicalDamageBuff = new BuffMechanic(BuffType.MAGIC_DAMAGE, multipliers, ticks);

        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.CRIT, ArrangementParticle.CIRCLE, 2.4, 7, 0, 0, 0, 0, 1.8, 0, 0, null);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(hologramMechanic);
        selfTarget.addChildren(new SoundMechanic(GoaSound.SKILL_BUFF));
        hologramMechanic.addChildren(repeatMechanic);

        //repeat part 1, area and effects
        repeatMechanic.addChildren(areaTarget);
        areaTarget.addChildren(physicalDamageBuff);
        areaTarget.addChildren(magicalDamageBuff);

        repeatMechanic.addChildren(particleMechanic);

        return skill;
    }

    private static Skill getPassive() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "If you take a damage that leaves you with less than 15% health,");
        description.add(ChatColor.GRAY + "unleash your rage and gain movement speed and jump boost");

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
        cooldowns.add(45);
        cooldowns.add(45);
        cooldowns.add(45);
        cooldowns.add(45);
        cooldowns.add(45);
        cooldowns.add(45);

        Skill skill = new Skill("Death Rattle", 6, Material.IRON_HOE, 47, description, reqLevels, reqPoints, manaCosts, cooldowns);

        InitializeTrigger initializeTrigger = new InitializeTrigger();

        TookPhysicalDamageTrigger tookPhysicalDamageTrigger = new TookPhysicalDamageTrigger(900L);

        HealthCondition healthCondition = new HealthCondition(0.0, 0.15);

        List<Integer> ticks = new ArrayList<>();
        ticks.add(120);
        ticks.add(140);
        ticks.add(160);
        ticks.add(180);
        ticks.add(200);
        ticks.add(240);
        List<Integer> amplifiers = new ArrayList<>();
        amplifiers.add(2);
        amplifiers.add(3);
        amplifiers.add(4);
        amplifiers.add(5);
        amplifiers.add(6);
        amplifiers.add(7);
        PotionEffectMechanic potionEffectMechanic = new PotionEffectMechanic(PotionEffectType.SPEED, ticks, amplifiers);

        List<Integer> repeatAmount = new ArrayList<>();
        repeatAmount.add(24);
        repeatAmount.add(28);
        repeatAmount.add(32);
        repeatAmount.add(36);
        repeatAmount.add(40);
        repeatAmount.add(48);
        ParticleAnimationMechanic particleAnimationMechanic = new ParticleAnimationMechanic(Particle.REDSTONE, ArrangementParticle.CIRCLE, 1, 4, 0, 0, 0,
                0, 0.5, 0, 0, 5, repeatAmount, new Particle.DustOptions(Color.RED, 8));

        skill.addTrigger(initializeTrigger);
        initializeTrigger.addChildren(tookPhysicalDamageTrigger);
        tookPhysicalDamageTrigger.addChildren(healthCondition);
        healthCondition.addChildren(new SoundMechanic(GoaSound.SKILL_SCREAM));
        healthCondition.addChildren(potionEffectMechanic);
        healthCondition.addChildren(particleAnimationMechanic);

        return skill;
    }

    private static Skill getUltimate() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Jump forward into the air. When you land");
        description.add(ChatColor.GRAY + "deal damage and launch targets into the sky.");

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
        manaCosts.add(50);
        manaCosts.add(50);
        manaCosts.add(50);
        manaCosts.add(50);
        manaCosts.add(50);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(50);
        cooldowns.add(50);
        cooldowns.add(50);
        cooldowns.add(50);
        cooldowns.add(50);
        cooldowns.add(50);

        Skill skill = new Skill("Grand Skyfall", 6, Material.IRON_HOE, 44, description, reqLevels, reqPoints, manaCosts, cooldowns);

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

        ImmunityMechanic immunityMechanic = new ImmunityMechanic(EntityDamageEvent.DamageCause.FALL, new ArrayList<>()); //0 for infinite
        ImmunityRemoveMechanic immunityRemoveMechanic = new ImmunityRemoveMechanic(EntityDamageEvent.DamageCause.FALL, 5);

        List<Integer> repeatAmounts = new ArrayList<>();
        repeatAmounts.add(10);
        repeatAmounts.add(12);
        repeatAmounts.add(14);
        repeatAmounts.add(16);
        repeatAmounts.add(18);
        repeatAmounts.add(20);
        ParticleAnimationMechanic particleAnimationMechanic = new ParticleAnimationMechanic(Particle.REDSTONE, ArrangementParticle.CIRCLE, 1, 3, -0.1, 1, 0, 0, 0, 0, 1, 4L, repeatAmounts, new Particle.DustOptions(Color.ORANGE, 2));

        List<Double> radiuses = new ArrayList<>();
        radiuses.add(8D);
        radiuses.add(9D);
        radiuses.add(10D);
        radiuses.add(11D);
        radiuses.add(12D);
        radiuses.add(14D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, radiuses);

        List<Double> damages = new ArrayList<>();
        damages.add(200.0);
        damages.add(480.0);
        damages.add(960.0);
        damages.add(1600.0);
        damages.add(2200.0);
        damages.add(3600.0);
        DamageMechanic damageMechanic = new DamageMechanic(damages, DamageMechanic.DamageType.MELEE);

        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.VILLAGER_ANGRY, ArrangementParticle.CIRCLE, 11, 33, 0, 0, 0, 0, 0.5, 0, 0, null);

        skill.addTrigger(selfTarget);

        selfTarget.addChildren(launchMechanic);
        selfTarget.addChildren(new SoundMechanic(GoaSound.SKILL_JUMP));
        selfTarget.addChildren(immunityMechanic);
        selfTarget.addChildren(particleAnimationMechanic);
        selfTarget.addChildren(landTrigger);

        landTrigger.addChildren(particleMechanic);
        landTrigger.addChildren(new SoundMechanic(GoaSound.SKILL_POWERFUL_LANDING));
        landTrigger.addChildren(immunityRemoveMechanic);
        landTrigger.addChildren(areaTarget);

        areaTarget.addChildren(damageMechanic);

        return skill;
    }
}
