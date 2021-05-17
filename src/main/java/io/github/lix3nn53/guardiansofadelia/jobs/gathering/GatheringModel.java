package io.github.lix3nn53.guardiansofadelia.jobs.gathering;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;


public class GatheringModel {

    // Id to get model data with
    private final int id;

    // State
    private final Location baseLocation;
    private ArmorStand armorStand;
    private boolean onCooldown = false;
    private boolean beingGathered = false;

    public GatheringModel(int id, Location baseLocation) {
        this.id = id;
        this.baseLocation = baseLocation;
    }

    public void createModel(GatheringModelData gatheringModelData) {
        int customModelData = gatheringModelData.getCustomModelData();
        int cooldownCustomModelData = gatheringModelData.getCooldownCustomModelData();
        Material material = gatheringModelData.getMaterial();
        String title = gatheringModelData.getTitle();

        double x = Math.toRadians(baseLocation.getPitch());
        double y = Math.toRadians(baseLocation.getYaw());
        EulerAngle eulerAngle = new EulerAngle(x, y, 0);

        Location clone = baseLocation.clone();
        clone.setPitch(0);
        clone.setYaw(0);

        this.armorStand = (ArmorStand) clone.getWorld().spawnEntity(clone, EntityType.ARMOR_STAND);
        armorStand.setHeadPose(eulerAngle);
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (onCooldown) {
            itemMeta.setCustomModelData(cooldownCustomModelData);
            armorStand.setCustomName(title + ChatColor.GRAY + " (On Cooldown)");
        } else {
            itemMeta.setCustomModelData(customModelData);
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

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public void onLoot(GatheringModelData gatheringModelData) {
        int customModelData = gatheringModelData.getCustomModelData();
        int cooldownCustomModelData = gatheringModelData.getCooldownCustomModelData();
        String title = gatheringModelData.getTitle();

        onCooldown = true;
        setBeingGathered(false);
        armorStand.setCustomName(title + ChatColor.GRAY + " (On Cooldown)");
        EntityEquipment equipment = armorStand.getEquipment();
        ItemStack helmet = equipment.getHelmet();
        ItemMeta itemMeta = helmet.getItemMeta();
        itemMeta.setCustomModelData(cooldownCustomModelData);
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

    public void resetName(GatheringModelData gatheringModelData) {
        armorStand.setCustomName(gatheringModelData.getTitle());
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

    public int getId() {
        return id;
    }

    public Location getBaseLocation() {
        return baseLocation;
    }
}
