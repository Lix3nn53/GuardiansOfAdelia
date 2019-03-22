package io.github.lix3nn53.guardiansofadelia.economy.trading;

import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class Trade {

    private final Player player1;
    private final Player player2;
    private final GuiGeneric tradeGui1;
    private final GuiGeneric tradeGui2;

    private final HashMap<Integer, ItemStack> player1Gifts = new HashMap<Integer, ItemStack>();
    private final HashMap<Integer, ItemStack> player2Gifts = new HashMap<Integer, ItemStack>();

    public Trade(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.tradeGui1 = new GuiGeneric(27, ChatColor.GOLD + "Trade with " + player2.getName(), 0);
        this.tradeGui2 = new GuiGeneric(27, ChatColor.GOLD + "Trade with " + player1.getName(), 0);

        //right side first part (guide for owner)
        ItemStack middle = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta middleItemMeta = middle.getItemMeta();
        middleItemMeta.setDisplayName(ChatColor.GOLD + "Trade Guide");
        middleItemMeta.setLore(new ArrayList() {{
            add(ChatColor.GOLD + "Click on items you want to add to trade");
            add(ChatColor.GOLD + "or remove from trade.");
            add("");
            add(ChatColor.YELLOW + "Click on yellow wool to");
            add(ChatColor.YELLOW + "lock the trade for both players.");
            add("");
            add(ChatColor.GREEN + "After trade is locked both players need");
            add(ChatColor.GREEN + "to click green wool to complete the trade.");
        }});
        middle.setItemMeta(middleItemMeta);
        for (int i = 23; i < 25; i++) {
            tradeGui1.setItem(i, middle);
            tradeGui2.setItem(i, middle);
        }

        //middle (info)
        ItemStack status = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        ItemMeta statusItemMeta = status.getItemMeta();
        statusItemMeta.setDisplayName(ChatColor.GOLD + "TRADE STATUS");
        statusItemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.YELLOW + "Players are selecting items to trade");
        }});
        status.setItemMeta(statusItemMeta);
        tradeGui1.setItem(4, status);
        tradeGui1.setItem(13, status);
        tradeGui1.setItem(22, status);
        tradeGui2.setItem(4, status);
        tradeGui2.setItem(13, status);
        tradeGui2.setItem(22, status);

        //left side (other player)
        ItemStack otherSide = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta otherSideItemMeta = otherSide.getItemMeta();
        otherSideItemMeta.setDisplayName(ChatColor.YELLOW + player2.getName() + "'s Items");
        otherSide.setItemMeta(otherSideItemMeta);
        for (int i = 18; i < 22; i++) {
            tradeGui1.setItem(i, otherSide);
        }
        otherSideItemMeta.setDisplayName(ChatColor.YELLOW + player1.getName() + "'s Items");
        otherSide.setItemMeta(otherSideItemMeta);
        for (int i = 18; i < 22; i++) {
            tradeGui2.setItem(i, otherSide);
        }

        //right side second part (buttons for gui owner)
        ItemStack lock = new ItemStack(Material.YELLOW_WOOL);
        ItemMeta lockItemMeta = lock.getItemMeta();
        lockItemMeta.setDisplayName(ChatColor.YELLOW.toString() + "Lock the Trade");
        lockItemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.YELLOW + "Locks the trade for both players");
        }});
        lock.setItemMeta(lockItemMeta);
        tradeGui1.setItem(25, lock);
        tradeGui2.setItem(25, lock);

        ItemStack finish = new ItemStack(Material.LIME_WOOL);
        ItemMeta finishItemMeta = finish.getItemMeta();
        finishItemMeta.setDisplayName(ChatColor.GREEN.toString() + "Accept the Trade");
        finishItemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GREEN + "After trade is locked, both players must");
            add(ChatColor.GREEN + "accept to complete the trade.");
        }});
        finish.setItemMeta(finishItemMeta);
        tradeGui1.setItem(26, finish);
        tradeGui2.setItem(26, finish);
    }

    public void addItem(Player player, int slotNoInPlayerInventory) {
        if (player.equals(player1)) {
            if (player1Gifts.size() >= 8) {
                return;
            }
            ItemStack itemStack = player.getInventory().getItem(slotNoInPlayerInventory);
            player1Gifts.put(slotNoInPlayerInventory, itemStack);
            addToGuiPlayer1Gift(itemStack);
        } else if (player.equals(player2)) {
            if (player2Gifts.size() >= 8) {
                return;
            }
            ItemStack itemStack = player.getInventory().getItem(slotNoInPlayerInventory);
            player2Gifts.put(slotNoInPlayerInventory, itemStack);
            addToGuiPlayer2Gift(itemStack);
        }
    }

    public void removeItem(Player player, int slotNoInPlayerInventory) {
        if (player.equals(player1)) {
            player1Gifts.remove(slotNoInPlayerInventory);
        } else if (player.equals(player2)) {
            player2Gifts.remove(slotNoInPlayerInventory);
        }
    }

    public void finish() {
        StringBuilder player1GiftsStr = new StringBuilder();
        StringBuilder player2GiftsStr = new StringBuilder();
        if (!player1Gifts.isEmpty()) {
            for (int i : player1Gifts.keySet()) {
                player1.getInventory().setItem(i, new ItemStack(Material.AIR));
                ItemStack itemStack1 = player1Gifts.get(i);
                player1GiftsStr.append(itemStack1.getItemMeta().getDisplayName()).append("x").append(itemStack1.getAmount()).append(" ");
                InventoryUtils.giveItemToPlayer(player2, itemStack1);
            }
        }
        if (!player2Gifts.isEmpty()) {
            for (int i : player2Gifts.keySet()) {
                player2.getInventory().setItem(i, new ItemStack(Material.AIR));
                ItemStack itemStack2 = player2Gifts.get(i);
                player2GiftsStr.append(itemStack2.getItemMeta().getDisplayName()).append("x").append(itemStack2.getAmount()).append(" ");
                InventoryUtils.giveItemToPlayer(player1, itemStack2);
            }
        }
        player1.sendMessage(ChatColor.YELLOW + "Given items to " + player2.getName() + ": " + player1GiftsStr.toString());
        player1.sendMessage(ChatColor.GOLD + "Received items from " + player2.getName() + ": " + player2GiftsStr.toString());

        player2.sendMessage(ChatColor.YELLOW + "Given items to " + player1.getName() + ": " + player2GiftsStr);
        player2.sendMessage(ChatColor.GOLD + "Received items from " + player1.getName() + ": " + player1GiftsStr);
    }

    private void addToGuiPlayer1Gift(ItemStack itemStack) {
        if (player1Gifts.isEmpty()) {
            return;
        }
        int size = player1Gifts.size();
        if (size <= 4) {
            tradeGui1.setItem(size + 4, itemStack);
            tradeGui2.setItem(size - 1, itemStack);
        } else if (size <= 8) {
            tradeGui1.setItem(size + 13, itemStack);
            tradeGui2.setItem(size + 8, itemStack);
        }
    }

    private void addToGuiPlayer2Gift(ItemStack itemStack) {
        if (player2Gifts.isEmpty()) {
            return;
        }
        int size = player2Gifts.size();
        if (size <= 4) {
            tradeGui2.setItem(size + 4, itemStack);
            tradeGui1.setItem(size - 1, itemStack);
        } else if (size <= 8) {
            tradeGui2.setItem(size + 13, itemStack);
            tradeGui1.setItem(size + 8, itemStack);
        }
    }
}
