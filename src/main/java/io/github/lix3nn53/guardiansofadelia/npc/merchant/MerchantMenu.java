package io.github.lix3nn53.guardiansofadelia.npc.merchant;

import io.github.lix3nn53.guardiansofadelia.utilities.gui.Gui;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class MerchantMenu {

    private final HashMap<ItemStack, Gui> itemToGui = new HashMap<ItemStack, Gui>();
    private final GuiGeneric merchantMenuGui;

    public MerchantMenu(String shopName, boolean canSell, boolean hasQuest, boolean canCoinConver, int resourceNpcNo) {
        this.merchantMenuGui = new GuiGeneric(27, shopName, resourceNpcNo);
        if (canSell) {
            int sellSlotNo = 0;
            ItemStack sellItem = new ItemStack(Material.ORANGE_WOOL);
            ItemMeta statusItemMeta = sellItem.getItemMeta();
            statusItemMeta.setDisplayName(ChatColor.GOLD + "Sell Items");
            statusItemMeta.setLore(new ArrayList() {{
                add("");
                add(ChatColor.YELLOW + "Sell your item for coins");
            }});
            sellItem.setItemMeta(statusItemMeta);
            merchantMenuGui.setItem(sellSlotNo, sellItem);
        }
        if (canCoinConver) {
            int coinConverterSlotNo = 2;
            ItemStack coinConvertItem = new ItemStack(Material.YELLOW_WOOL);
            ItemMeta statusItemMeta = coinConvertItem.getItemMeta();
            statusItemMeta.setDisplayName(ChatColor.YELLOW + "Convert coins");
            statusItemMeta.setLore(new ArrayList() {{
                add("");
                add(ChatColor.GREEN + "64 Bronze = " + ChatColor.WHITE + "1 Silver");
                add(ChatColor.WHITE + "64 Silver = " + ChatColor.YELLOW + "1 Gold");
            }});
            coinConvertItem.setItemMeta(statusItemMeta);
            merchantMenuGui.setItem(coinConverterSlotNo, coinConvertItem);
        }
        if (hasQuest) {
            int questSlotNo = 4;
            ItemStack questItem = new ItemStack(Material.PURPLE_WOOL);
            ItemMeta statusItemMeta = questItem.getItemMeta();
            statusItemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Quests");
            statusItemMeta.setLore(new ArrayList() {{
                add("");
                add(ChatColor.DARK_PURPLE + "Quest list of this NPC");
            }});
            questItem.setItemMeta(statusItemMeta);
            merchantMenuGui.setItem(questSlotNo, questItem);
        }
    }

    public boolean addGuiPage(ItemStack selection, Gui gui) {
        if (itemToGui.size() < 5) {
            int slotNo = (itemToGui.size() * 2) + 18;
            merchantMenuGui.setItem(slotNo, selection);
            itemToGui.put(selection, gui);
            return true;
        }
        return false;
    }

    public GuiGeneric getMerchantMenuGui() {
        return this.merchantMenuGui;
    }
}
