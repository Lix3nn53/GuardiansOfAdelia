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
        damages.add(90D);
        damages.add(160D);
        damages.add(220D);
        damages.add(290D);
        damages.add(360D);
        damages.add(480D);
        DamageMechanic damageMechanic = new DamageMechanic(damages, DamageMechanic.DamageType.MELEE);

        List<Double> multiplier = new ArrayList<>();
        multiplier.add(-0.08);
        multiplier.add(-0.1);
        multiplier.add(-0.12);
        multiplier.add(-0.14);
        multiplier.add(-0.16);
        multiplier.add(-0.2);
        List<Integer> ticks = new ArrayList<>();
        ticks.add(80);
        ticks.add(90);
        ticks.add(100);
        ticks.add(110);
        ticks.add(120);
        ticks.add(140);
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
        upward.add(1.5D);
        upward.add(1.75D);
        upward.add(2D);
        upward.add(2.25D);
        upward.add(2.5D);
        upward.add(3D);
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
        manaCosts.add(24);
        manaCosts.add(26);
        manaCosts.add(28);
        manaCosts.add(30);
        manaCosts.add(32);
        manaCosts.add(34);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(32);
        cooldowns.add(30);
        cooldowns.add(28);
        cooldowns.add(26);
        cooldowns.add(24);
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

        Skill skill = new Skill("Spell Block", 6, Material.IRON_HOE, 23, description, reqLevels, reqPoints, manaCosts, cooldowns);

        InitializeTrigger initializeTrigger = new InitializeTrigger();

        TookMagicalDamageTrigger tookMagicalDamageTrigger = new TookMagicalDamageTrigger(900L);

        List<Integer> ccTicks = new ArrayList<>();
        ccTicks.add(50);
        ccTicks.add(60);
        ccTicks.add(70);
        ccTicks.add(80);
        ccTicks.add(90);
        ccTicks.add(100);

        SilenceMechanic silenceMechanic = new SilenceMechanic(ccTicks);

        skill.addTrigger(initializeTrigger);
        initializeTrigger.addChildren(tookMagicalDamageTrigger);
        tookMagicalDamageTrigger.addChildren(silenceMechanic);
        tookMagicalDamageTrigger.addChildren(new MessageMechanic(ChatColor.AQUA + "You got silenced by a knight passive skill.."));
        tookMagicalDamageTrigger.addChildren(new SoundMechanic(GoaSound.SKILL_SPLASH));

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

        Skill skill = new Skill("Shield of the Heavens", 6, Material.IRON_HOE, 54, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        //Add HologramMechanic to SelfTarget's children
        List<Integer> seconds = new ArrayList<>();
        seconds.add(30);
        seconds.add(30);
        seconds.add(30);
        seconds.add(30);
        seconds.add(30);
        seconds.add(30);
        HologramMechanic hologramMechanic = new HologramMechanic(Material.DIAMOND_SWORD, 10000005, seconds);
        HologramMechanic hologramMechanic2 = new HologramMechanic(Material.SHIELD, 10000004, seconds, ChatColor.YELLOW + "< Shield-of-the-Heavens %caster% >");

        //Add repeatMechanic to hologramMechanic's children
        List<Integer> repetitions = new ArrayList<>();
        repetitions.add(10);
        repetitions.add(10);
        repetitions.add(10);
        repetitions.add(10);
        repetitions.add(10);
        repetitions.add(10);
        RepeatMechanic repeatMechanic = new RepeatMechanic(60L, repetitions);

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
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
        ticks.add(60);
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
