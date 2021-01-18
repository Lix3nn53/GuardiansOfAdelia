package io.github.lix3nn53.guardiansofadelia.quests.actions;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StartQuestAction implements Action {

    private final int questId;

    public StartQuestAction(int questId) {
        this.questId = questId;
    }

    @Override
    public void perform(Player player, int questID, int taskIndex) {
        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                Quest questCopyById = QuestNPCManager.getQuestCopyById(this.questId);
                boolean questListIsNotFull = activeCharacter.acceptQuest(questCopyById, player);
                if (questListIsNotFull) {

                } else {
                    player.sendMessage(ChatColor.RED + "Your quest list is full");
                }
            }
        }
    }

    @Override
    public boolean preventTaskCompilation() {
        return false;
    }
}
