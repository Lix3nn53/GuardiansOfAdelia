package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import org.bukkit.Location;
import org.bukkit.Material;
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
        } else {
            Location from = event.getFrom();
            Location to = event.getTo();

            String fromName = from.getWorld().getName();
            String toName = to.getWorld().getName();

            if (!fromName.equals(toName)) {
                InventoryUtils.removeAllFromInventoryByMaterial(event.getPlayer().getInventory(), Material.COMPASS);
            }
        }
    }
}
