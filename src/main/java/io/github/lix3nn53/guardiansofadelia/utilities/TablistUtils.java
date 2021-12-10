package io.github.lix3nn53.guardiansofadelia.utilities;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guild.Guild;
import io.github.lix3nn53.guardiansofadelia.guild.GuildManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TablistUtils {

    public static void updateTablist(Player player) {
        StringBuilder header = new StringBuilder(ChatPalette.GOLD + "Guardians of Adelia");
        header.append("\n");
        StringBuilder footer = new StringBuilder();
        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            String language = guardianData.getLanguage();

            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                //Add quests
                List<Quest> questList = activeCharacter.getQuestList();
                if (!questList.isEmpty()) {
                    header.append("\n" + ChatPalette.PURPLE + "QUESTS");
                    header.append("\n");

                    for (Quest quest : questList) {
                        header.append(ChatPalette.PURPLE_LIGHT);
                        header.append(quest.getName() + ": ");
                        header.append(ChatPalette.WHITE + quest.getObjectiveTextForTablist(language));
                        header.append("\n");
                    }
                }
            }

            //Add friends
            List<Player> friends = guardianData.getFriends();
            if (!friends.isEmpty()) {
                footer.append("\n");
                footer.append("\n" + ChatPalette.GREEN + "FRIENDS");
                footer.append("\n");

                int i = 0;
                for (Player friend : friends) {
                    if (friend.isOnline()) {
                        if (i > 10) {
                            footer.append("\n");
                        }
                        footer.append(friend.getName());
                        footer.append(" ");
                        i++;
                    }
                }
                footer = new StringBuilder(footer.substring(0, footer.length() - 1));
            }

            if (GuildManager.inGuild(player)) {
                footer.append("\n");
                footer.append("\n" + ChatPalette.PURPLE + "GUILD");
                footer.append("\n");

                Guild guild = GuildManager.getGuild(player);

                footer.append(guild.getName());
                footer.append("\n");
                footer.append("\n" + ChatPalette.PURPLE_LIGHT + "MEMBERS");
                footer.append("\n");

                Set<UUID> members = guild.getMembers();
                int i = 0;
                for (UUID memberUuid : members) {
                    Player member = Bukkit.getPlayer(memberUuid);
                    if (member != null) {
                        if (member.isOnline()) {
                            if (i > 10) {
                                footer.append("\n");
                            }
                            footer.append(member.getName());
                            footer.append(" ");
                            i++;
                        }
                    }
                }
            }
        }

        player.setPlayerListHeaderFooter(header.toString(), footer.toString());
    }
}
