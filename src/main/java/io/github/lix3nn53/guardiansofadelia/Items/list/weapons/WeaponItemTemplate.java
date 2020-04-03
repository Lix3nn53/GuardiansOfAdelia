package io.github.lix3nn53.guardiansofadelia.Items.list.weapons;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.RPGGearType;

public class WeaponItemTemplate {

    private final String name;
    private final int customModelData;
    private final int requiredLevel;
    private final int damage;
    private final RPGGearType gearType;

    public WeaponItemTemplate(String name, int customModelData, int requiredLevel, int damage, RPGGearType gearType) {
        this.name = name;
        this.customModelData = customModelData;
        this.requiredLevel = requiredLevel;
        this.damage = damage;
        this.gearType = gearType;
    }

    public String getName() {
        return name;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public int getDamage() {
        return damage;
    }

    public RPGGearType getGearType() {
        return gearType;
    }
}
