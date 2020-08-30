package io.github.lix3nn53.guardiansofadelia.commands;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.Items.enchanting.EnchantStone;
import io.github.lix3nn53.guardiansofadelia.Items.list.eggs.Companions;
import io.github.lix3nn53.guardiansofadelia.Items.list.eggs.Mounts;
import io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems.PassiveManager;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.WeaponManager;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.RequestHandler;
import io.github.lix3nn53.guardiansofadelia.chat.StaffRank;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.Companion;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.Mount;
import io.github.lix3nn53.guardiansofadelia.economy.Coin;
import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringManager;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.rewards.DailyRewardHandler;
import io.github.lix3nn53.guardiansofadelia.rewards.DailyRewardInfo;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import io.github.lix3nn53.guardiansofadelia.sounds.CustomSound;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandLix implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.getName().equals("admin")) {
            return false;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 1) {
                player.sendMessage(ChatColor.DARK_PURPLE + "---- ADMIN ----");
                player.sendMessage(ChatColor.DARK_PURPLE + "/admin setstaff <player> [NONE|OWNER|ADMIN|DEVELOPER|BUILDER|SUPPORT|YOUTUBER|TRAINEE]");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "---- UTILS ----");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "/admin fly");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "/admin tp town <num>");
                player.sendMessage(ChatColor.BLUE + "---- RPG ----");
                player.sendMessage(ChatColor.BLUE + "/admin exp <player> <amount>");
                player.sendMessage(ChatColor.BLUE + "/admin class unlock <player> <newClass>");
                player.sendMessage(ChatColor.BLUE + "---- OTHER ----");
                player.sendMessage(ChatColor.BLUE + "/admin setdaily");
                player.sendMessage(ChatColor.BLUE + "/admin model portal<1-5>");
                player.sendMessage(ChatColor.BLUE + "/admin sound <sound>");
                player.sendMessage(ChatColor.DARK_PURPLE + "---- QUEST ----");
                player.sendMessage(ChatColor.DARK_PURPLE + "/admin quest t - turn ins current quests tasks");
                player.sendMessage(ChatColor.DARK_PURPLE + "/admin quest a <num> - accept quest tasks");
                player.sendMessage(ChatColor.DARK_PURPLE + "/admin quest gui <npcNo> - open quest gui of an npc");
                player.sendMessage(ChatColor.BLUE + "---- ITEMS ----");
                player.sendMessage(ChatColor.BLUE + "/admin coin <num>");
                player.sendMessage(ChatColor.BLUE + "/admin weapon [type] <num>");
                player.sendMessage(ChatColor.BLUE + "/admin companion [type] <num>");
                player.sendMessage(ChatColor.BLUE + "/admin mount [type] <num>");
                player.sendMessage(ChatColor.BLUE + "/admin stone <grade> <amount>");
                player.sendMessage(ChatColor.BLUE + "/admin passive [parrot|earring|necklace|glove|ring] <num>");
                player.sendMessage(ChatColor.BLUE + "/admin premium item-id<1-24>");
                player.sendMessage(ChatColor.BLUE + "/admin ingredient id amount");
            } else if (args[0].equals("setdaily")) {
                GuiGeneric guiGeneric = new GuiGeneric(9, ChatColor.YELLOW + "Set Daily Rewards", 0);

                ItemStack filler = new ItemStack(Material.BLACK_STAINED_GLASS);
                ItemMeta itemMeta = filler.getItemMeta();
                itemMeta.setDisplayName("");
                itemMeta.setLore(new ArrayList<>());
                filler.setItemMeta(itemMeta);
                guiGeneric.setItem(0, filler);
                guiGeneric.setItem(8, filler);

                ItemStack[] rewards = DailyRewardHandler.getRewards();
                int i = 1;
                for (ItemStack itemStack : rewards) {
                    if (itemStack == null) continue;

                    guiGeneric.setItem(i, itemStack);
                    i++;
                }

                guiGeneric.setLocked(false);
                guiGeneric.openInventory(player);
            } else if (args[0].equals("exp")) {
                if (args.length == 3) {
                    int expToGive = Integer.parseInt(args[2]);
                    Player player2 = Bukkit.getPlayer(args[1]);
                    if (player2 != null) {
                        UUID uuid = player2.getUniqueId();
                        if (GuardianDataManager.hasGuardianData(uuid)) {
                            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                            if (guardianData.hasActiveCharacter()) {
                                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                                activeCharacter.getRpgCharacterStats().giveExp(expToGive);
                            }
                        }
                    }
                }
            } else if (args[0].equals("class")) {
                if (args[1].equals("unlock")) {
                    if (args.length == 4) {
                        Player player2 = Bukkit.getPlayer(args[2]);
                        String newClass = args[3];
                        if (player2 != null) {
                            UUID uuid = player2.getUniqueId();
                            if (GuardianDataManager.hasGuardianData(uuid)) {
                                GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                                if (guardianData.hasActiveCharacter()) {
                                    RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                                    activeCharacter.unlockClass(newClass);
                                    player2.sendMessage("Unlocked class: " + newClass);
                                }
                            }
                        }
                    }
                }
            } else if (args[0].equals("setstaff")) {
                if (args.length == 3) {
                    try {
                        StaffRank staffRank = StaffRank.valueOf(args[2]);
                        Player player2 = Bukkit.getPlayer(args[1]);
                        if (player2 != null) {
                            UUID uuid = player2.getUniqueId();
                            if (GuardianDataManager.hasGuardianData(uuid)) {
                                GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                                guardianData.setStaffRank(staffRank);
                            }
                        }
                    } catch (IllegalArgumentException illegalArgumentException) {
                        player.sendMessage("Unknown staff-rank");
                    }
                }
            } else if (args[0].equals("tp")) {
                if (args.length == 3) {
                    if (args[1].equals("town")) {
                        int no = Integer.parseInt(args[2]);
                        if (no < 6 && no > 0) {
                            Town town = TownManager.getTown(no);
                            player.teleport(town.getLocation());
                        }
                    }
                }
            } else if (args[0].equals("quest")) {
                if (args[1].equals("t")) {
                    if (args.length != 2) return false;
                    UUID uuid = player.getUniqueId();
                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                            List<Quest> questList = activeCharacter.getQuestList();

                            for (Quest quest : questList) {
                                activeCharacter.getTurnedInQuests().add(quest.getQuestID());
                                quest.onTurnIn(player);
                            }

                            questList.clear();
                        }
                    }
                } else if (args[1].equals("a")) {
                    if (args.length != 3) return false;
                    UUID uuid = player.getUniqueId();
                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                            Quest questCopyById = QuestNPCManager.getQuestCopyById(Integer.parseInt(args[2]));

                            boolean questListIsNotFull = activeCharacter.acceptQuest(questCopyById, player);
                            if (!questListIsNotFull) {
                                player.sendMessage(net.md_5.bungee.api.ChatColor.RED + "Your quest list is full");
                            }
                        }
                    }
                } else if (args[1].equals("gui")) {
                    if (args.length != 3) return false;
                    UUID uuid = player.getUniqueId();
                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                        if (guardianData.hasActiveCharacter()) {
                            GuiGeneric questGui = QuestNPCManager.getQuestGui(player, Integer.parseInt(args[2]));
                            questGui.openInventory(player);
                        }
                    }
                }
            } else if (args[0].equals("sound")) {
                if (args.length == 2) {
                    GoaSound goaSound = GoaSound.valueOf(args[1]);
                    CustomSound customSound = goaSound.getCustomSound();
                    customSound.play(player);
                }
            } else if (args[0].equals("fly")) {
                boolean allowFlight = player.getAllowFlight();
                player.setFlying(!allowFlight);
            } else if (args[0].equals("weapon")) {
                if (args.length == 3) {
                    WeaponGearType weaponGearType = WeaponGearType.valueOf(args[1]);
                    int no = Integer.parseInt(args[2]);
                    ItemStack weapon = WeaponManager.get(weaponGearType, no, 0, ItemTier.LEGENDARY, "Command", false);
                    InventoryUtils.giveItemToPlayer(player, weapon);
                }
            } else if (args[0].equals("companion")) {
                if (args.length == 3) {
                    Companion mount = Companion.valueOf(args[1]);
                    int i = Integer.parseInt(args[2]);

                    ItemStack egg = Companions.get(mount, i);
                    InventoryUtils.giveItemToPlayer(player, egg);
                }
            } else if (args[0].equals("mount")) {
                if (args.length == 3) {
                    Mount mount = Mount.valueOf(args[1]);
                    int i = Integer.parseInt(args[2]);

                    ItemStack egg = Mounts.get(mount, i);
                    InventoryUtils.giveItemToPlayer(player, egg);
                }
            } else if (args[0].equals("stone")) {
                if (args.length == 3) {
                    int grade = Integer.parseInt(args[1]);
                    int amount = Integer.parseInt(args[2]);
                    EnchantStone enchantStone = EnchantStone.TIER_ONE;
                    if (grade == 2) {
                        enchantStone = EnchantStone.TIER_TWO;
                    } else if (grade == 3) {
                        enchantStone = EnchantStone.TIER_THREE;
                    } else if (grade == 4) {
                        enchantStone = EnchantStone.TIER_FOUR;
                    }
                    InventoryUtils.giveItemToPlayer(player, enchantStone.getItemSTack(amount));
                }
            } else if (args[0].equals("ingredient")) {
                if (args.length == 3) {
                    int no = Integer.parseInt(args[1]);
                    int amount = Integer.parseInt(args[2]);

                    ItemStack itemStack = GatheringManager.getIngredient(no).getItemStack(amount);

                    InventoryUtils.giveItemToPlayer(player, itemStack);
                }
            } else if (args[0].equals("coin")) {
                if (args.length == 2) {
                    try {
                        int price = Integer.parseInt(args[1]);
                        List<Coin> coins = EconomyUtils.priceToCoins(price);

                        for (Coin coin : coins) {
                            InventoryUtils.giveItemToPlayer(player, coin.getCoin());
                        }
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.YELLOW + "(Enter a number)");
                    }
                }
            } else if (args[0].equals("passive")) {
                if (args.length == 3) {
                    RPGSlotType rpgSlotType = RPGSlotType.valueOf(args[1]);
                    int no = Integer.parseInt(args[2]);
                    ItemStack passive = PassiveManager.get(no, 0, rpgSlotType, ItemTier.LEGENDARY, "Command", false);
                    InventoryUtils.giveItemToPlayer(player, passive);
                }
            } else if (args[0].equals("model")) {
                if (args.length == 2) {
                    Location location = player.getLocation();
                    ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
                    ItemStack itemStack = new ItemStack(Material.IRON_PICKAXE);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    if (args[1].equals("portal1")) {
                        itemMeta.setCustomModelData(6);
                    } else if (args[1].equals("portal2")) {
                        itemMeta.setCustomModelData(7);
                    } else if (args[1].equals("portal3")) {
                        itemMeta.setCustomModelData(8);
                    } else if (args[1].equals("portal4")) {
                        itemMeta.setCustomModelData(9);
                    } else if (args[1].equals("portal5")) {
                        itemMeta.setCustomModelData(10);
                    }
                    itemMeta.setUnbreakable(true);
                    itemStack.setItemMeta(itemMeta);
                    EntityEquipment equipment = armorStand.getEquipment();
                    equipment.setHelmet(itemStack);
                    armorStand.setVisible(false);
                    armorStand.setInvulnerable(true);
                    armorStand.setGravity(false);
                }
            } else if (args[0].equals("premium")) {
                if (args.length == 2) {
                    int itemID = Integer.parseInt(args[1]);

                    RequestHandler.test(itemID, player);
                }
            } else if (args[0].equals("test")) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(player.getUniqueId());

                DailyRewardInfo dailyRewardInfo = guardianData.getDailyRewardInfo();
                LocalDate lastObtainDate = dailyRewardInfo.getLastObtainDate();

                player.sendMessage(lastObtainDate.toString());
            }

            // If the player (or console) uses our command correct, we can return true
            return true;
        }
        return false;
    }
}
