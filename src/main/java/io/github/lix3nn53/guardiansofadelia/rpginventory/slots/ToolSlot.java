package io.github.lix3nn53.guardiansofadelia.rpginventory.slots;


import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringToolType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class ToolSlot implements IToolSlot {

    private ItemStack itemOnSlot = null;

    @Override
    public boolean isEmpty() {
        return itemOnSlot == null;
    }

    public void clearItemOnSlot() {
        this.itemOnSlot = null;
    }

    public ItemStack getItemOnSlot() {
        return itemOnSlot;
    }

    public void setItemOnSlot(ItemStack itemOnSlot) {
        this.itemOnSlot = itemOnSlot;
    }

    @Override
    public boolean doesFit(ItemStack itemStack) {
        Material mat = itemStack.getType();

        return GatheringToolType.toolTypeToMaterials(getToolType()).contains(mat);
    }

    public abstract GatheringToolType getToolType();

    public abstract ItemStack getFillItem();
}
