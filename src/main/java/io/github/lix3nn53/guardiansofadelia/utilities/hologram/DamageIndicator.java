package io.github.lix3nn53.guardiansofadelia.utilities.hologram;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.utilities.LocationUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DamageIndicator {

    public static void spawnNonPacket(String text, Location baseLocation) {
        Location indicatorLocation = LocationUtils.randomPointNoY(baseLocation, 1.2);

        Hologram hologram = new Hologram(indicatorLocation, text);

        new BukkitRunnable() {

            @Override
            public void run() {
                hologram.getArmorStand().remove();
                cancel();
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), 18L);
    }

    public static void showPlayer(Player player, String text, Location baseLocation) {
        Location indicatorLocation = LocationUtils.randomPointNoY(baseLocation, 1.2);
        FakeHologram fakeHologram = new FakeHologram(indicatorLocation, text);

        fakeHologram.showToPlayer(player);
        new BukkitRunnable() {

            @Override
            public void run() {
                fakeHologram.destroy(player);
                cancel();
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), 18L);
    }
}
