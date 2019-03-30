package io.github.lix3nn53.guardiansofadelia.commands;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.utilities.invite.Invite;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandInvite implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.getName().equals("invite")) {
            return false;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();
            if (args.length < 1) {
                player.sendMessage(ChatColor.YELLOW + "/trade accept");
                player.sendMessage(ChatColor.YELLOW + "/trade reject");
            } else if (args[0].equals("accept")) {
                if (GuardianDataManager.hasGuardianData(uuid)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                    if (guardianData.hasPendingInvite()) {
                        Invite pendingInvite = guardianData.getPendingInvite();
                        pendingInvite.accept();
                    }
                }
            } else if (args[0].equals("reject")) {
                if (GuardianDataManager.hasGuardianData(uuid)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                    if (guardianData.hasPendingInvite()) {
                        Invite pendingInvite = guardianData.getPendingInvite();
                        pendingInvite.reject();
                    }
                }
            }
            // If the player (or console) uses our command correct, we can return true
            return true;
        }
        return false;
    }
}
