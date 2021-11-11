package io.github.lix3nn53.guardiansofadelia.utilities.hologram;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.utilities.LocationUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DamageIndicator {

    public static void spawnNonPacket(String text, Location baseLocation) {
        Location indicatorLocation = LocationUtils.randomPointNoY(baseLocation, 1.2f);

        Hologram hologram = new Hologram(indicatorLocation, text);

        new BukkitRunnable() {

            @Override
            public void run() {
                hologram.getArmorStand().remove();
                cancel();
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), 18L);
    }

    public static void showPlayer(Player player, String text, Location location, long duration) {
        FakeHologram fakeHologram = new FakeHologram(location, text);

        fakeHologram.showToPlayer(player);
        new BukkitRunnable() {

            @Override
            public void run() {
                fakeHologram.destroy(player);
                cancel();
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), duration);
    }

    public static void showPlayerRandomLocation(Player player, String text, Location baseLocation, long duration) {
        Location location = LocationUtils.randomPointNoY(baseLocation, 1.2f);

        showPlayer(player, text, location, duration);
    }
}
