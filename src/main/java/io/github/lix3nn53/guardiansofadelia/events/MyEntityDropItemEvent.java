package io.github.lix3nn53.guardiansofadelia.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;

public class MyEntityDropItemEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(EntityDropItemEvent e) {
        e.setCancelled(true);
    }
}
