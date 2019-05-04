package io.github.lix3nn53.guardiansofadelia.revive;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Tomb {

    private final Player owner;
    private final Location baseLocation;
    private ArmorStand tombModel;

    public Tomb(Player owner, Location deathLocation) {
        this.owner = owner;
        this.baseLocation = deathLocation.clone();
        this.baseLocation.setYaw(0f);
        this.baseLocation.setPitch(0f);
        createModel();
    }

    public void createModel() {
        this.tombModel = (ArmorStand) baseLocation.getWorld().spawnEntity(baseLocation, EntityType.ARMOR_STAND);
        ItemStack itemStack = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(10000001);
        itemMeta.setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);
        this.tombModel.setHelmet(itemStack);
        this.tombModel.setVisible(false);
        this.tombModel.setCustomName(ChatColor.DARK_PURPLE + "< Tomb " + ChatColor.LIGHT_PURPLE + owner.getName() + ChatColor.DARK_PURPLE + " >");
        this.tombModel.setCustomNameVisible(true);
        this.tombModel.setInvulnerable(true);
        this.tombModel.setGravity(false);
    }

    public void remove() {
        this.tombModel.remove();
        TombManager.onTombRemove(this);
    }

    public boolean isNear() {
        if (owner.getWorld().getName().equals("world")) {
            return owner.getLocation().distanceSquared(baseLocation) < 20;
        }
        return false;
    }

    public Location getBaseLocation() {
        return baseLocation;
    }

    public Player getOwner() {
        return owner;
    }
}
