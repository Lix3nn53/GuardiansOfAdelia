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
        description.add("Shoot an arrow that deals area");
        description.add("damage and pushes targets");

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

        Skill skill = new Skill("Explosive Arrow", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        SelfTarget selfTarget = new SelfTarget();

        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 1.9, 1, 30,
                0, 1, 0, 200, true, Arrow.class);

        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, 3);

        projectileMechanic.addChildren(areaTarget);

        areaTarget.addChildren(new DamageMechanic(10, 10, DamageMechanic.DamageType.MAGIC));

        PushMechanic pushMechanic = new PushMechanic(PushMechanic.PushType.FIXED, 1, true);

        areaTarget.addChildren(pushMechanic);

        selfTarget.addChildren(projectileMechanic);

        skill.addTrigger(selfTarget);
        return skill;
    }

    private static Skill getTwo() {
        List<String> description = new ArrayList<>();
        description.add("Fire multiple arrows in a cone");

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

        Skill skill = new Skill("Volley", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 1.9, 4, 30,
                0, 1, 0, 200, true, Arrow.class);

        projectileMechanic.addChildren(new DamageMechanic(10, 10, DamageMechanic.DamageType.RANGED));

        selfTarget.addChildren(projectileMechanic);

        skill.addTrigger(selfTarget);

        return skill;
    }

    private static Skill getThree() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Fire a grapple particle projectile.");
        description.add(ChatColor.GRAY + "If it collides with terrain dash towards it,");
        description.add(ChatColor.GRAY + "if it hits a target pull the target.");

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

        Skill skill = new Skill("Hookshot", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 1.9, 1, 30, 0, 0, 0, 90,
                false, SmallFireball.class, Particle.REDSTONE, ArrangementParticle.CIRCLE, 0.5, 4, new Particle.DustOptions(Color.AQUA, 8));
        projectileMechanic.setAddCasterAsFirstTargetIfHitSuccess(true);
        projectileMechanic.setAddCasterAsSecondTargetIfHitFail(true);

        PushMechanic pushMechanic = new PushMechanic(PushMechanic.PushType.FIXED, -4, false);

        selfTarget.addChildren(projectileMechanic);

        projectileMechanic.addChildren(pushMechanic);

        //add fall protection
        ImmunityMechanic immunityMechanic = new ImmunityMechanic(EntityDamageEvent.DamageCause.FALL, 0);
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
        description.add("Makes your arrows pierce through enemies");

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

        Skill skill = new Skill("Piercing Arrow", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);

        InitializeTrigger initializeTrigger = new InitializeTrigger();

        AddPiercingToArrowShootFromCrossbowTrigger addPiercingToArrowShootFromCrossbowTrigger = new AddPiercingToArrowShootFromCrossbowTrigger(1, 1);

        skill.addTrigger(initializeTrigger);
        initializeTrigger.addChildren(addPiercingToArrowShootFromCrossbowTrigger);

        return skill;
    }

    private static Skill getUltimate() {
        List<String> description = new ArrayList<>();
        description.add("Place a trap that triggers when an enemy");
        description.add("steps on it.");
        description.add("Stuns and marks the target when triggered.");

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

        Skill skill = new Skill("Bear Trap", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        //Add HologramMechanic to SelfTarget's children
        HologramMechanic hologramMechanic = new HologramMechanic(Material.IRON_PICKAXE, 10000005, 30, ChatColor.DARK_GRAY + "< Trap %caster% >");

        //Add repeatMechanic to hologramMechanic's children
        RepeatMechanic repeatMechanic = new RepeatMechanic(10L, 60);

        //Add areaTarget to repeatMechanic's children
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, 3);

        //Add effects and setRemoveFlag to areaTarget's children
        PotionEffectMechanic stopJumping = new PotionEffectMechanic(PotionEffectType.JUMP, 60, 999);
        PotionEffectMechanic stopWalking = new PotionEffectMechanic(PotionEffectType.SLOW, 60, 999);
        SilenceMechanic silenceMechanic = new SilenceMechanic(3);
        FlagSetMechanic setRemoveFlag = new FlagSetMechanic("shouldStopSkill", 0);

        //Add FlagCondition to repeatMechanic's children
        //Add RemoveMechanic and RepeatCancelMechanic to FlagCondition's children
        FlagCondition removeCondition = new FlagCondition("shouldStopSkill", true);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(hologramMechanic);
        hologramMechanic.addChildren(repeatMechanic);

        //repeat part 1, area and effects
        repeatMechanic.addChildren(areaTarget);
        areaTarget.addChildren(stopJumping);
        areaTarget.addChildren(stopWalking);
        areaTarget.addChildren(silenceMechanic);
        areaTarget.addChildren(setRemoveFlag);

        //repeat part 2, stop and remove
        repeatMechanic.addChildren(removeCondition);
        removeCondition.addChildren(new RemoveMechanic());
        removeCondition.addChildren(new RepeatCancelMechanic());
        removeCondition.addChildren(new FlagRemoveMechanic("shouldStopSkill"));

        return skill;
    }
}
