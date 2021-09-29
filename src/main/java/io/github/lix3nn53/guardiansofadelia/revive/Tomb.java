package io.github.lix3nn53.guardiansofadelia.revive;


import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
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
    }

    public void createModel() {
        this.tombModel = (ArmorStand) baseLocation.getWorld().spawnEntity(baseLocation, EntityType.ARMOR_STAND);
        ItemStack itemStack = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(1);
        itemMeta.setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);
        EntityEquipment equipment = tombModel.getEquipment();
        equipment.setHelmet(itemStack);
        this.tombModel.setVisible(false);
        this.tombModel.setCustomName(ChatPalette.PURPLE + "< Tomb " + ChatPalette.PURPLE_LIGHT + owner.getName() + ChatPalette.PURPLE + " >");
        this.tombModel.setCustomNameVisible(true);
        this.tombModel.setInvulnerable(true);
        this.tombModel.setGravity(false);
        this.tombModel.setGlowing(true);
    }

    public void remove() {
        if (this.tombModel != null) this.tombModel.remove();
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
