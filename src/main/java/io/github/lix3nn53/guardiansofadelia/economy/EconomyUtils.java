package io.github.lix3nn53.guardiansofadelia.economy;

import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EconomyUtils {

    public static List<Coin> priceToCoins(int price) {
        int copperAmount;
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

    public static String priceToString(int price) {
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
        return ChatColor.GREEN + Integer.toString(copperAmount) + " " + ChatColor.WHITE + silverAmount + " " +
                ChatColor.YELLOW + goldAmount;
    }

    public static ItemStack setShopPrice(ItemStack itemStack, int price) {
        int copper;
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
        String priceString = ChatColor.GREEN + Integer.toString(copper) + " " + ChatColor.WHITE + silver + " " +
                ChatColor.YELLOW + gold;
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
        int copper;
        int silver = 0;
        int gold = 0;
        int price = getItemPrice(itemStack);
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
        boolean payed = false;
        boolean payed1 = false;
        boolean payed2 = false;
        if (copper > 0) {
            if (InventoryUtils.inventoryContains(player.getInventory(), Material.IRON_INGOT, copper)) {
                payed = true;
            }
        } else {
            payed = true;
        }
        if (silver > 0) {
            if (InventoryUtils.inventoryContains(player.getInventory(), Material.GOLD_INGOT, silver)) {
                payed1 = true;
            }
        } else {
            payed1 = true;
        }
        if (gold > 0) {
            if (InventoryUtils.inventoryContains(player.getInventory(), Material.DIAMOND, gold)) {
                payed2 = true;
            }
        } else {
            payed2 = true;
        }
        if (payed && payed1 && payed2) {
            InventoryUtils.removeMaterialFromInventory(player.getInventory(), Material.IRON_INGOT, copper);
            InventoryUtils.removeMaterialFromInventory(player.getInventory(), Material.GOLD_INGOT, silver);
            InventoryUtils.removeMaterialFromInventory(player.getInventory(), Material.DIAMOND, gold);
            return true;
        }
        return false;
    }

    public static boolean pay(Player player, int price) {
        int copper;
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
        boolean payed = false;
        boolean payed1 = false;
        boolean payed2 = false;
        if (copper > 0) {
            if (InventoryUtils.inventoryContains(player.getInventory(), Material.IRON_INGOT, copper)) {
                payed = true;
            }
        } else {
            payed = true;
        }
        if (silver > 0) {
            if (InventoryUtils.inventoryContains(player.getInventory(), Material.GOLD_INGOT, silver)) {
                payed1 = true;
            }
        } else {
            payed1 = true;
        }
        if (gold > 0) {
            if (InventoryUtils.inventoryContains(player.getInventory(), Material.DIAMOND, gold)) {
                payed2 = true;
            }
        } else {
            payed2 = true;
        }
        if (payed && payed1 && payed2) {
            InventoryUtils.removeMaterialFromInventory(player.getInventory(), Material.IRON_INGOT, copper);
            InventoryUtils.removeMaterialFromInventory(player.getInventory(), Material.GOLD_INGOT, silver);
            InventoryUtils.removeMaterialFromInventory(player.getInventory(), Material.DIAMOND, gold);
            return true;
        }
        return false;
    }
}
