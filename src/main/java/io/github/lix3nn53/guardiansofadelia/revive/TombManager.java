package io.github.lix3nn53.guardiansofadelia.revive;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class TombManager {

    private static HashMap<ArmorStand, Player> tombToPlayer = new HashMap<ArmorStand, Player>();

    public static boolean isTomb(Entity entity) {
        return tombToPlayer.containsKey(entity);
    }

    public static void addTomb(Player player, Location location) {

    }
}
