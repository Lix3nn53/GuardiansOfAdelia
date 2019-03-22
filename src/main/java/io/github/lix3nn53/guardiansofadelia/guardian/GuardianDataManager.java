package io.github.lix3nn53.guardiansofadelia.guardian;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.database.DatabaseManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class GuardianDataManager {

    private HashMap<UUID, GuardianData> onlineGuardians = new HashMap<UUID, GuardianData>();

    public GuardianData getGuardianData(UUID uuid) {
        if (onlineGuardians.containsKey(uuid)) {
            return onlineGuardians.get(uuid);
        }
        return null;
    }

    public boolean hasGuardianData(UUID uuid) {
        return onlineGuardians.containsKey(uuid);
    }

    public void addGuardianData(UUID uuid, GuardianData guardianData) {
        onlineGuardians.put(uuid, guardianData);
    }

    public void onPlayerQuit(Player player) {
        UUID uuid = player.getUniqueId();
        if (hasGuardianData(uuid)) {
            GuardianData guardianData = getGuardianData(uuid);

            DatabaseManager databaseManager = GuardiansOfAdelia.getDatabaseManager();
            databaseManager.writeGuardianDataWithCurrentCharacter(player, guardianData);
        }
        onlineGuardians.remove(uuid);
    }
}
