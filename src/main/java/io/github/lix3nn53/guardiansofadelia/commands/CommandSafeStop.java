package io.github.lix3nn53.guardiansofadelia.commands;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.utilities.shutdown.ShutdownTask;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandSafeStop implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!"safe-stop".equals(command.getName())) {
            return true;
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(GuardiansOfAdelia.getInstance(), new ShutdownTask());

        return true;
    }
}
