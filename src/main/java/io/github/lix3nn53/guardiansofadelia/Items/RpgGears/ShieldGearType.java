package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

import org.bukkit.Material;

public enum ShieldGearType {
    BUCKLER,
    SHIELD;

    public Material getMaterial() {
        switch (this) {
            case BUCKLER:
                return Material.DIAMOND_PICKAXE;
            case SHIELD:
                return Material.SHIELD;
        }

        return null;
    }

    public String getDisplayName() {
        switch (this) {
            case BUCKLER:
                return "Buckler";
            case SHIELD:
                return "Shield";
        }

        return "";
    }

    public double getHealthReduction() {
        switch (this) {
            case SHIELD:
                return 1;
            case BUCKLER:
                return 0.6;
        }

        return 0;
    }

    public double getPhysicalDefenseReduction() {
        switch (this) {
            case SHIELD:
                return 1;
            case BUCKLER:
                return 0.6;
        }

        return 0;
    }

    public double getMagicDefenseReduction() {
        switch (this) {
            case SHIELD:
                return 1;
            case BUCKLER:
                return 0.6;
        }

        return 0;
    }
}
