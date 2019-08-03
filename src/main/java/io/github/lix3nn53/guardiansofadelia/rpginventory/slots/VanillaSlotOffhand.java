package io.github.lix3nn53.guardiansofadelia.rpginventory.slots;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class VanillaSlotOffhand implements VanillaSlot {

    @Override
    public boolean doesFit(ItemStack itemStack) {
        Material mat = itemStack.getType();
        if (!(mat.equals(Material.IRON_SWORD) || mat.equals(Material.DIAMOND_HOE))) {
            return false;
        }
        return false;
    }

    @Override
    public void setItemOnSlot(Player player, ItemStack itemOnSlot) {
        player.getInventory().setItemInOffHand(itemOnSlot);
    }

    @Override
    public boolean isEmpty(Player player) {
        return player.getInventory().getItemInOffHand() == null;
    }

    @Override
    public void setEmpty(Player player) {
        player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
    }

    @Override
    public ItemStack getItemOnSlot(Player player) {
        return player.getInventory().getItemInOffHand();
    }

    public ItemStack getFillItem() {
        ItemStack itemStack = new ItemStack(Material.IRON_AXE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(ChatColor.YELLOW + "Left Hand Slot");
        itemMeta.setLore(new ArrayList() {{
            add("Only shields and daggers can be placed here");
        }});
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setCustomModelData(10000008);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
