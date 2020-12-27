package io.github.lix3nn53.guardiansofadelia.quests.actions;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportAction implements Action {

    private final Location location;
    private final long delay;

    public TeleportAction(Location location, long delay) {
        this.location = location;
        this.delay = delay;
    }

    @Override
    public void perform(Player player, int questID, int taskIndex) {
        new BukkitRunnable() {
            @Override
            public void run() {
                player.teleport(location);
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), delay);
    }

    @Override
    public boolean preventTaskCompilation() {
        return false;
    }
}
