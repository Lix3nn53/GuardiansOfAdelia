package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public enum ShieldGearType {
    BUCKLER,
    SHIELD;

    public List<RPGClass> getRequiredClasses() {
        List<RPGClass> classList = new ArrayList<>();

        switch (this) {
            case BUCKLER:
                classList.add(RPGClass.KNIGHT);
                classList.add(RPGClass.PALADIN);
                classList.add(RPGClass.MONK);
                classList.add(RPGClass.WARRIOR);
                break;
            case SHIELD:
                classList.add(RPGClass.KNIGHT);
                classList.add(RPGClass.PALADIN);
                break;
        }

        return null;
    }

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
