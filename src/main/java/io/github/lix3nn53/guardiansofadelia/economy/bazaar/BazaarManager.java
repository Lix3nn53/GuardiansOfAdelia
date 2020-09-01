package io.github.lix3nn53.guardiansofadelia.economy.bazaar;

import io.github.lix3nn53.guardiansofadelia.utilities.LocationUtils;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BazaarManager {

    private static final HashMap<ArmorStand, Player> bazaarToPlayer = new HashMap<>();
    private static final HashMap<String, List<Bazaar>> chunkKeyToBazaar = new HashMap<>();
    private static final HashMap<Player, ItemStack> playerToCurrentlySettingMoneyOfItem = new HashMap<>();

    public static boolean isBazaar(ArmorStand entity) {
        return bazaarToPlayer.containsKey(entity);
    }

    public static void onChunkLoad(String chunkKey) {
        if (chunkKeyToBazaar.containsKey(chunkKey)) {
            List<Bazaar> bazaars = chunkKeyToBazaar.get(chunkKey);
            for (Bazaar bazaar : bazaars) {
                bazaar.createModel();
            }
        }
    }

    public static void onBazaarRemove(Bazaar bazaar) {
        String chunkKey = LocationUtils.getChunkKey(bazaar.getBaseLocation());
        if (chunkKeyToBazaar.containsKey(chunkKey)) {
            List<Bazaar> bazaars = chunkKeyToBazaar.get(chunkKey);
            bazaars.remove(bazaar);
            if (bazaars.isEmpty()) {
                chunkKeyToBazaar.remove(chunkKey);
            } else {
                chunkKeyToBazaar.put(chunkKey, bazaars);
            }
        }
    }

    public static void onBazaarCreate(Location location, Bazaar bazaar) {
        String chunkKey = LocationUtils.getChunkKey(location);
        if (chunkKeyToBazaar.containsKey(chunkKey)) {
            List<Bazaar> bazaars = chunkKeyToBazaar.get(chunkKey);
            bazaars.add(bazaar);
            chunkKeyToBazaar.put(chunkKey, bazaars);
        } else {
            List<Bazaar> bazaars = new ArrayList<>();
            bazaars.add(bazaar);
            chunkKeyToBazaar.put(chunkKey, bazaars);
        }

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
