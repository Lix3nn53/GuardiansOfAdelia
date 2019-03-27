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
