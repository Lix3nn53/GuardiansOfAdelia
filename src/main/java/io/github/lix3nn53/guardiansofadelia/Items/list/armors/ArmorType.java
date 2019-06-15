package io.github.lix3nn53.guardiansofadelia.Items.list.armors;

import org.bukkit.Material;

public enum ArmorType {
    HELMET,
    CHESTPLATE,
    LEGGINGS,
    BOOTS;

    public static ArmorType getArmorType(Material material) {
        if (material.equals(Material.AIR)) return null;
        String type = material.name();
        if (type.endsWith("_HELMET") || type.endsWith("_SKULL")) return HELMET;
        else if (type.endsWith("_CHESTPLATE")) return CHESTPLATE;
        else if (type.endsWith("_LEGGINGS")) return LEGGINGS;
        else if (type.endsWith("_BOOTS")) return BOOTS;
        return null;
    }

    public int getSlot() {
        switch (this) {
            case CHESTPLATE:
                return 6;
            case LEGGINGS:
                return 7;
            case BOOTS:
                return 8;
        }
        return 5;
    }
}
