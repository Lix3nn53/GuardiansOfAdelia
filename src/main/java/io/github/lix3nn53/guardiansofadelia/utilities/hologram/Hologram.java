package io.github.lix3nn53.guardiansofadelia.utilities.hologram;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class Hologram {

    private final ArmorStand as;
    private final HologramType type;

    public Hologram(Location loc, String title) {
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
        as = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        as.setVisible(false);
        as.setGravity(false);
        as.setMarker(true);
        as.setInvulnerable(true);
        as.addPassenger(rider);
        type = HologramType.ITEM;
    }

    public Hologram(Location loc) {
        as = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        as.setVisible(false);
        as.setGravity(false);
        as.setMarker(true);
        as.setInvulnerable(true);
        as.setCustomNameVisible(false);
        as.setCustomName("");
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
        ITEM,
        INVISIBLE
    }
}
