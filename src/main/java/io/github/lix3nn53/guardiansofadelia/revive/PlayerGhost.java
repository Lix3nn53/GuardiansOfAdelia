package io.github.lix3nn53.guardiansofadelia.revive;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerGhost {

    private final Tomb tomb;

    public PlayerGhost(Player player, Location deathLocation) {
        player.setGameMode(GameMode.SPECTATOR);
        this.tomb = new Tomb(player, deathLocation);
    }

    public void startTimer() {
        new BukkitRunnable() {

            @Override
            public void run() {
                cancel();
                tomb.remove();
                if (tomb.getOwner().getGameMode().equals(GameMode.SPECTATOR)) {
                    Town nearestTown = TownManager.getNearestTown(tomb.getOwner().getLocation());
                }
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, 20 * 90L);
    }

    public Tomb getTomb() {
        return tomb;
    }
}
