package io.github.lix3nn53.guardiansofadelia.bossbar;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class HealthBarManager {

    private static final HashMap<Player, HealthBar> playerToHealthBar = new HashMap<Player, HealthBar>();

    public static void showToPlayerFor10Seconds(Player player, HealthBar healthBar) {
        if (playerToHealthBar.containsKey(player)) {
            HealthBar currentHealthBar = playerToHealthBar.get(player);
            currentHealthBar.destroy();
        }
        playerToHealthBar.put(player, healthBar);
        healthBar.addPlayer(player);
        new BukkitRunnable() {
            @Override
            public void run() {
                healthBar.destroy();
                playerToHealthBar.remove(player, healthBar);
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), 20 * 10);
    }
}
