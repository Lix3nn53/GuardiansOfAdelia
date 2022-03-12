package io.github.lix3nn53.guardiansofadelia.quests.actions;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FinishQuestAction implements Action {

    private final int questId;
    private final boolean ignoreCompilation;

    public FinishQuestAction(int questId, boolean ignoreCompilation) {
        this.questId = questId;
        this.ignoreCompilation = ignoreCompilation;
    }

    @Override
    public void perform(Player player, int questID, int taskIndex) {
        new BukkitRunnable() {
            @Override
            public void run() {

                if (GuardianDataManager.hasGuardianData(player)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                    if (guardianData.hasActiveCharacter()) {
                        RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                        boolean didComplete = activeCharacter.turnInQuest(questId, player, ignoreCompilation);
                    }
                }
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), 10L); // Less delay than StartQuestAction
    }

    @Override
    public boolean preventTaskCompilation() {
        return false;
    }
}
