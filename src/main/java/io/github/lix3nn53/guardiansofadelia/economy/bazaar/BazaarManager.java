package io.github.lix3nn53.guardiansofadelia.economy.bazaar;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class BazaarManager {

    private static HashMap<ArmorStand, Player> bazaarToPlayer = new HashMap<ArmorStand, Player>();

    public static boolean isBazaar(Entity entity) {
        return bazaarToPlayer.containsKey(entity);
    }
}
