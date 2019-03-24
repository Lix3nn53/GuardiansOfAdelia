package io.github.lix3nn53.guardiansofadelia.guild;

import io.github.lix3nn53.guardiansofadelia.database.DatabaseManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class GuildManager {

    private static List<Guild> guildList = new ArrayList<>();

    public static List<Guild> getTop10() {
        guildList.sort(Comparator.comparingInt(Guild::getWarPoints));
        int size = guildList.size();

        List<Guild> top10Guild = new ArrayList<>();
        for (int i = size - 10; i < size; i++) {
            top10Guild.add(guildList.get(i));
        }
        Collections.reverse(top10Guild);
        return top10Guild;
    }

    public static void addGuildToMemory(Guild guild) {
        guildList.add(guild);
    }

    public static void removeGuildFromMemory(Guild guild) {
        guildList.remove(guild);
    }

    public static boolean isGuildLoaded(String name) {
        Optional<Guild> guildOptional = guildList.stream()
                .filter(item -> item.getName().equals(name))
                .findAny();
        return guildOptional.isPresent();
    }

    public static Guild getGuild(String name) {
        Optional<Guild> guildOptional = guildList.stream()
                .filter(item -> item.getName().equals(name))
                .findAny();
        return guildOptional.orElse(null);
    }

    public static List<Guild> getActiveGuilds() {
        return guildList;
    }

    //call before removing player guardianData
    public static void onPlayerQuit(Player player) {
        UUID uuid = player.getUniqueId();
        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
        if (guardianData.isInGuild()) {
            Guild guild = guardianData.getGuild();
            Set<UUID> members = guild.getMembers();
            boolean anyOnlineMemberLeft = false;
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.getUniqueId() != uuid) {
                    if (members.contains(onlinePlayer.getUniqueId())) {
                        anyOnlineMemberLeft = true;
                        break;
                    }
                }
            }
            if (!anyOnlineMemberLeft) {
                DatabaseManager.writeGuildData(guild);
                guildList.remove(guild);
            }
        }

    }
}
