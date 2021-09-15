package io.github.lix3nn53.guardiansofadelia.rpginventory.slots;

import io.github.lix3nn53.guardiansofadelia.items.stats.StatPassive;
import io.github.lix3nn53.guardiansofadelia.items.stats.StatUtils;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class RPGSlotPassive implements RPGSlot {

    private ItemStack itemOnSlot = null;

    public boolean isEmpty() {
        return itemOnSlot == null;
    }

    public void clearItemOnSlot() {
        this.itemOnSlot = null;
    }

    public StatPassive getBonusStats() {
        if (itemOnSlot != null) {
            return (StatPassive) StatUtils.getStat(this.itemOnSlot);
        }
        return new StatPassive(new HashMap<>(), new HashMap<>());
    }

    public ItemStack getItemOnSlot() {
        return itemOnSlot;
    }

    public void setItemOnSlot(ItemStack itemOnSlot) {
        this.itemOnSlot = itemOnSlot;
    }
}
