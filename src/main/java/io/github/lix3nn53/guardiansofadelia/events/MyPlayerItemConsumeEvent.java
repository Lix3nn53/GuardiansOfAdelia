package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.Items.consumables.Consumable;
import io.github.lix3nn53.guardiansofadelia.Items.scrolls.TeleportScrollLocation;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class MyPlayerItemConsumeEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        if(item.getType().equals(Material.POTION)) {
            if(item.hasItemMeta()) {
                if (PersistentDataContainerUtil.hasString(item, "customConsumable")) {
                    String customConsumable = PersistentDataContainerUtil.getString(item, "customConsumable");
                    int consumableLevel = PersistentDataContainerUtil.getInteger(item, "consumableLevel");
                    Consumable consumable = Consumable.valueOf(customConsumable);
                    consumable.consume(event.getPlayer(), consumableLevel, item);
                    event.setCancelled(true);
                }
            }
        }
    }
}
