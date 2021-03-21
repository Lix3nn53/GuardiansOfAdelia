package io.github.lix3nn53.guardiansofadelia.Items.list.armors;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ArmorGearType;
import org.bukkit.Material;

public class ArmorSet {
    private final String name;
    private final int baseReqLevel;
    private final int health;
    private final int defense;

    public ArmorSet(String name, int baseReqLevel, int health, int defense) {
        this.name = name;
        this.baseReqLevel = baseReqLevel;
        this.health = health;
        this.defense = defense;
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
        return (int) (defense * armorSlot.getAttributeReduction() * gearType.getElementDefenseReduction() + 0.5);
    }

    public Material getMaterial(ArmorSlot armorSlot, ArmorGearType gearType) {
        return gearType.getArmorMaterial().getMaterial(armorSlot);
    }

    public int getBaseReqLevel() {
        return baseReqLevel;
    }
}
