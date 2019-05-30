package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.utilities.managers.PlayerTridentThrowManager;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupArrowEvent;

public class MyPlayerPickupArrowEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(PlayerPickupArrowEvent event) {
        AbstractArrow arrow = event.getArrow();

        if (arrow instanceof Trident) {
            arrow.remove();
            event.setCancelled(true);
            Player player = event.getPlayer();
            PlayerTridentThrowManager.onPlayerTridentPickUp(player);
        }
    }

}
