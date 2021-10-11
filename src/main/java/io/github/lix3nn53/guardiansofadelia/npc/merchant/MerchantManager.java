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
