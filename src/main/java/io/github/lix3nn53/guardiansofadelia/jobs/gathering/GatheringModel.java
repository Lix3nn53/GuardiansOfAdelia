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
    private final int cooldownCustomModelData;
    private final String title;
    private ArmorStand armorStand;
    private boolean onCooldown = false;
    private boolean beingGathered = false;

    public GatheringModel(Location baseLocation, int customModelData, int cooldownCustomModelData, String title) {
        this.baseLocation = baseLocation;
        this.customModelData = customModelData;
        this.cooldownCustomModelData = cooldownCustomModelData;
        this.title = ChatColor.translateAlternateColorCodes('&', title);
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
        ItemStack itemStack = new ItemStack(GatheringManager.gatheringMaterial);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (onCooldown) {
            itemMeta.setCustomModelData(this.cooldownCustomModelData);
            armorStand.setCustomName(title + ChatColor.GRAY + " (On Cooldown)");
        } else {
            itemMeta.setCustomModelData(this.customModelData);
            armorStand.setCustomName(title);
        }
        itemMeta.setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);
        EntityEquipment equipment = armorStand.getEquipment();
        equipment.setHelmet(itemStack);
        armorStand.setVisible(false);
        armorStand.setInvulnerable(true);
        armorStand.setGravity(false);
        armorStand.setCustomNameVisible(true);
        armorStand.setSmall(true);
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
        armorStand.setCustomName(title + ChatColor.GRAY + " (On Cooldown)");
        EntityEquipment equipment = armorStand.getEquipment();
        ItemStack helmet = equipment.getHelmet();
        ItemMeta itemMeta = helmet.getItemMeta();
        itemMeta.setCustomModelData(this.cooldownCustomModelData);
        helmet.setItemMeta(itemMeta);
        equipment.setHelmet(helmet);

        new BukkitRunnable() {
            @Override
            public void run() {
                onCooldown = false;
                if (baseLocation.getChunk().isLoaded()) {
                    armorStand.setCustomName(title);
                    EntityEquipment equipment = armorStand.getEquipment();
                    ItemStack helmet = equipment.getHelmet();
                    ItemMeta itemMeta = helmet.getItemMeta();
                    itemMeta.setCustomModelData(customModelData);
                    helmet.setItemMeta(itemMeta);
                    equipment.setHelmet(helmet);
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

    public boolean isOnCooldown() {
        return onCooldown;
    }
}
