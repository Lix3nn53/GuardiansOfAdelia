package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.gui.HelmetSkinApplyGui;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.gui.WeaponOrShieldSkinApplyGui;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.HelmetSkin;
import io.github.lix3nn53.guardiansofadelia.chat.ChatTag;
import io.github.lix3nn53.guardiansofadelia.economy.Coin;
import io.github.lix3nn53.guardiansofadelia.economy.CoinType;
import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.Bazaar;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.BazaarCustomerGui;
import io.github.lix3nn53.guardiansofadelia.economy.trading.Trade;
import io.github.lix3nn53.guardiansofadelia.economy.trading.TradeGui;
import io.github.lix3nn53.guardiansofadelia.economy.trading.TradeInvite;
import io.github.lix3nn53.guardiansofadelia.economy.trading.TradeManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.Attribute;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillBar;
import io.github.lix3nn53.guardiansofadelia.guild.Guild;
import io.github.lix3nn53.guardiansofadelia.guild.GuildInvite;
import io.github.lix3nn53.guardiansofadelia.guild.GuildManager;
import io.github.lix3nn53.guardiansofadelia.guild.PlayerRankInGuild;
import io.github.lix3nn53.guardiansofadelia.items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.items.config.WeaponReferenceData;
import io.github.lix3nn53.guardiansofadelia.items.enchanting.EnchantGui;
import io.github.lix3nn53.guardiansofadelia.items.list.armors.ArmorSlot;
import io.github.lix3nn53.guardiansofadelia.items.stats.GearStatType;
import io.github.lix3nn53.guardiansofadelia.items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.jobs.crafting.CraftingGuiManager;
import io.github.lix3nn53.guardiansofadelia.jobs.crafting.CraftingType;
import io.github.lix3nn53.guardiansofadelia.menu.MenuList;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.npc.merchant.MerchantManager;
import io.github.lix3nn53.guardiansofadelia.npc.merchant.MerchantMenu;
import io.github.lix3nn53.guardiansofadelia.npc.merchant.MerchantPageType;
import io.github.lix3nn53.guardiansofadelia.npc.merchant.SellGui;
import io.github.lix3nn53.guardiansofadelia.party.PartyInvite;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.revive.TombManager;
import io.github.lix3nn53.guardiansofadelia.rewards.daily.DailyRewardHandler;
import io.github.lix3nn53.guardiansofadelia.rpginventory.RPGInventory;
import io.github.lix3nn53.guardiansofadelia.sounds.CustomSound;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.transportation.InstantTeleportGuiItem;
import io.github.lix3nn53.guardiansofadelia.transportation.InstantTeleportGuiManager;
import io.github.lix3nn53.guardiansofadelia.transportation.TeleportationUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.Gui;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiBookGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.CharacterSelectionScreenManager;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.CompassManager;
import io.github.lix3nn53.guardiansofadelia.utilities.signmenu.SignMenuFactory;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
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
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                    } else if (event.getRawSlot() == ArmorSlot.HELMET.getSlot()) {
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
            } else if (event.getRawSlot() == ArmorSlot.HELMET.getSlot()) {
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

        GuardianData guardianData = null;
        RPGCharacter rpgCharacter = null;
        if (GuardianDataManager.hasGuardianData(player)) {
            guardianData = GuardianDataManager.getGuardianData(player);

            if (guardianData.hasActiveCharacter()) {
                rpgCharacter = guardianData.getActiveCharacter();

                /*if (event.getAction() != InventoryAction.NOTHING) {
                    if (clickedInventory != null && clickedInventory.getType().equals(InventoryType.PLAYER)) {
                        RPGCharacter rpgCharacterForEquipment = guardianData.getActiveCharacter();
                        String rpgClassStr = rpgCharacter.getRpgClassStr();

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                rpgCharacterForEquipment.getRpgCharacterStats().recalculateEquipment(rpgClassStr);
                            }
                        }.runTaskLater(GuardiansOfAdelia.getInstance(), 1L);
                    }
                }*/
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
                            if (!InventoryUtils.canTradeMaterial(currentType)) {
                                player.sendMessage(ChatPalette.RED + "You can't trade this item");
                            }
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
                        boolean b = sellGui.addItemToSell(current, slot);
                        if (!b) {
                            player.sendMessage(ChatPalette.RED + "You can't sell this item");
                        }
                    }
                } else if (activeGui instanceof EnchantGui) {
                    EnchantGui enchantGui = (EnchantGui) activeGui;
                    if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                        if (currentType.equals(Material.EMERALD_BLOCK)) {
                            enchantGui.startEnchanting(player);
                        }
                    } else if (clickedInventory.getType().equals(InventoryType.PLAYER)) {
                        if (PersistentDataContainerUtil.hasInteger(current, "ench_stone")) {
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
                                MessageUtils.sendCenteredMessage(player, ChatPalette.GRAY + "------------------------");
                                MessageUtils.sendCenteredMessage(player, "Applied Skin");
                                MessageUtils.sendCenteredMessage(player, "to " + weaponOrShieldSkinApplyGui.getItemOnSlot().getItemMeta().getDisplayName());
                                MessageUtils.sendCenteredMessage(player, ChatPalette.GRAY + "------------------------");
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
                                MessageUtils.sendCenteredMessage(player, ChatPalette.GRAY + "------------------------");
                                MessageUtils.sendCenteredMessage(player, "Applied Skin");
                                MessageUtils.sendCenteredMessage(player, "to " + helmetSkinApplyGui.getItemOnSlot().getItemMeta().getDisplayName());
                                MessageUtils.sendCenteredMessage(player, ChatPalette.GRAY + "------------------------");
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

        if (currentName.equals(ChatPalette.GREEN_DARK + "Menu")) {
            event.setCancelled(true);
            if (!cursorType.equals(Material.AIR)) {
                return;
            }

            new BukkitRunnable() {

                @Override
                public void run() {
                    GuiGeneric guiGeneric = MenuList.mainMenu();
                    guiGeneric.openInventory(player);
                }
            }.runTaskLater(GuardiansOfAdelia.getInstance(), 1);
        } else if (currentType.equals(Material.ARROW)) {
            event.setCancelled(true);
        } else if (title.equals(ChatPalette.GRAY_DARK + "Guardians of Adelia")) {
            if (currentName.equals(ChatPalette.GREEN_DARK + "Guides")) {
                GuiGeneric guide = MenuList.guide();
                guide.openInventory(player);
            } else if (currentName.equals(ChatPalette.BLUE + "Compass")) {
                GuiGeneric compass = MenuList.compass();
                compass.openInventory(player);
            } else if (currentName.equals(ChatPalette.GREEN_DARK + "Maps")) {
                player.closeInventory();
                TextComponent message = new TextComponent(" Maps ! (Click Me)");
                message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://guardiansofadelia.com/#t5"));
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatPalette.BLUE_LIGHT + "Click to see Maps from our website")));
                message.setColor(ChatPalette.GREEN_DARK.toChatColor());
                message.setBold(true);
                player.spigot().sendMessage(message);
            } else if (currentName.equals(ChatPalette.GREEN_DARK + "Character")) {
                GuiGeneric character = MenuList.character(guardianData);
                character.openInventory(player);
            } else if (currentName.equals(ChatPalette.PURPLE + "Guild")) {
                if (GuildManager.inGuild(player)) {
                    MenuList.guild().openInventory(player);
                }
            } else if (currentName.equals(ChatPalette.PURPLE + "Minigames")) {
                GuiGeneric minigames = MenuList.minigames();
                minigames.openInventory(player);
            } else if (currentName.equals(ChatPalette.GOLD + "Bazaar")) {
                GuiGeneric bazaar = MenuList.bazaar(player);
                bazaar.openInventory(player);
            } else if (currentName.equals(ChatPalette.PURPLE_LIGHT + "WebStore ♥")) {
                player.closeInventory();
                TextComponent message = new TextComponent(" Donation ♥ ! (Click Me)");
                message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://guardiansofadelia.com/store"));
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatPalette.PURPLE_LIGHT + "Click to donate ♥")));
                message.setColor(ChatPalette.PURPLE_LIGHT.toChatColor());
                message.setBold(true);
                player.spigot().sendMessage(message);
            } else if (currentName.equals(ChatPalette.GOLD + "Server Boosts")) {
                GuiGeneric serverBoostMenu = MenuList.serverBoostMenu();
                serverBoostMenu.openInventory(player);
            } else if (currentName.equals(ChatPalette.PURPLE_LIGHT + "Instant Teleportation")) {
                GuiBookGeneric guiBook = InstantTeleportGuiManager.getGuiBook(guardianData);
                guiBook.openInventory(player);
            } else if (currentName.equals(ChatPalette.GOLD + "Daily Rewards")) {
                GuiGeneric dailyRewardsMenu = MenuList.dailyRewardsMenu(player);
                dailyRewardsMenu.openInventory(player);
            }
        } else if (title.contains("Play Tutorial?")) {
            //Play Tutorial? ClassName CharNo
            String[] split = title.split("#");

            int charNo = Integer.parseInt(split[1]);

            if (currentType.equals(Material.LIME_WOOL)) {
                CharacterSelectionScreenManager.createCharacter(player, charNo);
            } else if (currentType.equals(Material.RED_WOOL)) {
                CharacterSelectionScreenManager.createCharacterWithoutTutorial(player, charNo);
            }
        } else if (title.contains("Character")) {
            if (title.contains("Selection")) {
                String charNoString = title.replace(ChatPalette.GRAY_DARK + "Character ", "");
                charNoString = charNoString.replace(" Selection", "");
                int charNo = Integer.parseInt(charNoString);

                if (currentName.contains("last location")) {
                    Location charLocation = CharacterSelectionScreenManager.getCharLocation(player, charNo);
                    if (charLocation != null) {
                        CharacterSelectionScreenManager.selectCharacter(player, charNo, charLocation);
                    } else {
                        player.sendMessage(ChatPalette.RED + "Your saved last location is not valid");
                    }
                } else if (currentName.contains("#")) {
                    String[] split = currentName.split("#");
                    int townNo = Integer.parseInt(split[1]);

                    Town town = TownManager.getTown(townNo);

                    int charLevel = CharacterSelectionScreenManager.getCharLevel(player, charNo);
                    if (charLevel < town.getLevel()) return;

                    Location location = town.getLocation();

                    CharacterSelectionScreenManager.selectCharacter(player, charNo, location);
                }
            } else {
                if (currentName.equals(ChatPalette.YELLOW + "Class")) {
                    GuiGeneric classManage = MenuList.classManager(player);
                    classManage.openInventory(player);
                } else if (currentName.equals(ChatPalette.PURPLE_LIGHT + "Skills")) {
                    GuiGeneric skill = MenuList.skill(player);
                    skill.openInventory(player);
                } else if (currentName.equals(ChatPalette.GREEN_DARK + "Stat Points")) {
                    GuiGeneric statPoints = MenuList.statPoints(player);
                    statPoints.openInventory(player);
                } else if (currentName.equals(ChatPalette.YELLOW + "Crafting")) {
                    GuiGeneric crafting = MenuList.crafting(player);
                    crafting.openInventory(player);
                } else if (currentName.equals(ChatPalette.BLUE_LIGHT + "Chat Tag")) {
                    GuiGeneric chatTag = MenuList.chatTagQuests(player);
                    chatTag.openInventory(player);
                }
            }
        } else if (title.contains(ChatPalette.GRAY_DARK + "Quest List of ")) {
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
                                List<Quest> questList = rpgCharacter.getQuestList();
                                Quest quest = null;
                                for (Quest q : questList) {
                                    if (q.getQuestID() == questNo) {
                                        quest = q;
                                        break;
                                    }
                                }
                                List<ItemStack> itemPrizesSelectOneOf = quest.getItemPrizesSelectOneOf();
                                WeaponReferenceData weaponPrizesSelectOneOf = quest.getWeaponPrizesSelectOneOf();
                                if (itemPrizesSelectOneOf.isEmpty() && weaponPrizesSelectOneOf == null) {
                                    //turnin quest
                                    boolean didTurnIn = rpgCharacter.turnInQuest(questNo, player, false);
                                    if (didTurnIn) {
                                        GuiGeneric questGui = QuestNPCManager.getQuestGui(player, resourceNPC);
                                        questGui.openInventory(player);
                                    } else {
                                        player.sendMessage(ChatPalette.RED + "Couldn't turn in the quest ERROR report pls");
                                    }
                                } else {
                                    // GUISIZE
                                    int guiSize = 18;
                                    if (weaponPrizesSelectOneOf != null) {
                                        guiSize += 9;
                                    }
                                    int normalSelectOneOfSize = itemPrizesSelectOneOf.size();
                                    if (normalSelectOneOfSize > 4) {
                                        guiSize += 9;
                                    } else if (normalSelectOneOfSize > 8) {
                                        guiSize += 18;
                                    } else if (normalSelectOneOfSize > 8) {
                                        guiSize += 18;
                                    }

                                    // ITEM SLOTS
                                    List<Integer> slotsToUse = new ArrayList<>();
                                    slotsToUse.add(10);
                                    slotsToUse.add(12);
                                    slotsToUse.add(14);
                                    slotsToUse.add(16);

                                    slotsToUse.add(19);
                                    slotsToUse.add(21);
                                    slotsToUse.add(23);
                                    slotsToUse.add(25);

                                    slotsToUse.add(28);
                                    slotsToUse.add(30);
                                    slotsToUse.add(32);
                                    slotsToUse.add(34);

                                    slotsToUse.add(28);
                                    slotsToUse.add(30);
                                    slotsToUse.add(32);
                                    slotsToUse.add(34);

                                    // CREATE GUI
                                    GuiGeneric guiGeneric = new GuiGeneric(guiSize, ChatPalette.BLACK + "Quest Item Prize Selection #" + questNo, resourceNPC);

                                    // PLACE ITEMS
                                    int index = 0;
                                    for (int i = index; i < normalSelectOneOfSize; i++) {
                                        ItemStack itemStack = itemPrizesSelectOneOf.get(i);
                                        Integer slotNo = slotsToUse.get(i);
                                        guiGeneric.setItem(slotNo, itemStack);
                                        index++;
                                    }

                                    if (weaponPrizesSelectOneOf != null) {
                                        List<ItemStack> items = weaponPrizesSelectOneOf.getItems(rpgCharacter.getRpgClassStr());
                                        for (ItemStack itemStack : items) {
                                            Integer slotNo = slotsToUse.get(index);
                                            guiGeneric.setItem(slotNo, itemStack);
                                            index++;
                                        }
                                    }

                                    guiGeneric.openInventory(player);
                                }
                            } else {
                                NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
                                NPC byId = npcRegistry.getById(whoCanCompleteThisQuest);
                                player.sendMessage(ChatPalette.RED + "You can't turn in this quest from this NPC. You need to talk with " + ChatPalette.WHITE + byId.getName());
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
                                    player.sendMessage(ChatPalette.RED + "Your quest list is full");
                                }
                            } else {
                                NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
                                NPC byId = npcRegistry.getById(whoCanGiveThisQuest);
                                player.sendMessage(ChatPalette.RED + "You can't take this quest from this NPC. You need to talk with " + ChatPalette.WHITE + byId.getName());
                            }
                        }
                    }
                }
            }
        } else if (title.contains(ChatPalette.BLACK + "Quest Item Prize Selection #")) {
            if (clickedInventory.getType().equals(InventoryType.CHEST) && !currentType.equals(Material.AIR)) {
                int resourceNPC = activeGui.getResourceNPC();

                String[] split = title.split("#");
                int questNo = Integer.parseInt(split[1]);

                //give item
                GearStatType gearStatType = StatUtils.getStatType(currentType);
                if (gearStatType != null) {
                    GearLevel gearLevel = GearLevel.getGearLevel(current);
                    ItemTier itemTier = ItemTier.getItemTierOfItemStack(current);
                    StatUtils.addRandomPassiveStats(current, gearLevel, itemTier);
                }
                InventoryUtils.giveItemToPlayer(player, current);

                //turnin quest
                boolean didTurnIn = rpgCharacter.turnInQuest(questNo, player, false);
                if (didTurnIn) {
                    GuiGeneric questGui = QuestNPCManager.getQuestGui(player, resourceNPC);
                    questGui.openInventory(player);
                } else {
                    player.sendMessage(ChatPalette.RED + "Couldn't turn in the quest ERROR report pls");
                }
            }
        } else if (title.contains(ChatPalette.BLACK + "Task Item Prize Selection #")) {
            if (clickedInventory.getType().equals(InventoryType.CHEST) && !currentType.equals(Material.AIR)) {
                int resourceNPC = activeGui.getResourceNPC();

                // Task Item Prize Selection #12&1
                String[] split = title.split("&");
                int taskNo = Integer.parseInt(split[1]);

                String[] split2 = split[0].split("#");
                int questNo = Integer.parseInt(split2[1]);

                //give item
                GearStatType gearStatType = StatUtils.getStatType(currentType);
                if (gearStatType != null) {
                    GearLevel gearLevel = GearLevel.getGearLevel(current);
                    ItemTier itemTier = ItemTier.getItemTierOfItemStack(current);
                    StatUtils.addRandomPassiveStats(current, gearLevel, itemTier);
                }
                InventoryUtils.giveItemToPlayer(player, current);

                //complete task
                boolean didComplete = rpgCharacter.progressTaskOfQuestWithIndex(player, questNo, taskNo);
                if (didComplete) {
                    player.closeInventory();
                    MessageUtils.sendCenteredMessage(player, ChatPalette.GOLD + "Obtained " + currentName);
                } else {
                    player.sendMessage(ChatPalette.RED + "Couldn't complete the task ERROR report pls");
                }
            }
        } else if (title.equals(ChatPalette.GOLD + "Coin Converter")) {
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
        } else if (title.contains(ChatPalette.GRAY_DARK + "Compass")) {
            if (title.contains("Towns")) {
                String displayName = itemMeta.getDisplayName();
                String[] split = displayName.split("#");
                int i = Integer.parseInt(split[1]);

                Town town = TownManager.getTown(i);
                CompassManager.setCompassItemLocation(player, ChatPalette.BLUE_LIGHT + town.getName(), town.getLocation());
                player.closeInventory();
            } else if (title.contains("Dungeon Gates")) {
                String displayName = itemMeta.getDisplayName();
                String[] split = displayName.split("#");
                Location portalLocationOfDungeonTheme = MiniGameManager.getPortalLocationOfDungeonTheme(split[1]);
                CompassManager.setCompassItemLocation(player, split[0], portalLocationOfDungeonTheme);
                player.closeInventory();
            } else if (title.contains("NPCs")) {
                String displayName = itemMeta.getDisplayName();
                String[] split = displayName.split("#");
                int i = Integer.parseInt(split[1]);
                CompassManager.setCompassItemNPC(player, i);
                player.closeInventory();
            } else if (title.contains("Active Quests")) {
                if (currentType.equals(Material.LIME_WOOL)) {
                    String displayName = itemMeta.getDisplayName();
                    String[] split = displayName.split("#");
                    int i = Integer.parseInt(split[1]);
                    CompassManager.setCompassItemNPC(player, i);
                    player.closeInventory();
                } else if (currentType.equals(Material.MAGENTA_WOOL)) {
                    String displayName = itemMeta.getDisplayName();
                    String[] split = displayName.split("#");

                    String[] splitSecond = split[1].split("-");

                    Location location = new Location(Bukkit.getWorld(splitSecond[0]), Double.parseDouble(splitSecond[1]),
                            Double.parseDouble(splitSecond[2]), Double.parseDouble(splitSecond[3]));

                    CompassManager.setCompassItemLocation(player, split[0], location);
                    player.closeInventory();
                }
            } else {
                if (currentType.equals(Material.LIGHT_BLUE_WOOL)) { //towns
                    GuiBookGeneric guiBookGeneric = MenuList.compassTowns();
                    guiBookGeneric.openInventory(player);
                } else if (currentType.equals(Material.PURPLE_WOOL)) { //dungeon gates
                    GuiBookGeneric guiBookGeneric = MenuList.compassDungeonGates();
                    guiBookGeneric.openInventory(player);
                } else if (currentType.equals(Material.LIME_WOOL)) { //npcs
                    GuiBookGeneric guiBookGeneric = MenuList.compassNPCs();
                    guiBookGeneric.openInventory(player);
                } else if (currentType.equals(Material.MAGENTA_WOOL)) { //quests
                    GuiBookGeneric guiBookGeneric = MenuList.compassActiveQuests(guardianData);
                    guiBookGeneric.openInventory(player);
                }
            }
        } else if (title.contains(ChatPalette.GRAY_DARK + "Stat Points (Points:")) {
            if (rpgCharacter != null) {
                RPGCharacterStats rpgCharacterStats = rpgCharacter.getRpgCharacterStats();
                Attribute attr = null;
                if (slot == 1) {
                    attr = rpgCharacterStats.getAttribute(AttributeType.BONUS_ELEMENT_DAMAGE);
                } else if (slot == 4) {
                    attr = rpgCharacterStats.getAttribute(AttributeType.BONUS_ELEMENT_DEFENSE);
                } else if (slot == 7) {
                    attr = rpgCharacterStats.getAttribute(AttributeType.BONUS_MAX_HEALTH);
                } else if (slot == 20) {
                    attr = rpgCharacterStats.getAttribute(AttributeType.BONUS_MAX_MANA);
                } else if (slot == 24) {
                    attr = rpgCharacterStats.getAttribute(AttributeType.BONUS_CRITICAL_CHANCE);
                }
                if (attr != null) {
                    if (event.isLeftClick()) {
                        int pointsLeftToSpend = rpgCharacterStats.getAttributePointsLeftToSpend();
                        if (pointsLeftToSpend > 0) {
                            int amount = 1;
                            if (event.isShiftClick()) {
                                if (pointsLeftToSpend >= 5) {
                                    amount = 5;
                                } else {
                                    amount = pointsLeftToSpend;
                                }
                            }

                            attr.investPoint(amount, rpgCharacterStats, true);
                            GuiGeneric statPoints = MenuList.statPoints(player);
                            statPoints.openInventory(player);
                        }
                    } else if (event.isRightClick()) {
                        int invested = attr.getInvested();
                        if (invested > 0) {
                            int amount = 1;
                            if (event.isShiftClick()) {
                                if (invested >= 5) {
                                    amount = 5;
                                } else {
                                    amount = invested;
                                }
                            }

                            attr.downgradePoint(amount, rpgCharacterStats, true);
                            GuiGeneric statPoints = MenuList.statPoints(player);
                            statPoints.openInventory(player);
                        }
                    }
                }
            }
        } else if (title.contains(ChatPalette.GRAY_DARK + "Class Manager")) {
            if (rpgCharacter != null) {
                String[] split = currentName.split("#");
                if (split.length != 2) return;
                int rank = Integer.parseInt(split[1]);
                GuiGeneric guiGeneric = MenuList.classChange(player, rank);
                guiGeneric.openInventory(player);
            }
        } else if (title.contains(ChatPalette.GRAY_DARK + "Class Change")) {
            if (rpgCharacter != null) {
                String displayName = itemMeta.getDisplayName();
                String stripColor = ChatColor.stripColor(displayName);
                String rpgClassStr = stripColor.toUpperCase();

                boolean b = rpgCharacter.changeClass(player, rpgClassStr);
                if (b) player.closeInventory();
            }
        } else if (title.contains(ChatPalette.GRAY_DARK + "Skills (Points: ")) {
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
                        boolean upgradeSkill = skillBar.upgradeSkill(skillIndex, rpgCharacter.getCurrentRPGClassStats());
                        if (upgradeSkill) {
                            GuiGeneric skill = MenuList.skill(player);
                            skill.openInventory(player);
                        }
                    } else if (event.isRightClick()) {
                        boolean downgradeSkill = skillBar.downgradeSkill(skillIndex, rpgCharacter.getCurrentRPGClassStats());
                        if (downgradeSkill) {
                            GuiGeneric skill = MenuList.skill(player);
                            skill.openInventory(player);
                        }
                    }
                }
            }
        } else if (title.equals(ChatPalette.YELLOW + "Crafting")) {
            //TODO Do we need click listeners in crafting info menu?
        } else if (title.equals(ChatPalette.GRAY_DARK + "Guild")) {
            if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                if (currentName.equals(ChatPalette.RED + "Join Guild War")) {
                    MiniGameManager.getGuildWarJoinGui().openInventory(player);
                }
            }
        } else if (title.equals(ChatPalette.GRAY_DARK + "Join Guild War")) {
            if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                int i = currentName.indexOf("#");
                String c = String.valueOf(currentName.charAt(i + 1));
                int instanceNo = Integer.parseInt(c);
                boolean joined = MiniGameManager.getGuildWar(instanceNo).joinQueue(player);
                if (joined) {
                    player.closeInventory();
                }
            }
        } else if (title.equals(ChatPalette.GRAY_DARK + "MiniGames")) {
            if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                if (currentName.equals(ChatPalette.RED + "Last One Standing")) {
                    MiniGameManager.getLastOneStandingJoinGui().openInventory(player);
                } else if (currentName.equals(ChatPalette.RED + "Win By Most Kills")) {
                    MiniGameManager.getWinByMostKillsJoinGui().openInventory(player);
                }
            }
        } else if (title.equals(ChatPalette.GOLD + "Join Last One Standing")) {
            if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                int i = currentName.indexOf("#");
                String c = String.valueOf(currentName.charAt(i + 1));
                int instanceNo = Integer.parseInt(c);
                boolean joined = MiniGameManager.getLastOneStanding(instanceNo).joinQueue(player);
                if (joined) {
                    player.closeInventory();
                }
            }
        } else if (title.equals(ChatPalette.GOLD + "Join Win By Most Kills")) {
            if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                int i = currentName.indexOf("#");
                String c = String.valueOf(currentName.charAt(i + 1));
                int instanceNo = Integer.parseInt(c);
                boolean joined = MiniGameManager.getWinByMostKills(instanceNo).joinQueue(player);
                if (joined) {
                    player.closeInventory();
                }
            }
        } else if (title.equals(ChatPalette.GRAY_DARK.toString() + ChatColor.BOLD + "RPG Inventory")) {
            RPGInventory rpgInventory = rpgCharacter.getRpgInventory();
            String rpgClassStr = rpgCharacter.getRpgClassStr();
            if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                event.setCancelled(true);
                if (cursorType.equals(Material.AIR)) {
                    boolean change = rpgInventory.onCursorClickWithAir(player, slot, topInventory, event.isShiftClick());
                } else {
                    boolean change = rpgInventory.onCursorClickWithItem(player, slot, cursor, topInventory, rpgClassStr);
                }
            } else if (clickedInventory.getType().equals(InventoryType.PLAYER)) {
                if (cursorType.equals(Material.AIR)) {
                    if (event.isShiftClick()) {
                        event.setCancelled(true);
                        boolean change = rpgInventory.onShiftClick(current, player, slot, topInventory, rpgClassStr);
                    }
                }
            }
        } else if (title.equals(ChatPalette.BLUE_LIGHT + "Revive Gui")) {
            if (slot == 5) {
                TombManager.startSearch(player);
                player.closeInventory(); //calling inventory close event after startSearch doesn't cancel search
            } else if (slot == 3) {
                //inventory close event cancels tomb search so no need to call it twice
                player.closeInventory();
            }
        } else if (title.equals(ChatPalette.GOLD + "Bazaar")) {
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
        } else if (title.equals(ChatPalette.GOLD + "Edit your bazaar")) {
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
                            player.sendMessage(ChatPalette.RED + "Removed your bazaar");
                        }
                    }
                } else {
                    if (guardianData != null) {
                        if (guardianData.hasBazaar()) {
                            Bazaar bazaar = guardianData.getBazaar();
                            boolean isRemoved = bazaar.removeItem(current, current.getAmount());
                            if (isRemoved) {
                                ItemStack itemStack = EconomyUtils.removeShopPrice(current);
                                InventoryUtils.giveItemToPlayer(player, itemStack);
                                bazaar.edit();
                            }
                        }
                    }
                }
            } else if (clickedInventory.getType().equals(InventoryType.PLAYER)) {
                SignMenuFactory signMenuFactory = GuardiansOfAdelia.getSignMenuFactory();

                SignMenuFactory.Menu menu = signMenuFactory.newMenu(Arrays.asList("Set price of:", currentName, "▼ Enter below ▼"))
                        .reopenIfFail(true)
                        .response((signPlayer, strings) -> {
                            if (GuardianDataManager.hasGuardianData(player)) {
                                GuardianData guardianDataBazaar = GuardianDataManager.getGuardianData(player);
                                if (guardianDataBazaar.hasBazaar()) {
                                    Bazaar bazaar = guardianDataBazaar.getBazaar();

                                    int price;
                                    try {
                                        price = Integer.parseInt(strings[3]);
                                    } catch (NumberFormatException e) {
                                        player.sendMessage(ChatPalette.RED + "Enter a number between 1-266239 to last line.");
                                        return false;
                                    }

                                    if (price > 266304) {
                                        player.sendMessage(ChatPalette.RED + "Max price is 266239.");
                                        return false;
                                    }

                                    bazaar.addItem(current, price);
                                    InventoryUtils.removeItemFromInventory(player.getInventory(), current, current.getAmount());
                                    bazaar.edit();
                                }
                            }

                            return true;
                        });

                menu.open(player);
            }
        } else if (title.equals("Bazaar Storage")) {
            if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                if (guardianData != null) {
                    if (guardianData.hasBazaar()) {
                        Bazaar bazaar = guardianData.getBazaar();
                        List<ItemStack> itemsOnSale = bazaar.getItemsOnSale();
                        if (itemsOnSale.contains(current)) {
                            player.closeInventory();
                            player.sendMessage(ChatPalette.RED + "You can't get an item from bazaar storage which is on sale");
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
                    //dungeon theme is after # of inv name
                    String dungeonThemeCode = title.split("#")[1];

                    //get char after # for dungeon room no from clicked
                    int i = currentName.indexOf("#");
                    String c = String.valueOf(currentName.charAt(i + 1));
                    int instanceNo = Integer.parseInt(c);

                    boolean joined = MiniGameManager.getDungeonInstance(dungeonThemeCode, instanceNo).joinQueue(player);
                    if (joined) {
                        player.closeInventory();
                    }
                }
            }
        } else if (title.contains(" Crafting")) {
            if (title.contains(" Level Selection")) {
                if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                    if (currentType.equals(Material.WOODEN_PICKAXE)) {
                        if (rpgCharacter != null) {
                            String replace = title.replace(" Crafting Level Selection", "");
                            CraftingType craftingType = CraftingType.valueOf(replace);

                            int currentLevel = rpgCharacter.getCraftingStats().getCurrentLevel(craftingType);

                            String s = currentName.replaceAll(ChatPalette.GOLD + "Level ", "");
                            int craftingLevel = Integer.parseInt(s);

                            if (currentLevel >= craftingLevel) {
                                GuiBookGeneric craftingBook = CraftingGuiManager.getCraftingBook(craftingType, craftingLevel);
                                craftingBook.openInventory(player);
                            } else {
                                player.sendMessage(ChatPalette.RED + "Required crafting level to craft " + currentName + ChatPalette.RED + " items is " + ChatPalette.GOLD + craftingLevel);
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
                            if (displayName.equals(ChatPalette.GOLD + "Crafting Guide")) {
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

                                        String[] split = title.split(" Crafting Level ");

                                        String levelStrWithPage = split[1];

                                        String[] split2 = levelStrWithPage.split(" Page");

                                        String levelStr = split2[0];
                                        int craftingLevel = Integer.parseInt(levelStr);
                                        jobExpToGive = jobExpToGive * craftingLevel;
                                        GearLevel gearLevel = GearLevel.values()[craftingLevel];

                                        StatUtils.addRandomPassiveStats(clone, gearLevel, ItemTier.MYSTIC);

                                        for (ItemStack ingredient : ingredients) {
                                            InventoryUtils.removeItemFromInventory(player.getInventory(), ingredient, ingredient.getAmount());
                                        }
                                        InventoryUtils.giveItemToPlayer(player, clone);

                                        CraftingType craftingType = CraftingType.valueOf(split[0]);

                                        rpgCharacter.getCraftingStats().addExperience(player, craftingType, jobExpToGive);

                                        CustomSound customSound = GoaSound.ANVIL.getCustomSound();
                                        customSound.play(player.getLocation());

                                        // Quest TaskCrafting
                                        List<Quest> questList = rpgCharacter.getQuestList();
                                        for (Quest quest : questList) {
                                            quest.progressCraftingTasks(player, craftingType, clone);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (title.contains(ChatPalette.GRAY_DARK + "Interact with ")) {
            if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                String rightClickedName = title.replace(ChatPalette.GRAY_DARK + "Interact with ", "");
                Player rightClicked = Bukkit.getPlayer(rightClickedName);
                if (rightClicked == null) return;

                if (rightClicked.isOnline()) {
                    if (slot == 12) {
                        String senderTitle = ChatPalette.BLUE_LIGHT + "Sent party invitation";
                        String receiverMessage = ChatPalette.BLUE_LIGHT + player.getName() + " invites you to party"; //sender = player
                        String receiverTitle = ChatPalette.BLUE_LIGHT + "Received party invitation";
                        PartyInvite partyInvite = new PartyInvite(player, rightClicked, senderTitle, receiverMessage, receiverTitle); //receiver = rightClicked
                        partyInvite.send();
                        player.closeInventory();
                    } else if (slot == 14) {
                        if (GuildManager.inGuild(player)) {
                            Guild guild = GuildManager.getGuild(player);
                            PlayerRankInGuild rank = guild.getRankInGuild(player.getUniqueId());
                            if (rank.equals(PlayerRankInGuild.LEADER) || rank.equals(PlayerRankInGuild.COMMANDER)) {
                                String receiverMessage = ChatPalette.PURPLE + player.getName() + " invites you to " + guild.getName() + " guild"; //sender = player
                                String receiverTitle = ChatPalette.PURPLE + "Received guild invitation";
                                String senderTitle = ChatPalette.PURPLE + "Sent guild invitation";
                                GuildInvite invite = new GuildInvite(player, rightClicked, senderTitle, receiverMessage, receiverTitle); //receiver = rightClicked
                                invite.send();
                            } else {
                                player.sendMessage(ChatPalette.RED + "You must be guild leader or commander to invite players to guild");
                            }
                        }
                        player.closeInventory();
                    } else if (slot == 16) {
                        String senderTitle = ChatPalette.GOLD + "Sent trade invitation";
                        String receiverMessage = ChatPalette.GOLD + player.getName() + " invites you to trade"; //sender = player
                        String receiverTitle = ChatPalette.GOLD + "Received trade invitation";
                        TradeInvite tradeInvite = new TradeInvite(player, rightClicked, senderTitle, receiverMessage, receiverTitle); //receiver = rightClicked
                        tradeInvite.send();
                        player.closeInventory();
                    }
                }
            }
        } else if (title.contains(ChatPalette.GRAY_DARK + "Chat Tag")) {
            if (currentType.equals(Material.LIME_WOOL)) {
                String stripColor = ChatColor.stripColor(currentName);

                ChatTag chatTag = ChatTag.valueOf(stripColor);

                rpgCharacter.setChatTag(chatTag);

                MessageUtils.sendCenteredMessage(player, ChatPalette.GOLD + "You selected a new chat tag: " + chatTag.getChatPalette() + chatTag);
            }
        } else if (title.equals(ChatPalette.GRAY_DARK + "Select item to show in chat")) {
            if (currentType.equals(Material.BLACK_STAINED_GLASS_PANE)) return;

            TextComponent message = new TextComponent(ChatPalette.GOLD + player.getName() + " shows an item to chat: ");

            TextComponent itemMessage = new TextComponent(currentName);
            StringBuilder stringBuilder = new StringBuilder(currentName);
            if (itemMeta.hasLore()) {
                List<String> lore = itemMeta.getLore();
                for (String line : lore) {
                    stringBuilder.append("\n");
                    stringBuilder.append(line);
                }
            }
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(stringBuilder.toString())));

            message.addExtra(itemMessage);

            for (Player online : Bukkit.getOnlinePlayers()) {
                online.spigot().sendMessage(message);
            }

            player.closeInventory();
        } else if (title.equals(ChatPalette.GRAY_DARK + "Daily Reward Claim")) {
            if (currentType.equals(Material.LIME_WOOL)) {
                DailyRewardHandler.giveReward(player);

                GuiGeneric guiGeneric = MenuList.dailyRewardsMenu(player);
                guiGeneric.openInventory(player);
            }
        } else if (title.contains(ChatPalette.PURPLE_LIGHT + "Instant Teleportation")) {
            if (currentType.equals(Material.LIME_WOOL)) {
                if (!player.getLocation().getWorld().getName().equals("world")) {
                    player.sendMessage(ChatPalette.RED + "You can't use teleport gui from this location.");
                    return;
                }

                String[] split = currentName.split("#");
                int i = Integer.parseInt(split[1]);
                InstantTeleportGuiItem teleport = InstantTeleportGuiManager.getTeleport(i);

                int cost = teleport.getCost();
                boolean canPay = EconomyUtils.canPay(player, cost);

                player.closeInventory();
                if (canPay) {
                    Location location = teleport.getLocation();
                    TeleportationUtils.teleport(player, location, teleport.getName(), 5, null, cost);
                } else {
                    player.sendMessage(ChatPalette.RED + "You don't have enough coins to teleport.");
                }
            }
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
