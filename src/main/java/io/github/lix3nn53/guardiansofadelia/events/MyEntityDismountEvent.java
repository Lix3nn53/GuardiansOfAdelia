package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityDismountEvent;

public class MyEntityDismountEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(EntityDismountEvent e) {
        Entity entity = e.getEntity();
        if (entity.getType().equals(EntityType.DROPPED_ITEM)) {
            GuardiansOfAdelia.getInstance().getLogger().info("DROPPED ITEM DISMOUNT");
            e.setCancelled(true);
        }
    }
}
