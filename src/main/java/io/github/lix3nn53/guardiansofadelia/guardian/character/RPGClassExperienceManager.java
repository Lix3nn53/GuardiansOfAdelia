package io.github.lix3nn53.guardiansofadelia.guardian.character;

public class RPGClassExperienceManager {

    public static int RPG_CLASS_MAX_LEVEL = 10;

    public static int getRequiredExperience(int level) {
        return RPGCharacterExperienceManager.getRequiredExperience(level * (90 / RPG_CLASS_MAX_LEVEL));
    }

    public static int getLevel(int totalExp) {
        int experience = 0;
        for (int i = 1; i <= RPG_CLASS_MAX_LEVEL; i++) {
            experience += getRequiredExperience(i);
            if (totalExp < experience) {
                return i;
            }
        }

        return RPG_CLASS_MAX_LEVEL;
    }

    public static int getCurrentExperience(int totalExp, int level) {
        int totalExpForLevel = getTotalRequiredExperience(level - 1);
        return totalExp - totalExpForLevel;
    }

    public static int getTotalRequiredExperience(int level) {
        int experience = 0;
        for (int i = 1; i <= level; i++) {
            experience += getRequiredExperience(i);
        }
        return experience;
    }
}
