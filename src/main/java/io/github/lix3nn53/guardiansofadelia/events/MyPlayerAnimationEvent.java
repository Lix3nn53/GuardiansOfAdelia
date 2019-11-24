package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.Bazaar;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.BazaarManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.jobs.GatheringType;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.Portal;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.PortalManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.revive.TombManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
        Material type = targetBlock.getType();

        //quest TaskReach
        if (type.equals(Material.EMERALD_BLOCK) || type.equals(Material.GOLD_BLOCK) || type.equals(Material.REDSTONE_BLOCK) || type.equals(Material.DIAMOND_BLOCK)) {
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
                        Location instantTeleportLocation = PortalManager.getInstantTeleportLocation(portal);
                        new BukkitRunnable() {

                            int timeoutSecond = 3;
                            int seconds = 0;

                            @Override
                            public void run() {
                                int i = timeoutSecond - seconds;
                                if (i <= 0) {
                                    player.teleport(instantTeleportLocation);
                                    cancel();
                                    return;
                                }
                                player.sendTitle("", ChatColor.AQUA + "Teleporting in.. " + i, 5, 20, 5);
                                seconds++;
                            }
                        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, 20L);
                    } else {
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
                }
            }
        }

        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (!itemInMainHand.hasItemMeta()) return;
        ItemMeta itemMeta = itemInMainHand.getItemMeta();
        if (itemMeta.hasCustomModelData()) return;

        if (type.equals(Material.EMERALD_ORE) || type.equals(Material.GOLD_ORE) || type.equals(Material.REDSTONE_ORE) || type.equals(Material.LAPIS_ORE)) {
            Material handType = itemInMainHand.getType();
            if (handType.equals(Material.WOODEN_PICKAXE) || handType.equals(Material.STONE_PICKAXE) || handType.equals(Material.IRON_PICKAXE)
                    || handType.equals(Material.GOLDEN_PICKAXE) || handType.equals(Material.DIAMOND_PICKAXE)) {
                GatheringType.MINING_JEWELRY.startGatheringAnimation(player, itemInMainHand);
            }
        } else if (type.equals(Material.COAL_ORE) || type.equals(Material.IRON_ORE) || type.equals(Material.DIAMOND_ORE)) {
            Material handType = itemInMainHand.getType();
            if (handType.equals(Material.WOODEN_PICKAXE) || handType.equals(Material.STONE_PICKAXE) || handType.equals(Material.IRON_PICKAXE)
                    || handType.equals(Material.GOLDEN_PICKAXE) || handType.equals(Material.DIAMOND_PICKAXE)) {
                GatheringType.MINING_ORE.startGatheringAnimation(player, itemInMainHand);
            }
        } else if (type.equals(Material.DANDELION) || type.equals(Material.POPPY)
                || type.equals(Material.BLUE_ORCHID) || type.equals(Material.ALLIUM) || type.equals(Material.AZURE_BLUET) || type.equals(Material.RED_TULIP)
                || type.equals(Material.ORANGE_TULIP) || type.equals(Material.WHITE_TULIP) || type.equals(Material.PINK_TULIP) || type.equals(Material.OXEYE_DAISY)
                || type.equals(Material.CORNFLOWER) || type.equals(Material.LILY_OF_THE_VALLEY) || type.equals(Material.WITHER_ROSE) || type.equals(Material.SUNFLOWER)
                || type.equals(Material.LILAC) || type.equals(Material.ROSE_BUSH) || type.equals(Material.PEONY)) {
            Material handType = itemInMainHand.getType();
            if (handType.equals(Material.WOODEN_HOE) || handType.equals(Material.STONE_HOE) || handType.equals(Material.IRON_HOE)
                    || handType.equals(Material.GOLDEN_HOE) || handType.equals(Material.DIAMOND_HOE)) {
                GatheringType.HARVESTING_FLOWER.startGatheringAnimation(player, itemInMainHand);
            }
        } else if (type.equals(Material.ACACIA_WOOD) || type.equals(Material.BIRCH_WOOD)
                || type.equals(Material.DARK_OAK_WOOD) || type.equals(Material.JUNGLE_WOOD) || type.equals(Material.OAK_WOOD) || type.equals(Material.SPRUCE_WOOD)
                || type.equals(Material.ACACIA_LOG) || type.equals(Material.BIRCH_LOG)
                || type.equals(Material.DARK_OAK_LOG) || type.equals(Material.JUNGLE_LOG) || type.equals(Material.OAK_LOG) || type.equals(Material.SPRUCE_LOG)) {
            Material handType = itemInMainHand.getType();
            if (handType.equals(Material.WOODEN_AXE) || handType.equals(Material.STONE_AXE) || handType.equals(Material.IRON_AXE)
                    || handType.equals(Material.GOLDEN_AXE) || handType.equals(Material.DIAMOND_AXE)) {
                GatheringType.WOODCUTTING.startGatheringAnimation(player, itemInMainHand);
            }
        }
    }
}
