package io.github.lix3nn53.guardiansofadelia.bungeelistener.products;

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
        String input = this.name();
        String s = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
        String itemName = getChatColor() + s + " Boost";

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
        itemMeta.setCustomModelData(getCustomModelData());
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public ChatColor getChatColor() {
        switch (this) {
            case EXPERIENCE:
                return ChatColor.LIGHT_PURPLE;
            case LOOT:
                return ChatColor.YELLOW;
            case ENCHANT:
                return ChatColor.AQUA;
            case GATHER:
                return ChatColor.GREEN;
        }

        return ChatColor.GRAY;
    }

    public int getCustomModelData() {
        switch (this) {
            case EXPERIENCE:
                return 5;
            case LOOT:
                return 6;
            case ENCHANT:
                return 7;
            case GATHER:
                return 8;
        }

        return 0;
    }
}
