package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorMaterial;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ArmorGearType {
    LIGHT_ARMOR,
    MEDIUM_ARMOR,
    HEAVY_ARMOR;

    public List<RPGClass> getRequiredClasses() {
        List<RPGClass> classList = new ArrayList<>();

        switch (this) {
            case LIGHT_ARMOR:
                classList.add(RPGClass.ARCHER);
                classList.add(RPGClass.MAGE);
                classList.add(RPGClass.ROGUE);
                classList.add(RPGClass.HUNTER);
                break;
            case MEDIUM_ARMOR:
                classList.addAll(Arrays.asList(RPGClass.values()));
                break;
            case HEAVY_ARMOR:
                classList.add(RPGClass.KNIGHT);
                classList.add(RPGClass.PALADIN);
                classList.add(RPGClass.WARRIOR);
                classList.add(RPGClass.MONK);
                break;
        }

        return null;
    }

    public String getDisplayName() {
        switch (this) {
            case LIGHT_ARMOR:
                return "Light Armor";
            case MEDIUM_ARMOR:
                return "Medium Armor";
            case HEAVY_ARMOR:
                return "Heavy Armor";
        }

        return "";
    }

    public double getHealthReduction() {
        switch (this) {
            case HEAVY_ARMOR:
                return 1;
            case MEDIUM_ARMOR:
                return 0.6;
            case LIGHT_ARMOR:
                return 0.4;
        }

        return 0;
    }

    public double getPhysicalDefenseReduction() {
        switch (this) {
            case HEAVY_ARMOR:
                return 1;
            case MEDIUM_ARMOR:
                return 0.6;
            case LIGHT_ARMOR:
                return 0.4;
        }

        return 0;
    }

    public double getMagicDefenseReduction() {
        switch (this) {
            case HEAVY_ARMOR:
                return 1;
            case MEDIUM_ARMOR:
                return 0.6;
            case LIGHT_ARMOR:
                return 0.4;
        }

        return 0;
    }

    public ArmorMaterial getArmorMaterial() {
        switch (this) {
            case HEAVY_ARMOR:
                return ArmorMaterial.DIAMOND;
            case MEDIUM_ARMOR:
                return ArmorMaterial.IRON;
            case LIGHT_ARMOR:
                return ArmorMaterial.CHAINMAIL;
        }

        return null;
    }
}
