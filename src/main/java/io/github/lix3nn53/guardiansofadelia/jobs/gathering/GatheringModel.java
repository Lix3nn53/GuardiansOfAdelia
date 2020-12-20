package io.github.lix3nn53.guardiansofadelia.jobs.gathering;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;


public class GatheringModel {
    private final Location baseLocation;
    private final int customModelData;
    private final String title;
    private ArmorStand armorStand;
    private boolean onCooldown = false;
    private boolean beingGathered = false;

    public GatheringModel(Location baseLocation, int customModelData, String title) {
        this.baseLocation = baseLocation;
        this.customModelData = customModelData;
        this.title = title;
    }

    public void createModel() {
        if (onCooldown) return;

        double x = Math.toRadians(baseLocation.getPitch());
        double y = Math.toRadians(baseLocation.getYaw());
        EulerAngle eulerAngle = new EulerAngle(x, y, 0);

        Location clone = baseLocation.clone();
        clone.setPitch(0);
        clone.setYaw(0);

        this.armorStand = (ArmorStand) clone.getWorld().spawnEntity(clone, EntityType.ARMOR_STAND);
        armorStand.setHeadPose(eulerAngle);
        ItemStack itemStack = new ItemStack(GatheringManager.gatheringMaterial);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(this.customModelData);
        itemMeta.setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);
        EntityEquipment equipment = armorStand.getEquipment();
        equipment.setHelmet(itemStack);
        armorStand.setVisible(false);
        armorStand.setInvulnerable(true);
        armorStand.setGravity(false);
        armorStand.setCustomNameVisible(true);
        armorStand.setSmall(true);
        String s = ChatColor.translateAlternateColorCodes('&', title);
        armorStand.setCustomName(s);
    }

    public Location getBaseLocation() {
        return baseLocation;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public void onLoot() {
        onCooldown = true;
        setBeingGathered(false);
        armorStand.remove();

        new BukkitRunnable() {
            @Override
            public void run() {
                onCooldown = false;
                if (baseLocation.getChunk().isLoaded()) {
                    createModel();
                }
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), 20 * 60L);
    }

    public void resetName() {
        armorStand.setCustomName(title);
    }

    public boolean isBeingGathered() {
        return beingGathered;
    }

    public void setBeingGathered(boolean beingGathered) {
        this.beingGathered = beingGathered;
    }
}
