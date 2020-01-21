package io.github.lix3nn53.guardiansofadelia.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDestroyItem implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.getName().equals("destroyitem")) {
            return false;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;

            CommandDestroyItemManager.destroy(player);
            // If the player (or console) uses our command correct, we can return true
            return true;
        }
        return false;
    }
}
