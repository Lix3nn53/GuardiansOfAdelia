package io.github.lix3nn53.guardiansofadelia.Items.list.shields;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ShieldGearType;
import org.bukkit.Material;

public class ShieldSet {
    private final String name;
    private final int reqLevel;
    private final int health;
    private final int defense;
    private final int magicDefense;
    private final int customModelData;

    public ShieldSet(String name, int reqLevel, int health, int defense, int magicDefense, int customModelData) {
        this.name = name;
        this.reqLevel = reqLevel;
        this.health = health;
        this.defense = defense;
        this.magicDefense = magicDefense;
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

    public int getMagicDefense(ShieldGearType shieldType) {
        return (int) (magicDefense * shieldType.getMagicDefenseReduction() + 0.5);
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
