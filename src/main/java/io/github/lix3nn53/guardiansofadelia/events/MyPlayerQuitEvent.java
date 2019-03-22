package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class MyPlayerQuitEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        GuardiansOfAdelia.packetLimitterOnQuit(player);
        GuardiansOfAdelia.getGuildManager().onPlayerQuit(player);
        GuardiansOfAdelia.getGuardianDataManager().onPlayerQuit(player);
        GuardiansOfAdelia.getCharacterSelectionScreenManager().clear(player);
    }

}
