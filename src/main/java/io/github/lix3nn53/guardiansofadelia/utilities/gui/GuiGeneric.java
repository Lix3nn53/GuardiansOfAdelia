package io.github.lix3nn53.guardiansofadelia.utilities.gui;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GuiGeneric implements Gui {

    private final int resourceNpcNo;
    private final Inventory inventory;
    private final List<Integer> fillableSlots = new ArrayList<>();
    private final List<Integer> itemSlots = new ArrayList<>();
    private boolean ownInventoryLocked = true;
    private boolean isLocked = true;

    public GuiGeneric(int size, String title, int resourceNpcNo) {
        this.resourceNpcNo = resourceNpcNo;
        this.inventory = Bukkit.createInventory(null, size, title);
    }

    @Override
    public List<Integer> getFillableSlots() {
        return fillableSlots;
    }

    @Override
    public List<Integer> getItemSlots() {
        return itemSlots;
    }

    @Override
    public List<Integer> getEmptySlots() {
        List<Integer> emptySlots = new ArrayList<>();
        for (int i = 0; i < inventory.getSize(); i++) {
            if (!(fillableSlots.contains(i) || itemSlots.contains(i))) {
                emptySlots.add(i);
            }
        }
        return emptySlots;
    }

    public void setFillable(int index, ItemStack item) {
        fillableSlots.add(index);
        inventory.setItem(index, item);
    }

    public void setItem(int index, ItemStack item) {
        itemSlots.add(index);
        inventory.setItem(index, item);
    }

    public boolean isOwnInventoryLocked() {
        return ownInventoryLocked;
    }

    public void lockOwnInventory() {
        ownInventoryLocked = true;
    }

    public void unlockOwnInventory() {
        ownInventoryLocked = false;
    }

    @Override
    public void openInventory(Player player) {
        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            player.openInventory(inventory);
            guardianData.setActiveGui(this);
        }
    }

    public ItemStack[] getContents() {
        return this.inventory.getContents();
    }

    public void setContents(ItemStack[] itemStacks) {
        this.inventory.setContents(itemStacks);
    }

    public void removeItem(ItemStack itemStack, int amount) {
        InventoryUtils.removeItemFromInventory(this.inventory, itemStack, amount);
    }

    public void addItem(ItemStack itemStack) {
        this.inventory.addItem(itemStack);
    }

    public ItemStack getItem(int slot) {
        return this.inventory.getItem(slot);
    }

    public int getSize() {
        return this.inventory.getSize();
    }

    public boolean anyEmpty() {
        //inventory has empty slot
        return this.inventory.firstEmpty() != -1;
    }

    @Override
    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    @Override
    public int getResourceNPC() {
        return resourceNpcNo;
    }
}
