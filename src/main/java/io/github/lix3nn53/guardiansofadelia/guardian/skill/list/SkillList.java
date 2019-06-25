package io.github.lix3nn53.guardiansofadelia.guardian.skill.list;

import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.ProjectileMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.SpreadType;
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
        //ARCHER
        List<String> description = new ArrayList<>();
        description.add("asd");
        Skill skillOne = new Skill("Blind Shot", Material.AIR, description, 10, 1);
        archer.add(skillOne);

        description.clear();
        description.add("asd");
        Skill skillTwo = new Skill("Zephyr", Material.AIR, description, 10, 1);
        archer.add(skillTwo);

        description.clear();
        description.add("asd");
        Skill skillThree = new Skill("Purple Wings", Material.AIR, description, 10, 1);
        archer.add(skillThree);

        description.clear();
        description.add("asd");
        Skill passive = new Skill("Hop Hop", Material.AIR, description, 10, 1);
        archer.add(passive);

        description.clear();
        description.add("asd");
        Skill ultimate = new Skill("Make It Rain", Material.AIR, description, 10, 1);
        archer.add(ultimate);

        //HUNTER
        description.clear();
        description.add("asd");
        skillOne = new Skill("SkillName", Material.AIR, description, 10, 1);
        hunter.add(skillOne);

        description.clear();
        description.add("asd");
        skillTwo = new Skill("SkillName", Material.AIR, description, 10, 1);
        hunter.add(skillTwo);

        description.clear();
        description.add("asd");
        skillThree = new Skill("SkillName", Material.AIR, description, 10, 1);
        hunter.add(skillThree);

        description.clear();
        description.add("asd");
        passive = new Skill("SkillName", Material.AIR, description, 10, 1);
        hunter.add(passive);

        description.clear();
        description.add("asd");
        ultimate = new Skill("SkillName", Material.AIR, description, 10, 1);
        hunter.add(ultimate);

        //KNIGHT
        description.clear();
        description.add("asd");
        skillOne = new Skill("Slice and Dice", Material.AIR, description, 10, 1);
        knight.add(skillOne);

        description.clear();
        description.add("asd");
        skillTwo = new Skill("Devastate", Material.AIR, description, 10, 1);
        knight.add(skillTwo);

        description.clear();
        description.add("asd");
        skillThree = new Skill("Battle Cry", Material.AIR, description, 10, 1);
        knight.add(skillThree);

        description.clear();
        description.add("asd");
        passive = new Skill("Block", Material.AIR, description, 10, 1);
        knight.add(passive);

        description.clear();
        description.add("asd");
        ultimate = new Skill("Wrath of Justice", Material.AIR, description, 10, 1);
        knight.add(ultimate);

        //MAGE
        description.clear();
        description.add("asd");
        skillOne = new Skill("Fireball", Material.AIR, description, 10, 1);
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 2.4, 1, 30,
                0, 0, 0, 10, Fireball.class);
        mage.add(skillOne);

        description.clear();
        description.add("asd");
        skillTwo = new Skill("Electric Shock", Material.AIR, description, 10, 1);
        mage.add(skillTwo);

        description.clear();
        description.add("asd");
        skillThree = new Skill("Shockwave", Material.AIR, description, 10, 1);
        mage.add(skillThree);

        description.clear();
        description.add("asd");
        passive = new Skill("Mental Fortitude", Material.AIR, description, 10, 1);
        mage.add(passive);

        description.clear();
        description.add("asd");
        ultimate = new Skill("Inferno", Material.AIR, description, 10, 1);
        mage.add(ultimate);

        //MONK
        description.clear();
        description.add("asd");
        skillOne = new Skill("Iron Fist", Material.AIR, description, 10, 1);
        monk.add(skillOne);

        description.clear();
        description.add("asd");
        skillTwo = new Skill("Tumble", Material.AIR, description, 10, 1);
        monk.add(skillTwo);

        description.clear();
        description.add("asd");
        skillThree = new Skill("Serenity", Material.AIR, description, 10, 1);
        monk.add(skillThree);

        description.clear();
        description.add("asd");
        passive = new Skill("Mark of Ocean", Material.AIR, description, 10, 1);
        monk.add(passive);

        description.clear();
        description.add("asd");
        ultimate = new Skill("Aqua Prison", Material.AIR, description, 10, 1);
        monk.add(ultimate);

        //PALADIN
        description.clear();
        description.add("asd");
        skillOne = new Skill("Hammerblow", Material.AIR, description, 10, 1);
        paladin.add(skillOne);

        description.clear();
        description.add("asd");
        skillTwo = new Skill("Heal", Material.AIR, description, 10, 1);
        paladin.add(skillTwo);

        description.clear();
        description.add("asd");
        skillThree = new Skill("Polymorph", Material.AIR, description, 10, 1);
        paladin.add(skillThree);

        description.clear();
        description.add("asd");
        passive = new Skill("Resurrection", Material.AIR, description, 10, 1);
        paladin.add(passive);

        description.clear();
        description.add("asd");
        ultimate = new Skill("Cosmic Radiance", Material.AIR, description, 10, 1);
        paladin.add(ultimate);

        //ROGUE
        description.clear();
        description.add("asd");
        skillOne = new Skill("Smoke", Material.AIR, description, 10, 1);
        rogue.add(skillOne);

        description.clear();
        description.add("asd");
        skillTwo = new Skill("Void Dash", Material.AIR, description, 10, 1);
        rogue.add(skillTwo);

        description.clear();
        description.add("asd");
        skillThree = new Skill("Shurikens", Material.AIR, description, 10, 1);
        rogue.add(skillThree);

        description.clear();
        description.add("asd");
        passive = new Skill("Backstab", Material.AIR, description, 10, 1);
        rogue.add(passive);

        description.clear();
        description.add("asd");
        ultimate = new Skill("Phantom Strike", Material.AIR, description, 10, 1);
        rogue.add(ultimate);

        //WARRIOR
        description.clear();
        description.add("asd");
        skillOne = new Skill("Power Slash", Material.AIR, description, 10, 1);
        warrior.add(skillOne);

        description.clear();
        description.add("asd");
        skillTwo = new Skill("Flame Slash", Material.AIR, description, 10, 1);
        warrior.add(skillTwo);

        description.clear();
        description.add("asd");
        skillThree = new Skill("Victory Flag", Material.AIR, description, 10, 1);
        warrior.add(skillThree);

        description.clear();
        description.add("asd");
        passive = new Skill("Death Rattle", Material.AIR, description, 10, 1);
        warrior.add(passive);

        description.clear();
        description.add("asd");
        ultimate = new Skill("Grand Skyfall", Material.AIR, description, 10, 1);
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
