package io.github.lix3nn53.guardiansofadelia.economy.trading;

import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TradeGui extends GuiGeneric {

    private final Player owner;
    private final Player receiver;
    private final HashMap<Integer, ItemStack> slotNoInPlayerToGift = new HashMap<>();
    private final HashMap<Integer, Integer> slotNoInTradeToSlotNoInPlayer = new HashMap<>();
    private boolean didAccept = false;

    /**
     * Works one way. Can only give items to receiver.
     *
     * @param owner
     * @param receiver
     */
    public TradeGui(Player owner, Player receiver) {
        super(27, ChatPalette.GOLD + "Trade with " + receiver.getName(), 0);

        this.owner = owner;
        this.receiver = receiver;

        //right side first part (guide for owner)
        ItemStack middle = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta middleItemMeta = middle.getItemMeta();
        middleItemMeta.setDisplayName(ChatPalette.GREEN_DARK + "Trade Guide");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatPalette.GOLD + "Click on items you want to add to trade");
        lore.add(ChatPalette.GOLD + "or remove from trade.");
        lore.add("");
        lore.add(ChatPalette.YELLOW + "Click on yellow wool to");
        lore.add(ChatPalette.YELLOW + "lock the trade for both players.");
        lore.add("");
        lore.add(ChatPalette.GREEN_DARK + "After trade is locked both players need");
        lore.add(ChatPalette.GREEN_DARK + "to click green wool to complete the trade.");
        middleItemMeta.setLore(lore);
        middle.setItemMeta(middleItemMeta);
        for (int i = 23; i < 25; i++) {
            setItem(i, middle);
        }

        //middle (info)
        ItemStack status = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemMeta statusItemMeta = status.getItemMeta();
        statusItemMeta.setDisplayName(ChatPalette.GOLD + "TRADE STATUS");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.YELLOW + "Players are selecting items to trade");
        statusItemMeta.setLore(lore);
        status.setItemMeta(statusItemMeta);
        setItem(4, status);
        setItem(13, status);
        setItem(22, status);

        //left side (other player)
        ItemStack otherSide = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta otherSideItemMeta = otherSide.getItemMeta();
        otherSideItemMeta.setDisplayName(ChatPalette.YELLOW + receiver.getName() + "'s Items");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.YELLOW + "You will receive this items");
        otherSideItemMeta.setLore(lore);
        otherSide.setItemMeta(otherSideItemMeta);
        for (int i = 18; i < 22; i++) {
            setItem(i, otherSide);
        }

        //right side second part (buttons for gui owner)
        ItemStack lock = new ItemStack(Material.YELLOW_WOOL);
        ItemMeta lockItemMeta = lock.getItemMeta();
        lockItemMeta.setDisplayName(ChatPalette.YELLOW + "Lock the Trade");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.YELLOW + "Locks the trade for both players");
        lockItemMeta.setLore(lore);
        lock.setItemMeta(lockItemMeta);
        setItem(25, lock);

        ItemStack finish = new ItemStack(Material.LIME_WOOL);
        ItemMeta finishItemMeta = finish.getItemMeta();
        finishItemMeta.setDisplayName(ChatPalette.GREEN_DARK + "Accept the Trade");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GREEN_DARK + "After trade is locked, both players must");
        lore.add(ChatPalette.GREEN_DARK + "accept to complete the trade.");
        finishItemMeta.setLore(lore);
        finish.setItemMeta(finishItemMeta);
        setItem(26, finish);
    }

    public Player getOwner() {
        return owner;
    }

    public boolean canAddMoreGiveItem() {
        return slotNoInPlayerToGift.size() < 8;
    }

    public boolean addGiveItem(ItemStack itemStack, int slotNoInOwnerPlayerInventory) {
        if (!slotNoInPlayerToGift.containsKey(slotNoInOwnerPlayerInventory)) {
            slotNoInPlayerToGift.put(slotNoInOwnerPlayerInventory, itemStack);

            int size = slotNoInPlayerToGift.size();

            if (size <= 4) {
                int slot = size + 4;
                setItem(slot, itemStack);
                slotNoInTradeToSlotNoInPlayer.put(slot, slotNoInOwnerPlayerInventory);
            } else if (size <= 8) {
                int slot = size + 9;
                setItem(slot, itemStack);
                slotNoInTradeToSlotNoInPlayer.put(slot, slotNoInOwnerPlayerInventory);
            }
            return true;
        }
        return false;
    }

    public void addReceiveItem(ItemStack itemStack, int receiveSlotNoFromOther) {
        setItem(receiveSlotNoFromOther, itemStack);
    }

    public int getCurrentReceiveSlotNoForOtherTradeGui() {
        int size = slotNoInPlayerToGift.size();
        if (size <= 4) {
            return size - 1;
        }
        //if (size <= 8)
        return size + 4;
    }

    public boolean removeGiveItem(int slotNoOnTradeInventory) {
        if (slotNoInTradeToSlotNoInPlayer.containsKey(slotNoOnTradeInventory)) {
            Integer slotNoInPlayerInventory = slotNoInTradeToSlotNoInPlayer.get(slotNoOnTradeInventory);
            slotNoInTradeToSlotNoInPlayer.remove(slotNoOnTradeInventory);
            slotNoInPlayerToGift.remove(slotNoInPlayerInventory);
            setItem(slotNoOnTradeInventory, new ItemStack(Material.AIR));
            return true;
        }
        return false;
    }

    public void removeReceiveItem(int slotNoOnOtherInventoryGive) {
        int slotNoOnThisReceive = slotNoOnOtherInventoryGive - 5;
        setItem(slotNoOnThisReceive, new ItemStack(Material.AIR));
    }

    private final List<ItemStack> itemsToGive = new ArrayList<>();

    public void removeFromOwnerAndStore() {
        StringBuilder giftStr = new StringBuilder();
        for (int slotNoInPlayer : slotNoInPlayerToGift.keySet()) {
            ItemStack giftItem = slotNoInPlayerToGift.get(slotNoInPlayer);
            itemsToGive.add(giftItem);

            //remove
            owner.getInventory().setItem(slotNoInPlayer, null);

            //string
            giftStr.append(giftItem.getItemMeta().getDisplayName()).append("x").append(giftItem.getAmount()).append(" ");
        }
    }

    public void giveItemsToReceiver() {
        StringBuilder giftStr = new StringBuilder();
        for (ItemStack giftItem : itemsToGive) {

            //give
            InventoryUtils.giveItemToPlayer(receiver, giftItem);

            //string
            giftStr.append(giftItem.getItemMeta().getDisplayName()).append("x").append(giftItem.getAmount()).append(" ");
        }
        owner.sendMessage(ChatPalette.GOLD + "Given items to " + receiver.getName() + ": " + giftStr);
        receiver.sendMessage(ChatPalette.GOLD + "Received items from " + owner.getName() + ": " + giftStr);
    }

    public void lock() {
        //middle (info)
        ItemStack status = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemMeta statusItemMeta = status.getItemMeta();
        statusItemMeta.setDisplayName(ChatPalette.GOLD + "TRADE STATUS");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GOLD + "One of the players LOCKED the trade");
        statusItemMeta.setLore(lore);
        status.setItemMeta(statusItemMeta);
        setItem(4, status);
        setItem(13, status);
        setItem(22, status);

        //right side first part (guide for owner)
        ItemStack middle = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta middleItemMeta = middle.getItemMeta();
        middleItemMeta.setDisplayName(ChatPalette.GOLD + "Trade Guide");
        lore = new ArrayList<>();
        lore.add(ChatPalette.GOLD + "Click on items you want to add to trade");
        lore.add(ChatPalette.GOLD + "or remove from trade.");
        lore.add("");
        lore.add(ChatPalette.YELLOW + "Click on yellow wool to");
        lore.add(ChatPalette.YELLOW + "lock the trade for both players.");
        lore.add("");
        lore.add(ChatPalette.GREEN_DARK + "After trade is locked both players need");
        lore.add(ChatPalette.GREEN_DARK + "to click green wool to complete the trade.");
        middleItemMeta.setLore(lore);
        middle.setItemMeta(middleItemMeta);
        setItem(25, middle);
    }

    public void accept() {
        this.didAccept = true;

        //middle (info)
        ItemStack status = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta statusItemMeta = status.getItemMeta();
        statusItemMeta.setDisplayName(ChatPalette.GOLD + "TRADE STATUS");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GREEN_DARK + "You have accepted the trade and");
        lore.add(ChatPalette.GREEN_DARK + "waiting for other player to accept");
        statusItemMeta.setLore(lore);
        status.setItemMeta(statusItemMeta);
        setItem(4, status);
        setItem(13, status);
        setItem(22, status);

        //right side first part (guide for owner)
        ItemStack middle = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta middleItemMeta = middle.getItemMeta();
        middleItemMeta.setDisplayName(ChatPalette.GOLD + "Trade Guide");
        lore = new ArrayList<>();
        lore.add(ChatPalette.GOLD + "Click on items you want to add to trade");
        lore.add(ChatPalette.GOLD + "or remove from trade.");
        lore.add("");
        lore.add(ChatPalette.YELLOW + "Click on yellow wool to");
        lore.add(ChatPalette.YELLOW + "lock the trade for both players.");
        lore.add("");
        lore.add(ChatPalette.GREEN_DARK + "After trade is locked both players need");
        lore.add(ChatPalette.GREEN_DARK + "to click green wool to complete the trade.");
        middleItemMeta.setLore(lore);
        middle.setItemMeta(middleItemMeta);
        setItem(26, middle);
    }

    public boolean didAccept() {
        return didAccept;
    }

    public boolean isEmptyGive() {
        return this.slotNoInPlayerToGift.isEmpty();
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();
        if (current == null) return;
        Material currentType = current.getType();

        Inventory clickedInventory = event.getClickedInventory();
        int slot = event.getSlot();
        if (TradeManager.hasTrade(player)) {
            Trade trade = TradeManager.getTrade(player);
            if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                if (currentType.equals(Material.LIME_WOOL)) {
                    trade.accept(player);
                } else if (currentType.equals(Material.YELLOW_WOOL)) {
                    trade.lock();
                } else if ((slot > 4 && slot < 9) || (slot > 13 && slot < 18)) {
                    trade.removeItemFromTrade(player, slot);
                }
            } else if (clickedInventory.getType().equals(InventoryType.PLAYER)) {
                if (!InventoryUtils.canTradeMaterial(currentType)) {
                    player.sendMessage(ChatPalette.RED + "You can't trade this item");
                }
                trade.addItem(player, slot);
            }
        }
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (TradeManager.hasTrade(player)) {
            Trade trade = TradeManager.getTrade(player);
            Player otherPlayer = trade.getOtherPlayer(player);
            TradeManager.removeTrade(player, otherPlayer);
            otherPlayer.closeInventory();
        }
    }
}
