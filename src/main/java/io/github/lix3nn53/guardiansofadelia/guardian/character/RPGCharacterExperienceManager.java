package io.github.lix3nn53.guardiansofadelia.guardian.character;

import java.util.HashMap;

public class RPGCharacterExperienceManager {

    private static final HashMap<Integer, Integer> levelToRequiredExperience = new HashMap<>();

    static {
        for (int level = 1; level <= 90; level++) {
            int experience = (int) (10 + Math.round(5 * Math.pow(level, 3) / 4) + 0.5);
            levelToRequiredExperience.put(level, experience);
        }
    }

    public static int getLevelFromTotalExperience(int totalExp) {
        int experience = 0;
        for (int level = 1; level <= 90; level++) {
            experience += levelToRequiredExperience.get(level);
            if (totalExp < experience) {
                return level;
            }
        }
        return 90;
    }

    public static int getRequiredExperience(int level) {
        return levelToRequiredExperience.get(level);
    }

    public static int getCurrentExperience(int totalExp) {
        int previousLevel = getLevelFromTotalExperience(totalExp) - 1;
        int experience = 0;
        for (int level = 1; level <= previousLevel; level++) {
            experience += levelToRequiredExperience.get(level);
        }
        return totalExp - experience;
    }

    public static int getTotalExpForLevel(int levelSearch) {
        int experience = 0;
        for (int level = 1; level <= levelSearch; level++) {
            experience += levelToRequiredExperience.get(level);
        }
        return experience;
    }
}
