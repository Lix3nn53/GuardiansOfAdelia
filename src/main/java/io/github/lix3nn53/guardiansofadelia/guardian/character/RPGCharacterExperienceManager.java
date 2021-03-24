package io.github.lix3nn53.guardiansofadelia.guardian.character;

import java.util.ArrayList;
import java.util.List;

public class RPGCharacterExperienceManager {

    private static final List<Integer> levelToRequiredExperience = new ArrayList<>();

    static {
        for (int level = 1; level <= 90; level++) {
            int experience = (int) (10 + Math.round(5 * Math.pow(level, 3) / 4) + 0.5); // exp formula
            levelToRequiredExperience.add(experience);
        }
    }

    public static int getLevel(int totalExp) {
        int experience = 0;
        for (int i = 0; i < 90; i++) {
            experience += levelToRequiredExperience.get(i);
            if (totalExp < experience) {
                return i + 1;
            }
        }
        return 90;
    }

    public static int getRequiredExperience(int level) {
        return levelToRequiredExperience.get(level - 1);
    }

    public static int getCurrentExperience(int totalExp, int level) {
        int totalExpForLevel = getTotalRequiredExperience(level - 1);
        return totalExp - totalExpForLevel;
    }

    public static int getTotalRequiredExperience(int level) {
        int experience = 0;
        for (int i = 0; i < level; i++) {
            experience += levelToRequiredExperience.get(i);
        }
        return experience;
    }
}
