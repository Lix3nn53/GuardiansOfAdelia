package io.github.lix3nn53.guardiansofadelia.party;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PartyManager {

    private static HashMap<Player, Party> playerToParty = new HashMap<>();

    public static void addParty(Player player1, Player player2, Party party) {
        playerToParty.put(player1, party);
        playerToParty.put(player2, party);
    }

    public static Party getParty(Player player) {
        return playerToParty.get(player);
    }

    public static boolean inParty(Player player) {
        return playerToParty.containsKey(player);
    }

    public static void removeMember(Player player) {
        playerToParty.remove(player);
    }

    public static void addMember(Player player, Party party) {
        playerToParty.put(player, party);
    }

    //QUEST PROGRESSES


    public static void progressDealDamageTasksOfOtherMembers(Player player, LivingEntity livingTarget, double finalDamage) {
        if (PartyManager.inParty(player)) {
            Party party = PartyManager.getParty(player);
            List<Player> members = party.getMembers();

            for (Player member : members) {
                UUID uuid = member.getUniqueId();
                if (!uuid.equals(player.getUniqueId())) {

                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                            List<Quest> questList = activeCharacter.getQuestList();
                            for (Quest quest : questList) {
                                quest.progressDealDamageTasks(member, livingTarget, (int) (finalDamage + 0.5));
                            }
                            member.sendMessage("Party quest deal damage share");
                        }
                    }
                }
            }
            player.sendMessage("Party quest deal damage share");
        }
    }

    public static void progressMobKillTasksOfOtherMembers(Player player, LivingEntity livingTarget) {
        if (PartyManager.inParty(player)) {
            Party party = PartyManager.getParty(player);
            List<Player> members = party.getMembers();

            for (Player member : members) {
                UUID uuid = member.getUniqueId();
                if (!uuid.equals(player.getUniqueId())) {

                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                            List<Quest> questList = activeCharacter.getQuestList();
                            for (Quest quest : questList) {
                                quest.progressKillTasks(member, livingTarget);
                            }
                            member.sendMessage("Party quest kill mob share");
                        }
                    }
                }
            }
            player.sendMessage("Party quest kill mob share");
        }
    }

    public static void onPlayerQuit(Player player) {
        if (inParty(player)) {
            Party party = getParty(player);
            party.leave(player);
        }
    }
}
