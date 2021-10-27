package io.github.lix3nn53.guardiansofadelia.guardian.character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RPGClassManager {

    private static final HashMap<String, RPGClass> rpgClassMap = new HashMap<>();

    private static final HashMap<Integer, Integer> classTierToRequiredLevel = new HashMap<>();
    public static int HIGHEST_CLASS_TIER = 1;
    private static String startingClass;

    public static void addClass(String className, RPGClass rpgClass) {
        int tier = rpgClass.getTier();
        if (tier > HIGHEST_CLASS_TIER) HIGHEST_CLASS_TIER = tier;

        rpgClassMap.put(className.toUpperCase(), rpgClass);
    }

    public static boolean hasClass(String className) {
        return rpgClassMap.containsKey(className.toUpperCase());
    }

    public static RPGClass getClass(String className) {
        return rpgClassMap.get(className.toUpperCase());
    }

    public static List<RPGClass> getClassesAtTier(int tier) {
        List<RPGClass> classes = new ArrayList<>();

        for (String classStr : rpgClassMap.keySet()) {
            RPGClass rpgClass = rpgClassMap.get(classStr);

            int tierOfCurrent = rpgClass.getTier();

            if (tierOfCurrent == tier) {
                classes.add(rpgClass);
            }
        }

        return classes;
    }

    public static void clearClasses() {
        rpgClassMap.clear();
    }

    public static int getRequiredLevelForClassTier(int classTier) {
        if (classTierToRequiredLevel.containsKey(classTier)) {
            return classTierToRequiredLevel.get(classTier);
        }

        return 9999;
    }

    public static void setRequiredLevelForClassTier(int classTier, int level) {
        classTierToRequiredLevel.put(classTier, level);
    }

    public static String getStartingClass() {
        return startingClass;
    }

    public static void setStartingClass(String startingClass) {
        RPGClassManager.startingClass = startingClass;
    }
}
