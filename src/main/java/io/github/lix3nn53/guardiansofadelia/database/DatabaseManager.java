package io.github.lix3nn53.guardiansofadelia.database;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guild.Guild;
import io.github.lix3nn53.guardiansofadelia.guild.GuildManager;
import io.github.lix3nn53.guardiansofadelia.guild.PlayerRankInGuild;
import io.github.lix3nn53.guardiansofadelia.utilities.SkillAPIUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.StaffRank;
import io.github.lix3nn53.guardiansofadelia.utilities.TablistUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.AdeliaRegionManager;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.LivingWatcher;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DatabaseManager {

    private final DatabaseQueries databaseQueries = new DatabaseQueries();

    public void onDisable() {
        databaseQueries.onDisable();
    }

    public void createTables() {
        databaseQueries.createTables();
    }

    /* updates tabList of player */
    public void loadCharacter(Player player, int charNo, Location location) {
        Bukkit.getScheduler().runTaskAsynchronously(GuardiansOfAdelia.getInstance(), () -> {
            UUID uuid = player.getUniqueId();
            try {
                RPGCharacter rpgCharacter = databaseQueries.getCharacterAndSetPlayerInventory(player, charNo);
                if (rpgCharacter != null) {
                    GuardianData guardianData = GuardiansOfAdelia.getGuardianDataManager().getGuardianData(uuid);
                    guardianData.setActiveCharacter(rpgCharacter, charNo);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            player.teleport(location);
            TablistUtils.updateTablist(player);
        });
    }

    /* updates tabList of player */
    public void loadPlayerDataAndCharacterSelection(Player player) {
        player.sendMessage("Loading your player data..");
        UUID uuid = player.getUniqueId();
        Bukkit.getScheduler().runTaskAsynchronously(GuardiansOfAdelia.getInstance(), () -> {
            try {
                GuardianData guardianData = databaseQueries.getGuardianDataWithStaffRankAndStorages(uuid);

                List<Player> friendsOfPlayer = databaseQueries.getFriendsOfPlayer(uuid);
                guardianData.setFriends(friendsOfPlayer);

                GuardiansOfAdelia.getGuardianDataManager().addGuardianData(uuid, guardianData);

                String guildNameOfPlayer = databaseQueries.getGuildNameOfPlayer(uuid);
                if (guildNameOfPlayer != null) {
                    GuildManager guildManager = GuardiansOfAdelia.getGuildManager();
                    if (!guildManager.isGuildLoaded(guildNameOfPlayer)) {
                        Guild guild = databaseQueries.getGuild(guildNameOfPlayer);
                        if (guild != null) {
                            HashMap<UUID, PlayerRankInGuild> guildMembers = databaseQueries.getGuildMembers(guildNameOfPlayer);
                            guild.setMembers(guildMembers);

                            guildManager.addGuildToMemory(guild);
                        }
                    }
                }

                player.sendMessage("Loaded player data");
                TablistUtils.updateTablist(player);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //character selection screen
            loadCharacterSelectionAndFormHolograms(player);
        });
    }

    //Not async, must run async
    private void loadCharacterSelectionAndFormHolograms(Player player) {
        player.sendMessage("Preparing character selection..");
        for (int charNo = 1; charNo <= 4; charNo++) {
            boolean characterExists = databaseQueries.characterExists(player.getUniqueId(), charNo);
            if (characterExists) {
                boolean hasValidData = SkillAPIUtils.hasValidData(player, charNo);
                if (hasValidData) {

                    //load last location of character
                    UUID uuid = player.getUniqueId();
                    HashMap<UUID, HashMap<Integer, Location>> charLocationsForSelection = GuardiansOfAdelia.getCharLocationsForSelection();

                    HashMap<Integer, Location> integerLocationHashMap = new HashMap<>();
                    if (charLocationsForSelection.containsKey(uuid)) {
                        integerLocationHashMap = charLocationsForSelection.get(uuid);
                    }
                    try {
                        Location lastLocationOfCharacter = databaseQueries.getLastLocationOfCharacter(uuid, charNo);
                        if (lastLocationOfCharacter != null) {
                            integerLocationHashMap.put(charNo, lastLocationOfCharacter);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    List<ArmorStand> armorStands = GuardiansOfAdelia.getCharacterSelectionScreenManager().getCharacterNoToArmorStands().get(charNo);

                    MobDisguise mobDisguise = new MobDisguise(DisguiseType.ARMOR_STAND, false);
                    LivingWatcher livingWatcher = mobDisguise.getWatcher();
                    livingWatcher.setInvisible(true);
                    livingWatcher.setNoGravity(true);
                    livingWatcher.setCustomNameVisible(true);

                    String className = SkillAPIUtils.getClassName(player, charNo);
                    int level = SkillAPIUtils.getLevel(player, charNo);
                    int health = SkillAPIUtils.getHealth(player, charNo);
                    int mana = SkillAPIUtils.getMana(player, charNo);
                    int exp = SkillAPIUtils.getExp(player, charNo);
                    int expReq = SkillAPIUtils.getRequiredExp(player, charNo);
                    int totalExp = SkillAPIUtils.getTotalExp(player, charNo);

                    Bukkit.getScheduler().runTask(GuardiansOfAdelia.getInstance(), () -> {
                        livingWatcher.setCustomName("Class: " + className);
                        DisguiseAPI.disguiseToPlayers(armorStands.get(5), mobDisguise, player);
                        livingWatcher.setCustomName("Level: " + level);
                        DisguiseAPI.disguiseToPlayers(armorStands.get(4), mobDisguise, player);
                        livingWatcher.setCustomName("Health: " + health);
                        DisguiseAPI.disguiseToPlayers(armorStands.get(3), mobDisguise, player);
                        livingWatcher.setCustomName("Mana: " + mana);
                        DisguiseAPI.disguiseToPlayers(armorStands.get(2), mobDisguise, player);
                        livingWatcher.setCustomName("Experience: " + exp + "/" + expReq);
                        DisguiseAPI.disguiseToPlayers(armorStands.get(1), mobDisguise, player);
                        livingWatcher.setCustomName("Total Experience: " + totalExp);
                        DisguiseAPI.disguiseToPlayers(armorStands.get(0), mobDisguise, player);
                    });
                }
            }
            player.sendMessage("Loaded character-" + charNo);
        }
        player.sendMessage("Prepared character selection");
    }

    public void writeGuardianDataWithCurrentCharacter(Player player, GuardianData guardianData) {
        //return if it is not safe to save this character now
        AdeliaRegionManager adeliaRegionManager = GuardiansOfAdelia.getAdeliaRegionManager();
        if (adeliaRegionManager.isCharacterSelectionRegion(player.getLocation())) {
            //if player is in character selection it is not safe to save
            return;
        }
        if (!SkillAPIUtils.isSafeToSave(player)) {
            return;
        }

        UUID uuid = player.getUniqueId();

        //player
        StaffRank staffRank = guardianData.getStaffRank();
        ItemStack[] personalStorage = guardianData.getPersonalStorage();
        ItemStack[] bazaarStorage = guardianData.getBazaarStorage();
        try {
            this.databaseQueries.setPlayerStaffRankAndStorages(uuid, staffRank, personalStorage, bazaarStorage);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Player> friends = guardianData.getFriends();
        this.databaseQueries.setFriendsOfPlayer(uuid, friends);

        //character
        int activeCharacterNo = guardianData.getActiveCharacterNo();
        RPGCharacter activeCharacter = guardianData.getActiveCharacter();
        ItemStack offHand = null;
        if (!activeCharacter.getRpgInventory().getOffhandSlot().isEmpty(player)) {
            offHand = activeCharacter.getRpgInventory().getOffhandSlot().getItemOnSlot(player);
        }
        try {
            this.databaseQueries.setCharacter(player.getUniqueId(), activeCharacterNo, activeCharacter, player.getInventory().getContents(),
                    player.getLocation(), player.getInventory().getArmorContents(), offHand);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void writeGuildData(Guild guild) {
        try {
            this.databaseQueries.setGuild(guild);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.databaseQueries.setMembersOfGuild(guild.getName(), guild.getMembersWithRanks());
    }

    public void clearGuild(String guildName) {
        try {
            this.databaseQueries.clearGuild(guildName);
            this.databaseQueries.clearMembersOfGuild(guildName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearCharacter(UUID uuid, int charNo) {
        try {
            this.databaseQueries.clearCharacter(uuid, charNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearPlayer(UUID uuid) {
        try {
            this.databaseQueries.clearFriendsOfPlayer(uuid);
            this.databaseQueries.clearPlayerStaffRankAndStorages(uuid);
            this.databaseQueries.clearGuildOfPlayer(uuid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
