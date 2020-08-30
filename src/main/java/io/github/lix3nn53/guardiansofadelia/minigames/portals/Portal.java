package io.github.lix3nn53.guardiansofadelia.minigames.portals;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.EulerAngle;

public class Portal {
    private final Location baseLocation;
    private final PortalColor portalColor;
    private ArmorStand armorStand;

    public Portal(Location baseLocation, PortalColor portalColor) {
        this.baseLocation = baseLocation;
        this.portalColor = portalColor;
    }

    public void createModel() {
        double x = Math.toRadians(baseLocation.getPitch());
        double y = Math.toRadians(baseLocation.getYaw());
        EulerAngle eulerAngle = new EulerAngle(x, y, 0);

        Location clone = baseLocation.clone();
        clone.setPitch(0);
        clone.setYaw(0);

        this.armorStand = (ArmorStand) clone.getWorld().spawnEntity(clone, EntityType.ARMOR_STAND);
        armorStand.setHeadPose(eulerAngle);
        ItemStack itemStack = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(this.portalColor.getCustomModelData());
        itemMeta.setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);
        EntityEquipment equipment = armorStand.getEquipment();
        equipment.setHelmet(itemStack);
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
