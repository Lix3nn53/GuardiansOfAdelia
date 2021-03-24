package io.github.lix3nn53.guardiansofadelia.Items.list.armors;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.Items.stats.StatUtils;
import org.bukkit.Material;

public class ArmorSet {
    private final String name;
    private final int baseRequiredLevel;

    public ArmorSet(String name, int baseRequiredLevel) {
        this.name = name;
        this.baseRequiredLevel = baseRequiredLevel;
    }

    public String getName(ArmorSlot armorSlot) {
        return name + " " + armorSlot.getDisplayName();
    }

    public int getReqLevel(ArmorSlot armorSlot) {
        return baseRequiredLevel + armorSlot.getReqLevelAddition();
    }

    public int getHealth(ArmorSlot armorSlot, ArmorGearType gearType) {
        int health = StatUtils.getHealthItem(baseRequiredLevel);
        return (int) (health * armorSlot.getAttributeReduction() * gearType.getHealthReduction() + 0.5);
    }

    public int getDefense(ArmorSlot armorSlot, ArmorGearType gearType) {
        int defense = StatUtils.getDefenseItem(baseRequiredLevel);
        return (int) (defense * armorSlot.getAttributeReduction() * gearType.getElementDefenseReduction() + 0.5);
    }

    public Material getMaterial(ArmorSlot armorSlot, ArmorGearType gearType) {
        return gearType.getArmorMaterial().getMaterial(armorSlot);
    }

    public int getBaseRequiredLevel() {
        return baseRequiredLevel;
    }
}
