package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.gearset.GearSetEffect;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorMaterial;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorSlot;
import org.bukkit.Material;

public enum ArmorGearType {
    HEAVY_ARMOR, // NETHERITE // Tank // knockback resistance
    PLATE_ARMOR, // DIAMOND // Warrior // critical damage
    CLOTH_ARMOR, // GOLD // Mage // ability haste
    LIGHT_ARMOR, // IRON // Archer // jump boost
    FEATHER_ARMOR;  // CHAINMAIL // Assassin // attack speed

    public static ArmorGearType fromMaterial(Material material) {
        for (ArmorGearType armorGearType : ArmorGearType.values()) {
            ArmorMaterial armorMaterial = armorGearType.getArmorMaterial();
            for (ArmorSlot armorSlot : ArmorSlot.values()) {
                Material materialCompare = armorMaterial.getMaterial(armorSlot);
                if (materialCompare.equals(material)) {
                    return armorGearType;
                }
            }
        }

        return null;
    }

    public String getDisplayName() {
        switch (this) {
            case HEAVY_ARMOR:
                return "Heavy ArmorType";
            case PLATE_ARMOR:
                return "Plate ArmorType";
            case CLOTH_ARMOR:
                return "Cloth ArmorType";
            case LIGHT_ARMOR:
                return "Light ArmorType";
            case FEATHER_ARMOR:
                return "Feather ArmorType";
        }

        return "";
    }

    public double getHealthReduction() {
        switch (this) {
            case HEAVY_ARMOR:
                return 1;
            case PLATE_ARMOR:
                return 0.7;
            case LIGHT_ARMOR:
            case FEATHER_ARMOR:
                return 0.5;
            case CLOTH_ARMOR:
                return 0.4;
        }

        return 0;
    }

    public double getElementDefenseReduction() {
        switch (this) {
            case HEAVY_ARMOR:
                return 1;
            case PLATE_ARMOR:
                return 0.7;
            case LIGHT_ARMOR:
            case FEATHER_ARMOR:
                return 0.5;
            case CLOTH_ARMOR:
                return 0.4;
        }

        return 0;
    }

    public ArmorMaterial getArmorMaterial() {
        switch (this) {
            case HEAVY_ARMOR:
                return ArmorMaterial.NETHERITE;
            case PLATE_ARMOR:
                return ArmorMaterial.DIAMOND;
            case CLOTH_ARMOR:
                return ArmorMaterial.GOLDEN;
            case LIGHT_ARMOR:
                return ArmorMaterial.IRON;
            case FEATHER_ARMOR:
                return ArmorMaterial.CHAINMAIL;
        }

        return null;
    }

    public GearSetEffect getSetEffect() {
        switch (this) {
            case HEAVY_ARMOR:
                return GearSetEffect.KNOCKBACK_RESISTANCE;
            case PLATE_ARMOR:
                return GearSetEffect.CRITICAL_DAMAGE;
            case CLOTH_ARMOR:
                return GearSetEffect.ABILITY_HASTE;
            case LIGHT_ARMOR:
                return GearSetEffect.JUMP_BOOST;
            case FEATHER_ARMOR:
                return GearSetEffect.ATTACK_SPEED_INCREASE;
        }

        return null;
    }

    public boolean isHeavy() {
        switch (this) {
            case HEAVY_ARMOR:
            case PLATE_ARMOR:
                return true;
            case CLOTH_ARMOR:
            case LIGHT_ARMOR:
            case FEATHER_ARMOR:
                return false;
        }

        return true;
    }
}
