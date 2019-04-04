package io.github.lix3nn53.guardiansofadelia.utilities;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guild.Guild;
import io.github.lix3nn53.guardiansofadelia.guild.GuildManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class TablistUtils {

    public static void updateTablist(Player player) {
        UUID uuid = player.getUniqueId();
        StringBuilder header = new StringBuilder(player.getDisplayName());
        header.append("\n");
        StringBuilder footer = new StringBuilder();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);

            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                //Add quests
                List<Quest> questList = activeCharacter.getQuestList();
                if (!questList.isEmpty()) {
                    header.append("\n" + ChatColor.DARK_PURPLE + "QUESTS");

                    int i = 1;
                    for (Quest quest : questList) {
                        header.append("\n" + ChatColor.LIGHT_PURPLE);
                        header.append(quest.getName() + ": ");
                        header.append(ChatColor.WHITE + quest.getObjectiveTextForTablist());
                        header.append("\n");
                    }
                }
            }

            //Add friends
            List<Player> friends = guardianData.getFriends();
            if (!friends.isEmpty()) {
                footer.append("\n");
                footer.append("\n" + ChatColor.GREEN + "FRIENDS");
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
                footer.append("\n" + ChatColor.DARK_PURPLE + "GUILD");
                footer.append("\n");

                Guild guild = GuildManager.getGuild(player);

                footer.append(guild.getName());
                footer.append("\n");
                footer.append("\n" + ChatColor.LIGHT_PURPLE + "MEMBERS");
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
        setPlayerlistHeaderFooter(player, header.toString(), footer.toString());
    }

    private static void setPlayerlistHeaderFooter(Player player, String header, String footer) {

        try {
            Object tabHeader = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + header + "\"}");
            Object tabFooter = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + footer + "\"}");
            Constructor<?> titleConstructor = Objects.requireNonNull(getNMSClass("PacketPlayOutPlayerListHeaderFooter")).getConstructor();
            Object packet = titleConstructor.newInstance();
            try {
                Field aField = packet.getClass().getDeclaredField("a");
                aField.setAccessible(true);
                aField.set(packet, tabHeader);
                Field bField = packet.getClass().getDeclaredField("b");
                bField.setAccessible(true);
                bField.set(packet, tabFooter);
            } catch (Exception e) {
                Field aField = packet.getClass().getDeclaredField("header");
                aField.setAccessible(true);
                aField.set(packet, tabHeader);
                Field bField = packet.getClass().getDeclaredField("footer");
                bField.setAccessible(true);
                bField.set(packet, tabFooter);
            }
            sendPacket(player, packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
