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


public class GatheringModelState {

    // Id to get model data with
    private final int id;

    // State
    private final Location baseLocation;
    private ArmorStand armorStand;
    private boolean onCooldown = false;
    private boolean beingGathered = false;

    public GatheringModelState(int id, Location baseLocation) {
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

        if (material != null) {
            ItemStack itemStack = new ItemStack(material);
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (onCooldown) {
                itemMeta.setCustomModelData(cooldownCustomModelData);
            } else {
                itemMeta.setCustomModelData(customModelData);
            }
            itemMeta.setUnbreakable(true);
            itemStack.setItemMeta(itemMeta);
            EntityEquipment equipment = armorStand.getEquipment();
            equipment.setHelmet(itemStack);
        }

        if (onCooldown) {
            armorStand.setCustomName(title + ChatColor.GRAY + " (On Cooldown)");
        } else {
            armorStand.setCustomName(title);
        }

        armorStand.setVisible(false);
        armorStand.setInvulnerable(true);
        armorStand.setGravity(false);
        armorStand.setCustomNameVisible(true);
        armorStand.setSmall(true);

        boolean playAnimation = gatheringModelData.isPlayAnimation();

        if (playAnimation) {
            new BukkitRunnable() {

                final double maxDistance = 1.2;
                final double speed = 0.1;
                final double startHeight = baseLocation.getY();
                boolean playingBack = false;

                @Override
                public void run() {
                    if (!armorStand.isValid()) {
                        cancel();
                        return;
                    }
                    if (onCooldown) {
                        return;
                    }

                    Location location = armorStand.getLocation();
                    double currentHeight = location.getY();

                    double distance = currentHeight - startHeight;

                    if (!playingBack) {
                        armorStand.teleport(location.add(0, speed, 0));

                        if (distance + speed >= maxDistance) {
                            playingBack = true;
                        }
                    } else {
                        armorStand.teleport(location.add(0, -speed, 0));

                        if (distance - speed <= 0) {
                            playingBack = false;
                        }
                    }

                    EulerAngle headPose = armorStand.getHeadPose();
                    armorStand.setHeadPose(headPose.add(0.1, 0, 0.1));
                }
            }.runTaskTimerAsynchronously(GuardiansOfAdelia.getInstance(), 5L, 5L);
        }
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public void onLoot(GatheringModelData gatheringModelData) {
        String title = gatheringModelData.getTitle();

        onCooldown = true;
        setBeingGathered(false);
        armorStand.setCustomName(title + ChatColor.GRAY + " (On Cooldown)");

        EntityEquipment equipment = armorStand.getEquipment();
        ItemStack helmet = equipment.getHelmet();
        if (helmet != null) {
            int cooldownCustomModelData = gatheringModelData.getCooldownCustomModelData();

            ItemMeta itemMeta = helmet.getItemMeta();
            itemMeta.setCustomModelData(cooldownCustomModelData);
            helmet.setItemMeta(itemMeta);
            equipment.setHelmet(helmet);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                onCooldown = false;
                if (baseLocation.getChunk().isLoaded()) {
                    armorStand.setCustomName(title);

                    if (helmet != null) {
                        int customModelData = gatheringModelData.getCustomModelData();

                        EntityEquipment equipment = armorStand.getEquipment();
                        ItemMeta itemMeta = helmet.getItemMeta();
                        itemMeta.setCustomModelData(customModelData);
                        helmet.setItemMeta(itemMeta);
                        equipment.setHelmet(helmet);
                    }
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
