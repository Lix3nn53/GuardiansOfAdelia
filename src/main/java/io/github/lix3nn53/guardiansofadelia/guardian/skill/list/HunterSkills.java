package io.github.lix3nn53.guardiansofadelia.guardian.skill.list;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition.FlagCondition;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.*;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.hologram.HologramMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityRemoveMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.ProjectileMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.SpreadType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.SilenceMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.AreaTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SelfTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.AddPiercingToArrowShootFromCrossbowTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.InitializeTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.LandTrigger;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ArrangementParticle;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class HunterSkills {

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
        description.add(ChatColor.GRAY + "Shoot an arrow that deals area");
        description.add(ChatColor.GRAY + "damage and pushes targets");

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

        Skill skill = new Skill("Explosive Arrow", 6, Material.IRON_HOE, 20, description, reqLevels, reqPoints, manaCosts, cooldowns);
        SelfTarget selfTarget = new SelfTarget();

        List<Integer> projectileAmounts = new ArrayList<>();
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 1.9, projectileAmounts, 30,
                0, 1, 0, 200, true, Arrow.class);

        List<Double> radiuses = new ArrayList<>();
        radiuses.add(2D);
        radiuses.add(2.5D);
        radiuses.add(3D);
        radiuses.add(3.5D);
        radiuses.add(4D);
        radiuses.add(4.5D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, radiuses);

        projectileMechanic.addChildren(areaTarget);

        List<Double> damages = new ArrayList<>();
        damages.add(3D);
        damages.add(3D);
        damages.add(3D);
        damages.add(3D);
        damages.add(3D);
        damages.add(3D);
        areaTarget.addChildren(new DamageMechanic(damages, DamageMechanic.DamageType.MAGIC));

        List<Double> speeds = new ArrayList<>();
        speeds.add(1.25);
        speeds.add(1.3);
        speeds.add(1.35);
        speeds.add(1.4);
        speeds.add(1.45);
        speeds.add(1.5);
        PushMechanic pushMechanic = new PushMechanic(PushMechanic.PushType.FIXED, speeds, true);

        areaTarget.addChildren(pushMechanic);

        selfTarget.addChildren(projectileMechanic);
        selfTarget.addChildren(new SoundMechanic(GoaSound.SKILL_PROJECTILE_ICE));

        skill.addTrigger(selfTarget);
        return skill;
    }

    private static Skill getTwo() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Fire multiple arrows in a cone");

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

        Skill skill = new Skill("Volley", 6, Material.IRON_HOE, 22, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Integer> projectileAmounts = new ArrayList<>();
        projectileAmounts.add(4);
        projectileAmounts.add(5);
        projectileAmounts.add(6);
        projectileAmounts.add(7);
        projectileAmounts.add(8);
        projectileAmounts.add(9);
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 1.9, projectileAmounts, 30,
                0, 1, 0, 200, true, Arrow.class);

        List<Double> damages = new ArrayList<>();
        damages.add(3D);
        damages.add(3D);
        damages.add(3D);
        damages.add(3D);
        damages.add(3D);
        damages.add(3D);
        projectileMechanic.addChildren(new DamageMechanic(damages, DamageMechanic.DamageType.RANGED));

        selfTarget.addChildren(projectileMechanic);
        selfTarget.addChildren(new SoundMechanic(GoaSound.SKILL_MULTI_ARROW));

        skill.addTrigger(selfTarget);

        return skill;
    }

    private static Skill getThree() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Fire a grapple particle projectile.");
        description.add(ChatColor.GRAY + "If it collides with terrain dash towards it,");
        description.add(ChatColor.GRAY + "if it hits a target pull the target.");

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

        Skill skill = new Skill("Hookshot", 6, Material.IRON_HOE, 32, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Integer> projectileAmounts = new ArrayList<>();
        projectileAmounts.add(1);
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 1.9, projectileAmounts, 30, 0, 0, 0, 90,
                false, SmallFireball.class, Particle.REDSTONE, ArrangementParticle.CIRCLE, 0.5, 4, new Particle.DustOptions(Color.AQUA, 8), true);
        projectileMechanic.setAddCasterAsFirstTargetIfHitSuccess(true);
        projectileMechanic.setAddCasterAsSecondTargetIfHitFail(true);

        List<Double> speeds = new ArrayList<>();
        speeds.add(-3D);
        speeds.add(-3.25D);
        speeds.add(-3.5D);
        speeds.add(-3.75D);
        speeds.add(-4D);
        speeds.add(-4.5D);
        PushMechanic pushMechanic = new PushMechanic(PushMechanic.PushType.FIXED, speeds, false);

        selfTarget.addChildren(projectileMechanic);
        selfTarget.addChildren(new SoundMechanic(GoaSound.SKILL_PROJECTILE_FIRE));

        projectileMechanic.addChildren(pushMechanic);

        //add fall protection
        ImmunityMechanic immunityMechanic = new ImmunityMechanic(EntityDamageEvent.DamageCause.FALL, new ArrayList<>()); //empty list for infinite
        projectileMechanic.addChildren(immunityMechanic);

        //remove fall protection
        LandTrigger landTrigger = new LandTrigger();
        ImmunityRemoveMechanic immunityRemoveMechanic = new ImmunityRemoveMechanic(EntityDamageEvent.DamageCause.FALL);
        selfTarget.addChildren(landTrigger);
        landTrigger.addChildren(immunityRemoveMechanic);

        skill.addTrigger(selfTarget);

        return skill;
    }

    private static Skill getPassive() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Your arrows shot from crossbow pierces through targets");

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
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);

        Skill skill = new Skill("Piercing Arrow", 6, Material.IRON_HOE, 21, description, reqLevels, reqPoints, manaCosts, cooldowns);

        InitializeTrigger initializeTrigger = new InitializeTrigger();

        List<Integer> pierceLevels = new ArrayList<>();
        pierceLevels.add(1);
        pierceLevels.add(2);
        pierceLevels.add(3);
        pierceLevels.add(4);
        pierceLevels.add(5);
        pierceLevels.add(6);
        AddPiercingToArrowShootFromCrossbowTrigger addPiercingToArrowShootFromCrossbowTrigger = new AddPiercingToArrowShootFromCrossbowTrigger(pierceLevels);

        skill.addTrigger(initializeTrigger);
        initializeTrigger.addChildren(addPiercingToArrowShootFromCrossbowTrigger);

        return skill;
    }

    private static Skill getUltimate() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Place a trap that triggers when");
        description.add(ChatColor.GRAY + "an enemy steps on it.");
        description.add(ChatColor.GRAY + "Stuns and marks the target when triggered.");

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
        cooldowns.add(16);
        cooldowns.add(16);
        cooldowns.add(16);
        cooldowns.add(16);
        cooldowns.add(16);
        cooldowns.add(16);

        Skill skill = new Skill("Bear Trap", 6, Material.IRON_HOE, 55, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        //Add HologramMechanic to SelfTarget's children
        List<Integer> seconds = new ArrayList<>();
        seconds.add(30);
        seconds.add(40);
        seconds.add(50);
        seconds.add(60);
        seconds.add(70);
        seconds.add(90);
        HologramMechanic hologramMechanic = new HologramMechanic(Material.IRON_PICKAXE, 10000005, seconds, ChatColor.DARK_GRAY + "< Trap %caster% >");

        //Add repeatMechanic to hologramMechanic's children
        List<Integer> repetitions = new ArrayList<>();
        repetitions.add(60);
        repetitions.add(80);
        repetitions.add(100);
        repetitions.add(120);
        repetitions.add(140);
        repetitions.add(180);
        RepeatMechanic repeatMechanic = new RepeatMechanic(10L, repetitions);

        //Add areaTarget to repeatMechanic's children
        List<Double> radiuses = new ArrayList<>();
        radiuses.add(3D);
        radiuses.add(3D);
        radiuses.add(3D);
        radiuses.add(3D);
        radiuses.add(3D);
        radiuses.add(3D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, radiuses);

        //Add effects and setRemoveFlag to areaTarget's children
        List<Integer> ticks = new ArrayList<>();
        ticks.add(80);
        ticks.add(90);
        ticks.add(100);
        ticks.add(110);
        ticks.add(120);
        ticks.add(140);
        List<Integer> amplifiers = new ArrayList<>();
        amplifiers.add(999);
        amplifiers.add(999);
        amplifiers.add(999);
        amplifiers.add(999);
        amplifiers.add(999);
        amplifiers.add(999);
        PotionEffectMechanic stopJumping = new PotionEffectMechanic(PotionEffectType.JUMP, ticks, amplifiers);
        PotionEffectMechanic stopWalking = new PotionEffectMechanic(PotionEffectType.SLOW, ticks, amplifiers);
        SilenceMechanic silenceMechanic = new SilenceMechanic(ticks);
        FlagSetMechanic setRemoveFlag = new FlagSetMechanic("shouldStopSkill", new ArrayList<>());

        //Add FlagCondition to repeatMechanic's children
        //Add RemoveMechanic and RepeatCancelMechanic to FlagCondition's children
        FlagCondition shouldStopSkill = new FlagCondition("shouldStopSkill", true);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(hologramMechanic);
        hologramMechanic.addChildren(repeatMechanic);

        //repeat part 1, area and effects
        repeatMechanic.addChildren(areaTarget);
        areaTarget.addChildren(new SoundMechanic(GoaSound.SKILL_STUN_HIT));
        areaTarget.addChildren(stopJumping);
        areaTarget.addChildren(stopWalking);
        areaTarget.addChildren(silenceMechanic);
        areaTarget.addChildren(new RemoveNearbyHologramMechanic(radiuses, ChatColor.DARK_GRAY + "< Trap %caster% >"));
        SelfTarget selfTargetForFlagSet = new SelfTarget();
        areaTarget.addChildren(selfTargetForFlagSet);
        selfTargetForFlagSet.addChildren(setRemoveFlag);

        //repeat part 2, stop and remove
        SelfTarget selfTargetForFlagCondition = new SelfTarget();
        repeatMechanic.addChildren(selfTargetForFlagCondition);
        selfTargetForFlagCondition.addChildren(shouldStopSkill);

        shouldStopSkill.addChildren(new RepeatCancelMechanic());
        shouldStopSkill.addChildren(new FlagRemoveMechanic("shouldStopSkill"));

        return skill;
    }
}
