package io.github.lix3nn53.guardiansofadelia.Items.list;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class OtherItems {

    public static ItemStack getBoat() {
        ItemStack item = new ItemStack(Material.OAK_BOAT);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ChatColor.YELLOW + "Boat");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "TODO explanation");
        im.setLore(lore);
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack getArrow(int amount) {
        ItemStack itemStack = new ItemStack(Material.ARROW, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.YELLOW + "Arrow");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "Ammunition for bows & crossbows");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getSaddle() {
        ItemStack itemStack = new ItemStack(Material.SADDLE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.YELLOW + "Saddle");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "Required to control mounts.");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getPetFood(int tier) {
        ItemStack itemStack = new ItemStack(Material.LAPIS_LAZULI);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Pet Food Tier " + tier);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "Right click on your pet while holding");
        lore.add(ChatColor.GRAY + "this item to feed and heal");
        if (tier == 1) {
            lore.add(ChatColor.GRAY + "Restores 100 health");
        } else if (tier == 2) {
            lore.add(ChatColor.GRAY + "Restores 200 health");
        } else if (tier == 3) {
            lore.add(ChatColor.GRAY + "Restores 400 health");
        } else if (tier == 4) {
            lore.add(ChatColor.GRAY + "Restores 800 health");
        } else if (tier == 5) {
            lore.add(ChatColor.GRAY + "Restores 1200 health");
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getUnassignedSkill() {
        ItemStack itemStack = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.YELLOW + "Unassigned Skill");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "You haven't unlocked a skill for this slot yet");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
