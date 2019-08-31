package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.revive.TombManager;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.CharacterSelectionScreenManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class MyPlayerDeathEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();

        final Location deathLocation = player.getLocation();

        if (deathLocation.getY() < 1) {
            deathLocation.setY(1);
        }

        new BukkitRunnable() {

            @Override
            public void run() {
                player.spigot().respawn();

                if (deathLocation.getWorld().getName().equals("world")) {
                    if (CharacterSelectionScreenManager.isPlayerInCharSelection(player)) {
                        player.teleport(CharacterSelectionScreenManager.getCharacterSelectionCenter());
                    } else {
                        TombManager.onDeath(player, deathLocation);
                    }
                } else if (deathLocation.getWorld().getName().equals("tutorial")) {
                    player.teleport(CharacterSelectionScreenManager.getTutorialStart());
                } else if (MiniGameManager.isInMinigame(player)) {
                    MiniGameManager.onPlayerDeath(player);
                } else {
                    Town town = TownManager.getTown(1);
                    player.teleport(town.getLocation());
                }
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), 5L);
    }

}
