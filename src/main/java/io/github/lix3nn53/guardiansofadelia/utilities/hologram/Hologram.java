package io.github.lix3nn53.guardiansofadelia.utilities.hologram;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class Hologram {

    private final Location loc;
    private String title;
    private Entity rider;
    private ArmorStand as;
    private final HologramType type;

    public Hologram(Location loc, String title) {
        this.loc = loc;
        this.title = title;
        as = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        as.setVisible(false);
        as.setGravity(false);
        as.setMarker(true);
        as.setInvulnerable(true);
        as.setCustomNameVisible(true);
        as.setCustomName(title);
        type = HologramType.TEXT;
    }

    public Hologram(Location loc, Entity rider) {
        this.loc = loc;
        this.rider = rider;
        as = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        as.setVisible(false);
        as.setGravity(false);
        as.setMarker(true);
        as.setInvulnerable(true);
        as.addPassenger(rider);
        type = HologramType.PASSENGER;
    }

    public Hologram(Location loc) {
        this.loc = loc;
        as = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        as.setVisible(false);
        as.setGravity(false);
        as.setMarker(true);
        as.setInvulnerable(true);
        as.setCustomNameVisible(false);
        type = HologramType.INVISIBLE;
    }

    public ArmorStand getArmorStand() {
        return as;
    }

    public HologramType getType() {
        return type;
    }

    public enum HologramType {
        TEXT,
        PASSENGER,
        INVISIBLE
    }

    public void respawn() {
        as = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        as.setVisible(false);
        as.setGravity(false);
        as.setMarker(true);
        as.setInvulnerable(true);
        if (title != null) {
            as.setCustomNameVisible(true);
            as.setCustomName(title);
        }
        if (rider != null) {
            as.addPassenger(rider);
        }
    }

    public void remove() {
        as.remove();
    }

    public Location getLocation() {
        return loc;
    }
}
