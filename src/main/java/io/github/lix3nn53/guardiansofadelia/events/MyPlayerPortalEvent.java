package io.github.lix3nn53.guardiansofadelia.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class MyPlayerPortalEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(PlayerPortalEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("teleporrrrrrrrrrrrrrt portaaaaaaal eventerino");
    }

}
