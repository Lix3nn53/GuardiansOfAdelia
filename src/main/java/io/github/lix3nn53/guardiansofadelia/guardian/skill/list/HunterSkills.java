package io.github.lix3nn53.guardiansofadelia.guardian.skill.list;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition.FlagCondition;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition.NearbyEntityCondition;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.*;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.hologram.HologramMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityRemoveMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.ProjectileMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.SpreadType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.DisarmMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.RootMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.SilenceMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.AreaTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.FilterCurrentTargets;
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
import org.bukkit.entity.EntityType;
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
        manaCosts.add(10);
        manaCosts.add(12);
        manaCosts.add(14);
        manaCosts.add(16);
        manaCosts.add(18);
        manaCosts.add(20);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(12);
        cooldowns.add(12);
        cooldowns.add(12);
        cooldowns.add(12);
        cooldowns.add(12);
        cooldowns.add(12);

        Skill skill = new Skill("Explosive Arrow", 6, Material.IRON_HOE, 20, description, reqLevels, reqPoints, manaCosts, cooldowns);
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
                ArrangementParticle.CIRCLE, 0.5, 4, new Particle.DustOptions(Color.ORANGE, 2), false);

        List<Double> radiuses = new ArrayList<>();
        radiuses.add(2D);
        radiuses.add(2.5D);
        radiuses.add(3D);
        radiuses.add(3.5D);
        radiuses.add(4D);
        radiuses.add(4.5D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 99, radiuses);

        ParticleMechanic explosionParticle = new ParticleMechanic(Particle.EXPLOSION_HUGE, ArrangementParticle.CIRCLE, 3.4, 2, 0, 0, 0, 0, 0.5, 0, 0, null);

        List<Double> damages = new ArrayList<>();
        damages.add(24.0);
        damages.add(60.0);
        damages.add(180.0);
        damages.add(300.0);
        damages.add(420.0);
        damages.add(720.0);

        List<Double> speeds = new ArrayList<>();
        speeds.add(2D);
        speeds.add(2.4);
        speeds.add(2.8);
        speeds.add(3.2);
        speeds.add(3.6);
        speeds.add(4.2);
        PushMechanic pushMechanic = new PushMechanic(PushMechanic.PushType.FIXED, speeds, true);

        selfTarget.addChildren(projectileMechanic);
        selfTarget.addChildren(new SoundMechanic(GoaSound.SKILL_PROJECTILE_ICE));
        projectileMechanic.addChildren(new SoundMechanic(GoaSound.SKILL_WIND_PUSH));
        projectileMechanic.addChildren(areaTarget);
        projectileMechanic.addChildren(explosionParticle);
        areaTarget.addChildren(new DamageMechanic(damages, DamageMechanic.DamageType.RANGED));
        areaTarget.addChildren(pushMechanic);

        skill.addTrigger(selfTarget);
        return skill;
    }

    private static Skill getTwo() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Fire multiple arrows in a cone");

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
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 2.7, projectileAmounts, 30,
                0, 1, 0, 200, true, Arrow.class);

        FilterCurrentTargets filterCurrentTargets = new FilterCurrentTargets(false, true, false, 99);

        List<Double> damages = new ArrayList<>();
        damages.add(24.0);
        damages.add(60.0);
        damages.add(180.0);
        damages.add(300.0);
        damages.add(420.0);
        damages.add(720.0);
        DamageMechanic damageMechanic = new DamageMechanic(damages, DamageMechanic.DamageType.RANGED);

        selfTarget.addChildren(projectileMechanic);
        projectileMechanic.addChildren(filterCurrentTargets);
        filterCurrentTargets.addChildren(damageMechanic);
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
        cooldowns.add(21);
        cooldowns.add(21);
        cooldowns.add(21);
        cooldowns.add(21);
        cooldowns.add(21);
        cooldowns.add(21);

        Skill skill = new Skill("Hookshot", 6, Material.IRON_HOE, 32, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Integer> projectileAmounts = new ArrayList<>();
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 1.9, projectileAmounts, 30, 0, 1, 0, 90,
                false, SmallFireball.class, Particle.REDSTONE, ArrangementParticle.CIRCLE, 0.5, 4, new Particle.DustOptions(Color.GRAY, 2), true);
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
        ImmunityRemoveMechanic immunityRemoveMechanic = new ImmunityRemoveMechanic(EntityDamageEvent.DamageCause.FALL, 5);
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
        reqPoints.add(5);
        reqPoints.add(6);
        reqPoints.add(7);
        reqPoints.add(8);
        reqPoints.add(9);
        reqPoints.add(10);

        List<Integer> manaCosts = new ArrayList<>();
        manaCosts.add(30);
        manaCosts.add(30);
        manaCosts.add(30);
        manaCosts.add(30);
        manaCosts.add(30);
        manaCosts.add(30);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(35);
        cooldowns.add(33);
        cooldowns.add(31);
        cooldowns.add(29);
        cooldowns.add(27);
        cooldowns.add(25);

        Skill skill = new Skill("Bear Trap", 6, Material.IRON_HOE, 55, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        NearbyEntityCondition nearbyEntityCondition = new NearbyEntityCondition(EntityType.ARMOR_STAND, "< Trap", 6D, false);

        //Add HologramMechanic to SelfTarget's children
        List<Integer> seconds = new ArrayList<>();
        seconds.add(50);
        seconds.add(55);
        seconds.add(60);
        seconds.add(65);
        seconds.add(70);
        seconds.add(80);
        HologramMechanic hologramMechanic = new HologramMechanic(Material.IRON_PICKAXE, 5, seconds, ChatColor.DARK_GRAY + "< Trap %caster% >");

        //Add repeatMechanic to hologramMechanic's children
        List<Integer> repetitions = new ArrayList<>();
        repetitions.add(120);
        repetitions.add(140);
        repetitions.add(160);
        repetitions.add(180);
        repetitions.add(200);
        repetitions.add(240);
        RepeatMechanic repeatMechanic = new RepeatMechanic(10L, repetitions);

        //Add areaTarget to repeatMechanic's children
        List<Double> radiuses = new ArrayList<>();
        radiuses.add(2.4D);
        radiuses.add(2.4D);
        radiuses.add(2.4D);
        radiuses.add(2.4D);
        radiuses.add(2.4D);
        radiuses.add(2.4D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 99, radiuses);

        //Add effects and setRemoveFlag to areaTarget's children
        List<Integer> ticks = new ArrayList<>();
        ticks.add(60);
        ticks.add(70);
        ticks.add(80);
        ticks.add(90);
        ticks.add(100);
        ticks.add(120);
        List<Integer> jumpAmplifiers = new ArrayList<>();
        jumpAmplifiers.add(-99);
        jumpAmplifiers.add(-99);
        jumpAmplifiers.add(-99);
        jumpAmplifiers.add(-99);
        jumpAmplifiers.add(-99);
        jumpAmplifiers.add(-99);
        List<Integer> slowAmplifiers = new ArrayList<>();
        slowAmplifiers.add(99);
        slowAmplifiers.add(99);
        slowAmplifiers.add(99);
        slowAmplifiers.add(99);
        slowAmplifiers.add(99);
        slowAmplifiers.add(99);
        RootMechanic root = new RootMechanic(ticks, true);
        PotionEffectMechanic stopWalking = new PotionEffectMechanic(PotionEffectType.SLOW, ticks, slowAmplifiers);
        DisarmMechanic disarmMechanic = new DisarmMechanic(ticks);
        SilenceMechanic silenceMechanic = new SilenceMechanic(ticks);
        FlagSetMechanic setRemoveFlag = new FlagSetMechanic("shouldStopSkill", new ArrayList<>(), true);

        //Add FlagCondition to repeatMechanic's children
        //Add RemoveMechanic and RepeatCancelMechanic to FlagCondition's children
        FlagCondition shouldStopSkill = new FlagCondition("shouldStopSkill", true, true);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(nearbyEntityCondition);
        nearbyEntityCondition.addChildren(hologramMechanic);
        hologramMechanic.addChildren(repeatMechanic);

        //repeat part 1, area and effects
        repeatMechanic.addChildren(areaTarget);
        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.EXPLOSION_LARGE, ArrangementParticle.CIRCLE, 1.4, 2, 0, 0, 0, 0, 0.8, 0, 0, null);
        areaTarget.addChildren(particleMechanic);
        areaTarget.addChildren(new SoundMechanic(GoaSound.SKILL_STUN_HIT));
        areaTarget.addChildren(root);
        areaTarget.addChildren(stopWalking);
        areaTarget.addChildren(disarmMechanic);
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
        shouldStopSkill.addChildren(new FlagRemoveMechanic("shouldStopSkill", true));

        return skill;
    }
}
