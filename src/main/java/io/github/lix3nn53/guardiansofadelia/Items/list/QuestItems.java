package io.github.lix3nn53.guardiansofadelia.Items.list;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class QuestItems {

    public static ItemStack getQuestItem(int questNo) {
        if (questNo == 14) {
            ItemStack item = new ItemStack(Material.ROTTEN_FLESH);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(ChatColor.DARK_GREEN + "Rotten Flesh");
            im.setLore(new ArrayList() {{
                add("");
                add(ChatColor.GRAY + "Quest item for #14");
            }});
            item.setItemMeta(im);
            return item;
        } else if (questNo == 15) {
            ItemStack item = new ItemStack(Material.ROTTEN_FLESH);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(ChatColor.LIGHT_PURPLE + "Zombie Brain");
            im.setLore(new ArrayList() {{
                add("");
                add(ChatColor.GRAY + "Quest item for #15");
            }});
            item.setItemMeta(im);
            return item;
        } else if (questNo == 23) {
            ItemStack item = new ItemStack(Material.ROTTEN_FLESH);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(ChatColor.GRAY + "Magical Bone");
            im.setLore(new ArrayList() {{
                add("");
                add(ChatColor.GRAY + "Quest item for #23");
            }});
            item.setItemMeta(im);
            return item;
        } else if (questNo == 31) {
            ItemStack item = new ItemStack(Material.PINK_DYE);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(ChatColor.LIGHT_PURPLE + "Bad tasting sugar");
            im.setLore(new ArrayList() {{
                add("");
                add(ChatColor.GRAY + "Quest item for #31");
            }});
            item.setItemMeta(im);
            return item;
        } else if (questNo == 39) {
            ItemStack item = new ItemStack(Material.ROTTEN_FLESH);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(ChatColor.GRAY + "Pirate Hat");
            im.setLore(new ArrayList() {{
                add("");
                add(ChatColor.GRAY + "Quest item for #39");
            }});
            item.setItemMeta(im);
            return item;
        } else if (questNo == 47) {
            ItemStack item = new ItemStack(Material.ROTTEN_FLESH);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(ChatColor.GRAY + "Frozen Shard");
            im.setLore(new ArrayList() {{
                add("");
                add(ChatColor.GRAY + "Quest item for #47");
            }});
            item.setItemMeta(im);
            return item;
        } else if (questNo == 56) {
            ItemStack item = new ItemStack(Material.ROTTEN_FLESH);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(ChatColor.GRAY + "Mummy Bandage");
            im.setLore(new ArrayList() {{
                add("");
                add(ChatColor.GRAY + "Quest item for #56");
            }});
            item.setItemMeta(im);
            return item;
        } else if (questNo == 64) {
            ItemStack item = new ItemStack(Material.ROTTEN_FLESH);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(ChatColor.GRAY + "Goblin Flag");
            im.setLore(new ArrayList() {{
                add("");
                add(ChatColor.GRAY + "Quest item for #64");
            }});
            item.setItemMeta(im);
            return item;
        } else if (questNo == 76) {
            ItemStack item = new ItemStack(Material.ROTTEN_FLESH);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(ChatColor.GRAY + "Orc Flag");
            im.setLore(new ArrayList() {{
                add("");
                add(ChatColor.GRAY + "Quest item for #76");
            }});
            item.setItemMeta(im);
            return item;
        } else if (questNo == 85) {
            ItemStack item = new ItemStack(Material.ROTTEN_FLESH);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(ChatColor.GRAY + "Dark Soul");
            im.setLore(new ArrayList() {{
                add("");
                add(ChatColor.GRAY + "Quest item for #85");
            }});
            item.setItemMeta(im);
            return item;
        }

        return null;
    }
}
