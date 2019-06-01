package io.github.lix3nn53.guardiansofadelia.events;

import org.bukkit.event.Listener;

public class MyEntityDismountEvent implements Listener {

    /*@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(EntityDismountEvent e) {
        Entity entity = e.getEntity();
        if (entity.getType().equals(EntityType.DROPPED_ITEM)) {
            GuardiansOfAdelia.getInstance().getLogger().info("DROPPED ITEM DISMOUNT");
            e.setCancelled(true);
        }
    }*/
}
