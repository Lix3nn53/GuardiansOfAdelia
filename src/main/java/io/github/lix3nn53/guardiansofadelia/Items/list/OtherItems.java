package io.github.lix3nn53.guardiansofadelia.Items.list;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
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
}
