package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.utilities.shutdown.ShutdownTask;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class MyPlayerCommandPreprocessEvent implements Listener {

    @EventHandler
    public static void onCommand(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage();

        if (command.equals("/stop")) {
            event.setCancelled(true);

            Bukkit.getScheduler().scheduleSyncDelayedTask(GuardiansOfAdelia.getInstance(), new ShutdownTask());
        }
    }
}
