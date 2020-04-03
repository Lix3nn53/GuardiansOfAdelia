package io.github.lix3nn53.guardiansofadelia.Items.list.armors;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.RPGGearType;

public class ShieldItemTemplate {
    private final String name;
    private final int level;
    private final int health;
    private final int defense;
    private final int magicDefense;
    private final int customModelData;
    private final RPGGearType gearType;

    public ShieldItemTemplate(String name, int level, int health, int defense, int magicDefense, int customModelData, RPGGearType gearType) {
        this.name = name;
        this.level = level;
        this.health = health;
        this.defense = defense;
        this.magicDefense = magicDefense;
        this.customModelData = customModelData;
        this.gearType = gearType;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    public int getDefense() {
        return defense;
    }

    public int getMagicDefense() {
        return magicDefense;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public RPGGearType getGearType() {
        return gearType;
    }
}
