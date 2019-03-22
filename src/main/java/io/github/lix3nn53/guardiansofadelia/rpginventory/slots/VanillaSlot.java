package io.github.lix3nn53.guardiansofadelia.rpginventory.slots;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface VanillaSlot {

    void setItemOnSlot(Player player, ItemStack itemOnSlot);

    boolean isEmpty(Player player);

    void setEmpty(Player player);

    ItemStack getItemOnSlot(Player player);

    boolean doesFit(ItemStack itemStack);
}
