package io.github.lix3nn53.guardiansofadelia.rpginventory.slots;

import org.bukkit.inventory.ItemStack;

public interface RPGSlot {

    boolean isEmpty();

    void clearItemOnSlot();

    ItemStack getItemOnSlot();
}
