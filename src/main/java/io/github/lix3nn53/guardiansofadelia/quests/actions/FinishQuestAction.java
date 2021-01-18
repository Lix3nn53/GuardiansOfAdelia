package io.github.lix3nn53.guardiansofadelia.quests.actions;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import org.bukkit.entity.Player;

public class FinishQuestAction implements Action {

    private final int questId;
    private final boolean ignoreCompilation;

    public FinishQuestAction(int questId, boolean ignoreCompilation) {
        this.questId = questId;
        this.ignoreCompilation = ignoreCompilation;
    }

    @Override
    public void perform(Player player, int questID, int taskIndex) {
        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                boolean didComplete = activeCharacter.turnInQuest(this.questId, player, ignoreCompilation);
            }
        }
    }

    @Override
    public boolean preventTaskCompilation() {
        return false;
    }
}
