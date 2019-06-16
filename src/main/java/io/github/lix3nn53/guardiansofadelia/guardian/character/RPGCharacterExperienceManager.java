package io.github.lix3nn53.guardiansofadelia.guardian.character;

import java.util.HashMap;

public class RPGCharacterExperienceManager {

    private static final HashMap<Integer, Integer> levelToTotalRequiredExperience = new HashMap<>();

    static {
        int experience = 0;
        for (int level = 1; level <= 90; level++) {
            experience += (int) (10 + Math.round(5 * Math.pow(level, 3) / 4) + 0.5);
            levelToTotalRequiredExperience.put(level, experience);
        }
    }

    public static int getLevelFromTotalExperience(int totalExp) {
        for (int level = 1; level <= 90; level++) {
            int experience = levelToTotalRequiredExperience.get(level);
            if (totalExp < experience) {
                return level;
            }
        }
        return 90;
    }

    public static int getRequiredExperience(int level) {
        if (level == 1) {
            return levelToTotalRequiredExperience.get(level);
        }
        return levelToTotalRequiredExperience.get(level) - levelToTotalRequiredExperience.get(level - 1);
    }

    public static int getCurrentExperience(int totalExp, int level) {
        if (level == 1) {
            return totalExp;
        }
        return totalExp - levelToTotalRequiredExperience.get(level - 1);
    }

    public static int getTotalExpForLevel(int level) {
        return levelToTotalRequiredExperience.get(level);
    }
}
