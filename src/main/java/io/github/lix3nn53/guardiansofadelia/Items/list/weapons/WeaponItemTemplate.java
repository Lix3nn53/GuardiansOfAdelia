package io.github.lix3nn53.guardiansofadelia.Items.list.weapons;

public class WeaponItemTemplate {


    private final String name;
    private final int customModelData;
    private final int level;
    private final int damage;

    public WeaponItemTemplate(String name, int customModelData, int level, int damage) {
        this.name = name;
        this.customModelData = customModelData;
        this.level = level;
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public int getLevel() {
        return level;
    }

    public int getDamage() {
        return damage;
    }
}
