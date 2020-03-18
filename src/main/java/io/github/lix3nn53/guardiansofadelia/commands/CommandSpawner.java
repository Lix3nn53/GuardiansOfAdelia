package io.github.lix3nn53.guardiansofadelia.commands;

import io.github.lix3nn53.guardiansofadelia.creatures.AdeliaEntity;
import io.github.lix3nn53.guardiansofadelia.creatures.AdeliaEntityManager;
import io.github.lix3nn53.guardiansofadelia.creatures.spawners.Spawner;
import io.github.lix3nn53.guardiansofadelia.creatures.spawners.SpawnerManager;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CommandSpawner implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.getName().equals("spawner")) {
            return false;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 1) {
                player.sendMessage(ChatColor.YELLOW + "/spawner add <adeliaEntity> [amountPerSpawn] [maxAmount]");
                player.sendMessage(ChatColor.YELLOW + "/spawner spawn <adeliaEntity>");
            } else if (args[0].equals("add")) {
                if (args.length == 4) {
                    try {
                        String adeliaEntityString = args[1];
                        AdeliaEntity adeliaEntity = AdeliaEntityManager.getEntity(adeliaEntityString);

                        Block block = player.getTargetBlock(null, 5);

                        if (block.getType().equals(Material.AIR)) {
                            player.sendMessage(ChatColor.RED + "You are not looking at a valid block!");
                            return false;
                        }

                        Location location = block.getLocation().add(0D, 1D, 0D);
                        player.sendMessage(ChatColor.YELLOW + "LOCATION: " + location);

                        int amountPerSpawn = Integer.parseInt(args[2]);
                        int maxAmount = Integer.parseInt(args[3]);
                        player.sendMessage(ChatColor.GREEN + "MOB: " + adeliaEntity + "   AMOUNT-PER-SPAWN: " + amountPerSpawn + "   MAX-AMOUNT: " + maxAmount);

                        Spawner spawner = new Spawner(location, adeliaEntity, amountPerSpawn, maxAmount);
                        SpawnerManager.addSpawner(spawner);

                        Chunk chunk = location.getChunk();
                        SpawnerManager.activateSpawnersOnChunk(chunk);
                    } catch (Exception e) {
                        player.sendMessage(ChatColor.RED + "No such adeliaEntity");
                        return false;
                    }
                }
            } else if (args[0].equals("spawn")) {
                if (args.length == 2) {
                    try {
                        String adeliaEntityString = args[1];
                        AdeliaEntity adeliaEntity = AdeliaEntityManager.getEntity(adeliaEntityString);

                        Entity mob = adeliaEntity.getMob(player.getLocation());
                    } catch (Exception e) {
                        player.sendMessage(ChatColor.RED + "No such adeliaEntity");
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
