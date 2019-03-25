package io.github.lix3nn53.guardiansofadelia.Items.list;

import io.github.lix3nn53.guardiansofadelia.utilities.NBTTagUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class OtherItems {

    public static ItemStack getBoat() {
        ItemStack item = new ItemStack(Material.OAK_BOAT);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ChatColor.YELLOW + "Boat");
        im.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "");
            add("");
            add(ChatColor.GREEN + "✔ ");
            add(ChatColor.GREEN + "✔ ");

        }});
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack getFishingRod(int durability) {
        ItemStack itemStack = new ItemStack(Material.FISHING_ROD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + "Fishing Rod");
        itemMeta.setLore(new ArrayList() {{
            add(ChatColor.YELLOW + "Durability: " + durability);
            add("");
            add(ChatColor.GRAY + "Material collection tool");
        }});
        itemMeta.addEnchant(Enchantment.LURE, 3, false);
        itemStack.setItemMeta(itemMeta);
        itemStack = NBTTagUtils.putInteger("durability", durability, itemStack);
        return itemStack;
    }
}
