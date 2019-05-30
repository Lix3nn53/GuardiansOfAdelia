package io.github.lix3nn53.guardiansofadelia.minigames.portals;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Portal {
    private final Location baseLocation;
    private final PortalColor portalColor;
    private ArmorStand armorStand;

    public Portal(Location baseLocation, PortalColor portalColor) {
        this.baseLocation = baseLocation;
        this.portalColor = portalColor;
    }

    public void createModel() {
        this.armorStand = (ArmorStand) baseLocation.getWorld().spawnEntity(baseLocation, EntityType.ARMOR_STAND);
        ItemStack itemStack = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(this.portalColor.getCustomModelData());
        itemMeta.setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);
        armorStand.setHelmet(itemStack);
        armorStand.setVisible(false);
        armorStand.setInvulnerable(true);
        armorStand.setGravity(false);
    }

    public Location getBaseLocation() {
        return baseLocation;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }
}
