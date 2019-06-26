package io.github.lix3nn53.guardiansofadelia.guardian.skill;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.DamageMechanic;

public class SkillUtils {

    private static DamageMechanic.DamageType damageType = null;

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
    public static DamageMechanic.DamageType getDamageType() {
        return damageType;
    }

    public static void setDamageType(DamageMechanic.DamageType current) {
        damageType = current;
    }

    public static void clearSkillDamage() {
        damageType = null;
    }
}
