package io.github.lix3nn53.guardiansofadelia.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class MyPlayerTeleportEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(PlayerTeleportEvent event) {
        PlayerTeleportEvent.TeleportCause cause = event.getCause();

        if (cause.equals(PlayerTeleportEvent.TeleportCause.SPECTATE)) {
            event.setCancelled(true);
        }
    }
}
