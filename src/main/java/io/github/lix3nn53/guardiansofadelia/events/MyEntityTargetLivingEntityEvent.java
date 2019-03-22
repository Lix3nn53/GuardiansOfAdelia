package io.github.lix3nn53.guardiansofadelia.events;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class MyEntityTargetLivingEntityEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(EntityTargetLivingEntityEvent e) {
        if (e.getTarget() != null) {
            EntityType type = e.getTarget().getType();
            if (!(type.equals(EntityType.WOLF) || type.equals(EntityType.PLAYER))) {
                e.setCancelled(true);
            }
        }
    }
}
