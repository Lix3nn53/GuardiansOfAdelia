package io.github.lix3nn53.guardiansofadelia.utilities.gui;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
                statusItemMeta.setDisplayName(ChatColor.YELLOW + "Previous Page");
                previousPage.setItemMeta(statusItemMeta);
                pageGui.setItem(45, previousPage);
            }
            if (pageIndex < pageList.size() - 1) {
                ItemStack nextPage = new ItemStack(Material.YELLOW_WOOL);
                ItemMeta statusItemMeta = nextPage.getItemMeta();
                statusItemMeta.setDisplayName(ChatColor.YELLOW + "Next Page");
                nextPage.setItemMeta(statusItemMeta);
                pageGui.setItem(53, nextPage);
            }
            int i = 0;
            for (GuiLine line : pageList.get(pageIndex).guiLines) {
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
        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
            getPageInventory(0).openInventory(player);
            guardianData.setActiveGui(this);
        }
    }

    public void openInventoryPage(Player player, int pageIndex) {
        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
            getPageInventory(pageIndex).openInventory(player);
            guardianData.setActiveGui(this);
        }
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
}
