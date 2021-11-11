package io.github.lix3nn53.guardiansofadelia.jobs.gathering;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.DroppedItemWatcher;
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
    private Location baseLocation;
    private EulerAngle rotation;
    private ArmorStand armorStand;
    private boolean onCooldown = false;
    private boolean beingGathered = false;

    public GatheringModelState(int id, Location baseLocation, EulerAngle rotation) {
        this.id = id;
        this.baseLocation = baseLocation;
        this.rotation = rotation;
    }

    public void createModel(GatheringModelData gatheringModelData) {
        int customModelData = gatheringModelData.getCustomModelData();
        int cooldownCustomModelData = gatheringModelData.getCooldownCustomModelData();
        Material material = gatheringModelData.getMaterial();
        String title = gatheringModelData.getTitle();

        float x = (float) Math.toRadians(baseLocation.getPitch());
        float y = (float) Math.toRadians(baseLocation.getYaw());
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

            boolean isDisguise = gatheringModelData.isDisguise();
            if (isDisguise) {
                MiscDisguise disguise = new MiscDisguise(DisguiseType.DROPPED_ITEM);
                DroppedItemWatcher watcher = (DroppedItemWatcher) disguise.getWatcher();
                watcher.setItemStack(itemStack);

                DisguiseAPI.disguiseToAll(armorStand, disguise);
            }
        }

        if (onCooldown) {
            armorStand.setCustomName(title + ChatPalette.GRAY + " (On Cooldown)");
        } else {
            armorStand.setCustomName(title);
        }

        armorStand.setVisible(false);
        armorStand.setInvulnerable(true);
        armorStand.setGravity(false);
        armorStand.setCustomNameVisible(true);
        armorStand.setSmall(true);
        armorStand.setHeadPose(this.rotation);
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public void onLoot(GatheringModelData gatheringModelData) {
        String title = gatheringModelData.getTitle();

        onCooldown = true;
        setBeingGathered(false);
        armorStand.setCustomName(title + ChatPalette.GRAY + " (On Cooldown)");

        EntityEquipment equipment = armorStand.getEquipment();
        ItemStack helmet = equipment.getHelmet();
        if (helmet != null) {
            int cooldownCustomModelData = gatheringModelData.getCooldownCustomModelData();

            ItemMeta itemMeta = helmet.getItemMeta();
            itemMeta.setCustomModelData(cooldownCustomModelData);
            helmet.setItemMeta(itemMeta);
            equipment.setHelmet(helmet);

            boolean isDisguise = gatheringModelData.isDisguise();
            if (isDisguise) {
                MiscDisguise disguise = new MiscDisguise(DisguiseType.DROPPED_ITEM);
                DroppedItemWatcher watcher = (DroppedItemWatcher) disguise.getWatcher();
                watcher.setItemStack(helmet);

                DisguiseAPI.disguiseToAll(armorStand, disguise);
            }
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

                        boolean isDisguise = gatheringModelData.isDisguise();
                        if (isDisguise) {
                            MiscDisguise disguise = new MiscDisguise(DisguiseType.DROPPED_ITEM);
                            DroppedItemWatcher watcher = (DroppedItemWatcher) disguise.getWatcher();
                            watcher.setItemStack(helmet);

                            DisguiseAPI.disguiseToAll(armorStand, disguise);
                        }
                    }
                }
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), 20 * 20L); // TODO make a premium server buff for faster cooldown?
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

    public EulerAngle getRotation() {
        return rotation;
    }

    public void setRotation(EulerAngle eulerAngle) {
        this.rotation = eulerAngle;
    }

    public void setBaseLocation(Location location) {
        this.baseLocation = location;
    }
}
