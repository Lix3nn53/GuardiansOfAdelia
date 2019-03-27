package io.github.lix3nn53.guardiansofadelia.npc.merchant;

import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.NBTTagUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MerchantManager {

    private static final HashMap<Integer, MerchantMenu> npcIdToMerchant = new HashMap<>();
    private static final HashMap<UUID, Integer> playerConfirmBuy = new HashMap<>();


    public static boolean isMerchant(int npcId) {
        return npcIdToMerchant.containsKey(npcId);
    }

    public static MerchantMenu getMerchantMenu(int npcId) {
        return npcIdToMerchant.get(npcId);
    }

    public static void setMerchant(int npcId, MerchantMenu merchantMenu) {
        npcIdToMerchant.put(npcId, merchantMenu);
    }

    public static void init() {
        setMerchant(14, new MerchantMenu(MerchantType.BLACKSMITH, 1, 14));
        setMerchant(24, new MerchantMenu(MerchantType.BLACKSMITH, 3, 24));
        setMerchant(29, new MerchantMenu(MerchantType.BLACKSMITH, 4, 29));
        setMerchant(36, new MerchantMenu(MerchantType.BLACKSMITH, 5, 36));

        setMerchant(17, new MerchantMenu(MerchantType.TOOL_SHOP, 1, 17));
        setMerchant(25, new MerchantMenu(MerchantType.TOOL_SHOP, 2, 25));
        setMerchant(31, new MerchantMenu(MerchantType.TOOL_SHOP, 3, 31));
        setMerchant(35, new MerchantMenu(MerchantType.TOOL_SHOP, 4, 35));
        setMerchant(43, new MerchantMenu(MerchantType.TOOL_SHOP, 5, 43));

        setMerchant(18, new MerchantMenu(MerchantType.PET_SHOP, 1, 18));
        setMerchant(32, new MerchantMenu(MerchantType.PET_SHOP, 3, 32));
        setMerchant(33, new MerchantMenu(MerchantType.PET_SHOP, 4, 33));
        setMerchant(34, new MerchantMenu(MerchantType.PET_SHOP, 5, 34));

        setMerchant(15, new MerchantMenu(MerchantType.STORAGE_KEEPER, 1, 15));
        setMerchant(26, new MerchantMenu(MerchantType.STORAGE_KEEPER, 2, 26));
        setMerchant(30, new MerchantMenu(MerchantType.STORAGE_KEEPER, 3, 30));
        setMerchant(38, new MerchantMenu(MerchantType.STORAGE_KEEPER, 4, 38));
        setMerchant(42, new MerchantMenu(MerchantType.STORAGE_KEEPER, 5, 42));

        setMerchant(16, new MerchantMenu(MerchantType.MAGIC_SHOP, 1, 16));
        setMerchant(27, new MerchantMenu(MerchantType.MAGIC_SHOP, 2, 27));
        setMerchant(28, new MerchantMenu(MerchantType.MAGIC_SHOP, 3, 28));
        setMerchant(37, new MerchantMenu(MerchantType.MAGIC_SHOP, 4, 37));
        setMerchant(44, new MerchantMenu(MerchantType.MAGIC_SHOP, 5, 44));
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
        itemStack = NBTTagUtils.putInteger("shopPrice", price, itemStack);
        return itemStack;
    }

    public static ItemStack removeShopPrice(ItemStack itemStack) {
        ItemStack clone = itemStack.clone();
        ItemMeta itemMeta = clone.getItemMeta();
        List<String> lore = itemMeta.getLore();
        int size = lore.size();
        lore.remove(size - 1);
        itemMeta.setLore(lore);
        clone.setItemMeta(itemMeta);
        clone = NBTTagUtils.removeTag("shopPrice", clone);
        return clone;
    }

    public static int getItemPrice(ItemStack itemStack) {
        if (NBTTagUtils.hasTag(itemStack, "shopPrice")) {
            return NBTTagUtils.getInteger(itemStack, "shopPrice");
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

    public static boolean onSellItemClick(Player player, int slot) {
        UUID uuid = player.getUniqueId();
        if (playerConfirmBuy.containsKey(uuid)) {
            if (playerConfirmBuy.get(uuid) == slot) {
                playerConfirmBuy.remove(uuid);
                return true;
            }
        }
        playerConfirmBuy.put(uuid, slot);
        return false;
    }

    public static void clearSellItemClick(Player player) {
        playerConfirmBuy.remove(player.getUniqueId());
    }
}
