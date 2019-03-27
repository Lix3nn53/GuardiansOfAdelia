package io.github.lix3nn53.guardiansofadelia.economy.bazaar;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class BazaarManager {

    private static HashMap<ArmorStand, Player> bazaarToPlayer = new HashMap<>();
    private static HashMap<Player, ItemStack> playerToCurrentlySettingMoneyOfItem = new HashMap<>();

    public static boolean isBazaar(Entity entity) {
        return bazaarToPlayer.containsKey(entity);
    }

    public static Player getOwner(Entity entity) {
        return bazaarToPlayer.get(entity);
    }

    public static void putBazaarToPlayer(Player player, ArmorStand armorStand) {
        bazaarToPlayer.put(armorStand, player);
    }

    public static void clearBazaarToPlayer(ArmorStand armorStand) {
        bazaarToPlayer.remove(armorStand);
    }

    public static boolean isSettingMoney(Player player) {
        return playerToCurrentlySettingMoneyOfItem.containsKey(player);
    }

    public static ItemStack getItemToSetMoney(Player player) {
        return playerToCurrentlySettingMoneyOfItem.get(player);
    }


    public static void setPlayerSettingMoneyOfItem(Player player, ItemStack itemStack) {
        playerToCurrentlySettingMoneyOfItem.put(player, itemStack);
    }

    public static void clearPlayerSettingMoneyOfItem(Player player) {
        playerToCurrentlySettingMoneyOfItem.remove(player);
    }
}
