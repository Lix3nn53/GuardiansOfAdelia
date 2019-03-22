package io.github.lix3nn53.guardiansofadelia.revive;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class TombManager {

    private HashMap<ArmorStand, Player> tombToPlayer = new HashMap<ArmorStand, Player>();

    public HashMap<ArmorStand, Player> getTombToPlayer() {
        return tombToPlayer;
    }

    public void setTombToPlayer(HashMap<ArmorStand, Player> tombToPlayer) {
        this.tombToPlayer = tombToPlayer;
    }
}
