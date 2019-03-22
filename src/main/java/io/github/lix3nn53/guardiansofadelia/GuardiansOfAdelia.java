package io.github.lix3nn53.guardiansofadelia;

import io.github.lix3nn53.guardiansofadelia.commands.CommandGuild;
import io.github.lix3nn53.guardiansofadelia.creatures.drops.DropManager;
import io.github.lix3nn53.guardiansofadelia.creatures.spawners.SpawnerManager;
import io.github.lix3nn53.guardiansofadelia.database.DatabaseManager;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.BazaarManager;
import io.github.lix3nn53.guardiansofadelia.events.*;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guild.Guild;
import io.github.lix3nn53.guardiansofadelia.guild.GuildManager;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.quests.list.TutorialQuests;
import io.github.lix3nn53.guardiansofadelia.revive.TombManager;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.MyPacketListeners;
import io.github.lix3nn53.guardiansofadelia.utilities.PacketLimitter;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.AdeliaRegionManager;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.CharacterSelectionScreenManager;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.ConfigManager;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.TutorialManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GuardiansOfAdelia extends JavaPlugin {

    private static final HashMap<UUID, HashMap<Integer, Location>> charLocationsForSelection = new HashMap<>();

    private static PacketLimitter PACKET_LIMITTER;
    private static ConfigManager CONFIG_MANAGER;
    private static AdeliaRegionManager ADELIA_REGION_MANAGER;
    private static TownManager ADELIA_TOWN_MANAGER;
    private static CharacterSelectionScreenManager CHARACTER_SELECTION_SCREEN_MANAGER;
    private static SpawnerManager SPAWNER_MANAGER;
    private static DatabaseManager DATABASE_MANAGER;
    private static GuardianDataManager GUARDIAN_DATA_MANAGER;
    private static GuildManager GUILD_MANAGER;
    private static PetManager PET_MANAGER;
    private static TombManager TOMB_MANAGER;
    private static BazaarManager BAZAAR_MANAGER;
    private static QuestNPCManager QUEST_NPC_MANAGER;
    private static TutorialManager TUTORIAL_MANAGER;
    private static DropManager DROP_MANAGER;

    private static GuardiansOfAdelia instance;

    public static GuardiansOfAdelia getInstance() {
        return instance;
    }

    public static void packetLimitterOnQuit(Player player) {
        PACKET_LIMITTER.onQuit(player);
    }

    public static TownManager getAdeliaTownManager() {
        return ADELIA_TOWN_MANAGER;
    }

    public static DatabaseManager getDatabaseManager() {
        return DATABASE_MANAGER;
    }

    public static CharacterSelectionScreenManager getCharacterSelectionScreenManager() {
        return CHARACTER_SELECTION_SCREEN_MANAGER;
    }

    public static void setCharacterSelectionScreenManager(CharacterSelectionScreenManager characterSelectionScreenManager) {
        CHARACTER_SELECTION_SCREEN_MANAGER = characterSelectionScreenManager;
    }

    public static SpawnerManager getSpawnerManager() {
        return SPAWNER_MANAGER;
    }

    public static GuardianDataManager getGuardianDataManager() {
        return GUARDIAN_DATA_MANAGER;
    }

    public static PetManager getPetManager() {
        return PET_MANAGER;
    }

    public static TombManager getTombManager() {
        return TOMB_MANAGER;
    }

    public static BazaarManager getBazaarManager() {
        return BAZAAR_MANAGER;
    }

    public static GuildManager getGuildManager() {
        return GUILD_MANAGER;
    }

    public static QuestNPCManager getQuestNpcManager() {
        return QUEST_NPC_MANAGER;
    }

    public static TutorialManager getTutorialManager() {
        return TUTORIAL_MANAGER;
    }

    public static DropManager getDropManager() {
        return DROP_MANAGER;
    }

    public static HashMap<UUID, HashMap<Integer, Location>> getCharLocationsForSelection() {
        return charLocationsForSelection;
    }

    public static AdeliaRegionManager getAdeliaRegionManager() {
        return ADELIA_REGION_MANAGER;
    }

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Starting GuardiansOfAdelia..");

        //register events
        Bukkit.getPluginManager().registerEvents(new MyChunkEvents(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityDamageByEntityEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityDamageEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityDeathEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityPickupItemEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityRegainHealthEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityTargetLivingEntityEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyInventoryClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyInventoryCloseEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyItemDespawnEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyItemMergeEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyNPCRightClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerDropItemEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerEggThrowEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerJoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerQuitEvent(), this);

        //init managers
        PACKET_LIMITTER = new PacketLimitter();
        CONFIG_MANAGER = new ConfigManager();
        ADELIA_REGION_MANAGER = new AdeliaRegionManager();
        ADELIA_TOWN_MANAGER = new TownManager();
        //CHARACTER_SELECTION_SCREEN_MANAGER is initialized at config loading
        SPAWNER_MANAGER = new SpawnerManager();
        DATABASE_MANAGER = new DatabaseManager();
        GUARDIAN_DATA_MANAGER = new GuardianDataManager();
        GUILD_MANAGER = new GuildManager();
        PET_MANAGER = new PetManager();
        TOMB_MANAGER = new TombManager();
        BAZAAR_MANAGER = new BazaarManager();
        QUEST_NPC_MANAGER = new QuestNPCManager();
        TUTORIAL_MANAGER = new TutorialManager();
        DROP_MANAGER = new DropManager();

        //set command executors
        this.getCommand("guild").setExecutor(new CommandGuild());

        for (World w : Bukkit.getServer().getWorlds()) {
            w.setDifficulty(Difficulty.HARD);
            w.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            w.setGameRule(GameRule.DISABLE_ELYTRA_MOVEMENT_CHECK, true);
            w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            w.setGameRule(GameRule.DO_ENTITY_DROPS, false);
            w.setGameRule(GameRule.DO_FIRE_TICK, false);
            w.setGameRule(GameRule.DO_MOB_LOOT, false);
            w.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            w.setGameRule(GameRule.DO_TILE_DROPS, false);
            w.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            w.setGameRule(GameRule.KEEP_INVENTORY, true);
            w.setGameRule(GameRule.MOB_GRIEFING, false);
            w.setGameRule(GameRule.NATURAL_REGENERATION, false);
            w.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
            w.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, false);
            w.setTime(3000);
            getLogger().info(w.getName() + " options set");
        }

        DATABASE_MANAGER.createTables();

        new BukkitRunnable() {
            @Override
            public void run() {
                Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
                for (Player player : onlinePlayers) {
                    UUID uuid = player.getUniqueId();
                    if (GUARDIAN_DATA_MANAGER.hasGuardianData(uuid)) {
                        GuardianData guardianData = GUARDIAN_DATA_MANAGER.getGuardianData(uuid);
                        DATABASE_MANAGER.writeGuardianDataWithCurrentCharacter(player, guardianData);
                    }
                }
                List<Guild> activeGuilds = GUILD_MANAGER.getActiveGuilds();
                for (Guild guild : activeGuilds) {
                    DATABASE_MANAGER.writeGuildData(guild);
                }
            }
        }.runTaskTimerAsynchronously(GuardiansOfAdelia.getInstance(), 20 * 60 * 5L, 20 * 60 * 5L);

        //SETUPS
        MyPacketListeners.addPacketListeners();
        PACKET_LIMITTER.register();
        CONFIG_MANAGER.createConfigALL();
        CONFIG_MANAGER.loadConfigALL();

        //DELAYED TASKS
        new BukkitRunnable() {
            @Override
            public void run() {
                SPAWNER_MANAGER.startSpawners();
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), 10 * 5L);

        //REGISTER QUESTS FROM LISTS
        TutorialQuests.createQuests();
    }

    @Override
    public void onDisable() {
        DATABASE_MANAGER.onDisable();
        CONFIG_MANAGER.writeConfigALL();
    }
}
