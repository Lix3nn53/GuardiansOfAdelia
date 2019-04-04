package io.github.lix3nn53.guardiansofadelia.guild;

import io.github.lix3nn53.guardiansofadelia.database.DatabaseManager;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class GuildManager {

    private static HashMap<Player, Guild> playerToGuild = new HashMap<>();

    public static void addPlayerGuild(Player player, Guild guild) {
        playerToGuild.put(player, guild);
    }

    public static Guild getGuild(Player player) {
        return playerToGuild.get(player);
    }

    public static boolean inGuild(Player player) {
        return playerToGuild.containsKey(player);
    }

    public static void removePlayer(Player player) {
        playerToGuild.remove(player);
    }

    public static void removeGuild(Guild guild) {
        playerToGuild.values().removeAll(Collections.singleton(guild));
    }

    public static List<Guild> getGuildsSortedByWarPoints() {
        Collection<Guild> guilds = playerToGuild.values();

        List<Guild> guildList = new ArrayList<>(guilds);

        List<Guild> guildListNoDuplicate = guildList.stream().distinct().collect(Collectors.toList());

        guildListNoDuplicate.sort(Comparator.comparingInt(Guild::getWarPoints));

        return guildListNoDuplicate;
    }

    public static void onPlayerQuit(Player player) {
        if (inGuild(player)) {
            Guild guild = getGuild(player);
            playerToGuild.remove(player);

            if (!getActiveGuilds().contains(guild)) {
                DatabaseManager.writeGuildData(guild);
            }
        }
    }

    public static List<Guild> getActiveGuilds() {
        Collection<Guild> guilds = playerToGuild.values();

        List<Guild> guildList = new ArrayList<>(guilds);

        return guildList.stream().distinct().collect(Collectors.toList());
    }

    public static Optional<Guild> getGuild(String name) {
        Collection<Guild> guilds = playerToGuild.values();

        List<Guild> guildList = new ArrayList<>(guilds);

        return guildList.stream()
                .filter(item -> item.getName().equals(name))
                .findAny();
    }


}
