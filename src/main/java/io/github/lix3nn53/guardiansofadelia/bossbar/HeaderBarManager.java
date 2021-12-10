package io.github.lix3nn53.guardiansofadelia.bossbar;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class HeaderBarManager {

    public static void onPlayerJoin(Player player, GuardianData guardianData) {
        HeaderBar bar = new HeaderBar(player, guardianData);

        // show to player
        bar.showToPlayer(player);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancel();
                    bar.destroy();
                    return;
                }

                bar.update();
            }
        }.runTaskTimerAsynchronously(GuardiansOfAdelia.getInstance(), 5L, 10L);
    }
}
