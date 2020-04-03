package io.github.lix3nn53.guardiansofadelia.Items.list.weapons;

public class WeaponItemTemplate {

    private final String name;
    private final int customModelData;
    private final int requiredLevel;
    private final int damage;

    public WeaponItemTemplate(String name, int customModelData, int requiredLevel, int damage) {
        this.name = name;
        this.customModelData = customModelData;
        this.requiredLevel = requiredLevel;
        this.damage = damage;
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
}
