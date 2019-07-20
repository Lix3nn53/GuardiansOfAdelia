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
        description.add("Deals area damage if");
        description.add("fireball hits a target.");

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
        cooldowns.add(3);
        cooldowns.add(3);
        cooldowns.add(3);
        cooldowns.add(3);
        cooldowns.add(3);
        cooldowns.add(3);

        Skill skill = new Skill("Fireball", Material.IRON_HOE, 12, description, reqLevels, reqPoints, manaCosts, cooldowns);
        SelfTarget selfTarget = new SelfTarget();

        List<Integer> projectileAmounts = new ArrayList<>();
        projectileAmounts.add(1);
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 1.9, projectileAmounts, 30,
                0, 1, 0, 200, true, Fireball.class);

        List<Double> radiuses = new ArrayList<>();
        radiuses.add(3D);
        radiuses.add(4D);
        radiuses.add(3D);
        radiuses.add(3D);
        radiuses.add(3D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, radiuses);

        List<Double> damages = new ArrayList<>();
        damages.add(10D);
        areaTarget.addChildren(new DamageMechanic(damages, DamageMechanic.DamageType.MAGIC));

        projectileMechanic.addChildren(areaTarget);

        selfTarget.addChildren(projectileMechanic);
        selfTarget.addChildren(new SoundMechanic(GoaSound.SKILL_FIRE_SLASH));

        skill.addTrigger(selfTarget);

        return skill;
    }

    private static Skill getTwo() {
        List<String> description = new ArrayList<>();
        description.add("Shoot a lightning bolt that deals damage and");
        description.add("silences targets in area if it hits a target.");

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
        cooldowns.add(12);
        cooldowns.add(12);
        cooldowns.add(12);
        cooldowns.add(12);
        cooldowns.add(12);
        cooldowns.add(12);

        Skill skill = new Skill("Lightning Bolt", Material.IRON_HOE, 7, description, reqLevels, reqPoints, manaCosts, cooldowns);
        SelfTarget selfTarget = new SelfTarget();

        List<Integer> projectileAmounts = new ArrayList<>();
        projectileAmounts.add(1);
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 1.9, projectileAmounts, 30,
                0, 1, 0, 200, true, SmallFireball.class, Particle.VILLAGER_HAPPY,
                ArrangementParticle.SPHERE, 0.5, 4, null, true);

        List<Double> radiuses = new ArrayList<>();
        radiuses.add(3D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, radiuses);

        List<Double> damages = new ArrayList<>();
        damages.add(10D);
        areaTarget.addChildren(new DamageMechanic(damages, DamageMechanic.DamageType.MAGIC));
        areaTarget.addChildren(new ParticleMechanic(Particle.VILLAGER_HAPPY, ArrangementParticle.CIRCLE, 2, 4, 0, 0, 0, 0, 0.5, 0, 0, null));
        List<Integer> durations = new ArrayList<>();
        durations.add(60);
        areaTarget.addChildren(new SilenceMechanic(durations));

        projectileMechanic.addChildren(areaTarget);
        projectileMechanic.addChildren(new SoundMechanic(GoaSound.SKILL_LIGHTNING_FLUX));

        selfTarget.addChildren(projectileMechanic);
        selfTarget.addChildren(new SoundMechanic(GoaSound.SKILL_LIGHTNING_NORMAL));

        skill.addTrigger(selfTarget);

        return skill;
    }

    private static Skill getThree() {
        List<String> description = new ArrayList<>();
        description.add("Push nearby enemies and cleanse yourself.");

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
        cooldowns.add(16);
        cooldowns.add(16);
        cooldowns.add(16);
        cooldowns.add(16);
        cooldowns.add(16);
        cooldowns.add(16);

        Skill skill = new Skill("Shockwave", Material.IRON_HOE, 11, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> areas = new ArrayList<>();
        areas.add(3D);
        areas.add(3D);
        areas.add(3D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, areas);

        List<Double> speeds = new ArrayList<>();
        speeds.add(1.8D);
        speeds.add(2D);
        speeds.add(2.2D);
        PushMechanic pushMechanic = new PushMechanic(PushMechanic.PushType.FIXED, speeds, true);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(areaTarget);
        areaTarget.addChildren(pushMechanic);
        SelfTarget selfTargetForSound = new SelfTarget();
        areaTarget.addChildren(selfTargetForSound);
        selfTargetForSound.addChildren(new SoundMechanic(GoaSound.SKILL_SONIC_BOOM));

        return skill;
    }

    private static Skill getPassive() {
        List<String> description = new ArrayList<>();
        description.add("When you took physical damage while");
        description.add("this skill is active, block it and");
        description.add("gain shields.");

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
        manaCosts.add(5);

        List<Integer> cooldowns = new ArrayList<>();
        cooldowns.add(10);
        cooldowns.add(10);
        cooldowns.add(10);
        cooldowns.add(10);
        cooldowns.add(10);
        cooldowns.add(10);

        Skill skill = new Skill("Mental Fortitude", Material.IRON_HOE, 10, description, reqLevels, reqPoints, manaCosts, cooldowns);

        InitializeTrigger initializeTrigger = new InitializeTrigger();

        TookMeleeDamageTrigger tookMeleeDamageTrigger = new TookMeleeDamageTrigger(20L * 10);

        List<Double> radiuses = new ArrayList<>();
        radiuses.add(3D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, radiuses);

        List<Integer> ccTicks = new ArrayList<>();
        ccTicks.add(60);
        ccTicks.add(60);
        ccTicks.add(60);
        ccTicks.add(60);
        List<Integer> ccAmplifiers = new ArrayList<>();
        ccAmplifiers.add(999);
        areaTarget.addChildren(new PotionEffectMechanic(PotionEffectType.SLOW, ccTicks, ccAmplifiers));
        areaTarget.addChildren(new PotionEffectMechanic(PotionEffectType.JUMP, ccTicks, ccAmplifiers));

        List<Double> multipliers = new ArrayList<>();
        multipliers.add(1.2);
        List<Integer> ticks = new ArrayList<>();
        ticks.add(200);
        BuffMechanic buffMechanic = new BuffMechanic(BuffType.PHYSICAL_DEFENSE, multipliers, ticks);

        skill.addTrigger(initializeTrigger);
        initializeTrigger.addChildren(tookMeleeDamageTrigger);
        tookMeleeDamageTrigger.addChildren(areaTarget);
        tookMeleeDamageTrigger.addChildren(buffMechanic);

        return skill;
    }

    private static Skill getUltimate() {
        List<String> description = new ArrayList<>();
        description.add("Summon a meteor from sky that hits");
        description.add("nearby targets when it reaches ground.");
        description.add("");
        description.add("The hit effect deals damage, ignites");
        description.add("and launches targets to sky.");

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
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);
        cooldowns.add(5);

        Skill skill = new Skill("Chaos Meteor", Material.IRON_HOE, 30, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> ranges = new ArrayList<>();
        ranges.add(12D);
        ranges.add(12D);
        ranges.add(12D);
        SingleTarget singleTarget = new SingleTarget(false, true, false, 1, ranges, 4);

        List<Integer> projectileAmounts = new ArrayList<>();
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        projectileAmounts.add(1);
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.RAIN, 8, 12, 0.4, projectileAmounts, 0,
                0, 0, 200, false, Fireball.class, Particle.FLAME,
                ArrangementParticle.SPHERE, 2, 32, null, false);

        List<Double> areas = new ArrayList<>();
        areas.add(8D);
        areas.add(8D);
        areas.add(8D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, areas);

        List<Integer> repeatAmounts = new ArrayList<>();
        repeatAmounts.add(2);
        repeatAmounts.add(2);
        repeatAmounts.add(2);
        repeatAmounts.add(2);
        RepeatMechanic repeatMechanic = new RepeatMechanic(40, repeatAmounts);

        skill.addTrigger(selfTarget);
        selfTarget.addChildren(singleTarget);
        singleTarget.addChildren(repeatMechanic);
        repeatMechanic.addChildren(projectileMechanic);
        List<Double> damages = new ArrayList<>();
        damages.add(16D);
        damages.add(16D);
        damages.add(16D);
        projectileMechanic.addChildren(areaTarget);
        areaTarget.addChildren(new DamageMechanic(damages, DamageMechanic.DamageType.RANGED));
        List<Integer> ticks = new ArrayList<>();
        ticks.add(80);
        ticks.add(80);
        ticks.add(80);
        ticks.add(80);
        areaTarget.addChildren(new FireMechanic(ticks));

        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.EXPLOSION_HUGE, ArrangementParticle.SPHERE, 4, 4,
                0, 0, 0, 0, 1, 0, 0, null);
        projectileMechanic.addChildren(particleMechanic);

        List<Double> upward = new ArrayList<>();
        upward.add(1.4D);
        upward.add(1.4D);
        upward.add(1.4D);
        List<Double> forward = new ArrayList<>();
        forward.add(0D);
        forward.add(0D);
        forward.add(0D);
        List<Double> right = new ArrayList<>();
        right.add(0D);
        right.add(0D);
        right.add(0D);
        LaunchMechanic launchMechanic = new LaunchMechanic(LaunchMechanic.Relative.TARGET, forward, upward, right);

        areaTarget.addChildren(launchMechanic);
        return skill;
    }
}
