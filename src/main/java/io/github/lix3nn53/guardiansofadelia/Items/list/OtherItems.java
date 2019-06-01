package io.github.lix3nn53.guardiansofadelia.Items.list;

import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
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
        itemMeta.setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);
        PersistentDataContainerUtil.putInteger("durability", durability, itemStack);
        return itemStack;
    }

    public static ItemStack getArrow(int amount) {
        ItemStack itemStack = new ItemStack(Material.ARROW, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.YELLOW + "Arrow");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Ammunition for bows & crossbows");
        }});
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getSaddle() {
        ItemStack itemStack = new ItemStack(Material.SADDLE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.YELLOW + "Saddle");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Required to control mounts.");
        }});
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getPetFood(int tier) {
        ItemStack itemStack = new ItemStack(Material.LAPIS_LAZULI);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Pet Food Tier " + tier);
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Right click on your pet while holding");
            add(ChatColor.GRAY + "this item to feed and heal");
            if (tier == 1) {
                add(ChatColor.GRAY + "Restores 100 health");
            } else if (tier == 2) {
                add(ChatColor.GRAY + "Restores 500 health");
            } else if (tier == 3) {
                add(ChatColor.GRAY + "Restores 1000 health");
            } else if (tier == 4) {
                add(ChatColor.GRAY + "Restores 2000 health");
            }
        }});
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
