package io.github.lix3nn53.guardiansofadelia.utilities.hologram;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.utilities.LocationUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FakeIndicator {

    public static void showPlayerNonPacket(Player player, String text, Location baseLocation) {
        Location indicatorLocation = LocationUtils.randomPointNoY(baseLocation, 1.5);

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
        Location indicatorLocation = LocationUtils.randomPointNoY(baseLocation, 1.5);
        FakeHologram fakeHologramProtocolLib = new FakeHologram(indicatorLocation, text);

        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        fakeHologramProtocolLib.showToPlayer(player, protocolManager);
        new BukkitRunnable() {

            @Override
            public void run() {
                fakeHologramProtocolLib.remove(player, protocolManager);
                cancel();
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), 18L);
    }
}
