package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.economy.bazaar.Bazaar;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.BazaarManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringManager;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringModelState;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.checkpoint.Checkpoint;
import io.github.lix3nn53.guardiansofadelia.minigames.checkpoint.CheckpointManager;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonPrizeChestManager;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.revive.TombManager;
import io.github.lix3nn53.guardiansofadelia.transportation.TeleportationUtils;
import io.github.lix3nn53.guardiansofadelia.transportation.portals.InstantTeleportPortal;
import io.github.lix3nn53.guardiansofadelia.transportation.portals.Portal;
import io.github.lix3nn53.guardiansofadelia.transportation.portals.PortalManager;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;

public class MyPlayerAnimationEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(PlayerAnimationEvent event) {
        if (!event.getAnimationType().equals(PlayerAnimationType.ARM_SWING)) {
            return; // off hand packet, ignore.
        }

        Player player = event.getPlayer();

        HashSet<Material> transparentBlocks = new HashSet<>();
        transparentBlocks.add(Material.AIR);
        transparentBlocks.add(Material.WATER);

        Block targetBlock = player.getTargetBlock(transparentBlocks, 5);
        Material targetType = targetBlock.getType();

        //quest TaskReach
        if (targetType.equals(Material.EMERALD_BLOCK) || targetType.equals(Material.GOLD_BLOCK) || targetType.equals(Material.REDSTONE_BLOCK) || targetType.equals(Material.DIAMOND_BLOCK)) {
            if (GuardianDataManager.hasGuardianData(player)) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                if (guardianData.hasActiveCharacter()) {
                    RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                    List<Quest> questList = activeCharacter.getQuestList();
                    for (Quest quest : questList) {
                        quest.progressReachTasks(player, targetBlock.getLocation());
                    }
                }
            }
            return;
        }

        List<Entity> nearbyEntities = player.getNearbyEntities(2, 2, 2);
        for (Entity entity : nearbyEntities) {
            if (entity.getType().equals(EntityType.ARMOR_STAND)) {
                ArmorStand armorStand = (ArmorStand) entity;
                Portal portal = PortalManager.getPortalFromArmorStand(armorStand);

                GatheringModelState gatheringModelState = GatheringManager.getGatheringModelFromArmorStand(armorStand);

                DungeonPrizeChestManager.loot(armorStand, player);
                if (gatheringModelState != null) {
                    ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
                    // FISHING IS HANDLED BY IT'S OWN EVENT
                    if (!InventoryUtils.isAirOrNull(itemInMainHand) && itemInMainHand.getType().equals(Material.FISHING_ROD))
                        return;

                    GatheringManager.startGathering(player, gatheringModelState);
                    break;
                } else if (portal != null) { //portal model
                    if (PortalManager.isInstantTeleportPortal(portal)) {
                        InstantTeleportPortal instantTeleport = PortalManager.getInstantTeleportPortal(portal);
                        if (instantTeleport.canTeleport(player)) {
                            TeleportationUtils.teleport(player, instantTeleport.getDestination(), "", 5, null, 0);
                        }
                    } else { //dungeon portal
                        DungeonTheme theme = MiniGameManager.getDungeonFromPortal(portal);
                        if (theme != null) {
                            theme.getJoinQueueGui(player).openInventory(player);
                        }
                    }
                    break;
                } else if (BazaarManager.isBazaar(armorStand)) {
                    Player owner = BazaarManager.getOwner(armorStand);
                    if (GuardianDataManager.hasGuardianData(owner)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(owner);
                        if (guardianData.hasBazaar()) {
                            Bazaar bazaar = guardianData.getBazaar();
                            bazaar.showToCustomer(player);
                        }
                    }
                    break;
                } else if (TombManager.hasTomb(player)) {
                    TombManager.onReachToTomb(player);
                    break;
                } else {
                    Checkpoint checkpoint = CheckpointManager.getCheckpointFromArmorStand(armorStand);
                    if (checkpoint != null) { //portal model
                        if (MiniGameManager.isInMinigame(player)) {
                            boolean b = MiniGameManager.onCheckpointSet(player, checkpoint);
                            if (b) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
