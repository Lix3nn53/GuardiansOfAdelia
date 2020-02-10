package io.github.lix3nn53.guardiansofadelia.jobs.crafting;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiBookGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiPage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CraftingGuiManager {

    public static GuiGeneric getLevelSelection(CraftingType craftingType) {
        GuiGeneric guiGeneric = new GuiGeneric(27, craftingType.toString() + " Crafting Level Selection", 0);
        ItemStack itemStack = new ItemStack(Material.STONE_PICKAXE, 10);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(36);
        itemMeta.setDisplayName(ChatColor.GOLD + "Level 20~29");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Click to craft items of this level.");
        }});
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(2);
        guiGeneric.setItem(9, itemStack);

        itemMeta.setDisplayName(ChatColor.GOLD + "Level 30~39");
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(3);
        guiGeneric.setItem(10, itemStack);

        itemMeta.setDisplayName(ChatColor.GOLD + "Level 40~49");
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(4);
        guiGeneric.setItem(11, itemStack);

        itemMeta.setDisplayName(ChatColor.GOLD + "Level 50~59");
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(5);
        guiGeneric.setItem(12, itemStack);

        itemMeta.setDisplayName(ChatColor.GOLD + "Level 60~69");
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(6);
        guiGeneric.setItem(13, itemStack);

        itemMeta.setDisplayName(ChatColor.GOLD + "Level 70~79");
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(7);
        guiGeneric.setItem(14, itemStack);

        itemMeta.setDisplayName(ChatColor.GOLD + "Level 80~89");
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(8);
        guiGeneric.setItem(15, itemStack);

        itemMeta.setDisplayName(ChatColor.GOLD + "Level 90~99");
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(9);
        guiGeneric.setItem(16, itemStack);

        return guiGeneric;
    }

    public static GuiBookGeneric getCraftingBook(CraftingType craftingType, int gearLevel) {
        GuiBookGeneric craftingBook = new GuiBookGeneric(craftingType.getName() + " Level" + GearLevel.getMinLevel(gearLevel) + "~" + GearLevel.getMaxLevel(gearLevel) + " Crafting", 0);

        List<ItemStack> itemStackList = craftingType.getItemsToCraft(gearLevel);

        List<GuiPage> guiPageList = new ArrayList<>();
        guiPageList.add(new GuiPage());

        //create pages
        int i = 0;
        for (ItemStack itemStack : itemStackList) {
            //create lines
            CraftingLine craftingLine = new CraftingLine(itemStack);
            //add ingredients to craftingLine
            List<ItemStack> ingredients = CraftingManager.getCraftingTypeAndLevelToIngredients(craftingType, gearLevel);
            for (ItemStack ingredient : ingredients) {
                craftingLine.addWord(ingredient);
            }

            //add lines to page, if full create new page and add it to the pageList
            if (guiPageList.get(i).isEmpty()) {
                guiPageList.get(i).addLine(craftingLine);
            } else {
                guiPageList.add(new GuiPage());
                i++;
                guiPageList.get(i).addLine(craftingLine);
            }
        }

        //add pages from temporary list to book
        craftingBook.setPages(guiPageList);

        return craftingBook;
    }
}
