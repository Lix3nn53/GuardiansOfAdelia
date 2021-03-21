package io.github.lix3nn53.guardiansofadelia.rpginventory.slots;

import io.github.lix3nn53.guardiansofadelia.Items.stats.StatPassive;
import io.github.lix3nn53.guardiansofadelia.Items.stats.StatUtils;
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
            StatPassive stat = (StatPassive) StatUtils.getStat(this.itemOnSlot);
            return stat;
        }
        return new StatPassive(new HashMap<>());
    }

    public ItemStack getItemOnSlot() {
        return itemOnSlot;
    }

    public void setItemOnSlot(ItemStack itemOnSlot) {
        this.itemOnSlot = itemOnSlot;
    }
}
