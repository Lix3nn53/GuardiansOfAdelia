package io.github.lix3nn53.guardiansofadelia.guardian;

import io.github.lix3nn53.guardiansofadelia.database.DatabaseManager;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.Bazaar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Set;

public class GuardianDataManager {

    private static final HashMap<Player, GuardianData> onlineGuardians = new HashMap<>();

    public static GuardianData getGuardianData(Player player) {
        return onlineGuardians.get(player);
    }

    public static boolean hasGuardianData(Player player) {
        return onlineGuardians.containsKey(player);
    }

    public static void addGuardianData(Player player, GuardianData guardianData) {
        onlineGuardians.put(player, guardianData);
    }

    public static void onPlayerQuit(Player player) {
        if (hasGuardianData(player)) {
            GuardianData guardianData = getGuardianData(player);
            DatabaseManager.writeGuardianDataWithCurrentCharacter(player, guardianData);
            if (guardianData.hasBazaar()) {
                Bazaar bazaar = guardianData.getBazaar();
                bazaar.remove();
            }
        }
        onlineGuardians.remove(player);
    }

    public static Set<Player> getOnlinePlayers() {
        return onlineGuardians.keySet();
    }

    public static void clearCurrentCharacterDataWithoutSaving(Player player) {
        if (hasGuardianData(player)) {
            GuardianData guardianData = getGuardianData(player);
            if (guardianData.hasBazaar()) {
                Bazaar bazaar = guardianData.getBazaar();
                bazaar.remove();
            }
        }
        onlineGuardians.remove(player);
    }
}
