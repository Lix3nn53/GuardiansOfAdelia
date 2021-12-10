package io.github.lix3nn53.guardiansofadelia.commands;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.guild.Guild;
import io.github.lix3nn53.guardiansofadelia.guild.GuildInvite;
import io.github.lix3nn53.guardiansofadelia.guild.GuildManager;
import io.github.lix3nn53.guardiansofadelia.guild.PlayerRankInGuild;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.TablistUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CommandGuild implements CommandExecutor {

    private final List<Player> guildDestroyWaitingForConfirm = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equals("guild")) {
            return false;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 1) {
                player.sendMessage(ChatPalette.PURPLE + "/guild create <guildName> <guildTag> - create a new guild");
                player.sendMessage(ChatPalette.PURPLE + "/guild top - 10 guild with highest war-point");
                player.sendMessage(ChatPalette.PURPLE + "Guild member commands");
                player.sendMessage(ChatPalette.PURPLE + "/guild leave - leave your current guild");
                player.sendMessage(ChatPalette.PURPLE + "/guild member - member list of your guild");
                player.sendMessage(ChatPalette.PURPLE + "/guild ann - view guild announcement");
                player.sendMessage(ChatPalette.PURPLE + "Guild commander commands");
                player.sendMessage(ChatPalette.PURPLE + "/guild invite <player> - invite a player to your guild");
                player.sendMessage(ChatPalette.PURPLE + "/guild kick <player> - kick a player from your guild");
                player.sendMessage(ChatPalette.PURPLE + "/guild ranklist - view rank list");
                player.sendMessage(ChatPalette.PURPLE + "/guild rank <player> <rankNo> - set a player's guild rank");
                player.sendMessage(ChatPalette.PURPLE + "/guild setann <sentence> - set guild's announcement");
                player.sendMessage(ChatPalette.PURPLE + "Guild leader commands");
                player.sendMessage(ChatPalette.PURPLE + "/guild newleader <player> - set new guild leader");
                player.sendMessage(ChatPalette.PURPLE + "/guild destroy - destroy the guild");
            } else if (args[0].equals("create")) {
                if (args.length == 3) {
                    String name = args[1];
                    String tag = args[2];

                    if (name.length() > 20) {
                        player.sendMessage(ChatPalette.RED + "Max guild name length is 20");
                        return false;
                    }

                    if (tag.length() > 5) {
                        player.sendMessage(ChatPalette.RED + "Max guild tag length is 5");
                        return false;
                    }

                    int price = 64 * 2;
                    if (EconomyUtils.pay(player, price)) {
                        Guild guild = new Guild(args[1], args[2]);
                        guild.addMember(player.getUniqueId());
                        guild.setLeader(player.getUniqueId());
                        player.sendMessage(ChatPalette.PURPLE + "Successfully created a new guild called " + ChatPalette.WHITE + guild.getName());
                        TablistUtils.updateTablist(player);
                    } else {
                        player.sendMessage(ChatPalette.RED + "Creating a new guild costs 2 silver");
                    }
                }
            } else if (args[0].equals("top")) {
                List<Guild> sortedGuilds = GuildManager.getGuildsSortedByWarPoints();
                int bound = Math.min(10, sortedGuilds.size());
                for (int i = 0; i < bound; i++) {
                    Guild guild = sortedGuilds.get(i);
                    player.sendMessage(ChatPalette.PURPLE + guild.getName() + " - " + guild.getWarPoints());
                }
            } else if (args[0].equals("leave")) {
                if (GuildManager.inGuild(player)) {
                    Guild guild = GuildManager.getGuild(player);
                    PlayerRankInGuild rank = guild.getRankInGuild(player.getUniqueId());
                    if (!rank.equals(PlayerRankInGuild.LEADER)) {
                        guild.removeMember(player.getUniqueId());
                        GuildManager.removePlayer(player);
                    } else {
                        player.sendMessage(ChatPalette.RED + "Guild leader can't leave his/her guild. Choose a new leader first.");
                    }
                }
            } else if (args[0].equals("member")) {
                if (GuildManager.inGuild(player)) {
                    Guild guild = GuildManager.getGuild(player);
                    HashMap<UUID, PlayerRankInGuild> members = guild.getMembersWithRanks();
                    player.sendMessage(ChatPalette.PURPLE + "GuildMember  -  Rank");
                    for (UUID member : members.keySet()) {
                        OfflinePlayer memberPlayer = Bukkit.getOfflinePlayer(member);
                        PlayerRankInGuild playerRankInGuild = members.get(member);
                        player.sendMessage(memberPlayer.getName() + "  -  " + playerRankInGuild.toString());
                    }
                }
            } else if (args[0].equals("ann")) {
                if (GuildManager.inGuild(player)) {
                    Guild guild = GuildManager.getGuild(player);
                    String announcement = guild.getAnnouncement();
                    player.sendMessage(ChatPalette.PURPLE + "Guild announcement: " + ChatPalette.WHITE + announcement);
                }
            } else if (args[0].equals("invite")) {
                if (args.length == 2) {
                    Player player2 = Bukkit.getPlayer(args[1]);
                    if (player2 != null && player != player2) {
                        if (player2.isOnline()) {
                            if (GuildManager.inGuild(player)) {
                                Guild guild = GuildManager.getGuild(player);
                                PlayerRankInGuild rank = guild.getRankInGuild(player.getUniqueId());
                                if (rank.equals(PlayerRankInGuild.LEADER) || rank.equals(PlayerRankInGuild.COMMANDER)) {
                                    String receiverMessage = ChatPalette.PURPLE + sender.getName() + " invites you to " + guild.getName() + " guild";
                                    String receiverTitle = ChatPalette.PURPLE + "Received guild invitation";
                                    String senderTitle = ChatPalette.PURPLE + "Sent guild invitation";
                                    GuildInvite invite = new GuildInvite(player, player2, senderTitle, receiverMessage, receiverTitle);
                                    invite.send();
                                } else {
                                    player.sendMessage(ChatPalette.RED + "You must be guild leader or commander to invite players to guild");
                                }
                            }
                        }
                    } else {
                        player.sendMessage(ChatPalette.RED + "You can't invite yourself!");
                    }
                }
            } else if (args[0].equals("kick")) {
                if (args.length == 2) {
                    if (!args[1].equalsIgnoreCase(player.getName())) {
                        Player player2 = Bukkit.getPlayer(args[1]);
                        if (player2 != null) {
                            if (GuildManager.inGuild(player)) {
                                Guild guild = GuildManager.getGuild(player);
                                if (guild.isMember(player2.getUniqueId())) {
                                    PlayerRankInGuild rank = guild.getRankInGuild(player.getUniqueId());
                                    if (rank.equals(PlayerRankInGuild.LEADER)) {
                                        guild.removeMember(player2.getUniqueId());
                                        player.sendMessage(ChatPalette.RED + "You kicked " + player2.getName() + " from guild " + guild.getName());
                                        if (player2.isOnline()) {
                                            player2.sendMessage(player.getName() + " kicked you from guild " + guild.getName());
                                            TablistUtils.updateTablist(player2);
                                        }
                                        for (UUID uuid : guild.getMembers()) {
                                            Player member = Bukkit.getPlayer(uuid);
                                            if (member != null) {
                                                TablistUtils.updateTablist(member);
                                            }
                                        }
                                    } else if (rank.equals(PlayerRankInGuild.COMMANDER)) {
                                        PlayerRankInGuild rank2 = guild.getRankInGuild(player.getUniqueId());
                                        if (!(rank2.equals(PlayerRankInGuild.LEADER) || rank2.equals(PlayerRankInGuild.COMMANDER))) {
                                            guild.removeMember(player2.getUniqueId());
                                            player.sendMessage(ChatPalette.RED + "You kicked " + player2.getName() + " from guild " + guild.getName());
                                            if (player2.isOnline()) {
                                                player2.sendMessage(player.getName() + " kicked you from guild " + guild.getName());
                                                TablistUtils.updateTablist(player2);
                                            }
                                            for (UUID uuid : guild.getMembers()) {
                                                Player member = Bukkit.getPlayer(uuid);
                                                if (member != null) {
                                                    TablistUtils.updateTablist(member);
                                                }
                                            }
                                        } else {
                                            player.sendMessage(ChatPalette.RED + "You can't kick the guild leader or another commander");
                                        }
                                    } else {
                                        player.sendMessage(ChatPalette.RED + "You must be guild leader or commander to kick guild members");
                                    }
                                } else {
                                    player.sendMessage(ChatPalette.RED + player2.getName() + " is not a member of your guild");
                                }
                            } else {
                                player.sendMessage(ChatPalette.RED + "You are not in a guild");
                            }
                        } else {
                            player.sendMessage(ChatPalette.RED + "Player not found");
                        }
                    } else {
                        player.sendMessage(ChatPalette.RED + "You can't kick yourself");
                    }
                }
            } else if (args[0].equals("ranklist")) {
                player.sendMessage(ChatPalette.PURPLE + "Ranks in guild: 1-Leader, 2-Commander, 3-Sergeant, 4-Corporal, 5-Soldier");
            } else if (args[0].equals("rank")) {
                if (args.length == 3) {
                    if (!args[1].equalsIgnoreCase(player.getName())) {
                        if (args[2].equals("1")) {
                            player.sendMessage(ChatPalette.RED + "You can't give guild leader rank to a player. Use /guild newleader instead");
                            return true;
                        }
                        Player player2 = Bukkit.getPlayer(args[1]);
                        if (player2 != null) {
                            if (GuildManager.inGuild(player)) {
                                Guild guild = GuildManager.getGuild(player);
                                if (guild.isMember(player2.getUniqueId())) {
                                    PlayerRankInGuild rank = guild.getRankInGuild(player.getUniqueId());
                                    if (rank.equals(PlayerRankInGuild.LEADER) || rank.equals(PlayerRankInGuild.COMMANDER)) {
                                        if (args[2].equals("2")) {
                                            guild.setRankInGuild(player2.getUniqueId(), PlayerRankInGuild.COMMANDER);
                                            player.sendMessage(ChatPalette.PURPLE + player2.getName() + "'s new guild rank is Commander");
                                            player2.sendMessage(ChatPalette.PURPLE + player.getName() + " changed your guild rank to Commander");
                                        } else if (args[2].equals("3")) {
                                            guild.setRankInGuild(player2.getUniqueId(), PlayerRankInGuild.SERGEANT);
                                            player.sendMessage(ChatPalette.PURPLE + player2.getName() + "'s new guild rank is Sergeant");
                                            player2.sendMessage(ChatPalette.PURPLE + player.getName() + " changed your guild rank to Sergeant");
                                        } else if (args[2].equals("4")) {
                                            guild.setRankInGuild(player2.getUniqueId(), PlayerRankInGuild.CORPORAL);
                                            player.sendMessage(ChatPalette.PURPLE + player2.getName() + "'s new guild rank is Corporal");
                                            player2.sendMessage(ChatPalette.PURPLE + player.getName() + " changed your guild rank to Corporal");
                                        } else if (args[2].equals("5")) {
                                            guild.setRankInGuild(player2.getUniqueId(), PlayerRankInGuild.SOLDIER);
                                            player.sendMessage(ChatPalette.PURPLE + player2.getName() + "'s new guild rank is Soldier");
                                            player2.sendMessage(ChatPalette.PURPLE + player.getName() + " changed your guild rank to Soldier");
                                        }
                                    } else {
                                        player.sendMessage(ChatPalette.RED + "You must be guild leader or commander to change guild ranks");
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (args[0].equals("setann")) {
                if (args.length >= 2) {
                    if (GuildManager.inGuild(player)) {
                        Guild guild = GuildManager.getGuild(player);
                        PlayerRankInGuild rank = guild.getRankInGuild(player.getUniqueId());
                        if (rank.equals(PlayerRankInGuild.LEADER) || rank.equals(PlayerRankInGuild.COMMANDER)) {
                            String announcement = "";
                            for (int i = 1; i < args.length; i++) {
                                announcement += args[i];
                            }
                            if (announcement.length() < 420) {
                                guild.setAnnouncement(announcement);
                                player.sendMessage(ChatPalette.PURPLE + "Set guild announcement to: " + ChatPalette.WHITE + announcement);
                            }
                        } else {
                            player.sendMessage(ChatPalette.RED + "You must be guild leader or commander to set announcement");
                        }
                    }
                }
            } else if (args[0].equals("newleader")) {
                if (args.length >= 2) {
                    if (!args[1].equalsIgnoreCase(player.getName())) {
                        Player player2 = Bukkit.getPlayer(args[1]);
                        if (player2 != null) {
                            if (GuildManager.inGuild(player)) {
                                Guild guild = GuildManager.getGuild(player);
                                if (guild.isMember(player2.getUniqueId())) {
                                    PlayerRankInGuild rank = guild.getRankInGuild(player.getUniqueId());
                                    if (rank.equals(PlayerRankInGuild.LEADER)) {
                                        guild.setLeader(player2.getUniqueId());
                                    } else {
                                        player.sendMessage(ChatPalette.RED + "You must be guild leader to choose a new guild leader");
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (args[0].equals("destroy")) {
                if (GuildManager.inGuild(player)) {
                    Guild guild = GuildManager.getGuild(player);
                    PlayerRankInGuild rank = guild.getRankInGuild(player.getUniqueId());
                    if (rank.equals(PlayerRankInGuild.LEADER)) {
                        player.sendMessage(ChatPalette.RED + "Are you sure? Do you want to destroy this guild permanently?");
                        player.sendMessage(ChatPalette.RED + "This will destroy the item in guild storage");
                        player.sendMessage(ChatPalette.RED + "Use command '/guild comfirmdestroy' to confirm in 30 seconds");
                        guildDestroyWaitingForConfirm.add(player);
                        new BukkitRunnable() {


                            @Override
                            public void run() {
                                guildDestroyWaitingForConfirm.remove(player);
                                cancel();
                            }
                        }.runTaskLater(GuardiansOfAdelia.getInstance(), 20L * 30L);
                    } else {
                        player.sendMessage(ChatPalette.RED + "You must be guild leader to destroy the guild");
                    }
                }
            } else if (args[0].equals("confirmdestroy")) {
                if (guildDestroyWaitingForConfirm.contains(player)) {
                    if (GuildManager.inGuild(player)) {
                        Guild guild = GuildManager.getGuild(player);
                        guild.destroy();
                        for (UUID uuid : guild.getMembers()) {
                            Player member = Bukkit.getPlayer(uuid);
                            if (member != null) {
                                TablistUtils.updateTablist(member);
                                member.sendMessage(ChatPalette.RED + "Guild leader (" + player.getName() + ") destroyed the guild: " + guild.getName());
                            }
                        }
                        player.sendMessage(ChatPalette.RED + "Destroyed the guild: " + ChatPalette.WHITE + guild.getName());
                    }
                    guildDestroyWaitingForConfirm.remove(player);
                }
            }
        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
