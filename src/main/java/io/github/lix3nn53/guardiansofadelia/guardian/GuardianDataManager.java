package io.github.lix3nn53.guardiansofadelia.guardian;

import io.github.lix3nn53.guardiansofadelia.database.DatabaseManager;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.Bazaar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class GuardianDataManager {

    private static final HashMap<UUID, GuardianData> onlineGuardians = new HashMap<>();

    public static GuardianData getGuardianData(UUID uuid) {
        if (onlineGuardians.containsKey(uuid)) {
            return onlineGuardians.get(uuid);
        }

        return null;
    }

    public static boolean hasGuardianData(UUID uuid) {
        return onlineGuardians.containsKey(uuid);
    }

    public static void addGuardianData(UUID uuid, GuardianData guardianData) {
        onlineGuardians.put(uuid, guardianData);
    }

    public static void onPlayerQuit(Player player) {
        UUID uuid = player.getUniqueId();
        if (hasGuardianData(uuid)) {
            GuardianData guardianData = getGuardianData(uuid);
            DatabaseManager.writeGuardianDataWithCurrentCharacter(player, guardianData);
            if (guardianData.hasBazaar()) {
                Bazaar bazaar = guardianData.getBazaar();
                bazaar.remove();
            }
        }
        onlineGuardians.remove(uuid);
    }

    public static Set<UUID> getOnlineUUIDs() {
        return onlineGuardians.keySet();
    }

    public static void clearCurrentCharacterDataWithoutSaving(UUID uuid) {
        if (hasGuardianData(uuid)) {
            GuardianData guardianData = getGuardianData(uuid);
            if (guardianData.hasBazaar()) {
                Bazaar bazaar = guardianData.getBazaar();
                bazaar.remove();
            }
        }
        onlineGuardians.remove(uuid);
    }
}
