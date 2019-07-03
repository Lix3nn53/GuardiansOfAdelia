package io.github.lix3nn53.guardiansofadelia.guardian.skill.list;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.*;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.hologram.HologramMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityRemoveMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.ProjectileMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.SpreadType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.AreaTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SelfTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.InitializeTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.LandTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.TookPhysicalDamageTrigger;
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
        description.add("Deal damage to nearby enemies");

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

        Skill skill = new Skill("Power Slash", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, 4);
        DamageMechanic damageMechanic = new DamageMechanic(10, 10, DamageMechanic.DamageType.MELEE);

        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.VILLAGER_ANGRY, ArrangementParticle.CIRCLE, 3, 20, 0, 0, 0, 0, 0.5, 0, 0, null);

        skill.addTrigger(selfTarget);

        selfTarget.addChildren(areaTarget);
        selfTarget.addChildren(particleMechanic);

        areaTarget.addChildren(damageMechanic);

        return skill;
    }

    private static Skill getTwo() {
        List<String> description = new ArrayList<>();
        description.add("Shoot a flame-burst that pierces through");
        description.add("targets and launches them into the air");

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

        Skill skill = new Skill("Flame Burst", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 1.9, 1, 30,
                0, 1, 0, 200, true, SmallFireball.class, Particle.FLAME, ArrangementParticle.SPHERE, 0.5, 4, null);

        DamageMechanic damageMechanic = new DamageMechanic(10, 10, DamageMechanic.DamageType.MELEE);

        LaunchMechanic launchMechanic = new LaunchMechanic(LaunchMechanic.Relative.TARGET, 0, 0.75, 0, 0.1);

        skill.addTrigger(selfTarget);

        selfTarget.addChildren(projectileMechanic);

        projectileMechanic.addChildren(damageMechanic);
        projectileMechanic.addChildren(launchMechanic);

        return skill;
    }

    private static Skill getThree() {
        List<String> description = new ArrayList<>();
        description.add("Place a flag at your location that");
        description.add("gives damage buff to nearby allies");

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

        Skill skill = new Skill("Victory Flag", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        //Add HologramMechanic to SelfTarget's children
        HologramMechanic hologramMechanic = new HologramMechanic(Material.IRON_PICKAXE, 10000004, 30, ChatColor.AQUA + "< Victory-Flag %caster% >");

        //Add repeatMechanic to hologramMechanic's children
        RepeatMechanic repeatMechanic = new RepeatMechanic(40L, 15);

        //Add areaTarget to repeatMechanic's children
        AreaTarget areaTarget = new AreaTarget(true, false, true, 999, 9);
        BuffMechanic physicalDamageBuff = new BuffMechanic(BuffType.PHYSICAL_DAMAGE, 0.1, 40L, 0.03);
        BuffMechanic magicalDamageBuff = new BuffMechanic(BuffType.MAGIC_DAMAGE, 0.1, 40L, 0.03);
        PotionEffectMechanic potionEffectMechanic = new PotionEffectMechanic(PotionEffectType.INCREASE_DAMAGE, 41, 0);

        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.CRIT, ArrangementParticle.CIRCLE, 2, 6, 0, 0, 0, 0, 2, 0, 0, null);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(hologramMechanic);
        hologramMechanic.addChildren(repeatMechanic);

        //repeat part 1, area and effects
        repeatMechanic.addChildren(areaTarget);
        areaTarget.addChildren(physicalDamageBuff);
        areaTarget.addChildren(magicalDamageBuff);
        areaTarget.addChildren(potionEffectMechanic);

        repeatMechanic.addChildren(particleMechanic);

        return skill;
    }

    private static Skill getPassive() {
        List<String> description = new ArrayList<>();
        description.add("Unleash your rage when your health is low");
        description.add("Gain movement speed and jump boost");

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

        Skill skill = new Skill("Death Rattle", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);

        InitializeTrigger initializeTrigger = new InitializeTrigger();

        TookPhysicalDamageTrigger tookPhysicalDamageTrigger = new TookPhysicalDamageTrigger(20L * 30);

        MessageMechanic messageMechanic = new MessageMechanic("tookPhysicalDamageTrigger activates MessageMechanic");

        skill.addTrigger(initializeTrigger);
        initializeTrigger.addChildren(tookPhysicalDamageTrigger);
        tookPhysicalDamageTrigger.addChildren(messageMechanic);

        return skill;
    }

    private static Skill getUltimate() {
        List<String> description = new ArrayList<>();
        description.add("Jump forward into the air. When you land");
        description.add("deal damage and launch targets upwards.");

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

        Skill skill = new Skill("Grand Skyfall", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        LaunchMechanic launchMechanic = new LaunchMechanic(LaunchMechanic.Relative.TARGET, 1.5, 1, 0, 0.25);

        LandTrigger landTrigger = new LandTrigger();

        ImmunityMechanic immunityMechanic = new ImmunityMechanic(EntityDamageEvent.DamageCause.FALL, 0);//0 for infinite
        ImmunityRemoveMechanic immunityRemoveMechanic = new ImmunityRemoveMechanic(EntityDamageEvent.DamageCause.FALL);

        ParticleAnimationMechanic particleAnimationMechanic = new ParticleAnimationMechanic(Particle.REDSTONE, ArrangementParticle.CIRCLE, 1, 3, -0.1, 1, 0, 0, 0, 0, 1, 4L, 4, new Particle.DustOptions(Color.FUCHSIA, 2));

        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, 5);

        DamageMechanic damageMechanic = new DamageMechanic(10, 10, DamageMechanic.DamageType.MELEE);

        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.REDSTONE, ArrangementParticle.CIRCLE, 1, 3, -0.6, 0.4, 0.1, 0, 0, 0, 1, new Particle.DustOptions(Color.YELLOW, 2));

        skill.addTrigger(selfTarget);

        selfTarget.addChildren(launchMechanic);
        selfTarget.addChildren(immunityMechanic);
        selfTarget.addChildren(particleAnimationMechanic);
        selfTarget.addChildren(landTrigger);

        landTrigger.addChildren(particleMechanic);
        landTrigger.addChildren(immunityRemoveMechanic);
        landTrigger.addChildren(areaTarget);

        areaTarget.addChildren(damageMechanic);

        return skill;
    }
}
