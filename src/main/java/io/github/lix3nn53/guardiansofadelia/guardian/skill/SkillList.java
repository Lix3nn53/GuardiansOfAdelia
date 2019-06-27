package io.github.lix3nn53.guardiansofadelia.guardian.skill;

import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.DamageMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.ProjectileMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.SpreadType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.AreaTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.CastTrigger;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;

import java.util.ArrayList;
import java.util.List;

/**
 * Common list of skills for skill-bars etc
 * <p>
 * Not unique for players
 */
public class SkillList {

    private static final List<Skill> archer = new ArrayList<>();
    private static final List<Skill> hunter = new ArrayList<>();
    private static final List<Skill> knight = new ArrayList<>();
    private static final List<Skill> mage = new ArrayList<>();
    private static final List<Skill> monk = new ArrayList<>();
    private static final List<Skill> paladin = new ArrayList<>();
    private static final List<Skill> rogue = new ArrayList<>();
    private static final List<Skill> warrior = new ArrayList<>();

    static {
        List<String> description = new ArrayList<>();

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

        //ARCHER
        description.add("asd");
        Skill skillOne = new Skill("Blind Shot", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        archer.add(skillOne);

        description.clear();
        description.add("asd");
        Skill skillTwo = new Skill("Zephyr", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        archer.add(skillTwo);

        description.clear();
        description.add("asd");
        Skill skillThree = new Skill("Purple Wings", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        archer.add(skillThree);

        description.clear();
        description.add("asd");
        Skill passive = new Skill("Hop Hop", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        archer.add(passive);

        description.clear();
        description.add("asd");
        Skill ultimate = new Skill("Make It Rain", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        archer.add(ultimate);

        //HUNTER
        //TODO hunter skill names
        description.clear();
        description.add("asd");
        skillOne = new Skill("SkillName", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        hunter.add(skillOne);

        description.clear();
        description.add("asd");
        skillTwo = new Skill("SkillName", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        hunter.add(skillTwo);

        description.clear();
        description.add("asd");
        skillThree = new Skill("Bear Trap", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        hunter.add(skillThree);

        description.clear();
        description.add("asd");
        passive = new Skill("SkillName", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        hunter.add(passive);

        description.clear();
        description.add("asd");
        ultimate = new Skill("SkillName", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        hunter.add(ultimate);

        //KNIGHT
        description.clear();
        description.add("asd");
        skillOne = new Skill("Slice and Dice", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        knight.add(skillOne);

        description.clear();
        description.add("asd");
        skillTwo = new Skill("Devastate", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        knight.add(skillTwo);

        description.clear();
        description.add("asd");
        skillThree = new Skill("Battle Cry", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        knight.add(skillThree);

        description.clear();
        description.add("asd");
        passive = new Skill("Block", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        knight.add(passive);

        description.clear();
        description.add("asd");
        ultimate = new Skill("Wrath of Justice", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        knight.add(ultimate);

        //MAGE
        //TODO fireball implement & test
        description.clear();
        description.add("asd");
        skillOne = new Skill("Fireball", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        CastTrigger castTrigger = new CastTrigger();

        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 1.9, 1, 30,
                0, 1, 0, 200, true, Fireball.class);

        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, 3);

        areaTarget.addChildren(new DamageMechanic(10, 10, DamageMechanic.DamageType.MAGIC));

        projectileMechanic.addChildren(areaTarget);

        castTrigger.addChildren(projectileMechanic);

        skillOne.addTrigger(castTrigger);
        mage.add(skillOne);

        description.clear();
        description.add("asd");
        skillTwo = new Skill("Electric Shock", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        mage.add(skillTwo);

        description.clear();
        description.add("asd");
        skillThree = new Skill("Shockwave", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        mage.add(skillThree);

        description.clear();
        description.add("asd");
        passive = new Skill("Mental Fortitude", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        mage.add(passive);

        description.clear();
        description.add("asd");
        ultimate = new Skill("Inferno", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        mage.add(ultimate);

        //MONK
        description.clear();
        description.add("asd");
        skillOne = new Skill("Iron Fist", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        monk.add(skillOne);

        description.clear();
        description.add("asd");
        skillTwo = new Skill("Tumble", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        monk.add(skillTwo);

        description.clear();
        description.add("asd");
        skillThree = new Skill("Serenity", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        monk.add(skillThree);

        description.clear();
        description.add("asd");
        passive = new Skill("Mark of Ocean", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        monk.add(passive);

        description.clear();
        description.add("asd");
        ultimate = new Skill("Aqua Prison", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        monk.add(ultimate);

        //PALADIN
        description.clear();
        description.add("asd");
        skillOne = new Skill("Hammerblow", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        paladin.add(skillOne);

        description.clear();
        description.add("asd");
        skillTwo = new Skill("Heal", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        paladin.add(skillTwo);

        description.clear();
        description.add("asd");
        skillThree = new Skill("Polymorph", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        paladin.add(skillThree);

        description.clear();
        description.add("asd");
        passive = new Skill("Resurrection", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        paladin.add(passive);

        description.clear();
        description.add("asd");
        ultimate = new Skill("Cosmic Radiance", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        paladin.add(ultimate);

        //ROGUE
        description.clear();
        description.add("asd");
        skillOne = new Skill("Smoke", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        rogue.add(skillOne);

        description.clear();
        description.add("asd");
        skillTwo = new Skill("Void Dash", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        rogue.add(skillTwo);

        description.clear();
        description.add("asd");
        skillThree = new Skill("Shurikens", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        rogue.add(skillThree);

        description.clear();
        description.add("asd");
        passive = new Skill("Backstab", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        rogue.add(passive);

        description.clear();
        description.add("asd");
        ultimate = new Skill("Phantom Strike", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        rogue.add(ultimate);

        //WARRIOR
        description.clear();
        description.add("asd");
        skillOne = new Skill("Power Slash", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        warrior.add(skillOne);

        description.clear();
        description.add("asd");
        skillTwo = new Skill("Flame Slash", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        warrior.add(skillTwo);

        description.clear();
        description.add("asd");
        skillThree = new Skill("Victory Flag", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        warrior.add(skillThree);

        description.clear();
        description.add("asd");
        passive = new Skill("Death Rattle", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        warrior.add(passive);

        description.clear();
        description.add("asd");
        ultimate = new Skill("Grand Skyfall", Material.ENCHANTED_BOOK, description, reqLevels, reqPoints, manaCosts, cooldowns);
        warrior.add(ultimate);
    }

    /**
     * Elements in returned list: 0,1,2 normal skills, 3 passive, 4 ultimate
     *
     * @param rpgClass
     */
    public static List<Skill> getSkillSet(RPGClass rpgClass) {
        List<Skill> skills = new ArrayList<>();
        switch (rpgClass) {
            case ARCHER:
                skills.addAll(archer);
                break;
            case KNIGHT:
                skills.addAll(knight);
                break;
            case MAGE:
                skills.addAll(mage);
                break;
            case MONK:
                skills.addAll(monk);
                break;
            case ROGUE:
                skills.addAll(rogue);
                break;
            case PALADIN:
                skills.addAll(paladin);
                break;
            case WARRIOR:
                skills.addAll(warrior);
                break;
            case HUNTER:
                skills.addAll(hunter);
                break;
        }
        return skills;
    }

    /**
     * @param rpgClass
     * @param skillIndex 0,1,2 normal skills, 3 passive, 4 ultimate
     * @return
     */
    public static Skill getSkill(RPGClass rpgClass, int skillIndex) {
        switch (rpgClass) {
            case ARCHER:
                return archer.get(skillIndex);
            case KNIGHT:
                return knight.get(skillIndex);
            case MAGE:
                return mage.get(skillIndex);
            case MONK:
                return monk.get(skillIndex);
            case ROGUE:
                return rogue.get(skillIndex);
            case PALADIN:
                return paladin.get(skillIndex);
            case WARRIOR:
                return warrior.get(skillIndex);
            case HUNTER:
                return hunter.get(skillIndex);
        }
        return null;
    }
}
