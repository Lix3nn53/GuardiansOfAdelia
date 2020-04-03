package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

import org.bukkit.inventory.ItemStack;

public interface RPGGear {

    ItemStack getItemStack();

    RPGGearType getGearType();

    ItemTier getTier();

    String getItemTag();

    int getLevel();
}
