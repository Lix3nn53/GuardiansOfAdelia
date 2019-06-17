package io.github.lix3nn53.guardiansofadelia.utilities.managers;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class PlayerTridentThrowManager {

    private static List<Player> waitingForReturn = new ArrayList<>();

    public static boolean canThrow(Player player) {
        return !waitingForReturn.contains(player);
    }

    public static void onPlayerTridentThrow(Player player) {
        waitingForReturn.add(player);
        new BukkitRunnable() {
            @Override
            public void run() {
                waitingForReturn.remove(player);
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), 20 * 15L);
    }

    public static void onPlayerTridentPickUp(Player player) {
        waitingForReturn.remove(player);
    }
}
