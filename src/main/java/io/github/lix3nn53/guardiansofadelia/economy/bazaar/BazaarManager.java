package io.github.lix3nn53.guardiansofadelia.economy.bazaar;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class BazaarManager {

    private HashMap<ArmorStand, Player> bazaarToPlayer = new HashMap<ArmorStand, Player>();

    public HashMap<ArmorStand, Player> getBazaarToPlayer() {
        return bazaarToPlayer;
    }

    public void setBazaarToPlayer(HashMap<ArmorStand, Player> bazaarToPlayer) {
        this.bazaarToPlayer = bazaarToPlayer;
    }
}
