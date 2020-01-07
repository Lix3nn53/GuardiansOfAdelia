package io.github.lix3nn53.guardiansofadelia.socket.products;

import io.github.lix3nn53.guardiansofadelia.creatures.pets.Companion;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public enum PetSkin {
    ICE_CREAM,
    FOX_RED,
    FOX_SNOW,
    VEX,
    MINI_DRAGON,
    BEE;

    public ItemStack getItemStack() {
        String name = name();
        Companion companion = Companion.valueOf(name);
        String itemName = companion.getName() + ChatColor.LIGHT_PURPLE + " Pet Skin";

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Premium");
        lore.add("");
        lore.add(ChatColor.GOLD + "Usage: ");
        lore.add(ChatColor.YELLOW + "1 - Equip the pet you want to apply this skin to.");
        lore.add(ChatColor.YELLOW + "2 - Right click to your pet when you are holding this.");

        ItemStack itemStack = new ItemStack(Material.BLACK_DYE);
        PersistentDataContainerUtil.putString("petSkinCode", companion.toString(), itemStack);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(itemName);
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setCustomModelData(1);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
