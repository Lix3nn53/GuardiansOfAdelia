package io.github.lix3nn53.guardiansofadelia.Items.list.shields;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ShieldGearType;
import org.bukkit.Material;

public class ShieldSet {
    private final String name;
    private final int reqLevel;
    private final int health;
    private final int defense;
    private final int customModelData;

    public ShieldSet(String name, int reqLevel, int health, int defense, int customModelData) {
        this.name = name;
        this.reqLevel = reqLevel;
        this.health = health;
        this.defense = defense;
        this.customModelData = customModelData;
    }

    public String getName(ShieldGearType shieldType) {
        return name + " " + shieldType.getDisplayName();
    }

    public int getHealth(ShieldGearType shieldType) {
        return (int) (health * shieldType.getHealthReduction() + 0.5);
    }

    public int getDefense(ShieldGearType shieldType) {
        return (int) (defense * shieldType.getPhysicalDefenseReduction() + 0.5);
    }

    public Material getMaterial(ShieldGearType shieldType) {
        return shieldType.getMaterial();
    }

    public int getReqLevel() {
        return reqLevel;
    }

    public int getCustomModelData() {
        return customModelData;
    }
}
