package io.github.lix3nn53.guardiansofadelia.socket.products;

import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public enum HelmetSkin {
    WINGS_ANGEL,
    WINGS_DEMON,
    WINGS_DRAGON_DARK,
    WINGS_DRAGON_WHITE,
    CROWN;

    public ItemStack getItemStack() {
        String s = this.name().toLowerCase().replaceAll("_", "");
        String itemName = ChatColor.LIGHT_PURPLE + s + " Helmet Skin";

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Premium");
        lore.add("");
        lore.add(ChatColor.GOLD + "Usage: ");
        lore.add(ChatColor.YELLOW + "1 - Right click while you are holding this item.");
        lore.add(ChatColor.YELLOW + "2 - Place your helmet to empty slot.");
        lore.add(ChatColor.YELLOW + "3 - Click green wool to confirm and apply skin.");

        ItemStack itemStack = new ItemStack(Material.BLACK_DYE);
        PersistentDataContainerUtil.putString("helmetSkinCode", this.toString(), itemStack);

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
