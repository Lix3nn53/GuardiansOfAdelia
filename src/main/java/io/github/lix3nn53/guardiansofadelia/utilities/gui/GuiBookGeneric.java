package io.github.lix3nn53.guardiansofadelia.utilities.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiBookGeneric implements GuiBook {

    private final String name;
    private final int resourceNpcNo;
    private List<GuiPage> pageList = new ArrayList<GuiPage>();
    private boolean isLocked = true;

    public GuiBookGeneric(String name, int resourceNpcNo) {
        this.name = name;
        this.resourceNpcNo = resourceNpcNo;
    }

    public GuiGeneric getPageInventory(int pageNo) {
        pageNo = pageNo - 1;
        GuiGeneric pageGui = new GuiGeneric(54, name + " Crafting Page-" + pageNo, resourceNpcNo);
        if (pageNo < pageList.size()) {
            if (pageNo > 0) {
                ItemStack previousPage = new ItemStack(Material.YELLOW_WOOL);
                ItemMeta statusItemMeta = previousPage.getItemMeta();
                statusItemMeta.setDisplayName(ChatColor.YELLOW + "Previous Page");
                previousPage.setItemMeta(statusItemMeta);
                pageGui.setItem(45, previousPage);
            }
            if (pageNo < pageList.size() - 1) {
                ItemStack nextPage = new ItemStack(Material.YELLOW_WOOL);
                ItemMeta statusItemMeta = nextPage.getItemMeta();
                statusItemMeta.setDisplayName(ChatColor.YELLOW + "Next Page");
                nextPage.setItemMeta(statusItemMeta);
                pageGui.setItem(53, nextPage);
            }
            int i = 0;
            for (GuiLine line : pageList.get(pageNo).guiLines) {
                int y = 0;
                for (ItemStack itemStack : line.getLine()) {
                    pageGui.setItem(i + y, itemStack);
                    y += 1;
                }
                i += 9;
            }
        }
        return pageGui;
    }

    public void addPage(GuiPage page) {
        pageList.add(page);
    }

    public void setPages(List<GuiPage> guiPageList) {
        this.pageList = guiPageList;
    }


    @Override
    public List<Integer> getFillableSlots() {
        return getPageInventory(1).getFillableSlots();
    }

    @Override
    public List<Integer> getItemSlots() {
        return getPageInventory(1).getItemSlots();
    }

    @Override
    public List<Integer> getEmptySlots() {
        return getPageInventory(1).getEmptySlots();
    }

    @Override
    public void openInventory(Player player) {
        getPageInventory(1).openInventory(player);
    }

    @Override
    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    @Override
    public int getResourceNPC() {
        return resourceNpcNo;
    }

    public void openInventoryPage(Player player, int pageNo) {
        getPageInventory(pageNo).openInventory(player);
    }
}
