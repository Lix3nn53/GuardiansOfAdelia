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
import io.github.lix3nn53.guardiansofadelia.utilities.gui.Gui;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

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
                } else if (guardianData.hasBazaar()) {
                    String title = event.getView().getTitle();
                    if (title.equals(ChatColor.GOLD + "Edit your bazaar")) {
                        Bazaar bazaar = guardianData.getBazaar();
                        bazaar.setOpen(true);
                    }
                }

                guardianData.clearActiveGui();
            }

            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();

                rpgCharacterStats.recalculateEquipment(activeCharacter.getRpgClass());
            }
        }
        MerchantManager.clearSellItemClick(player);
    }
}
