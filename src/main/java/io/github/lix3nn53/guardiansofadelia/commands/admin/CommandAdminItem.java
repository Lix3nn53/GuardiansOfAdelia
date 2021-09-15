package io.github.lix3nn53.guardiansofadelia.commands.admin;

import io.github.lix3nn53.guardiansofadelia.bungeelistener.RequestHandler;
import io.github.lix3nn53.guardiansofadelia.economy.Coin;
import io.github.lix3nn53.guardiansofadelia.economy.CoinType;
import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.items.enchanting.EnchantStone;
import io.github.lix3nn53.guardiansofadelia.items.list.Eggs;
import io.github.lix3nn53.guardiansofadelia.items.list.armors.ArmorManager;
import io.github.lix3nn53.guardiansofadelia.items.list.armors.ArmorSlot;
import io.github.lix3nn53.guardiansofadelia.items.list.passiveItems.PassiveManager;
import io.github.lix3nn53.guardiansofadelia.items.list.weapons.WeaponManager;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringManager;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CommandAdminItem implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.getName().equals("adminitem")) {
            return false;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 1) {
                player.sendMessage(ChatPalette.BLUE_LIGHT + "---- ITEMS ----");
                player.sendMessage(ChatPalette.BLUE_LIGHT + "/adminitem coin <num>");
                player.sendMessage(ChatPalette.BLUE_LIGHT + "/adminitem weapon [type] <num> [gearSet]");
                player.sendMessage(ChatPalette.BLUE_LIGHT + "/adminitem armor [slot] [type] <num> [gearSet]");
                player.sendMessage(ChatPalette.BLUE_LIGHT + "/adminitem egg [code] <gearLevel> <petLevel>");
                player.sendMessage(ChatPalette.BLUE_LIGHT + "/adminitem stone <grade> <amount>");
                player.sendMessage(ChatPalette.BLUE_LIGHT + "/adminitem passive [parrot|earring|necklace|glove|ring] <num>");
                player.sendMessage(ChatPalette.BLUE_LIGHT + "/adminitem premium item-id<1-24>");
                player.sendMessage(ChatPalette.BLUE_LIGHT + "/adminitem ingredient id amount");
                player.sendMessage(ChatPalette.BLUE_LIGHT + "/adminitem helmet");
            } else if (args[0].equals("weapon")) {
                if (args.length >= 3) {
                    WeaponGearType weaponGearType = WeaponGearType.valueOf(args[1]);
                    int no = Integer.parseInt(args[2]);
                    GearLevel gearLevel = GearLevel.values()[no];
                    String gearSet = "Command";
                    if (args.length == 4) {
                        gearSet = args[3];
                    }
                    ItemStack weapon = WeaponManager.get(weaponGearType, gearLevel, ItemTier.LEGENDARY, false, gearSet).get(0);
                    InventoryUtils.giveItemToPlayer(player, weapon);
                }
            } else if (args[0].equals("armor")) {
                if (args.length >= 4) {
                    ArmorSlot armorSlot = ArmorSlot.valueOf(args[1]);
                    ArmorGearType armorGearType = ArmorGearType.valueOf(args[2]);
                    int no = Integer.parseInt(args[3]);
                    GearLevel gearLevel = GearLevel.values()[no];
                    String gearSet = "Command";
                    if (args.length == 5) {
                        gearSet = args[4];
                    }
                    ItemStack weapon = ArmorManager.get(armorSlot, armorGearType, gearLevel, ItemTier.LEGENDARY, false, gearSet).get(0);
                    InventoryUtils.giveItemToPlayer(player, weapon);
                }
            } else if (args[0].equals("egg")) {
                if (args.length == 4) {
                    String petCode = args[1];
                    int no = Integer.parseInt(args[2]);
                    GearLevel gearLevel = GearLevel.values()[no];
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
                        int[] coins = EconomyUtils.priceToCoins(price);

                        if (coins[0] > 0) {
                            InventoryUtils.giveItemToPlayer(player, new Coin(CoinType.COPPER, coins[0]).getCoin());
                        }
                        if (coins[1] > 0) {
                            InventoryUtils.giveItemToPlayer(player, new Coin(CoinType.SILVER, coins[1]).getCoin());
                        }
                        if (coins[2] > 0) {
                            InventoryUtils.giveItemToPlayer(player, new Coin(CoinType.GOLD, coins[2]).getCoin());
                        }
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatPalette.GOLD + "(Enter a number)");
                    }
                }
            } else if (args[0].equals("passive")) {
                if (args.length == 3) {
                    RPGSlotType rpgSlotType = RPGSlotType.valueOf(args[1]);
                    int no = Integer.parseInt(args[2]);
                    GearLevel gearLevel = GearLevel.values()[no];
                    ItemStack passive = PassiveManager.get(gearLevel, rpgSlotType, ItemTier.LEGENDARY, false, "Command").get(0);
                    InventoryUtils.giveItemToPlayer(player, passive);
                }
            } else if (args[0].equals("helmet")) {
                PlayerInventory inventory = player.getInventory();
                ItemStack itemInMainHand = inventory.getItemInMainHand();
                ItemStack helmet = inventory.getHelmet();
                if (helmet != null && !helmet.getType().equals(Material.AIR)) {
                    InventoryUtils.giveItemToPlayer(player, helmet);
                }
                inventory.setHelmet(itemInMainHand);
            } else if (args[0].equals("premium")) {
                // TODO premium items, going to redesign
                if (args.length == 2) {
                    int itemID = Integer.parseInt(args[1]);

                    RequestHandler.test(itemID, player);
                }
            }

            // If the player (or console) uses our command correct, we can return true
            return true;
        }
        return false;
    }
}
