package io.github.lix3nn53.guardiansofadelia.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemMergeEvent;

public class MyItemMergeEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(ItemMergeEvent event) {
        event.setCancelled(true);
    }

}
