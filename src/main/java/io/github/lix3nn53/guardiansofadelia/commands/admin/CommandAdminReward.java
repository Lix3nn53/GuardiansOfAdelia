package io.github.lix3nn53.guardiansofadelia.commands.admin;

import io.github.lix3nn53.guardiansofadelia.menu.admin.GuiAdminDailyRewards;
import io.github.lix3nn53.guardiansofadelia.rewards.chest.LootChest;
import io.github.lix3nn53.guardiansofadelia.rewards.chest.LootChestManager;
import io.github.lix3nn53.guardiansofadelia.rewards.chest.LootChestTier;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAdminReward implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.getName().equals("adminreward")) {
            return false;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 1) {
                player.sendMessage(ChatPalette.YELLOW + "/adminreward setdaily");
                player.sendMessage(ChatPalette.YELLOW + "/adminreward addlootchest [0-3 = tier]");
            } else if (args[0].equals("setdaily")) {
                GuiAdminDailyRewards gui = new GuiAdminDailyRewards();
                gui.openInventory(player);
            } else if (args[0].equals("addlootchest")) {
                Block targetBlock = player.getTargetBlock(null, 12);

                Material type = targetBlock.getType();

                if (!type.equals(Material.CHEST)) {
                    player.sendMessage(ChatPalette.RED + "You must be looking to a chest");
                    return false;
                }

                int tierIndex = Integer.parseInt(args[1]);

                LootChestTier value = LootChestTier.values()[tierIndex];

                LootChest lootChest = new LootChest(targetBlock.getLocation(), value);

                LootChestManager.addLootChest(lootChest);

                player.sendMessage(ChatPalette.GREEN_DARK + "Added loot chest, tier: " + value.toString());
                lootChest.startPlayingParticles();
            }

            // If the player (or console) uses our command correct, we can return true
            return true;
        }
        return false;
    }
}
