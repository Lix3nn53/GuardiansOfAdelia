package io.github.lix3nn53.guardiansofadelia.commands.admin;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandAdminQuest implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.getName().equals("adminquest")) {
            return false;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 1) {
                player.sendMessage(ChatColor.YELLOW + "/adminquest t - turn ins current quests tasks");
                player.sendMessage(ChatColor.YELLOW + "/adminquest a <num> - accept quest tasks");
                player.sendMessage(ChatColor.YELLOW + "/adminquest gui <npcNo> - open quest gui of an npc");
            } else if (args[0].equals("t")) {
                if (args.length != 1) return false;
                if (GuardianDataManager.hasGuardianData(player)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                    if (guardianData.hasActiveCharacter()) {
                        RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                        List<Quest> questList = activeCharacter.getQuestList();

                        for (Quest quest : questList) {
                            activeCharacter.getTurnedInQuests().add(quest.getQuestID());
                            quest.onTurnIn(player);
                        }

                        questList.clear();
                    }
                }
            } else if (args[0].equals("a")) {
                if (args.length != 2) return false;
                if (GuardianDataManager.hasGuardianData(player)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                    if (guardianData.hasActiveCharacter()) {
                        RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                        Quest questCopyById = QuestNPCManager.getQuestCopyById(Integer.parseInt(args[1]));

                        boolean questListIsNotFull = activeCharacter.acceptQuest(questCopyById, player);
                        if (!questListIsNotFull) {
                            player.sendMessage(net.md_5.bungee.api.ChatColor.RED + "Your quest list is full");
                        }
                    }
                }
            } else if (args[0].equals("gui")) {
                if (args.length != 2) return false;
                if (GuardianDataManager.hasGuardianData(player)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                    if (guardianData.hasActiveCharacter()) {
                        GuiGeneric questGui = QuestNPCManager.getQuestGui(player, Integer.parseInt(args[1]));
                        questGui.openInventory(player);
                    }
                }
            }

            // If the player (or console) uses our command correct, we can return true
            return true;
        }
        return false;
    }
}
