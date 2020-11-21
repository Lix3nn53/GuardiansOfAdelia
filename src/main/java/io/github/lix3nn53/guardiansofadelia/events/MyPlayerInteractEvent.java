package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.Items.PrizeChest;
import io.github.lix3nn53.guardiansofadelia.Items.PrizeChestType;
import io.github.lix3nn53.guardiansofadelia.Items.TeleportScroll;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorSlot;
import io.github.lix3nn53.guardiansofadelia.Items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.BoostPremiumManager;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.gui.HelmetSkinApplyGui;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.gui.WeaponOrShieldSkinApplyGui;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.BoostPremium;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.HelmetSkin;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.SkinChest;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.jobs.crafting.CraftingGuiManager;
import io.github.lix3nn53.guardiansofadelia.jobs.crafting.CraftingType;
import io.github.lix3nn53.guardiansofadelia.menu.MenuList;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
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
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
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

            ArmorSlot armorSlot = ArmorSlot.getArmorSlot(itemInMainHandType);

            if (armorSlot != null) {
                UUID uuid = player.getUniqueId();
                if (GuardianDataManager.hasGuardianData(uuid)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);

                    if (guardianData.hasActiveCharacter()) {
                        RPGCharacter rpgCharacter = guardianData.getActiveCharacter();

                        if (StatUtils.doesCharacterMeetRequirements(itemInMainHand, player, rpgCharacter.getRpgClassStr())) {
                            rpgCharacter.getRpgCharacterStats().onArmorEquip(itemInMainHand, true);

                            if (itemInMainHandType.equals(HelmetSkin.getHelmetMaterial())) {
                                EntityEquipment equipment = player.getEquipment();
                                equipment.setHelmet(itemInMainHand);
                                equipment.setItemInMainHand(null);
                            }
                        } else {
                            event.setCancelled(true);
                        }
                    }
                }
            } else if (itemInMainHandType.equals(Material.COMPASS)) {
                GuiGeneric compass = MenuList.compass();
                compass.openInventory(player);
            } else if (itemInMainHandType.equals(Material.PAPER)) {
                if (PersistentDataContainerUtil.hasString(itemInMainHand, "teleportScroll")) {
                    String teleportScroll = PersistentDataContainerUtil.getString(itemInMainHand, "teleportScroll");

                    TeleportScroll teleportScrollLocation = new TeleportScroll(teleportScroll);
                    teleportScrollLocation.teleport(player, itemInMainHand);
                }
            } else if (itemInMainHandType.equals(Material.STONE_PICKAXE)) {
                if (PersistentDataContainerUtil.hasString(itemInMainHand, "prizeDungeon")) { //dungeon chests
                    String dungeonThemeString = PersistentDataContainerUtil.getString(itemInMainHand, "prizeDungeon");
                    int typeIndex = PersistentDataContainerUtil.getInteger(itemInMainHand, "prizeType");
                    PrizeChestType type = PrizeChestType.values()[typeIndex];

                    HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
                    DungeonTheme dungeonTheme = dungeonThemes.get(dungeonThemeString);

                    List<ItemStack> itemStacks = dungeonTheme.generateChestItems(type);

                    PrizeChest prizeChest = new PrizeChest(dungeonTheme, type);
                    prizeChest.play(player, itemStacks);
                    player.getInventory().setItemInMainHand(null);
                }
            } else if (itemInMainHandType.equals(Material.BLACK_DYE)) {
                event.setCancelled(true);
                if (!itemInMainHand.hasItemMeta()) return;
                if (!itemInMainHand.getItemMeta().hasDisplayName()) return;

                ItemMeta itemMeta = itemInMainHand.getItemMeta();
                String displayName = itemMeta.getDisplayName();

                if (displayName.equals(ChatColor.GOLD + "Weapon/Shield Skin Scroll")) {

                    new WeaponOrShieldSkinApplyGui().openInventory(player);
                } else if (PersistentDataContainerUtil.hasString(itemInMainHand, "helmetSkinCode")) {
                    String helmetSkinCode = PersistentDataContainerUtil.getString(itemInMainHand, "helmetSkinCode");

                    HelmetSkin helmetSkin = HelmetSkin.valueOf(helmetSkinCode);

                    new HelmetSkinApplyGui(helmetSkin).openInventory(player);
                } else if (PersistentDataContainerUtil.hasInteger(itemInMainHand, "skinChest")) {

                    SkinChest skinChest = new SkinChest();

                    skinChest.play(player);
                    int amount = itemInMainHand.getAmount();
                    itemInMainHand.setAmount(amount - 1);
                } else if (PersistentDataContainerUtil.hasString(itemInMainHand, "boostCode")) {
                    String boostCode = PersistentDataContainerUtil.getString(itemInMainHand, "boostCode");

                    BoostPremium boostPremium = BoostPremium.valueOf(boostCode);

                    BoostPremiumManager.tellBungeeToActivateBoost(player, boostPremium);
                    int amount = itemInMainHand.getAmount();
                    itemInMainHand.setAmount(amount - 1);
                }

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
                            GuiGeneric levelSelection = CraftingGuiManager.getLevelSelection(CraftingType.FOOD);
                            levelSelection.openInventory(player);
                        }
                    }
                } else if (clickedBlockType.equals(Material.BREWING_STAND)) {
                    event.setCancelled(true);
                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                        if (guardianData.hasActiveCharacter()) {
                            GuiGeneric levelSelection = CraftingGuiManager.getLevelSelection(CraftingType.POTION);
                            levelSelection.openInventory(player);
                        }
                    }
                } else if (clickedBlockType.equals(Material.GRINDSTONE)) {
                    event.setCancelled(true);
                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                        if (guardianData.hasActiveCharacter()) {
                            GuiGeneric levelSelection = CraftingGuiManager.getLevelSelection(CraftingType.WEAPON_MELEE);
                            levelSelection.openInventory(player);
                        }
                    }
                } else if (clickedBlockType.equals(Material.FLETCHING_TABLE)) {
                    event.setCancelled(true);
                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                        if (guardianData.hasActiveCharacter()) {
                            GuiGeneric levelSelection = CraftingGuiManager.getLevelSelection(CraftingType.WEAPON_RANGED);
                            levelSelection.openInventory(player);
                        }
                    }
                } else if (clickedBlockType.equals(Material.ANVIL)) {
                    event.setCancelled(true);
                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                        if (guardianData.hasActiveCharacter()) {
                            GuiGeneric levelSelection = CraftingGuiManager.getLevelSelection(CraftingType.ARMOR_HEAVY);
                            levelSelection.openInventory(player);
                        }
                    }
                } else if (clickedBlockType.equals(Material.LOOM)) {
                    event.setCancelled(true);
                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                        if (guardianData.hasActiveCharacter()) {
                            GuiGeneric levelSelection = CraftingGuiManager.getLevelSelection(CraftingType.ARMOR_LIGHT);
                            levelSelection.openInventory(player);
                        }
                    }
                } else if (clickedBlockType.equals(Material.SMITHING_TABLE)) {
                    event.setCancelled(true);
                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                        if (guardianData.hasActiveCharacter()) {
                            GuiGeneric levelSelection = CraftingGuiManager.getLevelSelection(CraftingType.JEWEL);
                            levelSelection.openInventory(player);
                        }
                    }
                } else if (clickedBlockType.equals(Material.ENCHANTING_TABLE)) {
                    event.setCancelled(true);
                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                        if (guardianData.hasActiveCharacter()) {
                            GuiGeneric levelSelection = CraftingGuiManager.getLevelSelection(CraftingType.ENCHANT_STONE);
                            levelSelection.openInventory(player);
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
