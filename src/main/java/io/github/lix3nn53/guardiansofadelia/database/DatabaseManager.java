package io.github.lix3nn53.guardiansofadelia.database;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterExperienceManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guild.Guild;
import io.github.lix3nn53.guardiansofadelia.guild.GuildManager;
import io.github.lix3nn53.guardiansofadelia.guild.PlayerRankInGuild;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.StaffRank;
import io.github.lix3nn53.guardiansofadelia.utilities.TablistUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.AdeliaRegionManager;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.CharacterSelectionScreenManager;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.LivingWatcher;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.*;

public class DatabaseManager {

    private static final DatabaseQueries databaseQueries = new DatabaseQueries();

    public static void onDisable() {
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
        for (Player player : onlinePlayers) {
            UUID uuid = player.getUniqueId();
            if (GuardianDataManager.hasGuardianData(uuid)) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                writeGuardianDataWithCurrentCharacter(player, guardianData);
                GuardiansOfAdelia.getInstance().getLogger().info("Player save on disable: " + player.getName());
            }
        }
        List<Guild> activeGuilds = GuildManager.getActiveGuilds();
        for (Guild guild : activeGuilds) {
            writeGuildData(guild);
            GuardiansOfAdelia.getInstance().getLogger().info("Guild save on disable: " + guild.getName());
        }
        databaseQueries.onDisable();
    }

    public static void createTables() {
        databaseQueries.createTables();
    }

    /* updates tabList of player */
    public static void loadCharacter(Player player, int charNo, Location location) {
        Bukkit.getScheduler().runTaskAsynchronously(GuardiansOfAdelia.getInstance(), () -> {
            UUID uuid = player.getUniqueId();
            try {
                RPGCharacter rpgCharacter = databaseQueries.getCharacterAndSetPlayerInventory(player, charNo);
                if (rpgCharacter != null) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                    guardianData.setActiveCharacter(rpgCharacter, charNo);
                    Bukkit.getScheduler().runTask(GuardiansOfAdelia.getInstance(), () -> {
                        player.teleport(location);
                    });
                    TablistUtils.updateTablist(player);
                    InventoryUtils.setMenuItemPlayer(player);
                    rpgCharacter.getSkillBar().remakeSkillBar();
                    rpgCharacter.getRpgCharacterStats().onMaxHealthChange();
                    player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /* updates tabList of player */
    public static void loadPlayerDataAndCharacterSelection(Player player) {
        player.sendMessage("Loading your player data..");
        UUID uuid = player.getUniqueId();
        Bukkit.getScheduler().runTaskAsynchronously(GuardiansOfAdelia.getInstance(), () -> {
            try {
                GuardianData guardianData = databaseQueries.getGuardianDataWithStaffRankAndStorages(uuid);

                List<Player> friendsOfPlayer = databaseQueries.getFriendsOfPlayer(uuid);
                guardianData.setFriends(friendsOfPlayer);

                GuardianDataManager.addGuardianData(uuid, guardianData);

                String guildNameOfPlayer = databaseQueries.getGuildNameOfPlayer(uuid);
                if (guildNameOfPlayer != null) {
                    Optional<Guild> guildOptional = GuildManager.getGuild(guildNameOfPlayer);
                    if (guildOptional.isPresent()) {
                        Guild guild = guildOptional.get();
                        GuildManager.addPlayerGuild(player, guild);
                        GuildManager.sendJoinMessageToMembers(player);
                    } else {
                        Guild guild = databaseQueries.getGuild(player, guildNameOfPlayer);
                        if (guild != null) {
                            HashMap<UUID, PlayerRankInGuild> guildMembers = databaseQueries.getGuildMembers(guildNameOfPlayer);
                            guild.setMembers(guildMembers);

                            GuildManager.addPlayerGuild(player, guild);
                        }
                    }
                }

                player.sendMessage("Loaded player data");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //character selection screen
            loadCharacterSelectionAndFormHolograms(player);
        });
    }

    //Not async, must run async
    private static void loadCharacterSelectionAndFormHolograms(Player player) {
        player.sendMessage("Preparing character selection..");
        for (int charNo = 1; charNo <= 4; charNo++) {
            boolean characterExists = databaseQueries.characterExists(player.getUniqueId(), charNo);
            if (characterExists) {
                UUID uuid = player.getUniqueId();

                //load last location of character
                try {
                    Location lastLocationOfCharacter = databaseQueries.getLastLocationOfCharacter(uuid, charNo);
                    if (lastLocationOfCharacter != null) {
                        CharacterSelectionScreenManager.setCharLocation(uuid, charNo, lastLocationOfCharacter);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                List<ArmorStand> armorStands = CharacterSelectionScreenManager.getCharacterNoToArmorStands().get(charNo);

                MobDisguise mobDisguise = new MobDisguise(DisguiseType.ARMOR_STAND, false);
                LivingWatcher livingWatcher = mobDisguise.getWatcher();
                livingWatcher.setInvisible(true);
                livingWatcher.setNoGravity(true);
                livingWatcher.setCustomNameVisible(true);

                try {
                    RPGClass rpgClassCharacter = databaseQueries.getRPGClassCharacter(uuid, charNo);
                    int totalExp = databaseQueries.getTotalExp(uuid, charNo);
                    int level = RPGCharacterExperienceManager.getLevelFromTotalExperience(totalExp);

                    Bukkit.getScheduler().runTask(GuardiansOfAdelia.getInstance(), () -> {
                        DisguiseAPI.disguiseToPlayers(armorStands.get(2), mobDisguise, player);
                        livingWatcher.setCustomName("Level: " + level);
                        DisguiseAPI.disguiseToPlayers(armorStands.get(1), mobDisguise, player);
                        livingWatcher.setCustomName("Total Experience: " + totalExp);
                        DisguiseAPI.disguiseToPlayers(armorStands.get(0), mobDisguise, player);
                        livingWatcher.setCustomName("Class: " + rpgClassCharacter.getClassString());
                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            player.sendMessage("Loaded character-" + charNo);
        }
        player.sendMessage("Prepared character selection");
    }

    public static void writeGuardianDataWithCurrentCharacter(Player player, GuardianData guardianData) {
        //return if it is not safe to save this character now
        if (AdeliaRegionManager.isCharacterSelectionRegion(player.getLocation())) {
            //if player is in character selection it is not safe to save
            return;
        }
        if (player.getLocation().getWorld().getName().equals("begining")) { //tutorial
            return;
        }

        UUID uuid = player.getUniqueId();

        //player
        StaffRank staffRank = guardianData.getStaffRank();
        ItemStack[] personalStorage = guardianData.getPersonalStorage();
        ItemStack[] bazaarStorage = guardianData.getBazaarStorage();
        try {
            databaseQueries.setPlayerStaffRankAndStorages(uuid, staffRank, personalStorage, bazaarStorage);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Player> friends = guardianData.getFriends();
        databaseQueries.setFriendsOfPlayer(uuid, friends);

        //character
        if (guardianData.hasActiveCharacter()) {
            int activeCharacterNo = guardianData.getActiveCharacterNo();
            RPGCharacter activeCharacter = guardianData.getActiveCharacter();
            ItemStack offHand = null;
            if (!activeCharacter.getRpgInventory().getOffhandSlot().isEmpty(player)) {
                offHand = activeCharacter.getRpgInventory().getOffhandSlot().getItemOnSlot(player);
            }
            try {
                databaseQueries.setCharacter(player.getUniqueId(), activeCharacterNo, activeCharacter, player.getInventory().getContents(),
                        player.getLocation(), player.getInventory().getArmorContents(), offHand);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeGuildData(Guild guild) {
        try {
            databaseQueries.setGuild(guild);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        databaseQueries.setMembersOfGuild(guild.getName(), guild.getMembersWithRanks());
    }

    public static void clearGuild(String guildName) {
        try {
            databaseQueries.clearMembersOfGuild(guildName);
            databaseQueries.clearGuild(guildName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clearCharacter(UUID uuid, int charNo) {
        try {
            databaseQueries.clearCharacter(uuid, charNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clearPlayer(UUID uuid) {
        try {
            databaseQueries.clearFriendsOfPlayer(uuid);
            databaseQueries.clearPlayerStaffRankAndStorages(uuid);
            databaseQueries.clearGuildOfPlayer(uuid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeGuildOfPlayer(UUID uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(GuardiansOfAdelia.getInstance(), () -> {
            try {
                databaseQueries.clearGuildOfPlayer(uuid);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
