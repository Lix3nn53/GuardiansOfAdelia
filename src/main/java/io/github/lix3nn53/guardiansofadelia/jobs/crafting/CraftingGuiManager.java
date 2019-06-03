package io.github.lix3nn53.guardiansofadelia.jobs.crafting;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.list.Ingredients;
import io.github.lix3nn53.guardiansofadelia.jobs.JobType;
import io.github.lix3nn53.guardiansofadelia.utilities.ItemPoolGenerator;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiBookGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiPage;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CraftingGuiManager {

    public GuiGeneric getLevelSelection(JobType jobType) {
        GuiGeneric guiGeneric = new GuiGeneric(27, jobType.getName() + " Level Selection", 0);
        ItemStack itemStack = new ItemStack(Material.STONE_PICKAXE, 10);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(10000036);
        itemMeta.setDisplayName(ChatColor.GOLD + "Level 20~29");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Click to craft items of this level.");
        }});
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(20);
        guiGeneric.setItem(9, itemStack);

        itemMeta.setDisplayName(ChatColor.GOLD + "Level 30~39");
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(30);
        guiGeneric.setItem(10, itemStack);

        itemMeta.setDisplayName(ChatColor.GOLD + "Level 40~49");
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(40);
        guiGeneric.setItem(11, itemStack);

        itemMeta.setDisplayName(ChatColor.GOLD + "Level 50~59");
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(50);
        guiGeneric.setItem(12, itemStack);

        itemMeta.setDisplayName(ChatColor.GOLD + "Level 60~69");
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(60);
        guiGeneric.setItem(13, itemStack);

        itemMeta.setDisplayName(ChatColor.GOLD + "Level 70~79");
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(70);
        guiGeneric.setItem(14, itemStack);

        itemMeta.setDisplayName(ChatColor.GOLD + "Level 80~89");
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(80);
        guiGeneric.setItem(15, itemStack);

        itemMeta.setDisplayName(ChatColor.GOLD + "Level 90~99");
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(90);
        guiGeneric.setItem(16, itemStack);

        return guiGeneric;
    }

    public GuiBookGeneric getCraftingBook(Player player, JobType jobType, GearLevel gearLevel) {

        GuiBookGeneric craftingBook = new GuiBookGeneric(jobType.getName() + " Level-" + gearLevel.getMinLevel() + "~" + gearLevel.getMaxLevel() + " Crafting", 0);

        String itemTag = "";
        ItemTier tier = ItemTier.MYSTIC;

        List<ItemStack> itemStackList = new ArrayList<>();

        if (this.equals(JobType.ALCHEMIST)) {
            itemTag = "Alchemist's";
            itemStackList = ItemPoolGenerator.generateConsumables(itemTag, gearLevel.getConsumableNo());
        } else if (this.equals(JobType.ARMORSMITH)) {
            itemTag = "Armorsmith's";
            itemStackList = ItemPoolGenerator.generateArmors(tier, itemTag, gearLevel);
        } else if (this.equals(JobType.JEWELLER)) {
            itemTag = "Jeweller's";
            itemStackList = ItemPoolGenerator.generatePassives(tier, itemTag, gearLevel);
        } else if (this.equals(JobType.JEWELLER)) {
            itemTag = "Weaponsmith's";
            itemStackList = ItemPoolGenerator.generateWeapons(tier, itemTag, gearLevel);
        }

        List<GuiPage> guiPageList = new ArrayList<>();
        guiPageList.add(new GuiPage());

        //create pages
        int i = 0;
        for (ItemStack itemStack : itemStackList) {
            //create lines
            CraftingLine craftingLine = new CraftingLine(itemStack);
            //add ingredients to craftingLine
            List<ItemStack> ingredients = Ingredients.getReqIngredients(itemStack);
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
