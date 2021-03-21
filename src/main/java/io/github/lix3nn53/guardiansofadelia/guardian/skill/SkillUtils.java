package io.github.lix3nn53.guardiansofadelia.guardian.skill;

import io.github.lix3nn53.guardiansofadelia.guardian.element.ElementType;

public class SkillUtils {

    private static ElementType damageType = null;

    /**
     * Method to use in EntityDamageByEntityEvent to check if damage is caused by skill
     *
     * @return
     */
    public static boolean isSkillDamage() {
        return damageType != null;
    }

    /**
     * Method to get damage type if isSkillDamage() returns true
     *
     * @return
     */
    public static ElementType getDamageType() {
        return damageType;
    }

    public static void setDamageType(ElementType current) {
        damageType = current;
    }

    public static void clearSkillDamage() {
        damageType = null;
    }
}
