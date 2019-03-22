package io.github.lix3nn53.guardiansofadelia.economy;

import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.NBTTagUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EconomyUtils {

    public static boolean pay(Player player, int price) {
        int copperAmount = 0;
        int silverAmount = 0;
        int goldAmount = 0;
        if (price > 63) {
            copperAmount = price % 64;
            silverAmount = price / 64;
            if (silverAmount > 63) {
                silverAmount = price % 64;
                goldAmount = price / 64;
            }
        } else {
            copperAmount = price;
        }
        boolean canAffordCopper = false;
        boolean canAffordSilver = false;
        boolean canAffordGold = false;
        Inventory inventory = player.getInventory();
        if (copperAmount > 0) {
            if (inventory.containsAtLeast(new Coin(CoinType.COPPER, 1).getCoin(), copperAmount)) {
                canAffordCopper = true;
            }
        } else {
            canAffordCopper = true;
        }
        if (silverAmount > 0) {
            if (inventory.containsAtLeast(new Coin(CoinType.SILVER, 1).getCoin(), silverAmount)) {
                canAffordSilver = true;
            }
        } else {
            canAffordSilver = true;
        }
        if (goldAmount > 0) {
            if (inventory.containsAtLeast(new Coin(CoinType.GOLD, 1).getCoin(), goldAmount)) {
                canAffordGold = true;
            }
        } else {
            canAffordGold = true;
        }
        if (canAffordCopper && canAffordSilver && canAffordGold) {
            InventoryUtils.removeMaterialFromInventory(inventory, Material.IRON_INGOT, copperAmount);
            InventoryUtils.removeMaterialFromInventory(inventory, Material.GOLD_INGOT, silverAmount);
            InventoryUtils.removeMaterialFromInventory(inventory, Material.DIAMOND, goldAmount);
            return true;
        }
        return false;
    }

    public static ItemStack setItemPrice(ItemStack itemStack, int price) {
        int copper = 0;
        int silver = 0;
        int gold = 0;
        if (price > 63) {
            copper = price % 64;
            silver = price / 64;
            if (silver > 63) {
                silver = price % 64;
                gold = price / 64;
            }
        } else {
            copper = price;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();
        String priceString = ChatColor.GREEN.toString() + copper + " " + ChatColor.WHITE + silver + " " +
                ChatColor.YELLOW + gold;
        lore.add(ChatColor.GOLD + "Price: " + priceString);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        itemStack = NBTTagUtils.putInteger("price", price, itemStack);
        return itemStack;
    }

    public static int getItemPrice(ItemStack itemStack) {
        if (NBTTagUtils.hasTag(itemStack, "price")) {
            return NBTTagUtils.getInteger(itemStack, "price");
        }
        return 0;
    }

    public static boolean buyItem(Player player, ItemStack itemToBuy) {
        int price = getItemPrice(itemToBuy);
        if (price > 0) {
            if (pay(player, price)) {
                ItemStack copyStack = itemToBuy.clone();
                copyStack = clearItemPrice(copyStack);
                InventoryUtils.giveItemToPlayer(player, copyStack);
                return true;
            }
        }
        return false;
    }

    public static ItemStack clearItemPrice(ItemStack itemStack) {
        if (NBTTagUtils.hasTag(itemStack, "price")) {
            itemStack = NBTTagUtils.removeTag("price", itemStack);
            ItemMeta itemMeta = itemStack.getItemMeta();
            List<String> lore = itemMeta.getLore();
            lore.remove(lore.size() - 1);
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        }
        return null;
    }

    public static List<Coin> priceToCoins(int price) {
        int copperAmount = 0;
        int silverAmount = 0;
        int goldAmount = 0;
        if (price > 63) {
            copperAmount = price % 64;
            silverAmount = price / 64;
            if (silverAmount > 63) {
                silverAmount = price % 64;
                goldAmount = price / 64;
            }
        } else {
            copperAmount = price;
        }
        List<Coin> coins = new ArrayList<>();

        coins.add(new Coin(CoinType.COPPER, copperAmount));
        coins.add(new Coin(CoinType.SILVER, silverAmount));
        coins.add(new Coin(CoinType.GOLD, goldAmount));
        return coins;
    }
}
