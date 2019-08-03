package io.github.lix3nn53.guardiansofadelia.rpginventory.slots;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class VanillaSlotMainhand {

    public boolean isEmpty(Player player) {
        return player.getInventory().getItemInMainHand() == null;
    }

    public ItemStack getItemOnSlot(Player player) {
        return player.getInventory().getItemInMainHand();
    }

    public ItemStack getFillItem() {
        ItemStack itemStack = new ItemStack(Material.IRON_AXE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(ChatColor.YELLOW + "Right Hand Slot");
        itemMeta.setLore(new ArrayList() {{
            add("");
        }});
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setCustomModelData(10000013);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
