package io.github.lix3nn53.guardiansofadelia.Items.list.weapons;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponGearType;

public class WeaponSet {
    private final String name;
    private final int customModelData;
    private final int requiredLevel;
    private final int damage;

    public WeaponSet(String name, int customModelData, int requiredLevel, int damage) {
        this.name = name;
        this.customModelData = customModelData;
        this.requiredLevel = requiredLevel;
        this.damage = damage;
    }

    public String getName(WeaponGearType gearType) {
        return name + " " + gearType.getDisplayName();
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public int getElementDamage(WeaponGearType gearType) {
        return (int) (damage * gearType.getDamageReduction() + 0.5);
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }
}
