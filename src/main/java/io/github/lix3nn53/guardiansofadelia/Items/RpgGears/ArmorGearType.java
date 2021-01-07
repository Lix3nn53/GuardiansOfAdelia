package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.gearset.GearSetEffect;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorMaterial;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.inventory.ItemStack;

public enum ArmorGearType {
    HEAVY_ARMOR, // NETHERITE
    PLATE_ARMOR, // DIAMOND
    CLOTH_ARMOR, // GOLD
    LIGHT_ARMOR, // IRON
    FEATHER_ARMOR;  // CHAINMAIL

    public static ArmorGearType typeOf(ItemStack itemStack) {
        if (itemStack == null) return null;

        if (PersistentDataContainerUtil.hasString(itemStack, "gearType")) {
            String gearTypeStr = PersistentDataContainerUtil.getString(itemStack, "gearType");
            try {
                return ArmorGearType.valueOf(gearTypeStr);
            } catch (Exception e) {
                return null;
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

    public double getPhysicalDefenseReduction() {
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

    public double getMagicDefenseReduction() {
        switch (this) {
            case CLOTH_ARMOR:
                return 1;
            case LIGHT_ARMOR:
            case FEATHER_ARMOR:
                return 0.7;
            case PLATE_ARMOR:
                return 0.5;
            case HEAVY_ARMOR:
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
                return GearSetEffect.CRITICAL_CHANCE;
            case LIGHT_ARMOR:
                return GearSetEffect.MANA_REGEN;
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
