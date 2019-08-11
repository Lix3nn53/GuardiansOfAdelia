package io.github.lix3nn53.guardiansofadelia.commands;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.database.DatabaseManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class CommandCharacter implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.getName().equals("character")) {
            return false;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 1) {
                player.sendMessage(ChatColor.YELLOW + "/character remove <character_no>");
            } else if (args[0].equals("remove")) {
                if (args.length == 2) {
                    try {
                        int charNo = Integer.parseInt(args[1]);
                        if (charNo < 1) {
                            player.sendMessage(ChatColor.RED + "Character no can't be smaller than 1");
                            return false;
                        }

                        UUID uuid = player.getUniqueId();

                        new BukkitRunnable() {

                            // We don't want the task to run indefinitely
                            int secsRun;

                            @Override
                            public void run() {
                                secsRun++;

                                if (secsRun == 1) {
                                    player.sendMessage(ChatColor.RED + "Removing your character in 5 seconds..");
                                } else if (secsRun == 2) {
                                    player.sendMessage(ChatColor.RED + "Removing your character in 4 seconds..");
                                } else if (secsRun == 3) {
                                    player.sendMessage(ChatColor.RED + "Removing your character in 3 seconds..");
                                } else if (secsRun == 4) {
                                    player.sendMessage(ChatColor.RED + "Removing your character in 2 seconds..");
                                } else if (secsRun == 5) {
                                    player.sendMessage(ChatColor.RED + "Removing your character in 1 seconds..");
                                } else if (secsRun == 6) { // 100 ticks = 5 seconds
                                    GuardianDataManager.clearCurrentCharacterDataWithoutSaving(uuid);

                                    new BukkitRunnable() {

                                        @Override
                                        public void run() {
                                            DatabaseManager.clearCharacter(uuid, charNo);
                                        }
                                    }.runTaskAsynchronously(GuardiansOfAdelia.getInstance());

                                    player.kickPlayer(ChatColor.RED + "Removed your character on " + ChatColor.GOLD + "slot " + charNo);
                                }
                            }
                        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 0L, 20L);
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + "Enter a number for character no");
                        return false;
                    }
                }
            }
            // If the player (or console) uses our command correct, we can return true
            return true;
        }
        return false;
    }
}
