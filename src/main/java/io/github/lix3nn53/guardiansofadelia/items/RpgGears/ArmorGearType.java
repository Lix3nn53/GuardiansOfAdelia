package io.github.lix3nn53.guardiansofadelia.items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.items.RpgGears.gearset.GearSetEffect;
import io.github.lix3nn53.guardiansofadelia.items.list.armors.ArmorMaterial;
import io.github.lix3nn53.guardiansofadelia.items.list.armors.ArmorSlot;
import org.bukkit.Material;

public enum ArmorGearType {
    HEAVY_ARMOR, // NETHERITE // Tank // knockback resistance // 1
    PLATE_ARMOR, // DIAMOND // Warrior // critical damage // 0.85
    LIGHT_ARMOR, // IRON // Archer // critical chance // 0.7
    FEATHER_ARMOR,  // CHAINMAIL // Assassin // movement speed // 0.6
    CLOTH_ARMOR; // GOLD // Mage // ability haste // 0.6

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
                return "Heavy Armor";
            case PLATE_ARMOR:
                return "Plate Armor";
            case CLOTH_ARMOR:
                return "Cloth Armor";
            case LIGHT_ARMOR:
                return "Light Armor";
            case FEATHER_ARMOR:
                return "Feather Armor";
        }

        return "";
    }

    public float getHealthReduction() {
        switch (this) {
            case HEAVY_ARMOR:
                return 1;
            case PLATE_ARMOR:
                return 0.85f;
            case LIGHT_ARMOR:
            case FEATHER_ARMOR:
            case CLOTH_ARMOR:
                return 0.7f;
        }

        return 0;
    }

    public float getElementDefenseReduction() {
        switch (this) {
            case HEAVY_ARMOR:
                return 1;
            case PLATE_ARMOR:
                return 0.85f;
            case LIGHT_ARMOR:
            case FEATHER_ARMOR:
            case CLOTH_ARMOR:
                return 0.7f;
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
                return GearSetEffect.CRITICAL_CHANCE;
            case FEATHER_ARMOR:
                return GearSetEffect.MOVEMENT_SPEED;
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
