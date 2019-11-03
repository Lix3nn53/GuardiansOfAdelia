package io.github.lix3nn53.guardiansofadelia.Items.list;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestItems {


    private static HashMap<Integer, ItemStack> questNoToItem = new HashMap<>();

    static {
        ItemStack item = new ItemStack(Material.ROTTEN_FLESH);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ChatColor.DARK_GREEN + "Rotten Flesh");
        im.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Quest item for #14");
        }});
        item.setItemMeta(im);
        questNoToItem.put(14, item);

        ItemStack item2 = new ItemStack(Material.ROTTEN_FLESH);
        im.setDisplayName(ChatColor.LIGHT_PURPLE + "Zombie Brain");
        im.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Quest item for #15");
        }});
        item2.setItemMeta(im);
        questNoToItem.put(15, item2);
    }

    public static ItemStack getQuestItem(int questNo) {
        return questNoToItem.get(questNo);
    }
}
