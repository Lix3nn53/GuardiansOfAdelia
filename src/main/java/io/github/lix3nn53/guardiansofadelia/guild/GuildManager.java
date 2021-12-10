package io.github.lix3nn53.guardiansofadelia.guild;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.database.DatabaseManager;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.TablistUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

public class GuildManager {

    private static final HashMap<Player, Guild> playerToGuild = new HashMap<>();

    public static void addPlayerGuild(Player player, Guild guild) {
        playerToGuild.put(player, guild);
        GuildManager.updateTablistOfMembers(player);
    }

    public static void updateTablistOfMembers(Player player) {
        if (playerToGuild.containsKey(player)) {
            Guild guild = playerToGuild.get(player);
            for (UUID uuid : guild.getMembers()) {
                Player member = Bukkit.getPlayer(uuid);
                if (member != null) {
                    TablistUtils.updateTablist(member);
                }
            }
        }
    }

    public static void sendJoinMessageToMembers(Player player) {
        if (playerToGuild.containsKey(player)) {
            Guild guild = playerToGuild.get(player);
            for (UUID uuid : guild.getMembers()) {
                if (!uuid.equals(player.getUniqueId())) {
                    Player member = Bukkit.getPlayer(uuid);
                    if (member != null) {
                        MessageUtils.sendCenteredMessage(member, ChatPalette.PURPLE + "Guild member " + ChatPalette.WHITE + player.getName() + ChatPalette.PURPLE + " joined the server");
                    }
                }
            }
        }
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


            new BukkitRunnable() {
                @Override
                public void run() {
                    for (UUID uuid : guild.getMembers()) {
                        Player member = Bukkit.getPlayer(uuid);
                        if (member != null) {
                            TablistUtils.updateTablist(member);
                        }
                    }
                }
            }.runTaskLater(GuardiansOfAdelia.getInstance(), 20L);

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
