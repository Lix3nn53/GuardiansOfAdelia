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

        Skill skill = new Skill("Fireball", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        SelfTarget selfTarget = new SelfTarget();

        List<Integer> projectileAmounts = new ArrayList<>();
        projectileAmounts.add(1);
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 1.9, projectileAmounts, 30,
                0, 1, 0, 200, true, Fireball.class);

        List<Double> radiuses = new ArrayList<>();
        radiuses.add(3D);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, radiuses);

        List<Double> damages = new ArrayList<>();
        damages.add(10D);
        areaTarget.addChildren(new DamageMechanic(damages, DamageMechanic.DamageType.MAGIC));

        projectileMechanic.addChildren(areaTarget);

        selfTarget.addChildren(projectileMechanic);

        skill.addTrigger(selfTarget);

        return skill;
    }

    private static Skill getTwo() {
        List<String> description = new ArrayList<>();
        description.add("Shoot a lightning bolt that deals damage and");
        description.add("silences targets in area if it hits a target.");

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

        Skill skill = new Skill("Lightning Bolt", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        SelfTarget selfTarget = new SelfTarget();

        List<Integer> projectileAmounts = new ArrayList<>();
        projectileAmounts.add(1);
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 1.9, projectileAmounts, 30,
                0, 1, 0, 200, true, SmallFireball.class, Particle.VILLAGER_HAPPY, ArrangementParticle.SPHERE, 0.5, 4, null);

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

        selfTarget.addChildren(projectileMechanic);

        skill.addTrigger(selfTarget);

        return skill;
    }

    private static Skill getThree() {
        List<String> description = new ArrayList<>();
        description.add("Push nearby enemies and cleanse yourself.");

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

        Skill skill = new Skill("Shockwave", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);


        return skill;
    }

    private static Skill getPassive() {
        List<String> description = new ArrayList<>();
        description.add("When you took physical damage while");
        description.add("this skill is active, block it and");
        description.add("gain shields.");

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

        Skill skill = new Skill("Mental Fortitude", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);

        InitializeTrigger initializeTrigger = new InitializeTrigger();

        TookMeleeDamageTrigger tookMeleeDamageTrigger = new TookMeleeDamageTrigger(500);

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
        description.add("Summon meteors");
        description.add("todo");

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

        Skill skill = new Skill("Inferno", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);

        SelfTarget selfTarget = new SelfTarget();

        List<Double> ranges = new ArrayList<>();
        ranges.add(12D);
        SingleTarget singleTarget = new SingleTarget(false, true, false, 1, ranges, 4);

        List<Integer> projectileAmounts = new ArrayList<>();
        projectileAmounts.add(16);
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.RAIN, 8, 12, 1.9, projectileAmounts, 0,
                0, 0, 200, true, Fireball.class);

        List<Integer> repeatAmounts = new ArrayList<>();
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
        projectileMechanic.addChildren(new DamageMechanic(damages, DamageMechanic.DamageType.RANGED));
        List<Integer> ticks = new ArrayList<>();
        ticks.add(80);
        projectileMechanic.addChildren(new FireMechanic(ticks));

        return skill;
    }
}
