package io.github.lix3nn53.guardiansofadelia.economy;

import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class EconomyUtils {

    public static int[] priceToCoins(int price) {
        int copper;
        int silver = 0;
        int gold = 0;
        if (price > 63) {
            copper = price % 64;
            silver = price / 64;
            if (silver > 63) {
                int excessSilver = silver;
                silver = excessSilver % 64;
                gold = excessSilver / 64;
            }
        } else {
            copper = price;
        }

        return new int[]{copper, silver, gold};
    }

    public static String priceToString(int price) {
        int[] coins = priceToCoins(price);

        return ChatColor.GREEN + Integer.toString(coins[0]) + " " + ChatColor.WHITE + coins[1] + " " +
                ChatColor.YELLOW + coins[2];
    }

    public static ItemStack setShopPrice(ItemStack itemStack, int price) {
        int[] coins = priceToCoins(price);

        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();
        String priceString = ChatColor.GREEN + Integer.toString(coins[0]) + " " + ChatColor.WHITE + coins[1] + " " +
                ChatColor.YELLOW + coins[2];
        lore.add(ChatColor.GOLD + "Price: " + priceString);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        PersistentDataContainerUtil.putInteger("shopPrice", price, itemStack);
        return itemStack;
    }

    public static ItemStack removeShopPrice(ItemStack itemStack) {
        if (PersistentDataContainerUtil.hasInteger(itemStack, "shopPrice")) {
            ItemStack clone = itemStack.clone();
            ItemMeta itemMeta = clone.getItemMeta();
            List<String> lore = itemMeta.getLore();
            int size = lore.size();
            lore.remove(size - 1);
            itemMeta.setLore(lore);
            clone.setItemMeta(itemMeta);
            PersistentDataContainerUtil.removeTag(clone, "shopPrice");
            return clone;
        }
        return itemStack;
    }

    public static int getItemPrice(ItemStack itemStack) {
        if (PersistentDataContainerUtil.hasInteger(itemStack, "shopPrice")) {
            return PersistentDataContainerUtil.getInteger(itemStack, "shopPrice");
        }
        return 0;
    }

    public static boolean pay(Player player, ItemStack itemStack) {
        int price = getItemPrice(itemStack);

        int[] coins = priceToCoins(price);

        boolean payed = false;
        boolean payed1 = false;
        boolean payed2 = false;
        if (coins[0] > 0) {
            if (InventoryUtils.inventoryContains(player.getInventory(), Material.IRON_INGOT, coins[0])) {
                payed = true;
            }
        } else {
            payed = true;
        }
        if (coins[1] > 0) {
            if (InventoryUtils.inventoryContains(player.getInventory(), Material.GOLD_INGOT, coins[1])) {
                payed1 = true;
            }
        } else {
            payed1 = true;
        }
        if (coins[2] > 0) {
            if (InventoryUtils.inventoryContains(player.getInventory(), Material.DIAMOND, coins[2])) {
                payed2 = true;
            }
        } else {
            payed2 = true;
        }
        if (payed && payed1 && payed2) {
            InventoryUtils.removeMaterialFromInventory(player.getInventory(), Material.IRON_INGOT, coins[1]);
            InventoryUtils.removeMaterialFromInventory(player.getInventory(), Material.GOLD_INGOT, coins[2]);
            InventoryUtils.removeMaterialFromInventory(player.getInventory(), Material.DIAMOND, coins[3]);
            return true;
        }
        return false;
    }

    public static boolean pay(Player player, int price) {
        int[] coins = priceToCoins(price);

        boolean payed = false;
        boolean payed1 = false;
        boolean payed2 = false;
        if (coins[0] > 0) {
            if (InventoryUtils.inventoryContains(player.getInventory(), Material.IRON_INGOT, coins[0])) {
                payed = true;
            }
        } else {
            payed = true;
        }
        if (coins[1] > 0) {
            if (InventoryUtils.inventoryContains(player.getInventory(), Material.GOLD_INGOT, coins[1])) {
                payed1 = true;
            }
        } else {
            payed1 = true;
        }
        if (coins[2] > 0) {
            if (InventoryUtils.inventoryContains(player.getInventory(), Material.DIAMOND, coins[2])) {
                payed2 = true;
            }
        } else {
            payed2 = true;
        }
        if (payed && payed1 && payed2) {
            InventoryUtils.removeMaterialFromInventory(player.getInventory(), Material.IRON_INGOT, coins[0]);
            InventoryUtils.removeMaterialFromInventory(player.getInventory(), Material.GOLD_INGOT, coins[1]);
            InventoryUtils.removeMaterialFromInventory(player.getInventory(), Material.DIAMOND, coins[2]);
            return true;
        }
        return false;
    }

    public static boolean canPay(Player player, int price) {
        int[] coins = priceToCoins(price);

        boolean payed = false;
        boolean payed1 = false;
        boolean payed2 = false;
        if (coins[0] > 0) {
            if (InventoryUtils.inventoryContains(player.getInventory(), Material.IRON_INGOT, coins[0])) {
                payed = true;
            }
        } else {
            payed = true;
        }
        if (coins[1] > 0) {
            if (InventoryUtils.inventoryContains(player.getInventory(), Material.GOLD_INGOT, coins[1])) {
                payed1 = true;
            }
        } else {
            payed1 = true;
        }
        if (coins[2] > 0) {
            if (InventoryUtils.inventoryContains(player.getInventory(), Material.DIAMOND, coins[2])) {
                payed2 = true;
            }
        } else {
            payed2 = true;
        }

        return payed && payed1 && payed2;
    }
}
