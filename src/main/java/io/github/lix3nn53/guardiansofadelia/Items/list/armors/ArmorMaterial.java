package io.github.lix3nn53.guardiansofadelia.Items.list.armors;

import org.bukkit.Material;

public enum ArmorMaterial {
    NETHERITE,
    DIAMOND,
    IRON,
    CHAINMAIL,
    GOLDEN,
    LEATHER;

    public Material getMaterial(ArmorSlot armorSlot) {
        return Material.valueOf(this.name() + "_" + armorSlot.name());
    }
}
