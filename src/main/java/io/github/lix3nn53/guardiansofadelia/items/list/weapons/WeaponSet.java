package io.github.lix3nn53.guardiansofadelia.items.list.weapons;

import io.github.lix3nn53.guardiansofadelia.items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.items.stats.StatUtils;

public class WeaponSet {
    private final String name;
    private final int requiredLevel;
    private final int customModelData;

    public WeaponSet(String name, int requiredLevel, int customModelData) {
        this.name = name;
        this.requiredLevel = requiredLevel;
        this.customModelData = customModelData;
    }

    public String getName(WeaponGearType gearType) {
        return name + " " + gearType.getDisplayName();
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public int getElementDamage(WeaponGearType gearType) {
        int damage = StatUtils.getDamageItem(requiredLevel);
        return (int) (damage * gearType.getWeaponAttackDamage().getDamageReduction() + 0.5);
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }
}
