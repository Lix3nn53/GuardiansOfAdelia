package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class MyPlayerDeathEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();

        Location location = player.getLocation();

        if (location.getWorld().getName().equals("world")) {
            player.spigot().respawn();
            if (GuardiansOfAdelia.getCharacterSelectionScreenManager().isPlayerInCharSelection(player)) {
                player.teleport(GuardiansOfAdelia.getCharacterSelectionScreenManager().getCharacterSelectionCenter());
            } else {
                Town town = TownManager.getNearestTown(location);
                player.teleport(town.getLocation());
            }
        }
    }

}
