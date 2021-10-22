package io.github.lix3nn53.guardiansofadelia.guardian.character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RPGClassManager {

    private static final HashMap<String, RPGClass> rpgClassMap = new HashMap<>();

    public static final int HIGHEST_CLASS_TIER = 3;
    public static final String starterClass = "fighter";
    private static final HashMap<Integer, Integer> classTierToRequiredQuestId = new HashMap<>();

    private static final List<String> tutorialClasses = new ArrayList<>();

    public static void addClass(String className, RPGClass rpgClass) {
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

    public static int getRequiredQuestForClassTier(int classTier) {
        if (classTierToRequiredQuestId.containsKey(classTier)) {
            return classTierToRequiredQuestId.get(classTier);
        }

        return -1;
    }

    public static void setRequiredQuestForClassTier(int classTier, int questNo) {
        classTierToRequiredQuestId.put(classTier, questNo);
    }

    public static void addTutorialClass(List<String> tutorialClass) {
        tutorialClasses.addAll(tutorialClass);
    }

    public static List<String> getTutorialClasses() {
        return tutorialClasses;
    }
}
