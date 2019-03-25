package io.github.lix3nn53.guardiansofadelia.commands;

import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandLix implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.equals("lix")) {
            return false;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 1) {
                player.sendMessage(ChatColor.DARK_PURPLE + "/lix tp [town|?] <num>");
                player.sendMessage(ChatColor.DARK_PURPLE + "/guild top - 10 guild with highest war-point");
                player.sendMessage(ChatColor.DARK_PURPLE + "Guild member commands");
                player.sendMessage(ChatColor.DARK_PURPLE + "/guild leave - leave your current guild");
                player.sendMessage(ChatColor.DARK_PURPLE + "/guild member - member list of your guild");
                player.sendMessage(ChatColor.DARK_PURPLE + "/guild ann - view guild announcement");
                player.sendMessage(ChatColor.DARK_PURPLE + "Guild commander commands");
                player.sendMessage(ChatColor.DARK_PURPLE + "/guild invite <player> - invite a player to your guild");
                player.sendMessage(ChatColor.DARK_PURPLE + "/guild kick <player> - kick a player from your guild");
                player.sendMessage(ChatColor.DARK_PURPLE + "/guild ranklist - view rank list");
                player.sendMessage(ChatColor.DARK_PURPLE + "/guild rank <player> <rankNo> - set a player's guild rank");
                player.sendMessage(ChatColor.DARK_PURPLE + "/guild setann <sentence> - set guild's announcement");
                player.sendMessage(ChatColor.DARK_PURPLE + "Guild leader commands");
                player.sendMessage(ChatColor.DARK_PURPLE + "/guild newleader <player> - set new guild leader");
                player.sendMessage(ChatColor.DARK_PURPLE + "/guild destroy - destroy the guild");
            } else if (args[0].equals("tp")) {
                if (args.length == 3) {
                    if (args[1].equals("town")) {
                        int no = Integer.parseInt(args[2]);
                        if (no < 6 && no > 0) {
                            Town town = TownManager.getTown(no);
                            player.teleport(town.getLocation());
                        }
                    }
                }
            }

            // If the player (or console) uses our command correct, we can return true
            return true;
        }
        return false;
    }
}
