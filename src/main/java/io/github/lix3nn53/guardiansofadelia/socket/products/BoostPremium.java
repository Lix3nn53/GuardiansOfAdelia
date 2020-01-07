package io.github.lix3nn53.guardiansofadelia.socket.products;

import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public enum BoostPremium {
    EXPERIENCE,
    LOOT,
    ENCHANT,
    GATHER;

    public ItemStack getItemStack() {
        String s = this.name().toLowerCase();
        String itemName = ChatColor.LIGHT_PURPLE + s + " Boost";

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Boost");
        lore.add("");
        lore.add(ChatColor.GOLD + "Usage: ");
        lore.add(ChatColor.YELLOW + "1 - Right click while you are holding this item.");

        ItemStack itemStack = new ItemStack(Material.BLACK_DYE);
        PersistentDataContainerUtil.putString("boostCode", this.toString(), itemStack);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(itemName);
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setCustomModelData(3);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
