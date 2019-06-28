package io.github.lix3nn53.guardiansofadelia.guardian.skill;

import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.list.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Common list of skills for skill-bars etc
 * <p>
 * Not unique for players unlike SkillBar
 */
public class SkillList {

    private static final List<Skill> archer = new ArrayList<>(ArcherSkills.getSet());
    private static final List<Skill> hunter = new ArrayList<>(HunterSkills.getSet());
    private static final List<Skill> knight = new ArrayList<>(KnightSkills.getSet());
    private static final List<Skill> mage = new ArrayList<>(MageSkills.getSet());
    private static final List<Skill> monk = new ArrayList<>(MonkSkills.getSet());
    private static final List<Skill> paladin = new ArrayList<>(PaladinSkills.getSet());
    private static final List<Skill> rogue = new ArrayList<>(RogueSkills.getSet());
    private static final List<Skill> warrior = new ArrayList<>(WarriorSkills.getSet());

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
