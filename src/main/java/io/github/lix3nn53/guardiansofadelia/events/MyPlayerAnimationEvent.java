package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.Bazaar;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.BazaarManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringManager;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.checkpoint.Checkpoint;
import io.github.lix3nn53.guardiansofadelia.minigames.checkpoint.CheckpointManager;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.InstantTeleportPortal;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.Portal;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.PortalManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.revive.TombManager;
import org.bukkit.ChatColor;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

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
            UUID uniqueId = player.getUniqueId();
            if (GuardianDataManager.hasGuardianData(uniqueId)) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(uniqueId);
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
                if (portal != null) { //portal model
                    if (PortalManager.isInstantTeleportPortal(portal)) {
                        InstantTeleportPortal instantTeleport = PortalManager.getInstantTeleportPortal(portal);
                        if (instantTeleport.canTeleport(player)) {
                            new BukkitRunnable() {

                                int timeoutSecond = 3;
                                int seconds = 0;

                                @Override
                                public void run() {
                                    int i = timeoutSecond - seconds;
                                    if (i <= 0) {
                                        player.teleport(instantTeleport.getDestination());
                                        cancel();
                                        return;
                                    }
                                    player.sendTitle("", ChatColor.AQUA + "Teleporting in.. " + i, 5, 20, 5);
                                    seconds++;
                                }
                            }.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, 20L);
                        }
                    } else { //dungeon portal
                        DungeonTheme theme = MiniGameManager.getDungeonFromPortal(portal);
                        if (theme != null) {
                            theme.getJoinQueueGui().openInventory(player);
                        }
                    }
                    break;
                } else if (BazaarManager.isBazaar(armorStand)) {
                    Player owner = BazaarManager.getOwner(armorStand);
                    UUID uuid = owner.getUniqueId();
                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
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

                        }
                        break;
                    }
                }
            }
        }

        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (!itemInMainHand.hasItemMeta()) return;
        ItemMeta itemMeta = itemInMainHand.getItemMeta();
        if (itemMeta.hasCustomModelData()) return;

        GatheringManager.startGathering(player, itemInMainHand, targetType);
    }
}
