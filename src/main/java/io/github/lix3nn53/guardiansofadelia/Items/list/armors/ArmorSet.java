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

    public String getName(ArmorSlot armorSlot) {
        return name + " " + armorSlot.getDisplayName();
    }

    public int getReqLevel(ArmorSlot armorSlot) {
        return baseReqLevel + armorSlot.getReqLevelAddition();
    }

    public int getHealth(ArmorSlot armorSlot, ArmorGearType gearType) {
        return (int) (health * armorSlot.getAttributeReduction() * gearType.getHealthReduction() + 0.5);
    }

    public int getDefense(ArmorSlot armorSlot, ArmorGearType gearType) {
        return (int) (defense * armorSlot.getAttributeReduction() * gearType.getPhysicalDefenseReduction() + 0.5);
    }

    public int getMagicDefense(ArmorSlot armorSlot, ArmorGearType gearType) {
        return (int) (magicDefense * armorSlot.getAttributeReduction() * gearType.getMagicDefenseReduction() + 0.5);
    }

    public Material getMaterial(ArmorSlot armorSlot, ArmorGearType gearType) {
        return gearType.getArmorMaterial().getMaterial(armorSlot);
    }

    public int getBaseReqLevel() {
        return baseReqLevel;
    }
}
