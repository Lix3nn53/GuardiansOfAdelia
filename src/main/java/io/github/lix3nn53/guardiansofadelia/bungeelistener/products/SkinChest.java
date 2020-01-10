package io.github.lix3nn53.guardiansofadelia.bungeelistener.products;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SkinChest {

    public ItemStack getItemStack(int amount) {
        String itemName = ChatColor.GOLD + "Skin Chest";

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Premium");
        lore.add("");
        lore.add(ChatColor.GOLD + "Usage: ");
        lore.add(ChatColor.YELLOW + "1 - Right click while you are holding this item.");
        lore.add(ChatColor.YELLOW + "2 - Place your weapon/shield to empty slot.");
        lore.add(ChatColor.YELLOW + "3 - Click green wool to confirm and apply skin.");

        ItemStack itemStack = new ItemStack(Material.BLACK_DYE);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(itemName);
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setCustomModelData(4);
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(amount);

        return itemStack;
    }
}
