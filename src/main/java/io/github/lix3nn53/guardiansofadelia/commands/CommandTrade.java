package io.github.lix3nn53.guardiansofadelia.commands;

import io.github.lix3nn53.guardiansofadelia.economy.trading.TradeInvite;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTrade implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.getName().equals("trade")) {
            return false;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 1) {
                player.sendMessage(ChatColor.YELLOW + "/trade <player>");
            } else if (args.length == 1) {
                if (!args[0].equalsIgnoreCase(player.getName())) {
                    Player receiver = Bukkit.getPlayer(args[0]);
                    if (receiver != null) {
                        String senderTitle = ChatColor.GOLD + "Sent trade invitation";
                        String receiverMessage = ChatColor.GOLD + sender.getName() + " invites you to trade";
                        String receiverTitle = ChatColor.GOLD + "Received trade invitation";
                        TradeInvite tradeInvite = new TradeInvite(player, receiver, senderTitle, receiverMessage, receiverTitle);
                        tradeInvite.send();
                    }
                }
            }
            // If the player (or console) uses our command correct, we can return true
            return true;
        }
        return false;
    }
}
