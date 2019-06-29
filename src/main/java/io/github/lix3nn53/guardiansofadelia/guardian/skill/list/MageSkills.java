package io.github.lix3nn53.guardiansofadelia.guardian.skill.list;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.DamageMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.ProjectileMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.SpreadType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.AreaTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.CastTrigger;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ArrangementParticle;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.SmallFireball;

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
        CastTrigger castTrigger = new CastTrigger();

        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 1.9, 1, 30,
                0, 1, 0, 200, true, Fireball.class);

        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, 3);

        areaTarget.addChildren(new DamageMechanic(10, 10, DamageMechanic.DamageType.MAGIC));

        projectileMechanic.addChildren(areaTarget);

        castTrigger.addChildren(projectileMechanic);

        skill.addTrigger(castTrigger);

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
        CastTrigger castTrigger = new CastTrigger();

        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 1.9, 1, 30,
                0, 1, 0, 200, true, SmallFireball.class, Particle.VILLAGER_HAPPY, ArrangementParticle.SPHERE, 0.5, 4);

        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, 3);

        areaTarget.addChildren(new DamageMechanic(10, 10, DamageMechanic.DamageType.MAGIC));

        projectileMechanic.addChildren(areaTarget);

        castTrigger.addChildren(projectileMechanic);

        skill.addTrigger(castTrigger);

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

        return skill;
    }
}
