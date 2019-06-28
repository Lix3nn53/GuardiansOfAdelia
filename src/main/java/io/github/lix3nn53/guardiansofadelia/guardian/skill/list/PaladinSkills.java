package io.github.lix3nn53.guardiansofadelia.guardian.skill.list;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.DamageMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.ProjectileMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.SpreadType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.AreaTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.CastTrigger;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;

import java.util.ArrayList;
import java.util.List;

public class PaladinSkills {

    public static List<Skill> getSet() {
        List<Skill> mage = new ArrayList<>();

        mage.add(getOne());
        mage.add(getTwo());
        mage.add(getThree());
        mage.add(getPassive());
        mage.add(getUltimate());

        return mage;
    }

    private static Skill getOne() {
        List<String> description = new ArrayList<>();
        description.add("asd");
        description.add("asd");
        description.add("asd");
        description.add("asd");

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
        description.add("asd");
        description.add("asd");
        description.add("asd");
        description.add("asd");

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

        Skill skill = new Skill("Electric Shock", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);

        return skill;
    }

    private static Skill getThree() {
        List<String> description = new ArrayList<>();
        description.add("asd");
        description.add("asd");
        description.add("asd");
        description.add("asd");

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
        description.add("asd");
        description.add("asd");
        description.add("asd");
        description.add("asd");

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
        description.add("asd");
        description.add("asd");
        description.add("asd");
        description.add("asd");

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
