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
        setMerchant(20, new MerchantMenu(MerchantType.TOOL_SHOP, 1, 20));
        setMerchant(21, new MerchantMenu(MerchantType.TOOL_SHOP, 2, 21));

        setMerchant(40, new MerchantMenu(MerchantType.WEAPONSMITH, 1, 40));
        setMerchant(41, new MerchantMenu(MerchantType.WEAPONSMITH, 2, 41));

        setMerchant(60, new MerchantMenu(MerchantType.ARMORSMITH, 1, 60));
        setMerchant(61, new MerchantMenu(MerchantType.ARMORSMITH, 2, 61));

        setMerchant(80, new MerchantMenu(MerchantType.STORAGE_KEEPER, 1, 80));
        setMerchant(81, new MerchantMenu(MerchantType.STORAGE_KEEPER, 2, 81));

        setMerchant(100, new MerchantMenu(MerchantType.MAGIC_SHOP, 1, 100));
        setMerchant(101, new MerchantMenu(MerchantType.MAGIC_SHOP, 2, 101));
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
