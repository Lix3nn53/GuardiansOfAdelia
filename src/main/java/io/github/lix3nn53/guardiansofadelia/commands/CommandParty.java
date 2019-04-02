package io.github.lix3nn53.guardiansofadelia.commands;

import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.party.PartyInvite;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandParty implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.getName().equals("party")) {
            return false;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 1) {
                player.sendMessage(ChatColor.YELLOW + "/party invite <player>");
                player.sendMessage(ChatColor.YELLOW + "/party leave");
                player.sendMessage(ChatColor.YELLOW + "/party kick <player>");
                player.sendMessage(ChatColor.YELLOW + "/party leader <player>");
            } else if (args[0].equalsIgnoreCase("invite")) {
                if (args.length == 2) {
                    if (!args[1].equalsIgnoreCase(player.getName())) {
                        Player receiver = Bukkit.getPlayer(args[1]);
                        if (receiver != null) {
                            String senderTitle = ChatColor.AQUA + "Sent party invitation";
                            String receiverMessage = ChatColor.AQUA + sender.getName() + " invites you to party";
                            String receiverTitle = ChatColor.AQUA + "Received party invitation";
                            PartyInvite partyInvite = new PartyInvite(player, receiver, senderTitle, receiverMessage, receiverTitle);
                            partyInvite.send();
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("leave")) {
                if (PartyManager.inParty(player)) {
                    Party party = PartyManager.getParty(player);
                    party.leave(player);
                }
            } else if (args[0].equalsIgnoreCase("kick")) {
                if (args.length == 2) {
                    if (!args[1].equalsIgnoreCase(player.getName())) {
                        Player receiver = Bukkit.getPlayer(args[1]);
                        if (receiver != null) {
                            if (PartyManager.inParty(player)) {
                                Party party = PartyManager.getParty(player);
                                party.kickMember(player, receiver);
                            }
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("leader")) {
                if (args.length == 2) {
                    if (!args[1].equalsIgnoreCase(player.getName())) {
                        Player receiver = Bukkit.getPlayer(args[1]);
                        if (receiver != null) {
                            if (PartyManager.inParty(player)) {
                                Party party = PartyManager.getParty(player);
                                party.setNewLeader(player, receiver);
                            }
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
