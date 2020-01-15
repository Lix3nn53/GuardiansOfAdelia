package io.github.lix3nn53.guardiansofadelia.bungeelistener.products;

import io.github.lix3nn53.guardiansofadelia.bungeelistener.RequestHandler;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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
        lore.add(ChatColor.GRAY + "1 - Right click while holding to open!");

        ItemStack itemStack = new ItemStack(Material.BLACK_DYE);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(itemName);
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setCustomModelData(4);
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(amount);

        PersistentDataContainerUtil.putInteger("skinChest", 1, itemStack);

        return itemStack;
    }

    public void play(Player player) {
        List<ItemStack> skinChestItemPool = RequestHandler.getSkinChestItemPool();

        InventoryUtils.play(player, skinChestItemPool, "Premium Skin");
    }
}
