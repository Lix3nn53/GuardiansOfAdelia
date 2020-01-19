package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.enchanting.EnchantGui;
import io.github.lix3nn53.guardiansofadelia.Items.enchanting.EnchantStone;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorType;
import io.github.lix3nn53.guardiansofadelia.Items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.gui.HelmetSkinApplyGui;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.gui.WeaponOrShieldSkinApplyGui;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.HelmetSkin;
import io.github.lix3nn53.guardiansofadelia.chat.ChatTag;
import io.github.lix3nn53.guardiansofadelia.economy.Coin;
import io.github.lix3nn53.guardiansofadelia.economy.CoinType;
import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.Bazaar;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.BazaarCustomerGui;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.BazaarManager;
import io.github.lix3nn53.guardiansofadelia.economy.trading.Trade;
import io.github.lix3nn53.guardiansofadelia.economy.trading.TradeGui;
import io.github.lix3nn53.guardiansofadelia.economy.trading.TradeInvite;
import io.github.lix3nn53.guardiansofadelia.economy.trading.TradeManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.Attribute;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillBar;
import io.github.lix3nn53.guardiansofadelia.guild.Guild;
import io.github.lix3nn53.guardiansofadelia.guild.GuildInvite;
import io.github.lix3nn53.guardiansofadelia.guild.GuildManager;
import io.github.lix3nn53.guardiansofadelia.guild.PlayerRankInGuild;
import io.github.lix3nn53.guardiansofadelia.jobs.Job;
import io.github.lix3nn53.guardiansofadelia.jobs.JobType;
import io.github.lix3nn53.guardiansofadelia.jobs.crafting.CraftingGuiManager;
import io.github.lix3nn53.guardiansofadelia.jobs.crafting.CraftingType;
import io.github.lix3nn53.guardiansofadelia.menu.MenuList;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.npc.merchant.MerchantManager;
import io.github.lix3nn53.guardiansofadelia.npc.merchant.MerchantMenu;
import io.github.lix3nn53.guardiansofadelia.npc.merchant.MerchantPageType;
import io.github.lix3nn53.guardiansofadelia.npc.merchant.SellGui;
import io.github.lix3nn53.guardiansofadelia.party.PartyInvite;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.revive.TombManager;
import io.github.lix3nn53.guardiansofadelia.rpginventory.RPGInventory;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.Gui;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiBookGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.CharacterSelectionScreenManager;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.CompassManager;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MyInventoryClickEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        int slot = event.getSlot();

        ItemStack current = event.getCurrentItem();
        Material currentType = Material.AIR;
        if (current != null) {
            currentType = current.getType();
        }

        ItemStack cursor = event.getCursor();
        Material cursorType = Material.AIR;
        if (cursor != null) {
            cursorType = cursor.getType();
        }

        String title = event.getView().getTitle();

        if (event.getAction() != InventoryAction.NOTHING) {
            if (event.getClick().equals(ClickType.NUMBER_KEY)) {
                int hotbarButton = event.getHotbarButton();
                if (hotbarButton >= 0 && hotbarButton <= 3) { //skill bar
                    event.setCancelled(true);
                    return;
                }
            } else if (clickedInventory != null && clickedInventory.getType().equals(InventoryType.PLAYER)) {
                if (slot >= 0 && slot <= 3) { //skill bar
                    event.setCancelled(true);
                    return;
                }
            }
        }

        if (title.equals("Crafting")) {
            if (event.isShiftClick()) {
                if (currentType.equals(HelmetSkin.getHelmetMaterial())) {
                    InventoryType.SlotType slotType = event.getSlotType();
                    if (slotType.equals(InventoryType.SlotType.CONTAINER) || slotType.equals(InventoryType.SlotType.QUICKBAR)) {
                        EntityEquipment equipment = player.getEquipment();
                        ItemStack helmet = equipment.getHelmet();
                        equipment.setHelmet(current);
                        clickedInventory.setItem(slot, helmet);
                        event.setCancelled(true);
                        return;
                    } else if (event.getRawSlot() == ArmorType.HELMET.getSlot()) {
                        PlayerInventory playerInventory = player.getInventory();
                        if (playerInventory.firstEmpty() != -1) { //has empty slot
                            EntityEquipment equipment = player.getEquipment();
                            ItemStack helmet = equipment.getHelmet();
                            equipment.setHelmet(null);
                            InventoryUtils.giveItemToPlayer(player, helmet);
                            event.setCancelled(true);
                            return;
                        }
                    }
                }
            } else if (event.getRawSlot() == ArmorType.HELMET.getSlot()) {
                if (cursorType.equals(HelmetSkin.getHelmetMaterial())) {
                    EntityEquipment equipment = player.getEquipment();
                    ItemStack helmet = equipment.getHelmet();
                    equipment.setHelmet(cursor);
                    player.setItemOnCursor(helmet);
                    event.setCancelled(true);
                    return;
                }
            }
        }

        UUID uuid = player.getUniqueId();

        GuardianData guardianData = null;
        RPGCharacter rpgCharacter = null;
        if (GuardianDataManager.hasGuardianData(uuid)) {
            guardianData = GuardianDataManager.getGuardianData(uuid);

            if (guardianData.hasActiveCharacter()) {
                rpgCharacter = guardianData.getActiveCharacter();

                /*
                //manage armor and offhand attributes for player character
                if (event.getAction() != InventoryAction.NOTHING) {
                    armorAndOffhandListener(player, event.getSlotType(), clickedInventory, event, current, currentType, cursor, cursorType, guardianData, rpgCharacter);
                }*/
            }
        }

        //Open RPG-Inventory
        if (clickedInventory != null && clickedInventory.getType().equals(InventoryType.CRAFTING)) {
            event.setCancelled(true);
            if (!cursorType.equals(Material.AIR)) {
                InventoryUtils.giveItemToPlayer(player, cursor);
                cursor.setAmount(0);
            }
            if (rpgCharacter != null) {
                GuiGeneric guiGeneric = rpgCharacter.getRpgInventory().formRPGInventory(player);
                guiGeneric.openInventory(player);
            }
            return;
        }

        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType().equals(Material.AIR)) return;
        if (!(event.getCurrentItem().hasItemMeta())) return;
        if (!(event.getCurrentItem().getItemMeta().hasDisplayName())) return;

        Gui activeGui = null;

        if (guardianData != null) {
            if (guardianData.hasActiveGui()) {
                activeGui = guardianData.getActiveGui();
                if (activeGui.isLocked()) {
                    event.setCancelled(true);
                }

                if (activeGui instanceof GuiBookGeneric) {
                    GuiBookGeneric guiBookGeneric = (GuiBookGeneric) activeGui;
                    if (slot == 53) {
                        //next page
                        String[] split = title.split("Page-");
                        int pageIndex = Integer.parseInt(split[1]) - 1;
                        pageIndex++;
                        guiBookGeneric.openInventoryPage(player, pageIndex);
                        return;
                    } else if (slot == 45) {
                        //previous page
                        String[] split = title.split("Page-");
                        int pageIndex = Integer.parseInt(split[1]) - 1;
                        pageIndex--;
                        guiBookGeneric.openInventoryPage(player, pageIndex);
                        return;
                    }
                } else if (activeGui instanceof MerchantMenu) {
                    MerchantMenu merchantMenu = (MerchantMenu) activeGui;
                    if (merchantMenu.isButton(current)) {
                        MerchantPageType buttonShop = merchantMenu.getButtonShop(current);
                        String[] split = title.split("#");
                        int shopLevel = Integer.parseInt(split[1]);
                        Gui gui = buttonShop.getGui(merchantMenu.getResourceNPC(), player, shopLevel);
                        if (gui != null) {
                            gui.openInventory(player);
                        }
                        return;
                    }
                } else if (activeGui instanceof TradeGui) {
                    if (TradeManager.hasTrade(player)) {
                        Trade trade = TradeManager.getTrade(player);
                        if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                            if (currentType.equals(Material.LIME_WOOL)) {
                                trade.accept(player);
                            } else if (currentType.equals(Material.YELLOW_WOOL)) {
                                trade.lock();
                            } else if ((slot > 4 && slot < 9) || (slot > 13 && slot < 18)) {
                                trade.removeItemFromTrade(player, slot);
                            }
                        } else if (clickedInventory.getType().equals(InventoryType.PLAYER)) {
                            trade.addItem(player, slot);
                        }
                    }
                } else if (activeGui instanceof SellGui) {
                    SellGui sellGui = (SellGui) activeGui;
                    if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                        if (currentType.equals(Material.LIME_WOOL)) {
                            sellGui.confirm();
                        } else if (currentType.equals(Material.YELLOW_WOOL)) {
                            sellGui.finish(player);
                        }
                    } else if (clickedInventory.getType().equals(InventoryType.PLAYER)) {
                        sellGui.addItemToSell(current, slot);
                    }
                } else if (activeGui instanceof EnchantGui) {
                    EnchantGui enchantGui = (EnchantGui) activeGui;
                    if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                        if (currentType.equals(Material.EMERALD_BLOCK)) {
                            enchantGui.startEnchanting(player);
                        }
                    } else if (clickedInventory.getType().equals(InventoryType.PLAYER)) {
                        List<Material> enchantStoneMaterials = new ArrayList<>();
                        for (EnchantStone enchantStone : EnchantStone.values()) {
                            enchantStoneMaterials.add(enchantStone.getType());
                        }
                        if (enchantStoneMaterials.contains(currentType)) {
                            enchantGui.setEnchantStone(current);
                        } else if (StatUtils.hasStatType(currentType)) {
                            if (currentType.equals(Material.SHEARS))
                                return; //TODO enchanting passive items are disabled. Stay this way?
                            enchantGui.setItemToEnchant(current);
                        }
                    }
                } else if (activeGui instanceof WeaponOrShieldSkinApplyGui) {
                    event.setCancelled(true);
                    WeaponOrShieldSkinApplyGui weaponOrShieldSkinApplyGui = (WeaponOrShieldSkinApplyGui) activeGui;
                    if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                        if (currentType.equals(Material.LIME_WOOL)) {
                            boolean b = weaponOrShieldSkinApplyGui.onConfirm(player);
                            if (b) {
                                player.closeInventory();
                                MessageUtils.sendCenteredMessage(player, org.bukkit.ChatColor.GRAY + "------------------------");
                                MessageUtils.sendCenteredMessage(player, "Applied Skin");
                                MessageUtils.sendCenteredMessage(player, "to " + weaponOrShieldSkinApplyGui.getItemOnSlot().getItemMeta().getDisplayName());
                                MessageUtils.sendCenteredMessage(player, org.bukkit.ChatColor.GRAY + "------------------------");
                            }
                        }
                    } else if (clickedInventory.getType().equals(InventoryType.PLAYER)) {
                        boolean b = weaponOrShieldSkinApplyGui.setWeaponOrShield(current, slot);
                        if (!b) player.sendMessage(weaponOrShieldSkinApplyGui.getNotFitErrorMessage());
                    }
                } else if (activeGui instanceof HelmetSkinApplyGui) {
                    event.setCancelled(true);
                    HelmetSkinApplyGui helmetSkinApplyGui = (HelmetSkinApplyGui) activeGui;
                    if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                        if (currentType.equals(Material.LIME_WOOL)) {
                            boolean b = helmetSkinApplyGui.onConfirm(player);
                            if (b) {
                                player.closeInventory();
                                MessageUtils.sendCenteredMessage(player, org.bukkit.ChatColor.GRAY + "------------------------");
                                MessageUtils.sendCenteredMessage(player, "Applied Skin");
                                MessageUtils.sendCenteredMessage(player, "to " + helmetSkinApplyGui.getItemOnSlot().getItemMeta().getDisplayName());
                                MessageUtils.sendCenteredMessage(player, org.bukkit.ChatColor.GRAY + "------------------------");
                            }
                        }
                    } else if (clickedInventory.getType().equals(InventoryType.PLAYER)) {
                        boolean b = helmetSkinApplyGui.setHelmet(current, slot);
                        if (!b) player.sendMessage(helmetSkinApplyGui.getNotFitErrorMessage());
                    }
                }
                if (!title.equals("Bazaar Storage")) {
                    if (PersistentDataContainerUtil.hasInteger(current, "shopPrice")) {
                        boolean didClickBefore = MerchantManager.onSellItemClick(player, slot);
                        if (didClickBefore) {
                            if (activeGui instanceof BazaarCustomerGui) {
                                BazaarCustomerGui bazaarCustomerGui = (BazaarCustomerGui) activeGui;
                                Bazaar bazaar = bazaarCustomerGui.getBazaar();
                                if (bazaar != null) {
                                    bazaar.buyItem(player, current);
                                }
                            } else {
                                boolean pay = EconomyUtils.pay(player, current);
                                if (pay) {
                                    ItemStack itemStack = EconomyUtils.removeShopPrice(current);
                                    InventoryUtils.giveItemToPlayer(player, itemStack);
                                }
                            }
                        }
                        return;
                    }
                }
            }
        }

        ItemMeta itemMeta = current.getItemMeta();
        String currentName = itemMeta.getDisplayName();
        Inventory topInventory = event.getView().getTopInventory();

        if (currentName.equals(ChatColor.GREEN + "Menu")) {
            event.setCancelled(true);
            if (!cursorType.equals(Material.AIR)) {
                InventoryUtils.giveItemToPlayer(player, cursor);
                cursor.setAmount(0);
            }
            GuiGeneric guiGeneric = MenuList.mainMenu(guardianData);
            guiGeneric.openInventory(player);
        } else if (title.equals(org.bukkit.ChatColor.GREEN + "Menu")) {
            if (currentName.equals(ChatColor.GREEN + "Guides")) {
                GuiGeneric guide = MenuList.guide();
                guide.openInventory(player);
            } else if (currentName.equals(ChatColor.BLUE + "Compass")) {
                GuiBookGeneric compass = MenuList.compass();
                compass.openInventory(player);
            } else if (currentName.equals(ChatColor.DARK_GREEN + "Maps")) {
                player.closeInventory();
                TextComponent message = new TextComponent(" Maps ! (Click Me)");
                message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://guardiansofadelia.com/#t5"));
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to see Maps from our website").color(ChatColor.AQUA).create()));
                message.setColor(ChatColor.GREEN);
                message.setBold(true);
                player.spigot().sendMessage(message);
            } else if (currentName.equals(ChatColor.GREEN + "Character")) {
                GuiGeneric character = MenuList.character();
                character.openInventory(player);
            } else if (currentName.equals(ChatColor.DARK_PURPLE + "Guild")) {
                if (GuildManager.inGuild(player)) {
                    MenuList.guild().openInventory(player);
                }
            } else if (currentName.equals(ChatColor.DARK_PURPLE + "Minigames")) {
                GuiGeneric minigames = MenuList.minigames();
                minigames.openInventory(player);
            } else if (currentName.equals(ChatColor.GOLD + "Bazaar")) {
                GuiGeneric bazaar = MenuList.bazaar(player);
                bazaar.openInventory(player);
            } else if (currentName.equals(ChatColor.LIGHT_PURPLE + "Donation ♥")) {
                player.closeInventory();
                TextComponent message = new TextComponent(" Donation ♥ ! (Click Me)");
                message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://guardiansofadelia.com/#t6"));
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to see donate ♥").color(ChatColor.AQUA).create()));
                message.setColor(ChatColor.LIGHT_PURPLE);
                message.setBold(true);
                player.spigot().sendMessage(message);
            } else if (currentName.equals(ChatColor.YELLOW + "Server Boosts")) {
                GuiGeneric serverBoostMenu = MenuList.serverBoostMenu();
                serverBoostMenu.openInventory(player);
            }
        } else if (title.contains("Character")) {
            if (title.contains("Creation")) {
                String charNoString = title.replace(ChatColor.YELLOW + "Character ", "");
                charNoString = charNoString.replace(" Creation", "");
                int charNo = Integer.parseInt(charNoString);

                if (currentName.contains("Knight")) {
                    CharacterSelectionScreenManager.createCharacter(player, charNo, RPGClass.KNIGHT);
                } else if (currentName.contains("Paladin")) {
                    CharacterSelectionScreenManager.createCharacter(player, charNo, RPGClass.PALADIN);
                } else if (currentName.contains("Rogue")) {
                    CharacterSelectionScreenManager.createCharacter(player, charNo, RPGClass.ROGUE);
                } else if (currentName.contains("Archer")) {
                    CharacterSelectionScreenManager.createCharacter(player, charNo, RPGClass.ARCHER);
                } else if (currentName.contains("Mage")) {
                    CharacterSelectionScreenManager.createCharacter(player, charNo, RPGClass.MAGE);
                } else if (currentName.contains("Warrior")) {
                    CharacterSelectionScreenManager.createCharacter(player, charNo, RPGClass.WARRIOR);
                } else if (currentName.contains("Monk")) {
                    CharacterSelectionScreenManager.createCharacter(player, charNo, RPGClass.MONK);
                } else if (currentName.contains("Hunter")) {
                    CharacterSelectionScreenManager.createCharacter(player, charNo, RPGClass.HUNTER);
                }
            } else if (title.contains("Selection")) {
                String charNoString = title.replace(ChatColor.YELLOW + "Character ", "");
                charNoString = charNoString.replace(" Selection", "");
                int charNo = Integer.parseInt(charNoString);

                if (currentName.contains("Roumen")) {
                    Location location = TownManager.getTown(1).getLocation();
                    CharacterSelectionScreenManager.selectCharacter(player, charNo, location);
                } else if (currentName.contains("Port Veloa")) {
                    Location location = TownManager.getTown(2).getLocation();
                    CharacterSelectionScreenManager.selectCharacter(player, charNo, location);
                } else if (currentName.contains("Elderine")) {
                    Location location = TownManager.getTown(3).getLocation();
                    CharacterSelectionScreenManager.selectCharacter(player, charNo, location);
                } else if (currentName.contains("Uruga")) {
                    Location location = TownManager.getTown(4).getLocation();
                    CharacterSelectionScreenManager.selectCharacter(player, charNo, location);
                } else if (currentName.contains("Alberstol Ruins")) {
                    Location location = TownManager.getTown(5).getLocation();
                    CharacterSelectionScreenManager.selectCharacter(player, charNo, location);
                } else if (currentName.contains("last location")) {
                    Location charLocation = CharacterSelectionScreenManager.getCharLocation(player, charNo);
                    if (charLocation != null) {
                        CharacterSelectionScreenManager.selectCharacter(player, charNo, charLocation);
                    } else {
                        player.sendMessage(ChatColor.RED + "Your saved quit-location is not valid");
                    }
                }
            } else {
                if (currentName.equals(ChatColor.LIGHT_PURPLE + "Skills")) {
                    GuiGeneric skill = MenuList.skill(player);
                    skill.openInventory(player);
                } else if (currentName.equals(ChatColor.DARK_GREEN + "Elements")) {
                    GuiGeneric element = MenuList.element(player);
                    element.openInventory(player);
                } else if (currentName.equals(ChatColor.YELLOW + "Job")) {
                    GuiGeneric job = MenuList.job(player);
                    job.openInventory(player);
                } else if (currentName.equals(ChatColor.AQUA + "Chat Tag")) {
                    GuiGeneric chatTag = MenuList.chatTagQuests(player);
                    chatTag.openInventory(player);
                }
            }
        } else if (title.contains(org.bukkit.ChatColor.YELLOW + "Quest List of ")) {
            if (rpgCharacter != null) {
                if (activeGui != null) {
                    int resourceNPC = activeGui.getResourceNPC();
                    if (resourceNPC != 0) {
                        Material type = currentType;
                        if (type.equals(Material.MAGENTA_WOOL)) {
                            String[] split = currentName.split("#");
                            int questNo = Integer.parseInt(split[1]);
                            int whoCanCompleteThisQuest = QuestNPCManager.getWhoCanCompleteThisQuest(questNo);
                            if (whoCanCompleteThisQuest == resourceNPC) {
                                //complete quest
                                boolean didComplete = rpgCharacter.turnInQuest(questNo, player);
                                if (didComplete) {
                                    GuiGeneric questGui = QuestNPCManager.getQuestGui(player, resourceNPC);
                                    questGui.openInventory(player);
                                } else {
                                    player.sendMessage(ChatColor.RED + "Couldn't complete the quest ERROR report pls");
                                }
                            } else {
                                NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
                                NPC byId = npcRegistry.getById(whoCanCompleteThisQuest);
                                player.sendMessage(ChatColor.RED + "You can't complete this quest from this NPC. You need to talk with " + ChatColor.WHITE + byId.getName());
                            }
                        } else if (type.equals(Material.LIME_WOOL)) {
                            String[] split = currentName.split("#");
                            int questNo = Integer.parseInt(split[1]);
                            int whoCanGiveThisQuest = QuestNPCManager.getWhoCanGiveThisQuest(questNo);
                            if (whoCanGiveThisQuest == resourceNPC) {
                                //give quest
                                Quest questCopyById = QuestNPCManager.getQuestCopyById(questNo);

                                boolean questListIsNotFull = rpgCharacter.acceptQuest(questCopyById, player);
                                if (questListIsNotFull) {
                                    GuiGeneric questGui = QuestNPCManager.getQuestGui(player, resourceNPC);
                                    questGui.openInventory(player);
                                } else {
                                    player.sendMessage(ChatColor.RED + "Your quest list is full");
                                }
                            } else {
                                NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
                                NPC byId = npcRegistry.getById(whoCanGiveThisQuest);
                                player.sendMessage(ChatColor.RED + "You can't take this quest from this NPC. You need to talk with " + ChatColor.WHITE + byId.getName());
                            }
                        }
                    }
                }
            }
        } else if (title.equals(ChatColor.GOLD + "Coin Converter")) {
            if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                PlayerInventory playerInventory = player.getInventory();
                if (currentType.equals(Material.IRON_INGOT)) {
                    if (InventoryUtils.inventoryContains(playerInventory, Material.GOLD_INGOT, 1)) {
                        InventoryUtils.removeMaterialFromInventory(playerInventory, Material.GOLD_INGOT, 1);
                        InventoryUtils.giveItemToPlayer(player, new Coin(CoinType.COPPER, 64).getCoin());
                    }
                } else if (currentType.equals(Material.GOLD_INGOT)) {
                    if (current.getAmount() == 1) {
                        if (InventoryUtils.inventoryContains(playerInventory, Material.IRON_INGOT, 64)) {
                            InventoryUtils.removeMaterialFromInventory(playerInventory, Material.IRON_INGOT, 64);
                            InventoryUtils.giveItemToPlayer(player, new Coin(CoinType.SILVER, 1).getCoin());
                        }
                    } else if (current.getAmount() == 64) {
                        if (InventoryUtils.inventoryContains(playerInventory, Material.DIAMOND, 1)) {
                            InventoryUtils.removeMaterialFromInventory(playerInventory, Material.DIAMOND, 1);
                            InventoryUtils.giveItemToPlayer(player, new Coin(CoinType.SILVER, 64).getCoin());
                        }
                    }
                } else if (currentType.equals(Material.DIAMOND)) {
                    if (InventoryUtils.inventoryContains(playerInventory, Material.GOLD_INGOT, 64)) {
                        InventoryUtils.removeMaterialFromInventory(playerInventory, Material.GOLD_INGOT, 64);
                        InventoryUtils.giveItemToPlayer(player, new Coin(CoinType.GOLD, 1).getCoin());
                    }
                }
            }
        } else if (title.contains(ChatColor.BLUE + "Compass")) {
            if (currentType.equals(Material.LIGHT_BLUE_WOOL)) {
                String displayName = itemMeta.getDisplayName();
                String[] split = displayName.split("#");
                int i = Integer.parseInt(split[1]);

                Town town = TownManager.getTown(i);
                CompassManager.setCompassItemLocation(player, town.getName(), town.getLocation());
            } else if (currentType.equals(Material.LIME_WOOL)) {
                String displayName = itemMeta.getDisplayName();
                String[] split = displayName.split("#");
                int i = Integer.parseInt(split[1]);
                CompassManager.setCompassItemNPC(player, i);
            } else if (currentType.equals(Material.MAGENTA_WOOL)) {
                String displayName = itemMeta.getDisplayName();
                String[] split = displayName.split("#");
                int i = Integer.parseInt(split[1]);
                DungeonTheme value = DungeonTheme.values()[i - 1];
                Location portalLocationOfDungeonTheme = MiniGameManager.getPortalLocationOfDungeonTheme(value);
                CompassManager.setCompassItemLocation(player, value.getName(), portalLocationOfDungeonTheme);
            }
        } else if (title.contains(ChatColor.AQUA + "Elements (Points:")) {
            if (rpgCharacter != null) {
                RPGCharacterStats rpgCharacterStats = rpgCharacter.getRpgCharacterStats();
                Attribute attr = null;
                if (slot == 1) {
                    attr = rpgCharacterStats.getFire();
                } else if (slot == 4) {
                    attr = rpgCharacterStats.getWater();
                } else if (slot == 7) {
                    attr = rpgCharacterStats.getEarth();
                } else if (slot == 20) {
                    attr = rpgCharacterStats.getLightning();
                } else if (slot == 24) {
                    attr = rpgCharacterStats.getWind();
                }
                if (attr != null) {
                    if (event.isLeftClick()) {
                        int pointsLeftToSpend = rpgCharacterStats.getAttributePointsLeftToSpend();
                        if (pointsLeftToSpend > 0) {
                            attr.investOnePoint(rpgCharacterStats, true);
                            GuiGeneric element = MenuList.element(player);
                            element.openInventory(player);
                        }
                    } else if (event.isRightClick()) {
                        int invested = attr.getInvested();
                        if (invested > 0) {
                            attr.downgradeOnePoint(rpgCharacterStats, true);
                            GuiGeneric element = MenuList.element(player);
                            element.openInventory(player);
                        }
                    }
                }
            }
        } else if (title.contains(ChatColor.LIGHT_PURPLE + "Skills (Points: ")) {
            if (rpgCharacter != null) {
                SkillBar skillBar = rpgCharacter.getSkillBar();

                int skillIndex = -1;
                if (slot == 1) {
                    skillIndex = 0;
                } else if (slot == 4) {
                    skillIndex = 1;
                } else if (slot == 7) {
                    skillIndex = 2;
                } else if (slot == 20) {
                    skillIndex = 3;
                } else if (slot == 24) {
                    skillIndex = 4;
                }
                if (skillIndex != -1) {
                    if (event.isLeftClick()) {
                        boolean upgradeSkill = skillBar.upgradeSkill(skillIndex);
                        if (upgradeSkill) {
                            GuiGeneric skill = MenuList.skill(player);
                            skill.openInventory(player);
                        }
                    } else if (event.isRightClick()) {
                        boolean downgradeSkill = skillBar.downgradeSkill(skillIndex);
                        if (downgradeSkill) {
                            GuiGeneric skill = MenuList.skill(player);
                            skill.openInventory(player);
                        }
                    }
                }
            }
        } else if (title.equals(ChatColor.YELLOW + "Job")) {
            if (rpgCharacter != null) {
                if (!rpgCharacter.hasJob()) {
                    if (currentType.equals(Material.RED_WOOL)) {
                        rpgCharacter.setJob(new Job(JobType.WEAPONSMITH));
                    } else if (currentType.equals(Material.LIGHT_BLUE_WOOL)) {
                        rpgCharacter.setJob(new Job(JobType.ARMORSMITH));
                    } else if (currentType.equals(Material.MAGENTA_WOOL)) {
                        rpgCharacter.setJob(new Job(JobType.ALCHEMIST));
                    } else if (currentType.equals(Material.YELLOW_WOOL)) {
                        rpgCharacter.setJob(new Job(JobType.JEWELLER));
                    }
                    GuiGeneric job = MenuList.job(player);
                    job.openInventory(player);
                }
            }
        } else if (title.equals(ChatColor.DARK_PURPLE + "Guild")) {
            if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                if (currentName.equals(ChatColor.RED + "Join Guild War")) {
                    MiniGameManager.getGuildWarJoinGui().openInventory(player);
                }
            }
        } else if (title.equals(ChatColor.DARK_PURPLE + "Join Guild War")) {
            if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                int i = currentName.indexOf("#");
                String c = String.valueOf(currentName.charAt(i + 1));
                int roomNo = Integer.parseInt(c);
                boolean joined = MiniGameManager.getGuildWar(roomNo).joinQueue(player);
                if (joined) {
                    player.closeInventory();
                }
            }
        } else if (title.equals(ChatColor.GOLD + "MiniGames")) {
            if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                if (currentName.equals(ChatColor.RED + "Last One Standing")) {
                    MiniGameManager.getLastOneStandingJoinGui().openInventory(player);
                } else if (currentName.equals(ChatColor.RED + "Win By Most Kills")) {
                    MiniGameManager.getWinByMostKillsJoinGui().openInventory(player);
                }
            }
        } else if (title.equals(ChatColor.GOLD + "Join Last One Standing")) {
            if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                int i = currentName.indexOf("#");
                String c = String.valueOf(currentName.charAt(i + 1));
                int roomNo = Integer.parseInt(c);
                boolean joined = MiniGameManager.getLastOneStanding(roomNo).joinQueue(player);
                if (joined) {
                    player.closeInventory();
                }
            }
        } else if (title.equals(ChatColor.GOLD + "Join Win By Most Kills")) {
            if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                int i = currentName.indexOf("#");
                String c = String.valueOf(currentName.charAt(i + 1));
                int roomNo = Integer.parseInt(c);
                boolean joined = MiniGameManager.getWinByMostKills(roomNo).joinQueue(player);
                if (joined) {
                    player.closeInventory();
                }
            }
        } else if (title.equals(ChatColor.YELLOW.toString() + ChatColor.BOLD + "RPG Inventory")) {
            RPGInventory rpgInventory = rpgCharacter.getRpgInventory();
            RPGClass rpgClass = rpgCharacter.getRpgClass();
            if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                event.setCancelled(true);
                if (cursorType.equals(Material.AIR)) {
                    boolean change = rpgInventory.onCursorClickWithAir(player, slot, topInventory, event.isShiftClick());
                } else {
                    boolean change = rpgInventory.onCursorClickWithItem(player, slot, cursor, topInventory, rpgClass);
                }
            } else if (clickedInventory.getType().equals(InventoryType.PLAYER)) {
                if (cursorType.equals(Material.AIR)) {
                    if (event.isShiftClick()) {
                        event.setCancelled(true);
                        boolean change = rpgInventory.onShiftClick(current, player, slot, topInventory, rpgClass);
                    }
                }
            }
        } else if (title.equals(ChatColor.AQUA + "Revive Gui")) {
            if (slot == 5) {
                TombManager.startSearch(player);
                player.closeInventory(); //calling inventory close event after startSearch doesn't cancel search
            } else if (slot == 3) {
                //inventory close event cancels tomb search so no need to call it twice
                player.closeInventory();
            }
        } else if (title.equals(ChatColor.GOLD + "Bazaar")) {
            if (currentType.equals(Material.LIME_WOOL)) {
                if (guardianData != null) {
                    if (guardianData.hasBazaar()) {
                        Bazaar bazaar = guardianData.getBazaar();
                        bazaar.edit();
                    } else {
                        Bazaar bazaar = new Bazaar(player);
                        guardianData.setBazaar(bazaar);
                        bazaar.edit();
                    }
                }
            }
        } else if (title.equals(ChatColor.GOLD + "Edit your bazaar")) {
            if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                if (currentType.equals(Material.LIME_WOOL)) {
                    if (guardianData != null) {
                        if (guardianData.hasBazaar()) {
                            Bazaar bazaar = guardianData.getBazaar();
                            bazaar.setUp();
                        }
                    }
                } else if (currentType.equals(Material.RED_WOOL)) {
                    if (guardianData != null) {
                        if (guardianData.hasBazaar()) {
                            Bazaar bazaar = guardianData.getBazaar();
                            bazaar.remove();
                            guardianData.setBazaar(null);
                            player.closeInventory();
                            player.sendMessage(ChatColor.RED + "Removed your bazaar");
                        }
                    }
                } else {
                    if (guardianData != null) {
                        if (guardianData.hasBazaar()) {
                            Bazaar bazaar = guardianData.getBazaar();
                            boolean isRemoved = bazaar.removeItem(current);
                            if (isRemoved) {
                                ItemStack itemStack = EconomyUtils.removeShopPrice(current);
                                InventoryUtils.giveItemToPlayer(player, itemStack);
                                bazaar.edit();
                            }
                        }
                    }
                }
            } else if (clickedInventory.getType().equals(InventoryType.PLAYER)) {
                BazaarManager.setPlayerSettingMoneyOfItem(player, current);
                player.closeInventory();
                MessageUtils.sendCenteredMessage(player, ChatColor.GOLD + "Enter a price for item: " + currentName);
                MessageUtils.sendCenteredMessage(player, ChatColor.YELLOW + "(Enter a number to chat without '/' or anything)");
            }
        } else if (title.equals("Bazaar Storage")) {
            if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                if (guardianData != null) {
                    if (guardianData.hasBazaar()) {
                        Bazaar bazaar = guardianData.getBazaar();
                        List<ItemStack> itemsOnSale = bazaar.getItemsOnSale();
                        if (itemsOnSale.contains(current)) {
                            player.closeInventory();
                            player.sendMessage(ChatColor.RED + "You can't get an item from bazaar storage which is on sale");
                            return;
                        }
                    }
                }
                ItemStack removedShopPrice = EconomyUtils.removeShopPrice(current);
                InventoryUtils.giveItemToPlayer(player, removedShopPrice);
                clickedInventory.setItem(slot, new ItemStack(Material.AIR));

            }
        } else if (title.equals("Premium Storage")) {
            if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                InventoryUtils.giveItemToPlayer(player, current);
                clickedInventory.setItem(slot, new ItemStack(Material.AIR));
            }
        } else if (title.contains("Join dungeon: ")) {
            if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                if (currentType.equals(Material.LIME_WOOL)) {
                    String s = title.replace("Join dungeon: ", "");
                    DungeonTheme dungeonTheme = DungeonTheme.valueOf(s);
                    int i = currentName.indexOf("#");
                    String c = String.valueOf(currentName.charAt(i + 1));
                    int roomNo = Integer.parseInt(c);
                    boolean joined = MiniGameManager.getDungeon(dungeonTheme, roomNo).joinQueue(player);
                    if (joined) {
                        player.closeInventory();
                    }
                }
            }
        } else if (title.contains(" Crafting")) {
            if (title.contains(" Level Selection")) {
                if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                    if (currentType.equals(Material.STONE_PICKAXE)) {
                        if (rpgCharacter != null) {
                            Job job = rpgCharacter.getJob();
                            int jobLevel = job.getLevel();

                            String replace = title.replace(" Crafting Level Selection", "");
                            CraftingType craftingType = CraftingType.valueOf(replace);

                            GearLevel gearLevel = GearLevel.TWO;
                            int reqJobLevel = 1;
                            if (currentName.equals(ChatColor.GOLD + "Level 30~39")) {
                                gearLevel = GearLevel.THREE;
                                reqJobLevel = 2;
                            } else if (currentName.equals(ChatColor.GOLD + "Level 40~49")) {
                                gearLevel = GearLevel.FOUR;
                                reqJobLevel = 3;
                            } else if (currentName.equals(ChatColor.GOLD + "Level 50~59")) {
                                gearLevel = GearLevel.FIVE;
                                reqJobLevel = 4;
                            } else if (currentName.equals(ChatColor.GOLD + "Level 60~69")) {
                                gearLevel = GearLevel.SIX;
                                reqJobLevel = 5;
                            } else if (currentName.equals(ChatColor.GOLD + "Level 70~79")) {
                                gearLevel = GearLevel.SEVEN;
                                reqJobLevel = 6;
                            } else if (currentName.equals(ChatColor.GOLD + "Level 80~89")) {
                                gearLevel = GearLevel.EIGHT;
                                reqJobLevel = 7;
                            } else if (currentName.equals(ChatColor.GOLD + "Level 90~99")) {
                                gearLevel = GearLevel.NINE;
                                reqJobLevel = 8;
                            }

                            if (jobLevel >= reqJobLevel) {
                                GuiBookGeneric craftingBook = CraftingGuiManager.getCraftingBook(craftingType, gearLevel);
                                craftingBook.openInventory(player);
                            } else {
                                player.sendMessage(ChatColor.RED + "Required job-level to craft " + currentName + ChatColor.RED + " items is " + reqJobLevel);
                            }
                        }
                    }
                }
            } else {
                if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                    if (rpgCharacter != null) {
                        ItemStack item = clickedInventory.getItem(7);
                        ItemMeta craftingGuideGlass = item.getItemMeta();
                        if (craftingGuideGlass.hasDisplayName()) {
                            String displayName = craftingGuideGlass.getDisplayName();
                            if (displayName.equals(ChatColor.GOLD + "Crafting Guide")) {
                                if (slot == 8 || slot == 17 || slot == 26 || slot == 35 || slot == 44) {
                                    List<ItemStack> ingredients = new ArrayList<>();

                                    for (int i = slot - 8; i < slot - 1; i++) {
                                        ItemStack ingredient = clickedInventory.getItem(i);
                                        if (ingredient != null) {
                                            if (!ingredient.getType().equals(Material.AIR)) {
                                                ingredients.add(ingredient);
                                            }
                                        }
                                    }

                                    boolean hasIngredients = true;
                                    int jobExpToGive = 0;
                                    for (ItemStack ingredient : ingredients) {
                                        jobExpToGive += ingredient.getAmount();
                                        boolean inventoryContains = InventoryUtils.inventoryContains(player.getInventory(), ingredient.getType(), ingredient.getAmount());
                                        if (!inventoryContains) {
                                            hasIngredients = false;
                                            break;
                                        }
                                    }

                                    if (hasIngredients) {
                                        ItemStack clone = current.clone();

                                        GearLevel gearLevel = GearLevel.TWO;
                                        if (title.contains("30~39")) {
                                            gearLevel = GearLevel.THREE;
                                        } else if (title.contains("40~49")) {
                                            gearLevel = GearLevel.FOUR;
                                        } else if (title.contains("50~59")) {
                                            gearLevel = GearLevel.FIVE;
                                        } else if (title.contains("60~69")) {
                                            gearLevel = GearLevel.SIX;
                                        } else if (title.contains("70~79")) {
                                            gearLevel = GearLevel.SEVEN;
                                        } else if (title.contains("80~89")) {
                                            gearLevel = GearLevel.EIGHT;
                                        } else if (title.contains("90~99")) {
                                            gearLevel = GearLevel.NINE;
                                        }

                                        StatUtils.addRandomPassiveStats(clone, gearLevel, ItemTier.MYSTIC);

                                        for (ItemStack ingredient : ingredients) {
                                            InventoryUtils.removeMaterialFromInventory(player.getInventory(), ingredient.getType(), ingredient.getAmount());
                                        }
                                        InventoryUtils.giveItemToPlayer(player, clone);
                                        rpgCharacter.getJob().addExperience(player, jobExpToGive);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (title.contains(ChatColor.YELLOW + "Interact with ")) {
            if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                String rightClickedName = title.replace(ChatColor.YELLOW + "Interact with ", "");
                Player rightClicked = Bukkit.getPlayer(rightClickedName);
                if (rightClicked == null) return;

                if (rightClicked.isOnline()) {
                    if (slot == 12) {
                        String senderTitle = org.bukkit.ChatColor.AQUA + "Sent party invitation";
                        String receiverMessage = org.bukkit.ChatColor.AQUA + player.getName() + " invites you to party"; //sender = player
                        String receiverTitle = org.bukkit.ChatColor.AQUA + "Received party invitation";
                        PartyInvite partyInvite = new PartyInvite(player, rightClicked, senderTitle, receiverMessage, receiverTitle); //receiver = rightClicked
                        partyInvite.send();
                        player.closeInventory();
                    } else if (slot == 14) {
                        if (GuildManager.inGuild(player)) {
                            Guild guild = GuildManager.getGuild(player);
                            PlayerRankInGuild rank = guild.getRankInGuild(player.getUniqueId());
                            if (rank.equals(PlayerRankInGuild.LEADER) || rank.equals(PlayerRankInGuild.COMMANDER)) {
                                String receiverMessage = org.bukkit.ChatColor.DARK_PURPLE + player.getName() + " invites you to " + guild.getName() + " guild"; //sender = player
                                String receiverTitle = org.bukkit.ChatColor.DARK_PURPLE + "Received guild invitation";
                                String senderTitle = org.bukkit.ChatColor.DARK_PURPLE + "Sent guild invitation";
                                GuildInvite invite = new GuildInvite(player, rightClicked, senderTitle, receiverMessage, receiverTitle); //receiver = rightClicked
                                invite.send();
                            } else {
                                player.sendMessage(org.bukkit.ChatColor.RED + "You must be guild leader or commander to invite players to guild");
                            }
                        }
                        player.closeInventory();
                    } else if (slot == 16) {
                        String senderTitle = org.bukkit.ChatColor.GOLD + "Sent trade invitation";
                        String receiverMessage = org.bukkit.ChatColor.GOLD + player.getName() + " invites you to trade"; //sender = player
                        String receiverTitle = org.bukkit.ChatColor.GOLD + "Received trade invitation";
                        TradeInvite tradeInvite = new TradeInvite(player, rightClicked, senderTitle, receiverMessage, receiverTitle); //receiver = rightClicked
                        tradeInvite.send();
                        player.closeInventory();
                    }
                }
            }
        } else if (title.contains(org.bukkit.ChatColor.AQUA + "Chat Tag")) {
            if (currentType.equals(Material.LIME_WOOL)) {
                String stripColor = ChatColor.stripColor(currentName);

                ChatTag chatTag = ChatTag.valueOf(stripColor);

                rpgCharacter.setChatTag(chatTag);

                MessageUtils.sendCenteredMessage(player, ChatColor.YELLOW + "You selected a new chat tag: " + chatTag.getChatColor() + chatTag.toString());
            }
        } else if (title.equals(org.bukkit.ChatColor.YELLOW + "Select item to show in chat")) {
            TextComponent message = new TextComponent(" Maps ! (Click Me)");
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ComponentBuilder("Click to see Maps from our website").color(ChatColor.AQUA).create()));
            message.setColor(ChatColor.GREEN);
            message.setBold(true);
            player.spigot().sendMessage(message);
        }
    }

    /*

    //ARMOR EQUIP LISTENER

    private void armorShiftClickListener(ItemStack current, Material currentType, InventoryClickEvent event, Player player, GuardianData guardianData, RPGCharacter rpgCharacter) {
        ArmorType armorTypeOfCurrentItem = ArmorType.getArmorType(currentType);
        if (armorTypeOfCurrentItem == null) return;

        boolean equipping = true; //removing otherwise
        if (event.getRawSlot() == armorTypeOfCurrentItem.getSlot()) equipping = false;

        if (InventoryUtils.isArmorEquippedOnRelatedSlot(armorTypeOfCurrentItem, player)) {
            //player is already equipping an item on slot we are managing
            if (!equipping) { //removing armor with shift click
                rpgCharacter.getRpgCharacterStats().onArmorUnequip(current);
            } //there is no else because you can't equip another armor with shift click if you are already equipping one
        } else if (equipping) { //armor slot is empty & equipping item with shift click
            if (StatUtils.doesCharacterMeetRequirements(current, player, rpgCharacter.getRpgClass())) {
                rpgCharacter.getRpgCharacterStats().onArmorEquip(current);
            } else {
                event.setCancelled(true);
            }
        }
    }

    private void armorNumPressListener(int rawSlot, ItemStack current, Inventory clickedInventory, InventoryClickEvent event, Player player, GuardianData guardianData, RPGCharacter rpgCharacter) {
        ItemStack hotbarItem = clickedInventory.getItem(event.getHotbarButton()); //item in hotbar slot of pressed num key

        if (rawSlot >= 5 && rawSlot <= 8) {
            if (InventoryUtils.isAirOrNull(hotbarItem)) { //removing currently equipped item from armor slot
                if (!InventoryUtils.isAirOrNull(current)) {
                    rpgCharacter.getRpgCharacterStats().onArmorUnequip(current);
                }
            } else {
                ArmorType armorTypeOfHotBarItem = ArmorType.getArmorType(hotbarItem.getType());

                if (armorTypeOfHotBarItem != null) { //hotbar item is armor
                    if (event.getRawSlot() != armorTypeOfHotBarItem.getSlot())
                        return; //Used for drag and drop checking to make sure you aren't trying to place a helmet in the boots slot

                    if (InventoryUtils.isArmorEquippedOnRelatedSlot(armorTypeOfHotBarItem, player)) {
                        //player is already equipping an item on slot we are managing
                        //player replaces current armor with item on hotbar
                        if (StatUtils.doesCharacterMeetRequirements(hotbarItem, player, rpgCharacter.getRpgClass())) {
                            rpgCharacter.getRpgCharacterStats().onArmorUnequip(current);
                            rpgCharacter.getRpgCharacterStats().onArmorEquip(hotbarItem);
                        } else {
                            event.setCancelled(true);
                        }
                    } else {
                        //armor slot is empty
                        if (StatUtils.doesCharacterMeetRequirements(hotbarItem, player, rpgCharacter.getRpgClass())) {
                            rpgCharacter.getRpgCharacterStats().onArmorEquip(hotbarItem);
                        } else {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    private void armorNormalClickListener(ItemStack cursor, Material cursorType, ItemStack current, Material currentType, InventoryClickEvent event, Player player, GuardianData guardianData, RPGCharacter rpgCharacter) {
        ArmorType armorTypeOfCursor = ArmorType.getArmorType(cursorType);
        if (armorTypeOfCursor != null) {
            if (event.getRawSlot() != armorTypeOfCursor.getSlot())
                return; //Used for drag and drop checking to make sure you aren't trying to place a helmet in the boots slot

            if (InventoryUtils.isArmorEquippedOnRelatedSlot(armorTypeOfCursor, player)) {
                //player is already equipping an item on slot we are managing
                //player replaces current armor with item on cursor
                if (StatUtils.doesCharacterMeetRequirements(cursor, player, rpgCharacter.getRpgClass())) {
                    rpgCharacter.getRpgCharacterStats().onArmorUnequip(current);
                    rpgCharacter.getRpgCharacterStats().onArmorEquip(cursor);
                } else {
                    event.setCancelled(true);
                }
            } else {
                //armor slot is empty
                //player equips item on cursor
                if (StatUtils.doesCharacterMeetRequirements(cursor, player, rpgCharacter.getRpgClass())) {
                    rpgCharacter.getRpgCharacterStats().onArmorEquip(cursor);
                } else {
                    event.setCancelled(true);
                }
            }
        } else if (InventoryUtils.isAirOrNull(cursor)) { //cursor is empty
            ArmorType armorTypeOfCurrentItem = ArmorType.getArmorType(currentType);
            if (armorTypeOfCurrentItem == null) return; //clicked item is not armor

            if (event.getRawSlot() == armorTypeOfCurrentItem.getSlot()) { //unequip current
                rpgCharacter.getRpgCharacterStats().onArmorUnequip(current);
            } //else clicked item is armor which is not equipped
        }//no else since nothing happens with cursor with nonArmor item
    }

    private void offhandNormalClickListener(ItemStack cursor, Material cursorType, ItemStack current, Material currentType, InventoryClickEvent event, Player player, GuardianData guardianData, RPGCharacter rpgCharacter) {
        if (!InventoryUtils.isAirOrNull(cursor)) { //with item on cursor
            if (cursorType.equals(Material.SHIELD) || cursorType.equals(Material.DIAMOND_HOE)) {
                if (StatUtils.doesCharacterMeetRequirements(cursor, player, rpgCharacter.getRpgClass())) {
                    if (current != null) {
                        if (!current.getType().equals(Material.AIR)) {
                            rpgCharacter.getRpgCharacterStats().onOffhandUnequip(current);
                        }
                    }
                    rpgCharacter.getRpgCharacterStats().onOffhandEquip(cursor);
                } else {
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(true);
            }
        } else { //without item on cursor
            rpgCharacter.getRpgCharacterStats().onOffhandUnequip(current);
        }
    }

    private void offhandNumPressListener(int rawSlot, ItemStack current, Inventory clickedInventory, InventoryClickEvent event, Player player, GuardianData guardianData, RPGCharacter rpgCharacter) {
        ItemStack hotbarItem = clickedInventory.getItem(event.getHotbarButton()); //item in hotbar slot of pressed num key

        if (!InventoryUtils.isAirOrNull(hotbarItem)) { //with item on hotbar
            Material hotbarItemType = hotbarItem.getType();
            if (hotbarItemType.equals(Material.SHIELD) || hotbarItemType.equals(Material.DIAMOND_HOE)) {
                if (StatUtils.doesCharacterMeetRequirements(hotbarItem, player, rpgCharacter.getRpgClass())) {
                    if (current != null) {
                        if (!current.getType().equals(Material.AIR)) {
                            rpgCharacter.getRpgCharacterStats().onOffhandUnequip(current);
                        }
                    }
                    rpgCharacter.getRpgCharacterStats().onOffhandEquip(hotbarItem);
                } else {
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(true);
            }
        } else { //without item on cursor
            rpgCharacter.getRpgCharacterStats().onOffhandUnequip(current);
        }
    }

    private void armorAndOffhandListener(Player player, InventoryType.SlotType slotType, Inventory clickedInventory, InventoryClickEvent event, ItemStack current,
                                         Material currentType, ItemStack cursor, Material cursorType, GuardianData guardianData, RPGCharacter rpgCharacter) {
        if (slotType != InventoryType.SlotType.ARMOR && slotType != InventoryType.SlotType.QUICKBAR && slotType != InventoryType.SlotType.CONTAINER)
            return;
        if (!clickedInventory.getType().equals(InventoryType.PLAYER)) return; //Prevents shit in the 2by2 crafting

        if (event.getRawSlot() == 45) { //clicked on off hand slot
            if (event.getClick().equals(ClickType.NUMBER_KEY)) {
                offhandNumPressListener(event.getRawSlot(), current, clickedInventory, event, player, guardianData, rpgCharacter);
            } else {
                offhandNormalClickListener(cursor, cursorType, current, currentType, event, player, guardianData, rpgCharacter);
            }
            return;
        }

        if (event.isShiftClick()) {
            armorShiftClickListener(current, currentType, event, player, guardianData, rpgCharacter);
        } else if (event.getClick().equals(ClickType.NUMBER_KEY)) {
            armorNumPressListener(event.getRawSlot(), current, clickedInventory, event, player, guardianData, rpgCharacter);
        } else { //drag and drop
            armorNormalClickListener(cursor, cursorType, current, currentType, event, player, guardianData, rpgCharacter);
        }
    }*/
}
