package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.enchanting.EnchantGui;
import io.github.lix3nn53.guardiansofadelia.Items.enchanting.EnchantStone;
import io.github.lix3nn53.guardiansofadelia.Items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.economy.Coin;
import io.github.lix3nn53.guardiansofadelia.economy.CoinType;
import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.Bazaar;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.BazaarCustomerGui;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.BazaarManager;
import io.github.lix3nn53.guardiansofadelia.economy.trading.Trade;
import io.github.lix3nn53.guardiansofadelia.economy.trading.TradeGui;
import io.github.lix3nn53.guardiansofadelia.economy.trading.TradeManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guild.GuildManager;
import io.github.lix3nn53.guardiansofadelia.jobs.Job;
import io.github.lix3nn53.guardiansofadelia.jobs.JobType;
import io.github.lix3nn53.guardiansofadelia.menu.MenuList;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.npc.merchant.MerchantManager;
import io.github.lix3nn53.guardiansofadelia.npc.merchant.MerchantMenu;
import io.github.lix3nn53.guardiansofadelia.npc.merchant.MerchantPageType;
import io.github.lix3nn53.guardiansofadelia.npc.merchant.SellGui;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.revive.TombManager;
import io.github.lix3nn53.guardiansofadelia.rpginventory.RPGInventory;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.persistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.SkillAPIUtils;
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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
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
        Inventory clickedInventory = event.getClickedInventory();
        ItemStack cursor = event.getCursor();

        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();

        GuardianData guardianData = null;
        RPGCharacter rpgCharacter = null;
        if (GuardianDataManager.hasGuardianData(uuid)) {
            guardianData = GuardianDataManager.getGuardianData(uuid);

            if (guardianData.hasActiveCharacter()) {
                rpgCharacter = guardianData.getActiveCharacter();
            }
        }

        //Open RPG-Inventory
        if (clickedInventory != null && clickedInventory.getType().equals(InventoryType.CRAFTING)) {
            event.setCancelled(true);
            if (cursor.getType().equals(Material.AIR)) {
                if (rpgCharacter != null) {
                    GuiGeneric guiGeneric = rpgCharacter.getRpgInventory().formRPGInventory(player);
                    guiGeneric.openInventory(player);
                }
            }
            return;
        }

        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType().equals(Material.AIR)) return;
        if (!(event.getCurrentItem().hasItemMeta())) return;
        if (!(event.getCurrentItem().getItemMeta().hasDisplayName())) return;

        Gui activeGui = null;

        ItemStack current = event.getCurrentItem();
        String title = event.getView().getTitle();
        int slot = event.getSlot();

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
                            if (current.getType().equals(Material.LIME_WOOL)) {
                                trade.accept(player);
                            } else if (current.getType().equals(Material.YELLOW_WOOL)) {
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
                        if (current.getType().equals(Material.LIME_WOOL)) {
                            sellGui.confirm();
                        } else if (current.getType().equals(Material.YELLOW_WOOL)) {
                            sellGui.finish(player);
                        }
                    } else if (clickedInventory.getType().equals(InventoryType.PLAYER)) {
                        sellGui.addItemToSell(current, slot);
                    }
                } else if (activeGui instanceof EnchantGui) {
                    EnchantGui enchantGui = (EnchantGui) activeGui;
                    if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                        if (current.getType().equals(Material.EMERALD_BLOCK)) {
                            enchantGui.startEnchanting(player);
                        }
                    } else if (clickedInventory.getType().equals(InventoryType.PLAYER)) {
                        List<Material> enchantStoneMaterials = new ArrayList<>();
                        for (EnchantStone enchantStone : EnchantStone.values()) {
                            enchantStoneMaterials.add(enchantStone.getType());
                        }
                        if (enchantStoneMaterials.contains(current.getType())) {
                            enchantGui.setEnchantStone(current);
                        } else if (StatUtils.hasStatType(current.getType())) {
                            enchantGui.setItemToEnchant(current);
                        }
                    }
                }
                if (!title.equals("Bazaar Storage")) {
                    if (persistentDataContainerUtil.hasInteger(current, "shopPrice")) {
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
            GuiGeneric guiGeneric = MenuList.mainMenu();
            guiGeneric.openInventory(player);
        } else if (title.equals(org.bukkit.ChatColor.GREEN + "Menu")) {
            if (currentName.equals(ChatColor.GREEN + "Guides")) {
                GuiGeneric guide = MenuList.guide();
                guide.openInventory(player);
            } else if (currentName.equals(ChatColor.AQUA + "Compass")) {
                GuiGeneric compass = MenuList.compass();
                compass.openInventory(player);
            } else if (currentName.equals(ChatColor.DARK_GREEN + "Maps")) {
                player.closeInventory();
                TextComponent message = new TextComponent(" Maps ! (Click Me)");
                message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://guardiansofadelia.com/#t5"));
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to see Maps from our website").color(ChatColor.AQUA).create()));
                message.setColor(ChatColor.GREEN);
                message.setBold(true);
                player.spigot().sendMessage(message);
            } else if (currentName.equals(ChatColor.AQUA + "Announcements and News")) {
                player.closeInventory();
                TextComponent message = new TextComponent(" Announcements and News ! (Click Me)");
                message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://guardiansofadelia.com/#t2"));
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to see Announcements and News from our website").color(ChatColor.AQUA).create()));
                message.setColor(ChatColor.AQUA);
                message.setBold(true);
                player.spigot().sendMessage(message);
            } else if (currentName.equals(ChatColor.GOLD + "Character")) {
                GuiGeneric character = MenuList.character();
                character.openInventory(player);
            } else if (currentName.equals(ChatColor.DARK_PURPLE + "Guild")) {
                if (GuildManager.inGuild(player)) {
                    MenuList.guild().openInventory(player);
                }
            } else if (currentName.equals(ChatColor.DARK_PURPLE + "Minigames")) {
                GuiGeneric minigames = MenuList.minigames();
                minigames.openInventory(player);
            } else if (currentName.equals(ChatColor.YELLOW + "Bazaar")) {
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
            }
        } else if (title.contains("Character")) {
            if (title.contains("Creation")) {
                String charNoString = title.replace(ChatColor.YELLOW + "Character ", "");
                charNoString = charNoString.replace(" Creation", "");
                int charNo = Integer.parseInt(charNoString);

                CharacterSelectionScreenManager characterSelectionScreenManager = GuardiansOfAdelia.getCharacterSelectionScreenManager();
                if (currentName.contains("Knight")) {
                    characterSelectionScreenManager.createCharacter(player, charNo, RPGClass.KNIGHT);
                } else if (currentName.contains("Paladin")) {
                    characterSelectionScreenManager.createCharacter(player, charNo, RPGClass.PALADIN);
                } else if (currentName.contains("Rogue")) {
                    characterSelectionScreenManager.createCharacter(player, charNo, RPGClass.ROGUE);
                } else if (currentName.contains("Archer")) {
                    characterSelectionScreenManager.createCharacter(player, charNo, RPGClass.ARCHER);
                } else if (currentName.contains("Mage")) {
                    characterSelectionScreenManager.createCharacter(player, charNo, RPGClass.MAGE);
                } else if (currentName.contains("Warrior")) {
                    characterSelectionScreenManager.createCharacter(player, charNo, RPGClass.WARRIOR);
                } else if (currentName.contains("Monk")) {
                    characterSelectionScreenManager.createCharacter(player, charNo, RPGClass.MONK);
                }
            } else if (title.contains("Selection")) {
                String charNoString = title.replace(ChatColor.YELLOW + "Character ", "");
                charNoString = charNoString.replace(" Selection", "");
                int charNo = Integer.parseInt(charNoString);

                CharacterSelectionScreenManager characterSelectionScreenManager = GuardiansOfAdelia.getCharacterSelectionScreenManager();
                if (currentName.contains("Roumen")) {
                    Location location = TownManager.getTown(1).getLocation();
                    characterSelectionScreenManager.selectCharacter(player, charNo, location);
                } else if (currentName.contains("Port Veloa")) {
                    Location location = TownManager.getTown(2).getLocation();
                    characterSelectionScreenManager.selectCharacter(player, charNo, location);
                } else if (currentName.contains("Elderine")) {
                    Location location = TownManager.getTown(3).getLocation();
                    characterSelectionScreenManager.selectCharacter(player, charNo, location);
                } else if (currentName.contains("Uruga")) {
                    Location location = TownManager.getTown(4).getLocation();
                    characterSelectionScreenManager.selectCharacter(player, charNo, location);
                } else if (currentName.contains("Arberstol Ruins")) {
                    Location location = TownManager.getTown(5).getLocation();
                    characterSelectionScreenManager.selectCharacter(player, charNo, location);
                } else if (currentName.contains("last location")) {
                    Location charLocation = characterSelectionScreenManager.getCharLocation(player, charNo);
                    if (charLocation != null) {
                        characterSelectionScreenManager.selectCharacter(player, charNo, charLocation);
                    } else {
                        player.sendMessage(ChatColor.RED + "Your saved quit-location is not valid");
                    }
                }
            } else {
                if (currentName.equals(ChatColor.LIGHT_PURPLE + "Skills")) {
                    SkillAPIUtils.openSkillMenu(player);
                } else if (currentName.equals(ChatColor.DARK_GREEN + "Elements")) {
                    SkillAPIUtils.openAttributeMenu(player);
                } else if (currentName.equals(ChatColor.YELLOW + "Job")) {
                    GuiGeneric job = MenuList.job(player);
                    job.openInventory(player);
                } else if (currentName.equals(ChatColor.AQUA + "Chat Tag")) {
                    GuiGeneric chatTag = MenuList.chatTag();
                    chatTag.openInventory(player);
                }
            }
        } else if (title.contains(org.bukkit.ChatColor.YELLOW + "Quest List of ")) {
            if (rpgCharacter != null) {
                if (activeGui != null) {
                    int resourceNPC = activeGui.getResourceNPC();
                    if (resourceNPC != 0) {
                        Material type = current.getType();
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
                if (current.getType().equals(Material.IRON_INGOT)) {
                    if (InventoryUtils.inventoryContains(playerInventory, Material.GOLD_INGOT, 1)) {
                        InventoryUtils.removeMaterialFromInventory(playerInventory, Material.GOLD_INGOT, 1);
                        InventoryUtils.giveItemToPlayer(player, new Coin(CoinType.COPPER, 64).getCoin());
                    }
                } else if (current.getType().equals(Material.GOLD_INGOT)) {
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
                } else if (current.getType().equals(Material.DIAMOND)) {
                    if (InventoryUtils.inventoryContains(playerInventory, Material.GOLD_INGOT, 64)) {
                        InventoryUtils.removeMaterialFromInventory(playerInventory, Material.GOLD_INGOT, 64);
                        InventoryUtils.giveItemToPlayer(player, new Coin(CoinType.GOLD, 1).getCoin());
                    }
                }
            }
        } else if (title.equals(ChatColor.AQUA + "Compass")) {
            if (current.getType().equals(Material.LIGHT_BLUE_WOOL)) {
                String displayName = itemMeta.getDisplayName();
                String[] split = displayName.split("#");
                int i = Integer.parseInt(split[1]);

                Town town = TownManager.getTown(i);
                CompassManager.setCompassItemLocation(player, town.getName(), town.getLocation());
            } else if (current.getType().equals(Material.LIME_WOOL)) {
                String displayName = itemMeta.getDisplayName();
                String[] split = displayName.split("#");
                int i = Integer.parseInt(split[1]);
                CompassManager.setCompassItemNPC(player, i);
            }
        } else if (title.equals(ChatColor.YELLOW + "Job")) {
            if (rpgCharacter != null) {
                if (!rpgCharacter.hasJob()) {
                    if (current.getType().equals(Material.RED_WOOL)) {
                        rpgCharacter.setJob(new Job(JobType.WEAPONSMITH));
                    } else if (current.getType().equals(Material.LIGHT_BLUE_WOOL)) {
                        rpgCharacter.setJob(new Job(JobType.ARMORSMITH));
                    } else if (current.getType().equals(Material.MAGENTA_WOOL)) {
                        rpgCharacter.setJob(new Job(JobType.ALCHEMIST));
                    } else if (current.getType().equals(Material.YELLOW_WOOL)) {
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
            if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                event.setCancelled(true);
                if (cursor.getType().equals(Material.AIR)) {
                    boolean change = rpgInventory.onCursorClickWithAir(player, slot, topInventory, event.isShiftClick());
                } else {
                    boolean change = rpgInventory.onCursorClickWithItem(player, slot, cursor, topInventory);
                }
            } else if (clickedInventory.getType().equals(InventoryType.PLAYER)) {
                if (cursor.getType().equals(Material.AIR)) {
                    if (event.isShiftClick()) {
                        event.setCancelled(true);
                        boolean change = rpgInventory.onShiftClick(current, player, slot, topInventory);
                    }
                }
            }
        } else if (title.equals(ChatColor.AQUA + "Revive Gui")) {
            if (slot == 5) {
                TombManager.startSearch(player);
            }
        } else if (title.equals(ChatColor.GOLD + "Bazaar")) {
            if (current.getType().equals(Material.LIME_WOOL)) {
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
                if (current.getType().equals(Material.LIME_WOOL)) {
                    if (guardianData != null) {
                        if (guardianData.hasBazaar()) {
                            Bazaar bazaar = guardianData.getBazaar();
                            bazaar.setUp();
                        }
                    }
                } else if (current.getType().equals(Material.RED_WOOL)) {
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
                player.sendMessage(ChatColor.GOLD + "Enter a price for item: " + currentName);
                player.sendMessage(ChatColor.YELLOW + "(Enter a number to chat without '/' or anything)");
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
        } else if (title.contains("Join dungeon: ")) {
            if (clickedInventory.getType().equals(InventoryType.CHEST)) {
                if (current.getType().equals(Material.LIME_WOOL)) {
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
        }
    }

}
