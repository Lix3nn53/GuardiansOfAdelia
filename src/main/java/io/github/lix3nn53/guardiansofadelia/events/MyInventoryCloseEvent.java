package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.economy.bazaar.Bazaar;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.BazaarCustomerGui;
import io.github.lix3nn53.guardiansofadelia.economy.trading.Trade;
import io.github.lix3nn53.guardiansofadelia.economy.trading.TradeGui;
import io.github.lix3nn53.guardiansofadelia.economy.trading.TradeManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.npc.merchant.MerchantManager;
import io.github.lix3nn53.guardiansofadelia.revive.TombManager;
import io.github.lix3nn53.guardiansofadelia.rewards.daily.DailyRewardHandler;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.Gui;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class MyInventoryCloseEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
            if (guardianData.hasActiveGui()) {
                Gui activeGui = guardianData.getActiveGui();

                if (activeGui instanceof TradeGui) {
                    if (TradeManager.hasTrade(player)) {
                        Trade trade = TradeManager.getTrade(player);
                        Player otherPlayer = trade.getOtherPlayer(player);
                        TradeManager.removeTrade(player, otherPlayer);
                        otherPlayer.closeInventory();
                    }
                } else if (activeGui instanceof BazaarCustomerGui) {
                    BazaarCustomerGui bazaarCustomerGui = (BazaarCustomerGui) activeGui;
                    Bazaar bazaar = bazaarCustomerGui.getBazaar();
                    if (bazaar != null) {
                        bazaar.removeCustomer(player);
                    }
                } else {
                    String title = event.getView().getTitle();
                    if (title.equals(ChatColor.AQUA + "Revive Gui")) {
                        TombManager.cancelSearch(player);
                    }
                    if (title.equals(ChatColor.YELLOW + "Set Daily Rewards")) {
                        ItemStack[] contents = event.getInventory().getContents();

                        for (int i = 1; i < 8; i++) {
                            ItemStack content = contents[i];

                            DailyRewardHandler.setReward(i, content);
                        }
                    } else if (title.contains(ChatColor.BLACK + "Loot Chest #")) {
                        ItemStack[] contents = event.getInventory().getContents();
                        for (ItemStack content : contents) {
                            if (content == null) continue;
                            InventoryUtils.giveItemToPlayer(player, content);
                        }
                    } else if (guardianData.hasBazaar()) {
                        if (title.equals(ChatColor.GOLD + "Edit your bazaar")) {
                            Bazaar bazaar = guardianData.getBazaar();
                            bazaar.setOpen(true);
                        }
                    }
                }
                guardianData.clearActiveGui();
            }

            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();

                rpgCharacterStats.recalculateEquipment(activeCharacter.getRpgClassStr());
            }
        }
        MerchantManager.clearSellItemClick(player);
    }
}
