package io.github.lix3nn53.guardiansofadelia.Items.enchanting;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public enum EnchantStone {
    TIER_ONE,
    TIER_TWO,
    TIER_THREE,
    TIER_FOUR;

    public Material getType() {
        if (this.equals(EnchantStone.TIER_TWO)) {
            return Material.BRICK;
        } else if (this.equals(EnchantStone.TIER_THREE)) {
            return Material.CLAY_BALL;
        } else if (this.equals(EnchantStone.TIER_FOUR)) {
            return Material.GOLD_NUGGET;
        }
        return Material.FLINT;
    }

    public ItemStack getItemSTack(int amount) {
        if (this.equals(EnchantStone.TIER_TWO)) {
            ItemStack item = new ItemStack(Material.BRICK, amount);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(ChatColor.BLUE + "Enchant Stone Tier 2");
            itemMeta.setLore(new ArrayList() {{
                add(ChatColor.YELLOW + "----------------");
                add(ChatColor.GRAY + "Magical stone to enchant items");
                add(ChatColor.GRAY + "For +3, +4 ve +5 items");

            }});
            item.setItemMeta(itemMeta);
            return item;
        } else if (this.equals(EnchantStone.TIER_THREE)) {
            ItemStack item = new ItemStack(Material.CLAY_BALL, amount);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Enchant Stone Tier 3");
            itemMeta.setLore(new ArrayList() {{
                add(ChatColor.YELLOW + "----------------");
                add(ChatColor.GRAY + "Magical stone to enchant items");
                add(ChatColor.GRAY + "For +6, +7 ve +8 items");

            }});
            item.setItemMeta(itemMeta);
            return item;
        } else if (this.equals(EnchantStone.TIER_FOUR)) {
            ItemStack item = new ItemStack(Material.GOLD_NUGGET, amount);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(ChatColor.RED + "Enchant Stone Tier 4");
            itemMeta.setLore(new ArrayList() {{
                add(ChatColor.YELLOW + "----------------");
                add(ChatColor.GRAY + "Magical stone to enchant items");
                add(ChatColor.GRAY + "For +9, +10 ve +11 items");

            }});
            item.setItemMeta(itemMeta);
            return item;
        }
        ItemStack item = new ItemStack(Material.FLINT, amount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + "Enchant Stone Tier 1");
        itemMeta.setLore(new ArrayList() {{
            add(ChatColor.YELLOW + "----------------");
            add(ChatColor.GRAY + "Magical stone to enchant items");
            add(ChatColor.GRAY + "For +0, +1 ve +2 items");

        }});
        item.setItemMeta(itemMeta);
        return item;
    }
}
