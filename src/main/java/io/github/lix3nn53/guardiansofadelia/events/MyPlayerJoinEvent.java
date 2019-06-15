package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class MyPlayerJoinEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().clear();

        //character selection handles loading data too
        new BukkitRunnable() {
            @Override
            public void run() {
                GuardiansOfAdelia.getCharacterSelectionScreenManager().startCharacterSelection(player);
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), 40L);
    }
}
