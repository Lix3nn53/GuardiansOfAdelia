package io.github.lix3nn53.guardiansofadelia.items.list.shields;

import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ShieldGearType;
import io.github.lix3nn53.guardiansofadelia.items.stats.StatUtils;
import org.bukkit.Material;

public class ShieldSet {
    private final String name;
    private final int requiredLevel;
    private final int customModelData;

    public ShieldSet(String name, int requiredLevel, int customModelData) {
        this.name = name;
        this.requiredLevel = requiredLevel;
        this.customModelData = customModelData;
    }

    public String getName(ShieldGearType shieldType) {
        return name + " " + shieldType.getDisplayName();
    }

    public int getHealth(ShieldGearType shieldType) {
        int health = StatUtils.getHealthItem(requiredLevel);
        return (int) (health * shieldType.getHealthReduction() + 0.5);
    }

    public int getDefense(ShieldGearType shieldType) {
        int defense = StatUtils.getDefenseItem(requiredLevel);
        return (int) (defense * shieldType.getElementDefenseReduction() + 0.5);
    }

    public Material getMaterial(ShieldGearType shieldType) {
        return shieldType.getMaterial();
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public int getCustomModelData() {
        return customModelData;
    }
}
