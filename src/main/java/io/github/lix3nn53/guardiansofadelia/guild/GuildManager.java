package io.github.lix3nn53.guardiansofadelia.guild;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.database.DatabaseManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class GuildManager {

    private List<Guild> guildList = new ArrayList<>();

    public List<Guild> getTop10() {
        guildList.sort(Comparator.comparingInt(Guild::getWarPoints));
        int size = guildList.size();

        List<Guild> top10Guild = new ArrayList<>();
        for (int i = size - 10; i < size; i++) {
            top10Guild.add(guildList.get(i));
        }
        Collections.reverse(top10Guild);
        return top10Guild;
    }

    public void addGuildToMemory(Guild guild) {
        guildList.add(guild);
    }

    public void removeGuildFromMemory(Guild guild) {
        guildList.remove(guild);
    }

    public boolean isGuildLoaded(String name) {
        Optional<Guild> guildOptional = this.guildList.stream()
                .filter(item -> item.getName().equals(name))
                .findAny();
        return guildOptional.isPresent();
    }

    public Guild getGuild(String name) {
        Optional<Guild> guildOptional = this.guildList.stream()
                .filter(item -> item.getName().equals(name))
                .findAny();
        return guildOptional.orElse(null);
    }

    public List<Guild> getActiveGuilds() {
        return this.guildList;
    }

    //call before removing player guardianData
    public void onPlayerQuit(Player player) {
        UUID uuid = player.getUniqueId();
        GuardianData guardianData = GuardiansOfAdelia.getGuardianDataManager().getGuardianData(uuid);
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
                DatabaseManager databaseManager = GuardiansOfAdelia.getDatabaseManager();
                databaseManager.writeGuildData(guild);
                this.guildList.remove(guild);
            }
        }

    }
}
