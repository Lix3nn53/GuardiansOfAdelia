package io.github.lix3nn53.guardiansofadelia.rpginventory.slots;

import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class EggSlot {

    private final String requiredNbtTag = "petCode";
    private ItemStack itemOnSlot;

    public boolean doesFit(ItemStack itemStack) {
        return PersistentDataContainerUtil.hasString(itemStack, requiredNbtTag);
    }

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

    public ItemStack getFillItem() {
        ItemStack itemStack = new ItemStack(Material.IRON_AXE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(ChatColor.YELLOW + "Egg Slot");
        itemMeta.setLore(new ArrayList() {{
            add("");
        }});
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setCustomModelData(10000012);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
