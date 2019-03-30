package io.github.lix3nn53.guardiansofadelia.economy.trading;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Trade {

    private final TradeGui tradeGui1;
    private final TradeGui tradeGui2;
    private boolean isLocked = false;

    public Trade(Player player1, Player player2) {
        this.tradeGui1 = new TradeGui(player1, player2);
        tradeGui1.openInventory(player1);
        this.tradeGui2 = new TradeGui(player2, player1);
        tradeGui2.openInventory(player2);
    }

    public void addItem(Player player, int slotNoInPlayerInventory) {
        if (!isLocked) {
            if (tradeGui1.getOwner().equals(player)) {
                if (tradeGui1.canAddMoreGiveItem()) {
                    ItemStack itemStack = player.getInventory().getItem(slotNoInPlayerInventory);
                    boolean b = tradeGui1.addGiveItem(itemStack, slotNoInPlayerInventory);
                    if (b) {
                        int slotNo = tradeGui1.getCurrentReceiveSlotNoForOtherTradeGui();
                        tradeGui2.addReceiveItem(itemStack, slotNo);
                    }
                }
            } else if (tradeGui2.getOwner().equals(player)) {
                if (tradeGui2.canAddMoreGiveItem()) {
                    ItemStack itemStack = player.getInventory().getItem(slotNoInPlayerInventory);
                    boolean b = tradeGui2.addGiveItem(itemStack, slotNoInPlayerInventory);
                    if (b) {
                        int slotNo = tradeGui2.getCurrentReceiveSlotNoForOtherTradeGui();
                        tradeGui1.addReceiveItem(itemStack, slotNo);
                    }
                }
            }
        }
    }

    public void removeItemFromTrade(Player player, int slotNoInTradeInventory) {
        if (!isLocked) {
            if (tradeGui1.getOwner().equals(player)) {
                boolean b = tradeGui1.removeGiveItem(slotNoInTradeInventory);
                if (b) {
                    tradeGui2.removeReceiveItem(slotNoInTradeInventory);
                }
            } else if (tradeGui2.getOwner().equals(player)) {
                boolean b = tradeGui2.removeGiveItem(slotNoInTradeInventory);
                if (b) {
                    tradeGui1.removeReceiveItem(slotNoInTradeInventory);
                }
            }
        }
    }

    public void lock() {
        if (!tradeGui1.isEmptyGive() || !tradeGui2.isEmptyGive()) {
            tradeGui1.lock();
            tradeGui2.lock();
            this.isLocked = true;
        }
    }

    public void accept(Player player) {
        if (isLocked) {
            if (tradeGui1.getOwner().equals(player)) {
                tradeGui1.accept();
            } else if (tradeGui2.getOwner().equals(player)) {
                tradeGui2.accept();
            }
            finish();
        }
    }

    private void finish() {
        if (tradeGui1.didAccept() && tradeGui2.didAccept()) {
            tradeGui1.giveItemsToReceiverAndRemoveFromOwner();
            tradeGui2.giveItemsToReceiverAndRemoveFromOwner();
            tradeGui1.getOwner().closeInventory();
            tradeGui2.getOwner().closeInventory();
        }
    }

    public Player getOtherPlayer(Player player) {
        if (tradeGui1.getOwner().equals(player)) {
            return tradeGui2.getOwner();
        } else if (tradeGui2.getOwner().equals(player)) {
            return tradeGui1.getOwner();
        }
        return null;
    }
}
