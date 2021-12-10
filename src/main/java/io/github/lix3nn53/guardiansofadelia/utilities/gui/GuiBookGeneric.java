package io.github.lix3nn53.guardiansofadelia.utilities.gui;

import io.github.lix3nn53.guardiansofadelia.menu.ActiveGuiManager;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiBookGeneric implements GuiBook {

    private final String name;
    private final int resourceNpcNo;
    private List<GuiPage> pageList = new ArrayList<>();
    private boolean isLocked = true;

    public GuiBookGeneric(String name, int resourceNpcNo) {
        this.name = name;
        this.resourceNpcNo = resourceNpcNo;
    }

    public GuiGeneric getPageInventory(int pageIndex) {
        GuiGeneric pageGui = new GuiGeneric(54, name + " Page-" + (pageIndex + 1), resourceNpcNo);
        if (pageIndex < pageList.size()) {
            if (pageIndex > 0) {
                ItemStack previousPage = new ItemStack(Material.YELLOW_WOOL);
                ItemMeta statusItemMeta = previousPage.getItemMeta();
                statusItemMeta.setDisplayName(ChatPalette.YELLOW + "Previous Page");
                previousPage.setItemMeta(statusItemMeta);
                pageGui.setItem(45, previousPage);
            }
            if (pageIndex < pageList.size() - 1) {
                ItemStack nextPage = new ItemStack(Material.YELLOW_WOOL);
                ItemMeta statusItemMeta = nextPage.getItemMeta();
                statusItemMeta.setDisplayName(ChatPalette.YELLOW + "Next Page");
                nextPage.setItemMeta(statusItemMeta);
                pageGui.setItem(53, nextPage);
            }
            int i = 0;
            for (GuiLine line : pageList.get(pageIndex).getGuiLines()) {
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

    public GuiPage getFirstAvailablePage() {
        for (GuiPage guiPage : pageList) {
            boolean empty = guiPage.isEmpty();
            if (empty) {
                return guiPage;
            }
        }

        return null;
    }

    @Override
    public void addToFirstAvailableWord(ItemStack itemStack) {
        //check book full, add page if needed
        GuiPage currentPage = getFirstAvailablePage();
        if (currentPage == null) {
            currentPage = new GuiPage();
            addPage(currentPage);
        }

        //check page full, add line if needed
        int firstAvailableLine = currentPage.getFirstAvailableLine();
        boolean empty = currentPage.isEmpty();

        if (firstAvailableLine == -1) { //there is no available line
            if (!empty) { // we cant add more lines to the page so create new page
                currentPage = new GuiPage();
                currentPage.addLine(new GuiLineGeneric());
                firstAvailableLine = 0;
            } else { // but we can add new line to the page
                currentPage.addLine(new GuiLineGeneric());
                firstAvailableLine = currentPage.getFirstAvailableLine();
            }
        }

        //check line full, add word(ItemStack) if needed
        List<GuiLine> guiLines = currentPage.getGuiLines();
        GuiLine currentLine = guiLines.get(firstAvailableLine);
        boolean lineEmpty = currentLine.isEmpty();
        if (!lineEmpty) {
            currentLine = new GuiLineGeneric();
            currentPage.addLine(currentLine);
        }

        currentLine.addWord(itemStack);
    }

    public void setPages(List<GuiPage> guiPageList) {
        this.pageList = guiPageList;
    }


    @Override
    public List<Integer> getFillableSlots() {
        return getPageInventory(0).getFillableSlots();
    }

    @Override
    public List<Integer> getItemSlots() {
        return getPageInventory(0).getItemSlots();
    }

    @Override
    public List<Integer> getEmptySlots() {
        return getPageInventory(0).getEmptySlots();
    }

    @Override
    public void openInventory(Player player) {
        getPageInventory(0).openInventory(player);
        ActiveGuiManager.setActiveGui(player, this);
    }

    public void openInventoryPage(Player player, int pageIndex) {
        getPageInventory(pageIndex).openInventory(player);
        ActiveGuiManager.setActiveGui(player, this);
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

    @Override
    public void onClick(InventoryClickEvent event) {

    }

    @Override
    public void onClose(InventoryCloseEvent event) {

    }

    public int getPageIndex(String title) {
        String[] split = title.split(" Page-");
        String iStr = split[1];

        return Integer.parseInt(iStr);
    }
}
