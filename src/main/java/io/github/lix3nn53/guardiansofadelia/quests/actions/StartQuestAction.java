package io.github.lix3nn53.guardiansofadelia.quests.actions;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class StartQuestAction implements Action {

    private final int questId;

    public StartQuestAction(int questId) {
        this.questId = questId;
    }

    @Override
    public void perform(Player player) {
        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
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
}
