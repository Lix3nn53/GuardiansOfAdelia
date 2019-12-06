package io.github.lix3nn53.guardiansofadelia.guardian.skill.list;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.*;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.hologram.HologramMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.SilenceMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.AreaTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SelfTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.InitializeTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.TookMagicalDamageTrigger;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ArrangementParticle;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;

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
        description.add(ChatColor.GRAY + "Damage nearby targets and");
        description.add(ChatColor.GRAY + "reduce their physical defense.");

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
        cooldowns.add(15);
        cooldowns.add(15);
        cooldowns.add(15);
        cooldowns.add(15);
        cooldowns.add(15);
        cooldowns.add(15);

        Skill skill = new Skill("Slice and Dice", 6, Material.IRON_HOE, 9, description, reqLevels, reqPoints, manaCosts, cooldowns);

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
        damages.add(12.0);
        damages.add(30.0);
        damages.add(120.0);
        damages.add(195.0);
        damages.add(270.0);
        damages.add(450.0);
        DamageMechanic damageMechanic = new DamageMechanic(damages, DamageMechanic.DamageType.MELEE);

        List<Double> multiplier = new ArrayList<>();
        multiplier.add(-0.08);
        multiplier.add(-0.1);
        multiplier.add(-0.12);
        multiplier.add(-0.14);
        multiplier.add(-0.16);
        multiplier.add(-0.2);
        List<Integer> ticks = new ArrayList<>();
        ticks.add(100);
        ticks.add(110);
        ticks.add(120);
        ticks.add(130);
        ticks.add(140);
        ticks.add(160);
        BuffMechanic buffMechanic = new BuffMechanic(BuffType.PHYSICAL_DEFENSE, multiplier, ticks);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(areaTarget);
        areaTarget.addChildren(damageMechanic);
        areaTarget.addChildren(buffMechanic);

        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.SWEEP_ATTACK, ArrangementParticle.CIRCLE, 3.4, 7, 0, 0, 0, 0, 1, 0, 0, null);

        SelfTarget selfTargetForSound = new SelfTarget();
        selfTargetForSound.addChildren(particleMechanic);
        areaTarget.addChildren(selfTargetForSound);
        selfTargetForSound.addChildren(new SoundMechanic(GoaSound.SKILL_SWORD_MULTI_SLASH));

        return skill;
    }

    private static Skill getTwo() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Launch nearby targets upwards");

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
        cooldowns.add(15);
        cooldowns.add(15);
        cooldowns.add(15);
        cooldowns.add(15);
        cooldowns.add(15);
        cooldowns.add(15);

        Skill skill = new Skill("Devastate", 6, Material.IRON_HOE, 1, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> areas = new ArrayList<>();
        areas.add(3D);
        areas.add(3.5D);
        areas.add(4D);
        areas.add(5D);
        areas.add(6D);
        areas.add(8D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, areas);

        List<Double> upward = new ArrayList<>();
        upward.add(1.2D);
        upward.add(1.4D);
        upward.add(1.6D);
        upward.add(1.8D);
        upward.add(2D);
        upward.add(2.4D);
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

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(areaTarget);
        areaTarget.addChildren(launchMechanic);

        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.REDSTONE, ArrangementParticle.CIRCLE, 3.4, 9, 0, 0, 0, 0, 1, 0, 0, new Particle.DustOptions(Color.YELLOW, 2));

        SelfTarget selfTargetForSound = new SelfTarget();
        areaTarget.addChildren(particleMechanic);
        areaTarget.addChildren(selfTargetForSound);
        selfTargetForSound.addChildren(new SoundMechanic(GoaSound.SKILL_FIRE_SLASH));

        return skill;
    }

    private static Skill getThree() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Pull enemies around to yourself and");
        description.add(ChatColor.GRAY + "taunt pulled monsters.");

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
        manaCosts.add(22);
        manaCosts.add(24);
        manaCosts.add(26);
        manaCosts.add(28);
        manaCosts.add(30);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(20);
        cooldowns.add(20);
        cooldowns.add(20);
        cooldowns.add(20);
        cooldowns.add(20);
        cooldowns.add(20);

        Skill skill = new Skill("Battle Cry", 6, Material.IRON_HOE, 47, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> areas = new ArrayList<>();
        areas.add(7D);
        areas.add(7.5D);
        areas.add(8D);
        areas.add(8.5D);
        areas.add(9D);
        areas.add(10D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, areas);

        List<Double> speeds = new ArrayList<>();
        speeds.add(-3D);
        speeds.add(-3.25D);
        speeds.add(-3.5D);
        speeds.add(-3.75D);
        speeds.add(-4D);
        speeds.add(-4.5D);
        PushMechanic pushMechanic = new PushMechanic(PushMechanic.PushType.FIXED, speeds, true);

        TauntMechanic tauntMechanic = new TauntMechanic();

        skill.addTrigger(selfTarget);
        SelfTarget selfTargetForSound = new SelfTarget();

        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.FLAME, ArrangementParticle.CIRCLE, 7.5, 27, 0, 0, 0, 0, 1, 0, 0, null);

        areaTarget.addChildren(selfTargetForSound);
        selfTargetForSound.addChildren(particleMechanic);
        selfTargetForSound.addChildren(new SoundMechanic(GoaSound.SKILL_SCREAM));
        selfTarget.addChildren(areaTarget);
        areaTarget.addChildren(pushMechanic);
        areaTarget.addChildren(tauntMechanic);

        return skill;
    }

    private static Skill getPassive() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "When you took any damage from a skill while");
        description.add(ChatColor.GRAY + "this skill is active, silence the attacker.");

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

        Skill skill = new Skill("Spell Block", 6, Material.IRON_HOE, 23, description, reqLevels, reqPoints, manaCosts, cooldowns);

        InitializeTrigger initializeTrigger = new InitializeTrigger();

        TookMagicalDamageTrigger tookMagicalDamageTrigger = new TookMagicalDamageTrigger(cooldowns);

        List<Integer> ccTicks = new ArrayList<>();
        ccTicks.add(60);
        ccTicks.add(70);
        ccTicks.add(80);
        ccTicks.add(90);
        ccTicks.add(100);
        ccTicks.add(120);

        SilenceMechanic silenceMechanic = new SilenceMechanic(ccTicks);

        skill.addTrigger(initializeTrigger);
        initializeTrigger.addChildren(tookMagicalDamageTrigger);
        tookMagicalDamageTrigger.addChildren(silenceMechanic);
        tookMagicalDamageTrigger.addChildren(new MessageMechanic(ChatColor.AQUA + "You got silenced by a knight's passive skill.."));
        tookMagicalDamageTrigger.addChildren(new SoundMechanic(GoaSound.SKILL_SPLASH));
        SelfTarget selfTarget = new SelfTarget();
        tookMagicalDamageTrigger.addChildren(selfTarget);
        selfTarget.addChildren(new MessageMechanic(ChatColor.AQUA + "Your passive skill silenced the attacker.."));

        return skill;
    }

    private static Skill getUltimate() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Call Shield of the Heavens to protect");
        description.add(ChatColor.GRAY + "your nearby allies!");

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
        cooldowns.add(90);
        cooldowns.add(90);
        cooldowns.add(90);
        cooldowns.add(90);
        cooldowns.add(90);
        cooldowns.add(90);

        Skill skill = new Skill("Shield of the Heavens", 6, Material.IRON_HOE, 54, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        //Add HologramMechanic to SelfTarget's children
        List<Integer> seconds = new ArrayList<>();
        seconds.add(40);
        seconds.add(40);
        seconds.add(40);
        seconds.add(40);
        seconds.add(40);
        seconds.add(40);
        HologramMechanic hologramMechanic = new HologramMechanic(Material.DIAMOND_SWORD, 10000005, seconds);
        HologramMechanic hologramMechanic2 = new HologramMechanic(Material.SHIELD, 10000004, seconds, ChatColor.YELLOW + "< Shield-of-the-Heavens %caster% >");

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
        BuffMechanic physicalDamageBuff = new BuffMechanic(BuffType.PHYSICAL_DEFENSE, multipliers, ticks);
        BuffMechanic magicalDamageBuff = new BuffMechanic(BuffType.MAGIC_DEFENSE, multipliers, ticks);

        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.REDSTONE, ArrangementParticle.CIRCLE, 9, 27, 0, 0, 0, 0, 1.6, 0, 0, new Particle.DustOptions(Color.YELLOW, 2));

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(hologramMechanic);
        selfTarget.addChildren(hologramMechanic2);
        selfTarget.addChildren(new SoundMechanic(GoaSound.SKILL_BUFF));
        hologramMechanic.addChildren(repeatMechanic);

        //repeat part 1, area and effects
        repeatMechanic.addChildren(areaTarget);
        areaTarget.addChildren(physicalDamageBuff);
        areaTarget.addChildren(magicalDamageBuff);

        repeatMechanic.addChildren(particleMechanic);

        return skill;
    }
}
