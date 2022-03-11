package io.github.lix3nn53.guardiansofadelia.utilities.hologram;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.utilities.LocationUtils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class DamageIndicator {

    private static int ENTITY_ID = 123123;

//    public static void spawnNonPacket(String text, Location baseLocation, long duration) {
//        Location indicatorLocation = LocationUtils.randomPointNoY(baseLocation, 1.2f);
//
//        Hologram hologram = new Hologram(indicatorLocation, text);
//
//        new BukkitRunnable() {
//
//            @Override
//            public void run() {
//                hologram.getArmorStand().remove();
//                cancel();
//            }
//        }.runTaskLater(GuardiansOfAdelia.getInstance(), duration);
//    }

    public static void showPlayer(Player player, String text, Location location, long duration) {
        FakeHologram fakeHologram = new FakeHologram(ENTITY_ID, location, text);
        ENTITY_ID++;
        fakeHologram.show(player);

        new BukkitRunnable() {

            @Override
            public void run() {
                fakeHologram.hide(player);
                cancel();
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), duration);
    }

    public static void showPlayerNearby(String text, Location location, long duration, float radius) {
        FakeHologram fakeHologram = new FakeHologram(ENTITY_ID, location, text);
        ENTITY_ID++;

        Collection<Entity> nearbyEntities = location.getWorld().getNearbyEntities(location, radius, radius, radius);
        for (Entity e : nearbyEntities) {
            if (e instanceof Player) {
                Player p = (Player) e;

                fakeHologram.show(p);

                new BukkitRunnable() {

                    @Override
                    public void run() {
                        fakeHologram.hide(p);
                        cancel();
                    }
                }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), duration);
            }
        }
    }

    public static void showPlayerRandomLocation(Player player, String text, Location baseLocation, long duration) {
        Location location = LocationUtils.randomPointNoY(baseLocation, 1.2f);

        showPlayer(player, text, location, duration);
    }

//    public static void showPlayerRandomLocation(Player player, String text, Location baseLocation, long duration) {
//        Location location = LocationUtils.randomPointNoY(baseLocation, 1.2f);
//
//        spawnNonPacket(text, location, duration);
//    }
}
