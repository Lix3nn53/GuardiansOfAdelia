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
            ItemStack item = new ItemStack(Material.BLUE_DYE);
            ItemMeta im = item.getItemMeta();
            im.setCustomModelData(1);
            im.setDisplayName(ChatColor.LIGHT_PURPLE + "Zombie Brain");
            im.setLore(new ArrayList() {{
                add("");
                add(ChatColor.GRAY + "Quest item for #15");
            }});
            item.setItemMeta(im);
            return item;
        } else if (questNo == 23) {
            ItemStack item = new ItemStack(Material.BLUE_DYE);
            ItemMeta im = item.getItemMeta();
            im.setCustomModelData(2);
            im.setDisplayName(ChatColor.DARK_PURPLE + "Magical Bone");
            im.setLore(new ArrayList() {{
                add("");
                add(ChatColor.GRAY + "Quest item for #23");
            }});
            item.setItemMeta(im);
            return item;
        } else if (questNo == 31) {
            ItemStack item = new ItemStack(Material.PINK_DYE);
            ItemMeta im = item.getItemMeta();
            im.setCustomModelData(3);
            im.setDisplayName(ChatColor.GREEN + "Bad tasting sugar");
            im.setLore(new ArrayList() {{
                add("");
                add(ChatColor.GRAY + "Quest item for #31");
            }});
            item.setItemMeta(im);
            return item;
        } else if (questNo == 39) {
            ItemStack item = MonsterItem.PIRATE_HAT.getItem(0);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(ChatColor.DARK_GRAY + "Pirate Hat");
            im.setLore(new ArrayList() {{
                add("");
                add(ChatColor.GRAY + "Quest item for #39");
            }});
            item.setItemMeta(im);
            return item;
        } else if (questNo == 47) {
            ItemStack item = new ItemStack(Material.BLUE_DYE);
            ItemMeta im = item.getItemMeta();
            im.setCustomModelData(4);
            im.setDisplayName(ChatColor.AQUA + "Frozen Shard");
            im.setLore(new ArrayList() {{
                add("");
                add(ChatColor.GRAY + "Quest item for #47");
            }});
            item.setItemMeta(im);
            return item;
        } else if (questNo == 56) {
            ItemStack item = new ItemStack(Material.BLUE_DYE);
            ItemMeta im = item.getItemMeta();
            im.setCustomModelData(5);
            im.setDisplayName(ChatColor.YELLOW + "Mummy Bandage");
            im.setLore(new ArrayList() {{
                add("");
                add(ChatColor.GRAY + "Quest item for #56");
            }});
            item.setItemMeta(im);
            return item;
        } else if (questNo == 64) {
            ItemStack item = new ItemStack(Material.BLUE_DYE);
            ItemMeta im = item.getItemMeta();
            im.setCustomModelData(6);
            im.setDisplayName(ChatColor.GREEN + "Goblin Banner");
            im.setLore(new ArrayList() {{
                add("");
                add(ChatColor.GRAY + "Quest item for #64");
            }});
            item.setItemMeta(im);
            return item;
        } else if (questNo == 76) {
            ItemStack item = new ItemStack(Material.BLUE_DYE);
            ItemMeta im = item.getItemMeta();
            im.setCustomModelData(7);
            im.setDisplayName(ChatColor.RED + "Orc Banner");
            im.setLore(new ArrayList() {{
                add("");
                add(ChatColor.GRAY + "Quest item for #76");
            }});
            item.setItemMeta(im);
            return item;
        } else if (questNo == 85) {
            ItemStack item = new ItemStack(Material.BLUE_DYE);
            ItemMeta im = item.getItemMeta();
            im.setCustomModelData(8);
            im.setDisplayName(ChatColor.DARK_PURPLE + "Dark Soul");
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
