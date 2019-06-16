package io.github.lix3nn53.guardiansofadelia.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class MyFoodLevelChangeEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(FoodLevelChangeEvent e) {
        if (!(e.getEntity() instanceof Player)) return;

        e.setCancelled(true);
    }

}
