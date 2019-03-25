package io.github.lix3nn53.guardiansofadelia.npc.merchant;

import io.github.lix3nn53.guardiansofadelia.utilities.NBTTagUtils;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class MerchantManager {

    private static final HashMap<Integer, MerchantMenu> npcIdToMerchant = new HashMap<>();

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
        List<String> lore = itemStack.getItemMeta().getLore();
        String priceString = ChatColor.GREEN + Integer.toString(copper) + " " + ChatColor.WHITE + silver + " " +
                ChatColor.YELLOW + gold;
        lore.add(ChatColor.GOLD + "Price: " + priceString);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        itemStack = NBTTagUtils.putInteger("shopPrice", price, itemStack);
        return itemStack;
    }
}
