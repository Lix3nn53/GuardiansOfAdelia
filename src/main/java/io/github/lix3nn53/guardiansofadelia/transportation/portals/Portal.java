package io.github.lix3nn53.guardiansofadelia.transportation.portals;


import net.md_5.bungee.api.ChatColor;
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
    private final String title;
    private ArmorStand armorStand;

    public Portal(Location baseLocation, PortalColor portalColor, String title) {
        this.baseLocation = baseLocation;
        this.portalColor = portalColor;
        this.title = title;
    }

    public void createModel() {
        float x = (float) Math.toRadians(baseLocation.getPitch());
        float y = (float) Math.toRadians(baseLocation.getYaw());
        EulerAngle eulerAngle = new EulerAngle(x, y, 0);

        Location clone = baseLocation.clone();
        clone.setPitch(0);
        clone.setYaw(0);

        this.armorStand = (ArmorStand) clone.getWorld().spawnEntity(clone, EntityType.ARMOR_STAND);
        armorStand.setHeadPose(eulerAngle);
        ItemStack itemStack = new ItemStack(Material.WOODEN_SHOVEL);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(this.portalColor.getCustomModelData());
        itemMeta.setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);
        EntityEquipment equipment = armorStand.getEquipment();
        equipment.setHelmet(itemStack);
        armorStand.setVisible(false);
        armorStand.setInvulnerable(true);
        armorStand.setGravity(false);
        armorStand.setCustomNameVisible(true);
        String s = ChatColor.translateAlternateColorCodes('&', title);
        armorStand.setCustomName(s);
    }

    public Location getBaseLocation() {
        return baseLocation;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }
}
