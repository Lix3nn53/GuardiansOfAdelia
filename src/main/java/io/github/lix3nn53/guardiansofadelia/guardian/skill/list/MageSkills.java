package io.github.lix3nn53.guardiansofadelia.guardian.skill.list;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.*;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.ProjectileMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.SpreadType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.SilenceMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.AreaTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SelfTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SingleTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.InitializeTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.TookMeleeDamageTrigger;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ArrangementParticle;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.SmallFireball;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class MageSkills {

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
        description.add(ChatColor.GRAY + "Shoot a fireball that deals damage to");
        description.add(ChatColor.GRAY + "targets in area if it hits a target.");

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
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(3);
        cooldowns.add(3);
        cooldowns.add(3);
        cooldowns.add(3);
        cooldowns.add(3);
        cooldowns.add(3);

        Skill skill = new Skill("Fireball", 6, Material.IRON_HOE, 12, description, reqLevels, reqPoints, manaCosts, cooldowns);
        SelfTarget selfTarget = new SelfTarget();

        List<Integer> projectileAmounts = new ArrayList<>();
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 1.9, projectileAmounts, 30,
                0, 1, 0, 200, true, Fireball.class);

        List<Double> radiuses = new ArrayList<>();
        radiuses.add(2.5D);
        radiuses.add(2.75D);
        radiuses.add(3D);
        radiuses.add(3.25D);
        radiuses.add(3.5D);
        radiuses.add(4D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, radiuses);

        List<Double> damages = new ArrayList<>();
        damages.add(180D);
        damages.add(292D);
        damages.add(405D);
        damages.add(540D);
        damages.add(675D);
        damages.add(900D);
        areaTarget.addChildren(new DamageMechanic(damages, DamageMechanic.DamageType.MAGIC));

        projectileMechanic.addChildren(areaTarget);

        selfTarget.addChildren(projectileMechanic);
        selfTarget.addChildren(new SoundMechanic(GoaSound.SKILL_FIRE_SLASH));

        skill.addTrigger(selfTarget);

        return skill;
    }

    private static Skill getTwo() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Shoot a lightning bolt that deals damage and");
        description.add(ChatColor.GRAY + "silences targets in area if it hits a target.");

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
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);
        manaCosts.add(5);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(12);
        cooldowns.add(12);
        cooldowns.add(12);
        cooldowns.add(12);
        cooldowns.add(12);
        cooldowns.add(12);

        Skill skill = new Skill("Lightning Bolt", 6, Material.IRON_HOE, 7, description, reqLevels, reqPoints, manaCosts, cooldowns);
        SelfTarget selfTarget = new SelfTarget();

        List<Integer> projectileAmounts = new ArrayList<>();
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 1.9, projectileAmounts, 30,
                0, 1, 0, 200, true, SmallFireball.class, Particle.VILLAGER_HAPPY,
                ArrangementParticle.SPHERE, 0.5, 4, null, true);

        List<Double> radiuses = new ArrayList<>();
        radiuses.add(2.5D);
        radiuses.add(2.75D);
        radiuses.add(3D);
        radiuses.add(3.25D);
        radiuses.add(3.5D);
        radiuses.add(4D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, radiuses);

        List<Double> damages = new ArrayList<>();
        damages.add(128D);
        damages.add(208D);
        damages.add(288D);
        damages.add(384D);
        damages.add(480D);
        damages.add(640D);
        areaTarget.addChildren(new DamageMechanic(damages, DamageMechanic.DamageType.MAGIC));
        areaTarget.addChildren(new ParticleMechanic(Particle.REDSTONE, ArrangementParticle.CIRCLE, 3.4, 11, 0, 0, 0, 0, 1, 0, 0, new Particle.DustOptions(Color.TEAL, 2)));
        List<Integer> durations = new ArrayList<>();
        durations.add(40);
        durations.add(50);
        durations.add(60);
        durations.add(70);
        durations.add(80);
        durations.add(100);
        areaTarget.addChildren(new SilenceMechanic(durations));

        projectileMechanic.addChildren(areaTarget);
        projectileMechanic.addChildren(new SoundMechanic(GoaSound.SKILL_LIGHTNING_FLUX));
        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.VILLAGER_HAPPY, ArrangementParticle.CIRCLE, 3.4, 11, 0, 0, 0, 0, 0.5, 0, 0, null);
        projectileMechanic.addChildren(particleMechanic);

        selfTarget.addChildren(projectileMechanic);
        selfTarget.addChildren(new SoundMechanic(GoaSound.SKILL_LIGHTNING_NORMAL));

        skill.addTrigger(selfTarget);

        return skill;
    }

    private static Skill getThree() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Push nearby enemies away.");

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

        Skill skill = new Skill("Shockwave", 6, Material.IRON_HOE, 11, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> areas = new ArrayList<>();
        areas.add(3D);
        areas.add(3.25D);
        areas.add(3.5D);
        areas.add(3.75D);
        areas.add(4D);
        areas.add(4.5D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, areas);

        List<Double> speeds = new ArrayList<>();
        speeds.add(1.6D);
        speeds.add(1.8D);
        speeds.add(2D);
        speeds.add(2.2D);
        speeds.add(2.4D);
        speeds.add(2.8D);
        PushMechanic pushMechanic = new PushMechanic(PushMechanic.PushType.FIXED, speeds, true);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(areaTarget);
        areaTarget.addChildren(pushMechanic);
        SelfTarget selfTargetForSound = new SelfTarget();
        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.EXPLOSION_LARGE, ArrangementParticle.CIRCLE, 1.2, 1, 0, 0, 0, 0, 0.5, 0, 0, null);
        areaTarget.addChildren(particleMechanic);
        areaTarget.addChildren(selfTargetForSound);
        selfTargetForSound.addChildren(new SoundMechanic(GoaSound.SKILL_SONIC_BOOM));

        return skill;
    }

    private static Skill getPassive() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "When you took physical damage while");
        description.add(ChatColor.GRAY + "this skill is active, root nearby targets");
        description.add(ChatColor.GRAY + "and gain physical defense buff.");

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
        manaCosts.add(5);
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

        Skill skill = new Skill("Mental Fortitude", 6, Material.IRON_HOE, 10, description, reqLevels, reqPoints, manaCosts, cooldowns);

        InitializeTrigger initializeTrigger = new InitializeTrigger();

        TookMeleeDamageTrigger tookMeleeDamageTrigger = new TookMeleeDamageTrigger(20L * 64);

        List<Double> areas = new ArrayList<>();
        areas.add(3D);
        areas.add(3.25D);
        areas.add(3.5D);
        areas.add(3.75D);
        areas.add(4D);
        areas.add(4.5D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, areas);

        List<Integer> ccTicks = new ArrayList<>();
        ccTicks.add(40);
        ccTicks.add(50);
        ccTicks.add(60);
        ccTicks.add(70);
        ccTicks.add(80);
        ccTicks.add(100);
        List<Integer> ccAmplifiers = new ArrayList<>();
        ccAmplifiers.add(999);
        ccAmplifiers.add(999);
        ccAmplifiers.add(999);
        ccAmplifiers.add(999);
        ccAmplifiers.add(999);
        ccAmplifiers.add(999);
        areaTarget.addChildren(new PotionEffectMechanic(PotionEffectType.SLOW, ccTicks, ccAmplifiers));
        areaTarget.addChildren(new PotionEffectMechanic(PotionEffectType.JUMP, ccTicks, ccAmplifiers));

        List<Double> multipliers = new ArrayList<>();
        multipliers.add(0.1);
        multipliers.add(0.15);
        multipliers.add(0.2);
        multipliers.add(0.25);
        multipliers.add(0.3);
        multipliers.add(0.4);
        List<Integer> ticks = new ArrayList<>();
        ticks.add(200);
        ticks.add(225);
        ticks.add(250);
        ticks.add(275);
        ticks.add(300);
        ticks.add(350);
        BuffMechanic buffMechanic = new BuffMechanic(BuffType.PHYSICAL_DEFENSE, multipliers, ticks);

        skill.addTrigger(initializeTrigger);
        initializeTrigger.addChildren(tookMeleeDamageTrigger);
        tookMeleeDamageTrigger.addChildren(areaTarget);
        tookMeleeDamageTrigger.addChildren(buffMechanic);
        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.REDSTONE, ArrangementParticle.CIRCLE, 3.4, 11, 0, 0, 0, 0, 1, 0, 0, new Particle.DustOptions(Color.AQUA, 2));
        tookMeleeDamageTrigger.addChildren(particleMechanic);
        areaTarget.addChildren(new MessageMechanic(ChatColor.AQUA + "You got rooted by a mage passive skill.."));

        return skill;
    }

    private static Skill getUltimate() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Summon a meteor from sky that hits");
        description.add(ChatColor.GRAY + "nearby targets when it reaches ground.");
        description.add("");
        description.add(ChatColor.GRAY + "The hit effect deals damage, ignites");
        description.add(ChatColor.GRAY + "and launches targets to sky.");

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

        Skill skill = new Skill("Chaos Meteor", 6, Material.IRON_HOE, 30, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> ranges = new ArrayList<>();
        ranges.add(24D);
        ranges.add(24D);
        ranges.add(24D);
        ranges.add(24D);
        ranges.add(24D);
        ranges.add(24D);
        SingleTarget singleTarget = new SingleTarget(false, true, false, 1, ranges, 4);

        List<Integer> projectileAmounts = new ArrayList<>();
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.RAIN, 8, 12, 0.4, projectileAmounts, 0,
                0, 0, 200, false, Fireball.class, Particle.FLAME,
                ArrangementParticle.SPHERE, 2, 32, null, false);

        List<Double> areas = new ArrayList<>();
        areas.add(6D);
        areas.add(7D);
        areas.add(8D);
        areas.add(9D);
        areas.add(10D);
        areas.add(12D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, areas);

        List<Integer> repeatAmounts = new ArrayList<>();
        repeatAmounts.add(1);
        repeatAmounts.add(1);
        repeatAmounts.add(1);
        repeatAmounts.add(1);
        repeatAmounts.add(1);
        repeatAmounts.add(1);
        RepeatMechanic repeatMechanic = new RepeatMechanic(40, repeatAmounts);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(singleTarget);
        singleTarget.addChildren(repeatMechanic);
        repeatMechanic.addChildren(projectileMechanic);
        List<Double> damages = new ArrayList<>();
        damages.add(480D);
        damages.add(780D);
        damages.add(1080D);
        damages.add(1440D);
        damages.add(1800D);
        damages.add(2400D);
        projectileMechanic.addChildren(new SoundMechanic(GoaSound.SKILL_FIRE_AURA));
        projectileMechanic.addChildren(areaTarget);
        areaTarget.addChildren(new DamageMechanic(damages, DamageMechanic.DamageType.MAGIC));
        List<Integer> ticks = new ArrayList<>();
        ticks.add(80);
        ticks.add(90);
        ticks.add(100);
        ticks.add(120);
        ticks.add(140);
        ticks.add(180);
        areaTarget.addChildren(new FireMechanic(ticks));

        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.EXPLOSION_HUGE, ArrangementParticle.SPHERE, 4, 4,
                0, 0, 0, 0, 1, 0, 0, null);
        projectileMechanic.addChildren(particleMechanic);

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

        areaTarget.addChildren(launchMechanic);
        return skill;
    }
}
