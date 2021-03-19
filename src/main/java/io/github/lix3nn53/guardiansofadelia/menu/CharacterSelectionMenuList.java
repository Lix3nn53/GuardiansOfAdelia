package io.github.lix3nn53.guardiansofadelia.menu;

import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClassManager;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CharacterSelectionMenuList {

    public static GuiGeneric getCharacterCreationMenu(int charNo) {
        GuiGeneric guiGeneric = new GuiGeneric(54, ChatColor.DARK_GRAY + "Character " + charNo + " Creation", 0);

        List<RPGClass> classesAtTier = RPGClassManager.getClassesAtTier(3);

        int index = 10;
        for (RPGClass rpgClass : classesAtTier) {
            ItemStack itemStack = new ItemStack(Material.WOODEN_PICKAXE);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

            int classIconCustomModelData = rpgClass.getClassIconCustomModelData();
            itemMeta.setCustomModelData(classIconCustomModelData);

            String classString = rpgClass.getClassString();
            itemMeta.setDisplayName(classString);

            List<String> description = rpgClass.getDescription();
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.addAll(description);
            itemMeta.setLore(lore);

            itemStack.setItemMeta(itemMeta);
            guiGeneric.setItem(index, itemStack);

            if (index == 16) {
                index += 12;
            } else if (index == 34) {
                index += 12;
            } else {
                index += 2;
            }
        }

        return guiGeneric;
    }

    public static GuiGeneric characterSelectionMenu(int charNo) {
        GuiGeneric guiGeneric = new GuiGeneric(36, ChatColor.DARK_GRAY + "Character " + charNo + " Selection", 0);

        ItemStack lastLocation = new ItemStack(Material.MAGENTA_WOOL);
        ItemMeta itemMeta = lastLocation.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Teleport to your last location");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        lastLocation.setItemMeta(itemMeta);
        guiGeneric.setItem(11, lastLocation);

        int index = 13;
        HashMap<Integer, Town> towns = TownManager.getTowns();
        for (int key : towns.keySet()) {
            Town town = towns.get(key);
            ItemStack itemStack = new ItemStack(Material.LIGHT_BLUE_WOOL);
            itemMeta = itemStack.getItemMeta();

            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatColor.GRAY + "Click to start in this location!");
            lore.add("");
            lore.add(ChatColor.GRAY + "Required Level: " + town.getLevel());
            itemMeta.setLore(lore);

            itemMeta.setDisplayName(ChatColor.AQUA + town.getName() + " #" + key);
            itemStack.setItemMeta(itemMeta);

            guiGeneric.setItem(index, itemStack);
        }


        return guiGeneric;
    }

    public static GuiGeneric tutorialSkipMenu(String rpgClass, int charNo) {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatColor.DARK_GRAY + "Play Tutorial? " + rpgClass + " " + charNo, 0);

        ItemStack yes = new ItemStack(Material.LIME_WOOL);
        ItemMeta itemMeta = yes.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(ChatColor.GREEN + "Yes, play tutorial.");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        yes.setItemMeta(itemMeta);
        guiGeneric.setItem(11, yes);

        ItemStack no = new ItemStack(Material.RED_WOOL);
        itemMeta.setDisplayName(ChatColor.RED + "No, skip tutorial");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.YELLOW + "Required level: 1");
        itemMeta.setLore(lore);
        no.setItemMeta(itemMeta);
        guiGeneric.setItem(15, no);

        return guiGeneric;
    }
}
