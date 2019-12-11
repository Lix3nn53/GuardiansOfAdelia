package io.github.lix3nn53.guardiansofadelia.guardian.skill.list;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.*;
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
        manaCosts.add(6);
        manaCosts.add(7);
        manaCosts.add(8);
        manaCosts.add(9);
        manaCosts.add(10);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(4);
        cooldowns.add(4);
        cooldowns.add(4);
        cooldowns.add(4);
        cooldowns.add(4);
        cooldowns.add(4);

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
        AreaTarget areaTarget = new AreaTarget(false, true, false, 99, radiuses);

        List<Double> damages = new ArrayList<>();
        damages.add(20.0);
        damages.add(50.0);
        damages.add(200.0);
        damages.add(325.0);
        damages.add(450.0);
        damages.add(750.0);
        areaTarget.addChildren(new DamageMechanic(damages, DamageMechanic.DamageType.MAGIC));

        projectileMechanic.addChildren(areaTarget);

        selfTarget.addChildren(projectileMechanic);
        selfTarget.addChildren(new SoundMechanic(GoaSound.SKILL_FIRE_SLASH));

        skill.addTrigger(selfTarget);

        return skill;
    }

    private static Skill getTwo() {
        List<String> description = new ArrayList<>();
        description.add(ChatColor.GRAY + "Shoot a lightning bolt that deals damage, silences");
        description.add(ChatColor.GRAY + "and confuses targets in area if it hits a target.");
        description.add(ChatColor.GRAY + "");
        description.add(ChatColor.AQUA + "Silence: " + ChatColor.GRAY + "Target can not cast skills");
        description.add(ChatColor.DARK_PURPLE + "Confusion: " + ChatColor.GRAY + "Target's screen shakes");

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
        cooldowns.add(15);
        cooldowns.add(15);
        cooldowns.add(15);
        cooldowns.add(15);
        cooldowns.add(15);
        cooldowns.add(15);

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
        AreaTarget areaTarget = new AreaTarget(false, true, false, 99, radiuses);

        List<Double> damages = new ArrayList<>();
        damages.add(30D);
        damages.add(70D);
        damages.add(150D);
        damages.add(240.0);
        damages.add(320.0);
        damages.add(540.0);
        areaTarget.addChildren(new DamageMechanic(damages, DamageMechanic.DamageType.MAGIC));
        areaTarget.addChildren(new ParticleMechanic(Particle.REDSTONE, ArrangementParticle.CIRCLE, 3.4, 11, 0, 0, 0, 0, 1, 0, 0, new Particle.DustOptions(Color.TEAL, 2)));
        List<Integer> durations = new ArrayList<>();
        durations.add(60);
        durations.add(65);
        durations.add(70);
        durations.add(75);
        durations.add(80);
        durations.add(100);
        areaTarget.addChildren(new SilenceMechanic(durations));
        List<Integer> ccAmplifiers = new ArrayList<>();
        ccAmplifiers.add(99);
        ccAmplifiers.add(99);
        ccAmplifiers.add(99);
        ccAmplifiers.add(99);
        ccAmplifiers.add(99);
        ccAmplifiers.add(99);
        areaTarget.addChildren(new PotionEffectMechanic(PotionEffectType.CONFUSION, durations, ccAmplifiers));

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
        manaCosts.add(10);
        manaCosts.add(12);
        manaCosts.add(14);
        manaCosts.add(16);
        manaCosts.add(18);
        manaCosts.add(20);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(15);
        cooldowns.add(15);
        cooldowns.add(15);
        cooldowns.add(15);
        cooldowns.add(15);
        cooldowns.add(15);

        Skill skill = new Skill("Shockwave", 6, Material.IRON_HOE, 11, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> areas = new ArrayList<>();
        areas.add(3D);
        areas.add(3.25D);
        areas.add(3.5D);
        areas.add(3.75D);
        areas.add(4D);
        areas.add(4.5D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 99, areas);

        List<Double> speeds = new ArrayList<>();
        speeds.add(2.6);
        speeds.add(2.8);
        speeds.add(3.0);
        speeds.add(3.2D);
        speeds.add(3.4);
        speeds.add(4D);
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
        description.add(ChatColor.GRAY + "and gain a shield.");

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

        Skill skill = new Skill("Mental Fortitude", 6, Material.IRON_HOE, 10, description, reqLevels, reqPoints, manaCosts, cooldowns);

        InitializeTrigger initializeTrigger = new InitializeTrigger();

        TookMeleeDamageTrigger tookMeleeDamageTrigger = new TookMeleeDamageTrigger(cooldowns);

        List<Double> areas = new ArrayList<>();
        areas.add(3D);
        areas.add(3.25D);
        areas.add(3.5D);
        areas.add(3.75D);
        areas.add(4D);
        areas.add(4.5D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 99, areas);

        List<Integer> ccTicks = new ArrayList<>();
        ccTicks.add(40);
        ccTicks.add(50);
        ccTicks.add(60);
        ccTicks.add(70);
        ccTicks.add(80);
        ccTicks.add(100);
        List<Integer> ccAmplifiers = new ArrayList<>();
        ccAmplifiers.add(99);
        ccAmplifiers.add(99);
        ccAmplifiers.add(99);
        ccAmplifiers.add(99);
        ccAmplifiers.add(99);
        ccAmplifiers.add(99);
        areaTarget.addChildren(new PotionEffectMechanic(PotionEffectType.SLOW, ccTicks, ccAmplifiers));
        areaTarget.addChildren(new PotionEffectMechanic(PotionEffectType.JUMP, ccTicks, ccAmplifiers));

        List<Integer> ticks = new ArrayList<>();
        ticks.add(500);
        ticks.add(500);
        ticks.add(500);
        ticks.add(500);
        ticks.add(500);
        ticks.add(500);
        List<Integer> amplifiers = new ArrayList<>();
        amplifiers.add(100);
        amplifiers.add(200);
        amplifiers.add(300);
        amplifiers.add(450);
        amplifiers.add(600);
        amplifiers.add(1000);
        PotionEffectMechanic potionEffectMechanic = new PotionEffectMechanic(PotionEffectType.ABSORPTION, ticks, amplifiers);

        skill.addTrigger(initializeTrigger);
        initializeTrigger.addChildren(tookMeleeDamageTrigger);
        SelfTarget selfTarget = new SelfTarget();
        selfTarget.addChildren(new MessageMechanic(ChatColor.AQUA + "Your passive skill rooted enemies around you.."));
        tookMeleeDamageTrigger.addChildren(selfTarget);
        selfTarget.addChildren(areaTarget);
        selfTarget.addChildren(potionEffectMechanic);
        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.REDSTONE, ArrangementParticle.CIRCLE, 3.4, 11, 0, 0, 0, 0, 1, 0, 0, new Particle.DustOptions(Color.AQUA, 2));
        selfTarget.addChildren(particleMechanic);
        areaTarget.addChildren(new MessageMechanic(ChatColor.AQUA + "You got rooted by a mage's passive skill.."));

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
        manaCosts.add(50);
        manaCosts.add(55);
        manaCosts.add(60);
        manaCosts.add(65);
        manaCosts.add(70);
        manaCosts.add(70);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(45);
        cooldowns.add(45);
        cooldowns.add(45);
        cooldowns.add(45);
        cooldowns.add(45);
        cooldowns.add(45);

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
        AreaTarget areaTarget = new AreaTarget(false, true, false, 99, areas);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(singleTarget);
        singleTarget.addChildren(projectileMechanic);
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
