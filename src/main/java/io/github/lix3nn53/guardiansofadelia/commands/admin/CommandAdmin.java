package io.github.lix3nn53.guardiansofadelia.commands.admin;

import io.github.lix3nn53.guardiansofadelia.chat.StaffRank;
import io.github.lix3nn53.guardiansofadelia.events.MyBlockEvents;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClassManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillBar;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.config.ClassConfigurations;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class CommandAdmin implements CommandExecutor {

    public static boolean DEBUG_MODE = false;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.getName().equals("admin")) {
            return false;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 1) {
                player.sendMessage(ChatPalette.PURPLE + "---- ADMIN ----");
                player.sendMessage(ChatPalette.PURPLE + "/admin debug");
                player.sendMessage(ChatPalette.PURPLE + "/admin reload <skills|?>");
                player.sendMessage(ChatPalette.PURPLE + "/admin setstaff <player> [NONE|OWNER|ADMIN|DEVELOPER|BUILDER|SUPPORT|YOUTUBER|TRAINEE]");
                player.sendMessage(ChatPalette.PURPLE + "/admin build");
                player.sendMessage(ChatPalette.PURPLE_LIGHT + "---- UTILS ----");
                player.sendMessage(ChatPalette.PURPLE_LIGHT + "/admin fly");
                player.sendMessage(ChatPalette.PURPLE_LIGHT + "/admin speed <num>");
                player.sendMessage(ChatPalette.PURPLE_LIGHT + "/admin tp town <num>");
                player.sendMessage(ChatPalette.BLUE_DARK + "---- RPG ----");
                player.sendMessage(ChatPalette.BLUE_DARK + "/admin exp <player> <amount>");
                player.sendMessage(ChatPalette.BLUE_DARK + "/admin class unlock <player> <newClass>");
            } else if (args[0].equals("debug")) {
                DEBUG_MODE = !DEBUG_MODE;
            } else if (args[0].equals("speed")) {
                int val = Integer.parseInt(args[1]);
                if (val > 10 || val < -1) {
                    player.sendMessage(ChatPalette.RED + "Speed must be between 1-10");
                    return false;
                }
                float valf = val / 10f;
                player.setFlySpeed(valf);
            } else if (args[0].equals("exp")) {
                if (args.length == 3) {
                    int expToGive = Integer.parseInt(args[2]);
                    Player player2 = Bukkit.getPlayer(args[1]);
                    if (player2 != null) {
                        if (GuardianDataManager.hasGuardianData(player)) {
                            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                            if (guardianData.hasActiveCharacter()) {
                                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                                activeCharacter.getRpgCharacterStats().giveExp(expToGive);
                            }
                        }
                    }
                }
            } else if (args[0].equals("class")) {
                if (args[1].equals("unlock")) {
                    if (args.length == 4) {
                        Player player2 = Bukkit.getPlayer(args[2]);
                        String newClass = args[3];
                        if (player2 != null) {
                            if (GuardianDataManager.hasGuardianData(player)) {
                                GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                                if (guardianData.hasActiveCharacter()) {
                                    RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                                    activeCharacter.unlockClass(newClass);
                                    player2.sendMessage(ChatPalette.GREEN_DARK + "Unlocked class: " + newClass);
                                }
                            }
                        }
                    }
                }
            } else if (args[0].equals("setstaff")) {
                if (args.length == 3) {
                    try {
                        StaffRank staffRank = StaffRank.valueOf(args[2]);
                        Player player2 = Bukkit.getPlayer(args[1]);
                        if (player2 != null) {
                            if (GuardianDataManager.hasGuardianData(player)) {
                                GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                                guardianData.setStaffRank(staffRank);
                            }
                        }
                    } catch (IllegalArgumentException illegalArgumentException) {
                        player.sendMessage(ChatPalette.RED + "Unknown staff-rank");
                    }
                }
            } else if (args[0].equals("tp")) {
                if (args.length == 3) {
                    if (args[1].equals("town")) {
                        int no = Integer.parseInt(args[2]);
                        if (no < 6 && no > 0) {
                            Town town = TownManager.getTown(no);
                            player.teleport(town.getLocation());
                        }
                    }
                }
            } else if (args[0].equals("fly")) {
                boolean allowFlight = player.getAllowFlight();
                player.setFlying(!allowFlight);
            } else if (args[0].equals("reload")) {
                if (args[1].equals("skills")) {
                    ClassConfigurations.createConfigs();
                    ClassConfigurations.loadConfigs();
                    player.sendMessage(ChatPalette.GREEN_DARK + "Reloaded class configs!");
                    Set<Player> onlinePlayers = GuardianDataManager.getOnlinePlayers();
                    for (Player onlinePlayer : onlinePlayers) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(onlinePlayer);
                        RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                        if (activeCharacter == null) continue;

                        String rpgClassStr = activeCharacter.getRpgClassStr();
                        RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);

                        SkillBar skillBar = activeCharacter.getSkillBar();
                        skillBar.reloadSkillSet(rpgClass.getSkillSet());
                    }
                    player.sendMessage(ChatPalette.GREEN_DARK + "Reloaded player skills!");
                }
            } else if (args[0].equals("build")) {
                if (MyBlockEvents.allow.contains(player)) {
                    MyBlockEvents.allow.remove(player);
                    player.sendMessage(ChatPalette.RED + "You can not build anymore");
                } else {
                    MyBlockEvents.allow.add(player);
                    player.sendMessage(ChatPalette.GREEN_DARK + "You are allowed to build");
                }
            }

            // If the player (or console) uses our command correct, we can return true
            return true;
        }
        return false;
    }
}
