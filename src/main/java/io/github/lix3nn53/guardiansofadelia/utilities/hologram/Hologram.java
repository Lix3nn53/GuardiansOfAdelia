package io.github.lix3nn53.guardiansofadelia.utilities.hologram;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

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
        as.setRemoveWhenFarAway(false);
        type = HologramType.TEXT;
    }

    public Hologram(Location loc, ItemStack itemStack) {
        as = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        as.setVisible(false);
        as.setGravity(false);
        as.setMarker(true);
        as.setInvulnerable(true);
        Item item = loc.getWorld().dropItem(loc, itemStack);
        as.addPassenger(item);
        as.setRemoveWhenFarAway(false);
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
        as.setRemoveWhenFarAway(false);
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
