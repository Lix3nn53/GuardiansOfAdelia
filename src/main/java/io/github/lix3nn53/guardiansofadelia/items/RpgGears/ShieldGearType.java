package io.github.lix3nn53.guardiansofadelia.items.RpgGears;

import org.bukkit.Material;

public enum ShieldGearType {
    SHIELD;

    public static ShieldGearType fromMaterial(Material material) {
        if (material.equals(Material.SHIELD)) return SHIELD;

        return null;
    }

    public Material getMaterial() {
        return Material.SHIELD;
    }

    public String getDisplayName() {
        return "Shield";
    }

    public double getHealthReduction() {
        return 0.7;
    }

    public double getElementDefenseReduction() {
        return 0.7;
    }
}
