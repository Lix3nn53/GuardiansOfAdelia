package io.github.lix3nn53.guardiansofadelia.minigames.checkpoint;

import io.github.lix3nn53.guardiansofadelia.utilities.hologram.Hologram;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.DroppedItemWatcher;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Checkpoint {

    private final Location location;
    private ArmorStand armorStand;

    public Checkpoint(Location location) {
        this.location = location;
    }

    public void createModel() {
        ArmorStand rider = new Hologram(location).getArmorStand();

        Hologram hologram = new Hologram(location, rider);

        this.armorStand = hologram.getArmorStand();

        ItemStack holoItem = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta itemMeta = holoItem.getItemMeta();
        itemMeta.setCustomModelData(5);
        itemMeta.setUnbreakable(true);
        holoItem.setItemMeta(itemMeta);

        MiscDisguise disguise = new MiscDisguise(DisguiseType.DROPPED_ITEM);
        DroppedItemWatcher watcher = (DroppedItemWatcher) disguise.getWatcher();
        watcher.setItemStack(holoItem);
        watcher.setCustomName("Checkpoint");
        watcher.setCustomNameVisible(true);

        DisguiseAPI.disguiseToAll(hologram.getArmorStand().getPassengers().get(0), disguise);
    }

    public Location getLocation() {
        return location;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }
}
