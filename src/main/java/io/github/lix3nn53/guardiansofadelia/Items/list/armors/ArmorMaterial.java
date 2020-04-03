package io.github.lix3nn53.guardiansofadelia.Items.list.armors;

import org.bukkit.Material;

public enum ArmorMaterial {
    LEATHER,
    IRON,
    GOLDEN,
    DIAMOND,
    CHAINMAIL;

    public Material getMaterial(ArmorType armorType) {
        return Material.valueOf(this.name() + "_" + armorType.name());
    }
}
