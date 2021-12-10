package io.github.lix3nn53.guardiansofadelia.bungeelistener.products;


import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WeaponOrShieldSkinScroll {

    public static ItemStack getItemStack(int amount) {
        String itemName = ChatPalette.GOLD + "Weapon/Shield Skin Scroll";

        List<String> lore = new ArrayList<>();
        lore.add(ChatPalette.GRAY + "Premium");
        lore.add("");
        lore.add(ChatPalette.GOLD + "Usage: ");
        lore.add(ChatPalette.YELLOW + "1 - Right click while you are holding this item.");
        lore.add(ChatPalette.YELLOW + "2 - Place your weapon/shield to empty slot.");
        lore.add(ChatPalette.YELLOW + "3 - Click green wool to confirm and apply skin.");

        ItemStack itemStack = new ItemStack(Material.BLACK_DYE);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(itemName);
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setCustomModelData(2);
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(amount);

        return itemStack;
    }
}
