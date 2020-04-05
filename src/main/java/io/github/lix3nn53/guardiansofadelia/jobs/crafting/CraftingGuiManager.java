package io.github.lix3nn53.guardiansofadelia.jobs.crafting;

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
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Click to craft items of this crafting-level.");
        }});

        List<Integer> craftingTypeToLevels = CraftingManager.getCraftingTypeToLevels(craftingType);

        for (int level : craftingTypeToLevels) {
            itemMeta.setDisplayName(ChatColor.GOLD + "Level " + level);
            itemStack.setAmount(level);
            itemStack.setItemMeta(itemMeta);
            guiGeneric.setItem(8 + level, itemStack);
        }

        return guiGeneric;
    }

    public static GuiBookGeneric getCraftingBook(CraftingType craftingType, int craftingLevel) {
        GuiBookGeneric craftingBook = new GuiBookGeneric(craftingType.toString() + " Crafting Level " + craftingLevel, 0);

        List<GuiPage> guiPageList = new ArrayList<>();
        guiPageList.add(new GuiPage());

        List<CraftingLine> craftingLines = CraftingManager.getCraftingTypeAndLevelToCraftingLines(craftingType, craftingLevel);

        int i = 0;
        for (CraftingLine craftingLine : craftingLines) {
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
