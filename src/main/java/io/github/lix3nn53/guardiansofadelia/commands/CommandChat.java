package io.github.lix3nn53.guardiansofadelia.commands;

import io.github.lix3nn53.guardiansofadelia.chat.PremiumRank;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.menu.GuiChatItem;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandChat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.getName().equals("chat")) {
            return false;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 1) {
                player.sendMessage(ChatPalette.YELLOW + "/chat item - show item in chat");
            } else if (args[0].equals("item")) {
                if (!GuardianDataManager.hasGuardianData(player)) return false;

                GuardianData guardianData = GuardianDataManager.getGuardianData(player);

                PremiumRank premiumRank = guardianData.getPremiumRank();

                GuiChatItem gui = new GuiChatItem(player, guardianData);

                gui.openInventory(player);
            }
            // If the player (or console) uses our command correct, we can return true
            return true;
        }
        return false;
    }
}
