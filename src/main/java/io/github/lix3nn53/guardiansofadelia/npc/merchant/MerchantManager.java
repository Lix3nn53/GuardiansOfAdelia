package io.github.lix3nn53.guardiansofadelia.npc.merchant;

import org.bukkit.entity.Player;

import java.util.HashMap;
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
        setMerchant(8, new MerchantMenu(MerchantType.TOOL_SHOP, 1, 8));
        setMerchant(9, new MerchantMenu(MerchantType.TOOL_SHOP, 2, 9));
        setMerchant(10, new MerchantMenu(MerchantType.TOOL_SHOP, 3, 10));
        setMerchant(11, new MerchantMenu(MerchantType.TOOL_SHOP, 4, 11));
        setMerchant(12, new MerchantMenu(MerchantType.TOOL_SHOP, 5, 12));

        setMerchant(13, new MerchantMenu(MerchantType.BLACKSMITH, 1, 13));
        setMerchant(14, new MerchantMenu(MerchantType.BLACKSMITH, 3, 14));
        setMerchant(15, new MerchantMenu(MerchantType.BLACKSMITH, 4, 15));
        setMerchant(16, new MerchantMenu(MerchantType.BLACKSMITH, 5, 16));


        setMerchant(17, new MerchantMenu(MerchantType.STORAGE_KEEPER, 1, 17));
        setMerchant(18, new MerchantMenu(MerchantType.STORAGE_KEEPER, 2, 18));
        setMerchant(19, new MerchantMenu(MerchantType.STORAGE_KEEPER, 3, 19));
        setMerchant(20, new MerchantMenu(MerchantType.STORAGE_KEEPER, 4, 20));
        setMerchant(21, new MerchantMenu(MerchantType.STORAGE_KEEPER, 5, 21));

        setMerchant(22, new MerchantMenu(MerchantType.MAGIC_SHOP, 1, 22));
        setMerchant(23, new MerchantMenu(MerchantType.MAGIC_SHOP, 2, 23));
        setMerchant(24, new MerchantMenu(MerchantType.MAGIC_SHOP, 3, 24));
        setMerchant(25, new MerchantMenu(MerchantType.MAGIC_SHOP, 4, 25));
        setMerchant(26, new MerchantMenu(MerchantType.MAGIC_SHOP, 5, 26));
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
