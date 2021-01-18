package io.github.lix3nn53.guardiansofadelia.chat;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guild.Guild;
import io.github.lix3nn53.guardiansofadelia.guild.GuildManager;
import io.github.lix3nn53.guardiansofadelia.utilities.hologram.Hologram;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ChatManager {

    private static final List<Player> chatCooldown = new ArrayList<>();

    public static void chatHologram(Player player, String message) {
        double height = player.getHeight();
        Location location = player.getLocation().clone().add(0, height + 0.4, 0);

        new BukkitRunnable() {

            ArmorStand armorStand;
            int ticksPass = 0;
            final int ticksLimit = 30;

            @Override
            public void run() {
                if (ticksPass == ticksLimit) {
                    cancel();
                    chatCooldown.remove(player);
                    armorStand.remove();
                } else {
                    if (ticksPass == 0) {
                        armorStand = new Hologram(location, ChatColor.YELLOW + "< " + ChatColor.GRAY + message + ChatColor.YELLOW + " >").getArmorStand();
                    }
                    Location location = player.getLocation().clone().add(0, height + 0.4, 0);
                    armorStand.teleport(location);
                    ticksPass++;
                }
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 0L, 2L);
    }

    public static void chatHologramEntity(Entity entity, String message, int durationTicks, double offsetY) {
        double height = entity.getHeight();
        Location location = entity.getLocation().clone().add(0, height + 0.4 + offsetY, 0);

        new BukkitRunnable() {

            ArmorStand armorStand;
            int ticksPass = 0;

            @Override
            public void run() {
                if (ticksPass == durationTicks) {
                    cancel();
                    armorStand.remove();
                } else {
                    if (ticksPass == 0) {
                        armorStand = new Hologram(location, ChatColor.YELLOW + "< " + ChatColor.GRAY + message + ChatColor.YELLOW + " >").getArmorStand();
                    }
                    Location location = entity.getLocation().clone().add(0, height + 0.4 + offsetY, 0);
                    armorStand.teleport(location);
                    ticksPass += 2;
                }
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 0L, 2L);
    }

    public static void chatHologramEntityWithCountDown(Entity entity, String message, int durationTicks, double offsetY) {
        double height = entity.getHeight();
        Location location = entity.getLocation().clone().add(0, height + 0.4 + offsetY, 0);

        new BukkitRunnable() {

            ArmorStand armorStand;
            int ticksPass = 0;

            @Override
            public void run() {
                if (ticksPass == durationTicks) {
                    cancel();
                    armorStand.remove();
                } else {
                    if (ticksPass == 0) {
                        armorStand = new Hologram(location, message + ChatColor.YELLOW + " in " + durationTicks).getArmorStand();
                    } else {
                        armorStand.setCustomName(message + ChatColor.YELLOW + " in " + (durationTicks - ticksPass));
                    }
                    Location location = entity.getLocation().clone().add(0, height + 0.4 + offsetY, 0);
                    armorStand.teleport(location);
                    ticksPass += 2;
                }
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 0L, 2L);
    }

    /**
     * Create chat hologram and return boolean to allow on normal chat
     *
     * @param player
     * @param message
     * @return allow message to normal chat
     */
    public static boolean onChat(Player player, String message) {
        if (chatCooldown.contains(player)) {
            player.sendMessage(ChatColor.RED + "You can send a message per 3 seconds");
            return false;
        }
        chatCooldown.add(player);
        chatHologram(player, message);

        //send message to normal chat
        return true;
    }

    public static String getFormat(Player player) {
        String format = "<group-prefix>%s<group-suffix>%s"; //first %s is player.getDisplayName(), second %s is the message
        //replacing your values
        format = format.replace("<group-prefix>", getChatPrefix(player));
        format = format.replace("<group-suffix>", getChatSuffix(player));

        return format;
    }

    private static String getChatPrefix(Player player) {
        String prefix = "";
        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            StaffRank staffRank = guardianData.getStaffRank();
            if (!staffRank.equals(StaffRank.NONE)) {
                String s = staffRank.toString();
                prefix += ChatColor.DARK_GRAY + "[" + staffRank.getChatColor() + s + ChatColor.DARK_GRAY + "]";
            }
            PremiumRank premiumRank = guardianData.getPremiumRank();
            if (!premiumRank.equals(PremiumRank.NONE)) {
                String s = premiumRank.toString();
                prefix += ChatColor.DARK_GRAY + "[" + premiumRank.getChatColor() + s + ChatColor.DARK_GRAY + "]";
            }
            if (GuildManager.inGuild(player)) {
                Guild guild = GuildManager.getGuild(player);
                String s = guild.getTag();
                s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
                prefix += ChatColor.DARK_GRAY + "[" + ChatColor.DARK_PURPLE + s + ChatColor.DARK_GRAY + "]";
            }
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                ChatTag chatTag = activeCharacter.getChatTag();
                String s = chatTag.toString();
                s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
                prefix += ChatColor.DARK_GRAY + "[" + chatTag.getChatColor() + s + ChatColor.DARK_GRAY + "]";
            }
        }
        return prefix + ChatColor.GRAY + " ";
    }

    private static String getChatSuffix(Player player) {
        return ChatColor.GOLD + " > " + ChatColor.YELLOW;
    }
}
