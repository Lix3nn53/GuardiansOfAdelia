package io.github.lix3nn53.guardiansofadelia.minigames.portals;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class InstantTeleportPortal {

    private final Location destination;
    private final int requiredQuestNoAccepted;
    private final int requiredQuestNoTurnedIn;

    public InstantTeleportPortal(Location destination, int requiredQuestNoAccepted, int requiredQuestNoTurnedIn) {
        this.destination = destination;
        this.requiredQuestNoAccepted = requiredQuestNoAccepted;
        this.requiredQuestNoTurnedIn = requiredQuestNoTurnedIn;
    }

    public Location getDestination() {
        return destination;
    }

    public boolean canTeleport(Player player) {
        if (requiredQuestNoAccepted == 0 && requiredQuestNoTurnedIn == 0) return true;

        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                if (requiredQuestNoAccepted > 0) {
                    List<Quest> questList = activeCharacter.getQuestList();

                    boolean questIsInList = questList.stream().anyMatch(questInList -> questInList.getQuestID() == requiredQuestNoAccepted);

                    if (!questIsInList) {
                        player.sendMessage(ChatColor.RED + "You need to accept quest#" + requiredQuestNoAccepted + " to enter this portal.");
                        return false;
                    }
                }

                if (requiredQuestNoTurnedIn > 0) {
                    List<Integer> turnedInQuests = activeCharacter.getTurnedInQuests();

                    if (!turnedInQuests.contains(requiredQuestNoTurnedIn)) {
                        player.sendMessage(ChatColor.RED + "You need to turn in quest#" + requiredQuestNoTurnedIn + " to enter this portal.");
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
