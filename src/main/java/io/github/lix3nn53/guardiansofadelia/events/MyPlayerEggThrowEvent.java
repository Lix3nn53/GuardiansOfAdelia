package io.github.lix3nn53.guardiansofadelia.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;

public class MyPlayerEggThrowEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(PlayerEggThrowEvent e) {
        e.setHatching(false);
    }
}
