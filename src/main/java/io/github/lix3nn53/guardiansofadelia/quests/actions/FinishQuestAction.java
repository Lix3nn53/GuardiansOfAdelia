package io.github.lix3nn53.guardiansofadelia.quests.actions;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import org.bukkit.entity.Player;

import java.util.UUID;

public class FinishQuestAction implements Action {

    private final int questId;

    public FinishQuestAction(int questId) {
        this.questId = questId;
    }

    @Override
    public void perform(Player player) {
        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                boolean didComplete = activeCharacter.turnInQuest(this.questId, player);
            }
        }
    }
}
