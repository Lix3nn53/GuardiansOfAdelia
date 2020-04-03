package io.github.lix3nn53.guardiansofadelia.Items.list.armors;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ArmorGearType;
import org.bukkit.Material;

public class ArmorSet {
    private final String name;
    private final int baseReqLevel;
    private final int health;
    private final int defense;
    private final int magicDefense;

    public ArmorSet(String name, int baseReqLevel, int health, int defense, int magicDefense) {
        this.name = name;
        this.baseReqLevel = baseReqLevel;
        this.health = health;
        this.defense = defense;
        this.magicDefense = magicDefense;
    }

    public String getName(ArmorType armorType) {
        return name + " " + armorType.getDisplayName();
    }

    public int getReqLevel(ArmorType armorType) {
        return baseReqLevel + armorType.getReqLevelAddition();
    }

    public int getHealth(ArmorType armorType, ArmorGearType gearType) {
        return (int) (health * armorType.getAttributeReduction() * gearType.getHealthReduction() + 0.5);
    }

    public int getDefense(ArmorType armorType, ArmorGearType gearType) {
        return (int) (defense * armorType.getAttributeReduction() * gearType.getPhysicalDefenseReduction() + 0.5);
    }

    public int getMagicDefense(ArmorType armorType, ArmorGearType gearType) {
        return (int) (magicDefense * armorType.getAttributeReduction() * gearType.getMagicDefenseReduction() + 0.5);
    }

    public Material getMaterial(ArmorType armorType, ArmorGearType gearType) {
        return gearType.getArmorMaterial().getMaterial(armorType);
    }

    public int getBaseReqLevel() {
        return baseReqLevel;
    }
}
