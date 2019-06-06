package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.Items.Consumable;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class MyPlayerItemConsumeEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.hasItemMeta()) {
            if (PersistentDataContainerUtil.hasString(item, "customConsumable")) {
                String customConsumable = PersistentDataContainerUtil.getString(item, "customConsumable");
                int consumableLevel = PersistentDataContainerUtil.getInteger(item, "consumableLevel");
                Consumable consumable = Consumable.valueOf(customConsumable);
                consumable.consume(player, consumableLevel, player.getInventory().getItemInMainHand());
                event.setCancelled(true);
            }
        }
    }
}
