package io.github.lix3nn53.guardiansofadelia.commands;

import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringManager;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringModelData;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringModelState;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CommandJob implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.getName().equals("job")) {
            return false;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 1) {
                player.sendMessage(ChatColor.YELLOW + "/job addmodel <model-id>");
            } else if (args[0].equals("addmodel")) {
                String idStr = args[1];
                int id = Integer.parseInt(idStr);

                Block targetBlock = player.getTargetBlock(null, 12);

                Location add = targetBlock.getLocation().add(0.5, 1, 0.5);

                GatheringModelState gatheringModelState = new GatheringModelState(id, add);
                GatheringManager.putGatheringModelState(gatheringModelState);

                HashMap<Integer, GatheringModelData> modelIdToModelData = GatheringManager.getModelIdToModelData();
                GatheringModelData gatheringModelData = modelIdToModelData.get(id);
                gatheringModelState.createModel(gatheringModelData);

                player.sendMessage("Added new gathering model!");
            }

            // If the player (or console) uses our command correct, we can return true
            return true;
        }
        return false;
    }
}
