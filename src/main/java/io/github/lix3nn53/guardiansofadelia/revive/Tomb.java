package io.github.lix3nn53.guardiansofadelia.revive;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class Tomb {

    private final ArmorStand tompModel;
    private final Player owner;

    public Tomb(Player owner, Location location) {
        this.owner = owner;
        this.tompModel = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        ItemStack itemStack = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta instanceof Damageable) {
            Damageable damageable = (Damageable) itemMeta;
            damageable.setDamage(1);
        }
        itemMeta.setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);
        this.tompModel.setHelmet(itemStack);
        this.tompModel.setVisible(false);
        this.tompModel.setCustomName(ChatColor.DARK_PURPLE + "< Tomb " + ChatColor.LIGHT_PURPLE + owner.getName() + ChatColor.DARK_PURPLE + " >");
        this.tompModel.setCustomNameVisible(true);
        this.tompModel.setInvulnerable(true);
        this.tompModel.setGravity(false);
        this.tompModel.setRemoveWhenFarAway(false);
    }

    public Player getOwner() {
        return owner;
    }

    public void remove() {
        tompModel.remove();
    }

    public ArmorStand getTompModel() {
        return tompModel;
    }
}
