package io.github.lix3nn53.guardiansofadelia.utilities.managers;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.locale.Translation;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class DoNotGetAwayManager {

    private static final HashMap<Player, DoNotGetAwayData> playerToData = new HashMap<>();

    public static void addPlayer(Player player, DoNotGetAwayData doNotGetAwayData) {
        playerToData.put(player, doNotGetAwayData);
    }

    public static void removePlayer(Player player) {
        playerToData.remove(player);
    }

    public static void onMove(Player player, Location to) {
        if (playerToData.containsKey(player)) {
            DoNotGetAwayData doNotGetAwayData = playerToData.get(player);

            float distance = doNotGetAwayData.distance;
            if (doNotGetAwayData.center.distanceSquared(to) > distance * distance) {
                player.teleport(doNotGetAwayData.center);

                GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                player.sendMessage(ChatPalette.RED + Translation.t(guardianData, doNotGetAwayData.onLeave));
            }
        }
    }
}
