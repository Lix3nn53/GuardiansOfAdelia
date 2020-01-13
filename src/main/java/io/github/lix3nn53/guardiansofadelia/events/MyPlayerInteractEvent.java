package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.Items.PrizeChest;
import io.github.lix3nn53.guardiansofadelia.Items.PrizeChestType;
import io.github.lix3nn53.guardiansofadelia.Items.TeleportScroll;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorType;
import io.github.lix3nn53.guardiansofadelia.Items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.gui.WeaponOrShieldSkinApplyGui;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.jobs.Job;
import io.github.lix3nn53.guardiansofadelia.jobs.JobType;
import io.github.lix3nn53.guardiansofadelia.jobs.crafting.CraftingGuiManager;
import io.github.lix3nn53.guardiansofadelia.jobs.crafting.CraftingType;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiBookGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class MyPlayerInteractEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEvent(PlayerInteractEvent event) {
        EquipmentSlot hand = event.getHand();
        if (hand != null && !hand.equals(EquipmentSlot.HAND)) {
            return;
        }
        Action action = event.getAction();
        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            Player player = event.getPlayer();

            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            Material itemInMainHandType = itemInMainHand.getType();

            ArmorType armorType = ArmorType.getArmorType(itemInMainHandType);

            if (armorType != null) {
                UUID uuid = player.getUniqueId();
                if (GuardianDataManager.hasGuardianData(uuid)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);

                    if (guardianData.hasActiveCharacter()) {
                        RPGCharacter rpgCharacter = guardianData.getActiveCharacter();

                        if (StatUtils.doesCharacterMeetRequirements(itemInMainHand, player, rpgCharacter.getRpgClass())) {
                            rpgCharacter.getRpgCharacterStats().onArmorEquip(itemInMainHand, true);
                        } else {
                            event.setCancelled(true);
                        }
                    }
                }
            } else if (itemInMainHandType.equals(Material.PAPER)) {
                if (PersistentDataContainerUtil.hasString(itemInMainHand, "teleportScroll")) {
                    String teleportScroll = PersistentDataContainerUtil.getString(itemInMainHand, "teleportScroll");
                    TeleportScroll teleportScrollLocation = TeleportScroll.valueOf(teleportScroll);
                    teleportScrollLocation.teleport(player, itemInMainHand);
                }
            } else if (itemInMainHandType.equals(Material.STONE_PICKAXE)) {
                if (PersistentDataContainerUtil.hasString(itemInMainHand, "prizeDungeon")) { //dungeon chests
                    String dungeonThemeString = PersistentDataContainerUtil.getString(itemInMainHand, "prizeDungeon");
                    int typeIndex = PersistentDataContainerUtil.getInteger(itemInMainHand, "prizeType");
                    PrizeChestType type = PrizeChestType.values()[typeIndex];

                    DungeonTheme dungeonTheme = DungeonTheme.valueOf(dungeonThemeString);

                    List<ItemStack> itemStacks = dungeonTheme.generateChestItems(type);

                    PrizeChest prizeChest = new PrizeChest(dungeonTheme, type);
                    prizeChest.play(player, itemStacks);
                    player.getInventory().setItemInMainHand(null);
                }
            } else if (itemInMainHandType.equals(Material.BLACK_DYE)) {
                if (!itemInMainHand.hasItemMeta()) return;

                new WeaponOrShieldSkinApplyGui().openInventory(player);
            } else {
                Block clickedBlock = event.getClickedBlock();
                if (clickedBlock == null) return;

                Material clickedBlockType = clickedBlock.getType();

                UUID uuid = player.getUniqueId();

                if (clickedBlockType.equals(Material.CAMPFIRE)) {
                    event.setCancelled(true);
                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                            if (activeCharacter.hasJob()) {
                                Job job = activeCharacter.getJob();
                                if (job.getJobType().equals(JobType.ALCHEMIST)) {
                                    GuiGeneric levelSelection = CraftingGuiManager.getLevelSelection(CraftingType.FOOD);
                                    levelSelection.openInventory(player);
                                } else {
                                    player.sendMessage(ChatColor.RED + "Only alchemists can craft foods");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Only alchemists can craft foods");
                            }
                        }
                    }
                } else if (clickedBlockType.equals(Material.BREWING_STAND)) {
                    event.setCancelled(true);
                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                            if (activeCharacter.hasJob()) {
                                Job job = activeCharacter.getJob();
                                if (job.getJobType().equals(JobType.ALCHEMIST)) {
                                    GuiGeneric levelSelection = CraftingGuiManager.getLevelSelection(CraftingType.POTION);
                                    levelSelection.openInventory(player);
                                } else {
                                    player.sendMessage(ChatColor.RED + "Only alchemists can craft potions");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Only alchemists can craft potions");
                            }
                        }
                    }
                } else if (clickedBlockType.equals(Material.GRINDSTONE)) {
                    event.setCancelled(true);
                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                            if (activeCharacter.hasJob()) {
                                Job job = activeCharacter.getJob();
                                if (job.getJobType().equals(JobType.WEAPONSMITH)) {
                                    GuiGeneric levelSelection = CraftingGuiManager.getLevelSelection(CraftingType.WEAPON_MELEE);
                                    levelSelection.openInventory(player);
                                } else {
                                    player.sendMessage(ChatColor.RED + "Only weaponsmiths can craft melee weapons");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Only weaponsmiths can craft melee weapons");
                            }
                        }
                    }
                } else if (clickedBlockType.equals(Material.FLETCHING_TABLE)) {
                    event.setCancelled(true);
                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                            if (activeCharacter.hasJob()) {
                                Job job = activeCharacter.getJob();
                                if (job.getJobType().equals(JobType.WEAPONSMITH)) {
                                    GuiGeneric levelSelection = CraftingGuiManager.getLevelSelection(CraftingType.WEAPON_RANGED);
                                    levelSelection.openInventory(player);
                                } else {
                                    player.sendMessage(ChatColor.RED + "Only weaponsmiths can craft ranged weapons");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Only weaponsmiths can craft ranged weapons");
                            }
                        }
                    }
                } else if (clickedBlockType.equals(Material.ANVIL)) {
                    event.setCancelled(true);
                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                            if (activeCharacter.hasJob()) {
                                Job job = activeCharacter.getJob();
                                if (job.getJobType().equals(JobType.ARMORSMITH)) {
                                    GuiGeneric levelSelection = CraftingGuiManager.getLevelSelection(CraftingType.ARMOR_HEAVY);
                                    levelSelection.openInventory(player);
                                } else {
                                    player.sendMessage(ChatColor.RED + "Only armorsmiths can craft heavy armors");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Only armorsmiths can craft heavy armors");
                            }
                        }
                    }
                } else if (clickedBlockType.equals(Material.LOOM)) {
                    event.setCancelled(true);
                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                            if (activeCharacter.hasJob()) {
                                Job job = activeCharacter.getJob();
                                if (job.getJobType().equals(JobType.ARMORSMITH)) {
                                    GuiGeneric levelSelection = CraftingGuiManager.getLevelSelection(CraftingType.ARMOR_LIGHT);
                                    levelSelection.openInventory(player);
                                } else {
                                    player.sendMessage(ChatColor.RED + "Only armorsmiths can craft light armors");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Only armorsmiths can craft light armors");
                            }
                        }
                    }
                } else if (clickedBlockType.equals(Material.SMITHING_TABLE)) {
                    event.setCancelled(true);
                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                            if (activeCharacter.hasJob()) {
                                Job job = activeCharacter.getJob();
                                if (job.getJobType().equals(JobType.JEWELLER)) {
                                    GuiGeneric levelSelection = CraftingGuiManager.getLevelSelection(CraftingType.JEWEL);
                                    levelSelection.openInventory(player);
                                } else {
                                    player.sendMessage(ChatColor.RED + "Only jewellers can craft jewels");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Only jewellers can craft jewels");
                            }
                        }
                    }
                } else if (clickedBlockType.equals(Material.ENCHANTING_TABLE)) {
                    event.setCancelled(true);
                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                            if (activeCharacter.hasJob()) {
                                Job job = activeCharacter.getJob();
                                if (job.getJobType().equals(JobType.JEWELLER)) {
                                    GuiBookGeneric enchantStoneCraftingBook = CraftingGuiManager.getEnchantStoneCraftingBook();
                                    enchantStoneCraftingBook.openInventory(player);
                                } else {
                                    player.sendMessage(ChatColor.RED + "Only jewellers can craft enchant stones");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Only jewellers can craft enchant stones");
                            }
                        }
                    }
                } else if (clickedBlockType.equals(Material.CHEST) || clickedBlockType.equals(Material.BARREL) || clickedBlockType.equals(Material.ENDER_CHEST)
                        || clickedBlockType.equals(Material.TRAPPED_CHEST) || clickedBlockType.equals(Material.CHEST_MINECART) || clickedBlockType.equals(Material.CRAFTING_TABLE)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
