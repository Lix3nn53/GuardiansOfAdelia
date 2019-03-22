package io.github.lix3nn53.guardiansofadelia.rpginventory.slots;

import io.github.lix3nn53.guardiansofadelia.Items.stats.StatPassive;
import io.github.lix3nn53.guardiansofadelia.Items.stats.StatUtils;
import org.bukkit.inventory.ItemStack;

public class RPGSlotPassive implements RPGSlot {

    private ItemStack itemOnSlot = null;

    public boolean isEmpty() {
        return itemOnSlot == null;
    }

    public void clearItemOnSlot() {
        this.itemOnSlot = null;
    }

    public StatPassive getBonusStats() {
        StatPassive stat = (StatPassive) StatUtils.getStat(this.itemOnSlot);
        return stat;
    }

    public ItemStack getItemOnSlot() {
        return itemOnSlot;
    }

    public void setItemOnSlot(ItemStack itemOnSlot) {
        this.itemOnSlot = itemOnSlot;
    }
}
