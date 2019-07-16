package io.github.lix3nn53.guardiansofadelia.guardian.skill.list;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.*;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.SilenceMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.AreaTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SelfTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.InitializeTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.TookMagicalDamageTrigger;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class KnightSkills {

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
        description.add("Damage nearby targets and");
        description.add("reduce their physical defense");

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

        Skill skill = new Skill("Slice and Dice", Material.IRON_HOE, 9, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> areas = new ArrayList<>();
        areas.add(3D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, areas);

        List<Double> damages = new ArrayList<>();
        damages.add(3D);
        DamageMechanic damageMechanic = new DamageMechanic(damages, DamageMechanic.DamageType.MELEE);

        List<Double> multiplier = new ArrayList<>();
        multiplier.add(-0.1);
        List<Integer> ticks = new ArrayList<>();
        ticks.add(80);
        BuffMechanic buffMechanic = new BuffMechanic(BuffType.PHYSICAL_DEFENSE, multiplier, ticks);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(areaTarget);
        SelfTarget selfTargetForSound = new SelfTarget();
        areaTarget.addChildren(selfTargetForSound);
        selfTargetForSound.addChildren(new SoundMechanic(GoaSound.SKILL_SWORD_MULTI_SLASH));
        areaTarget.addChildren(damageMechanic);
        areaTarget.addChildren(buffMechanic);

        return skill;
    }

    private static Skill getTwo() {
        List<String> description = new ArrayList<>();
        description.add("Launch nearby targets upwards");

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

        Skill skill = new Skill("Devastate", Material.IRON_HOE, 1, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> areas = new ArrayList<>();
        areas.add(3D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, areas);

        List<Double> upward = new ArrayList<>();
        upward.add(3D);
        List<Double> forward = new ArrayList<>();
        forward.add(0D);
        List<Double> right = new ArrayList<>();
        right.add(0D);
        LaunchMechanic launchMechanic = new LaunchMechanic(LaunchMechanic.Relative.TARGET, forward, upward, right);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(areaTarget);
        SelfTarget selfTargetForSound = new SelfTarget();
        areaTarget.addChildren(selfTargetForSound);
        selfTargetForSound.addChildren(new SoundMechanic(GoaSound.SKILL_FIRE_SLASH));
        areaTarget.addChildren(launchMechanic);

        return skill;
    }

    private static Skill getThree() {
        List<String> description = new ArrayList<>();
        description.add("Pull enemies around to yourself and");
        description.add("taunt pulled monsters.");

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

        Skill skill = new Skill("Battle Cry", Material.IRON_HOE, 47, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> areas = new ArrayList<>();
        areas.add(3D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, areas);

        List<Double> speeds = new ArrayList<>();
        speeds.add(-4D);
        PushMechanic pushMechanic = new PushMechanic(PushMechanic.PushType.FIXED, speeds, true);

        TauntMechanic tauntMechanic = new TauntMechanic();

        skill.addTrigger(selfTarget);
        SelfTarget selfTargetForSound = new SelfTarget();
        areaTarget.addChildren(selfTargetForSound);
        selfTargetForSound.addChildren(new SoundMechanic(GoaSound.SKILL_SCREAM));
        selfTarget.addChildren(areaTarget);
        areaTarget.addChildren(pushMechanic);
        areaTarget.addChildren(tauntMechanic);

        return skill;
    }

    private static Skill getPassive() {
        List<String> description = new ArrayList<>();
        description.add("When you took any damage from a skill while");
        description.add("this skill is active, block it and");
        description.add("silence the attacker.");

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
        cooldowns.add(48);
        cooldowns.add(48);
        cooldowns.add(48);
        cooldowns.add(48);
        cooldowns.add(48);
        cooldowns.add(48);

        Skill skill = new Skill("Spell Block", Material.IRON_HOE, 23, description, reqLevels, reqPoints, manaCosts, cooldowns);

        InitializeTrigger initializeTrigger = new InitializeTrigger();

        TookMagicalDamageTrigger tookMagicalDamageTrigger = new TookMagicalDamageTrigger(20L * 48);

        List<Integer> ccTicks = new ArrayList<>();
        ccTicks.add(60);
        ccTicks.add(60);
        ccTicks.add(60);

        SilenceMechanic silenceMechanic = new SilenceMechanic(ccTicks);

        skill.addTrigger(initializeTrigger);
        initializeTrigger.addChildren(tookMagicalDamageTrigger);
        tookMagicalDamageTrigger.addChildren(silenceMechanic);

        return skill;
    }

    private static Skill getUltimate() {
        List<String> description = new ArrayList<>();
        //TODO place a giant sword that decreases attacks of nearby enemies
        description.add("Call power of justice, gain movement speed and");
        description.add("jump boost. Also your melee attacks ignites target");

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

        Skill skill = new Skill("Wrath of Justice", Material.IRON_HOE, 1, description, reqLevels, reqPoints, manaCosts, cooldowns);


        return skill;
    }
}
