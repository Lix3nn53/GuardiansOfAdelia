package io.github.lix3nn53.guardiansofadelia.commands;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.Items.enchanting.EnchantStone;
import io.github.lix3nn53.guardiansofadelia.Items.list.Eggs;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorManager;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorSlot;
import io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems.PassiveManager;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.WeaponManager;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.RequestHandler;
import io.github.lix3nn53.guardiansofadelia.chat.StaffRank;
import io.github.lix3nn53.guardiansofadelia.economy.Coin;
import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringManager;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.rewards.chest.LootChest;
import io.github.lix3nn53.guardiansofadelia.rewards.chest.LootChestManager;
import io.github.lix3nn53.guardiansofadelia.rewards.chest.LootChestTier;
import io.github.lix3nn53.guardiansofadelia.rewards.daily.DailyRewardHandler;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import io.github.lix3nn53.guardiansofadelia.sounds.CustomSound;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

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
                player.sendMessage(ChatColor.DARK_PURPLE + "/admin setdaily");
                player.sendMessage(ChatColor.DARK_PURPLE + "/admin addlootchest [0-3 = tier]");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "---- UTILS ----");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "/admin fly");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "/admin speed <num>");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "/admin tp town <num>");
                player.sendMessage(ChatColor.DARK_BLUE + "---- RPG ----");
                player.sendMessage(ChatColor.DARK_BLUE + "/admin exp <player> <amount>");
                player.sendMessage(ChatColor.DARK_BLUE + "/admin class unlock <player> <newClass>");
                player.sendMessage(ChatColor.BLUE + "---- OTHER ----");
                player.sendMessage(ChatColor.BLUE + "/admin model portal<1-5>");
                player.sendMessage(ChatColor.BLUE + "/admin sound <code> - play custom sounds");
                player.sendMessage(ChatColor.DARK_PURPLE + "---- QUEST ----");
                player.sendMessage(ChatColor.DARK_PURPLE + "/admin quest t - turn ins current quests tasks");
                player.sendMessage(ChatColor.DARK_PURPLE + "/admin quest a <num> - accept quest tasks");
                player.sendMessage(ChatColor.DARK_PURPLE + "/admin quest gui <npcNo> - open quest gui of an npc");
                player.sendMessage(ChatColor.AQUA + "---- ITEMS ----");
                player.sendMessage(ChatColor.AQUA + "/admin coin <num>");
                player.sendMessage(ChatColor.AQUA + "/admin weapon [type] <num> [gearSet]");
                player.sendMessage(ChatColor.AQUA + "/admin armor [slot] [type] <num> [gearSet]");
                player.sendMessage(ChatColor.AQUA + "/admin egg [code] <gearLevel> <petLevel>");
                player.sendMessage(ChatColor.AQUA + "/admin stone <grade> <amount>");
                player.sendMessage(ChatColor.AQUA + "/admin passive [parrot|earring|necklace|glove|ring] <num>");
                player.sendMessage(ChatColor.AQUA + "/admin premium item-id<1-24>");
                player.sendMessage(ChatColor.AQUA + "/admin ingredient id amount");
            } else if (args[0].equals("speed")) {
                int val = Integer.parseInt(args[1]);
                if (val > 10 || val < -1) {
                    player.sendMessage("Speed must be between 1-10");
                    return false;
                }
                float valf = val / 10f;
                player.setFlySpeed(valf);
            } else if (args[0].equals("setdaily")) {
                GuiGeneric guiGeneric = new GuiGeneric(9, ChatColor.YELLOW + "Set Daily Rewards", 0);

                ItemStack filler = new ItemStack(Material.BLACK_STAINED_GLASS);
                ItemMeta itemMeta = filler.getItemMeta();
                itemMeta.setDisplayName("");
                ArrayList<String> lore = new ArrayList<>();
                lore.add("");
                itemMeta.setLore(lore);
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
            } else if (args[0].equals("addlootchest")) {
                Block targetBlock = player.getTargetBlock(null, 12);

                Material type = targetBlock.getType();

                if (!type.equals(Material.CHEST)) {
                    player.sendMessage(ChatColor.RED + "You must be looking to a chest");
                    return false;
                }

                int tierIndex = Integer.parseInt(args[1]);

                LootChestTier value = LootChestTier.values()[tierIndex];

                LootChest lootChest = new LootChest(targetBlock.getLocation(), value);

                LootChestManager.addLootChest(lootChest);
            } else if (args[0].equals("exp")) {
                if (args.length == 3) {
                    int expToGive = Integer.parseInt(args[2]);
                    Player player2 = Bukkit.getPlayer(args[1]);
                    if (player2 != null) {
                        if (GuardianDataManager.hasGuardianData(player)) {
                            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
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
                            if (GuardianDataManager.hasGuardianData(player)) {
                                GuardianData guardianData = GuardianDataManager.getGuardianData(player);
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
                            if (GuardianDataManager.hasGuardianData(player)) {
                                GuardianData guardianData = GuardianDataManager.getGuardianData(player);
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
                    if (GuardianDataManager.hasGuardianData(player)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(player);
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
                    if (GuardianDataManager.hasGuardianData(player)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(player);
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
                    if (GuardianDataManager.hasGuardianData(player)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(player);
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
                if (args.length >= 3) {
                    WeaponGearType weaponGearType = WeaponGearType.valueOf(args[1]);
                    int no = Integer.parseInt(args[2]);
                    String gearSet = "Command";
                    if (args.length == 4) {
                        gearSet = args[3];
                    }
                    ItemStack weapon = WeaponManager.get(weaponGearType, no, 0, ItemTier.LEGENDARY, false, gearSet);
                    InventoryUtils.giveItemToPlayer(player, weapon);
                }
            } else if (args[0].equals("armor")) {
                if (args.length >= 4) {
                    ArmorSlot armorSlot = ArmorSlot.valueOf(args[1]);
                    ArmorGearType armorGearType = ArmorGearType.valueOf(args[2]);
                    int no = Integer.parseInt(args[3]);
                    String gearSet = "Command";
                    if (args.length == 5) {
                        gearSet = args[4];
                    }
                    ItemStack weapon = ArmorManager.get(armorSlot, armorGearType, no, 0, ItemTier.LEGENDARY, false, gearSet);
                    InventoryUtils.giveItemToPlayer(player, weapon);
                }
            } else if (args[0].equals("egg")) {
                if (args.length == 4) {
                    String petCode = args[1];
                    int gearLevel = Integer.parseInt(args[2]);
                    int petLevel = Integer.parseInt(args[3]);

                    ItemStack egg = Eggs.get(petCode, gearLevel, petLevel);
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
                    InventoryUtils.giveItemToPlayer(player, enchantStone.getItemStack(amount));
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
                    ItemStack passive = PassiveManager.get(no, 0, rpgSlotType, ItemTier.LEGENDARY, false, "Command");
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
                Location center = player.getLocation().clone().add(0, player.getHeight() / 2, 0);

                double radius = Double.parseDouble(args[1]);
                int amount = Integer.parseInt(args[2]);
                int amounty = Integer.parseInt(args[3]);

                double fullRadian = 2 * Math.PI;

                /*for (double i = 0; i < amount; i++) {
                    double percent = i / amount;
                    double theta = fullRadian * percent;
                    player.sendMessage("Math.PI: " + Math.PI);
                    player.sendMessage("theta: " + theta);
                    double dx = radius * Math.cos(theta);
                    player.sendMessage("dx: " + dx);
                    double dy = 0;
                    *//*if (height > 0) {
                        double v = Math.random();
                        dy = height * v;
                    }*//*
                    double dz = radius * Math.sin(theta);
                    player.sendMessage("dz: " + dz);
                    //double dy = radius * Math.sin(theta);
                    Location add = center.clone().add(dx, dy, dz);
                    ParticleUtil.playSingleParticle(add, Particle.FLAME, null);
                }*/

                for (double i = 0; i < amount; i++) {
                    for (double y = 0; y < amounty; y++) {
                        double percent = i / amount;
                        double percenty = y / amounty;

                        double v = 1 * percenty;
                        double theta = fullRadian * percent;
                        double phi = Math.acos(2 * v - 1);
                        double dx = radius * Math.sin(phi) * Math.cos(theta);
                        double dy = radius * Math.cos(phi);
                        double dz = radius * Math.sin(phi) * Math.sin(theta);
                        Location add = center.clone().add(dx, dy, dz);
                        ParticleUtil.playSingleParticle(add, Particle.FLAME, null);
                    }
                }
            } else if (args[0].equals("reload")) {
                /*ClassConfigurations.loadConfigs();
                player.sendMessage("Reloaded class configs!");
                Set<Player> onlinePlayers = GuardianDataManager.getOnlinePlayers();
                for (Player onlinePlayer : onlinePlayers) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(onlinePlayer);
                    RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                    if (activeCharacter == null) continue;

                    String rpgClassStr = activeCharacter.getRpgClassStr();
                    RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);

                    SkillBar skillBar = activeCharacter.getSkillBar();
                    skillBar.reloadSkillSet(rpgClass.getSkillSet());
                }
                player.sendMessage("Reloaded player skills!");*/
            }

            // If the player (or console) uses our command correct, we can return true
            return true;
        }
        return false;
    }
}
