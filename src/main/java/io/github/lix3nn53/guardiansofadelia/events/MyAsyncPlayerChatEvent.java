package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.chat.ChatManager;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.Bazaar;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.BazaarManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class MyAsyncPlayerChatEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (BazaarManager.isSettingMoney(player)) {
            UUID uuid = player.getUniqueId();
            if (GuardianDataManager.hasGuardianData(uuid)) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                if (guardianData.hasBazaar()) {
                    Bazaar bazaar = guardianData.getBazaar();
                    event.setCancelled(true);
                    String message = event.getMessage();
                    try {
                        int price = Integer.parseInt(message);
                        ItemStack itemToSetMoney = BazaarManager.getItemToSetMoney(player);
                        bazaar.addItem(itemToSetMoney, price);
                        InventoryUtils.removeItemFromInventory(player.getInventory(), itemToSetMoney, itemToSetMoney.getAmount());
                        bazaar.edit();
                        BazaarManager.clearPlayerSettingMoneyOfItem(player);
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.YELLOW + "(Enter a number to chat without '/' or anything)");
                    }
                }
            }
        } else {
            boolean allowOnNormalChat = ChatManager.onChat(player, event.getMessage());
            if (allowOnNormalChat) {
                String format = ChatManager.getFormat(player);
                event.setFormat(format);
            } else {
                event.setCancelled(true);
            }
        }
    }
}
