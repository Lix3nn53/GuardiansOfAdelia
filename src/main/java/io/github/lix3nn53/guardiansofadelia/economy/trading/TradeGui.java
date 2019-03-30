package io.github.lix3nn53.guardiansofadelia.economy.trading;

import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

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
        super(27, ChatColor.GOLD + "Trade with " + receiver.getName(), 0);

        this.owner = owner;
        this.receiver = receiver;

        //right side first part (guide for owner)
        ItemStack middle = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta middleItemMeta = middle.getItemMeta();
        middleItemMeta.setDisplayName(ChatColor.GREEN + "Trade Guide");
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
            setItem(i, middle);
        }

        //middle (info)
        ItemStack status = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemMeta statusItemMeta = status.getItemMeta();
        statusItemMeta.setDisplayName(ChatColor.GOLD + "TRADE STATUS");
        statusItemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.YELLOW + "Players are selecting items to trade");
        }});
        status.setItemMeta(statusItemMeta);
        setItem(4, status);
        setItem(13, status);
        setItem(22, status);

        //left side (other player)
        ItemStack otherSide = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta otherSideItemMeta = otherSide.getItemMeta();
        otherSideItemMeta.setDisplayName(ChatColor.YELLOW + receiver.getName() + "'s Items");
        otherSideItemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.YELLOW + "You will receive this items");
        }});
        otherSide.setItemMeta(otherSideItemMeta);
        for (int i = 18; i < 22; i++) {
            setItem(i, otherSide);
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
        setItem(25, lock);

        ItemStack finish = new ItemStack(Material.LIME_WOOL);
        ItemMeta finishItemMeta = finish.getItemMeta();
        finishItemMeta.setDisplayName(ChatColor.GREEN.toString() + "Accept the Trade");
        finishItemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GREEN + "After trade is locked, both players must");
            add(ChatColor.GREEN + "accept to complete the trade.");
        }});
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

    public void giveItemsToReceiverAndRemoveFromOwner() {
        StringBuilder giftStr = new StringBuilder();
        for (int i : slotNoInPlayerToGift.keySet()) {
            ItemStack giftItem = slotNoInPlayerToGift.get(i);

            //give
            InventoryUtils.giveItemToPlayer(receiver, giftItem);

            //remove
            owner.getInventory().setItem(i, new ItemStack(Material.AIR));

            //string
            giftStr.append(giftItem.getItemMeta().getDisplayName()).append("x").append(giftItem.getAmount()).append(" ");
        }
        owner.sendMessage(ChatColor.GOLD + "Given items to " + receiver.getName() + ": " + giftStr.toString());
        receiver.sendMessage(ChatColor.GOLD + "Received items from " + owner.getName() + ": " + giftStr.toString());
    }

    public void lock() {
        //middle (info)
        ItemStack status = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemMeta statusItemMeta = status.getItemMeta();
        statusItemMeta.setDisplayName(ChatColor.GOLD + "TRADE STATUS");
        statusItemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GOLD + "One of the players LOCKED the trade");
        }});
        status.setItemMeta(statusItemMeta);
        setItem(4, status);
        setItem(13, status);
        setItem(22, status);

        //right side first part (guide for owner)
        ItemStack middle = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
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
        setItem(25, middle);
    }

    public void accept() {
        this.didAccept = true;

        //middle (info)
        ItemStack status = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta statusItemMeta = status.getItemMeta();
        statusItemMeta.setDisplayName(ChatColor.GOLD + "TRADE STATUS");
        statusItemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GREEN + "You have accepted the trade and");
            add(ChatColor.GREEN + "waiting for other player to accept");
        }});
        status.setItemMeta(statusItemMeta);
        setItem(4, status);
        setItem(13, status);
        setItem(22, status);

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
        setItem(26, middle);
    }

    public boolean didAccept() {
        return didAccept;
    }

    public boolean isEmptyGive() {
        return this.slotNoInPlayerToGift.isEmpty();
    }
}
