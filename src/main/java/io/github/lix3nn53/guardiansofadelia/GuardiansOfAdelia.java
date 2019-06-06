package io.github.lix3nn53.guardiansofadelia;

import com.google.common.collect.ImmutableList;
import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.SkillPlugin;
import com.sucy.skill.dynamic.custom.CustomEffectComponent;
import io.github.lix3nn53.guardiansofadelia.commands.*;
import io.github.lix3nn53.guardiansofadelia.creatures.spawners.SpawnerManager;
import io.github.lix3nn53.guardiansofadelia.database.DatabaseManager;
import io.github.lix3nn53.guardiansofadelia.events.*;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guild.Guild;
import io.github.lix3nn53.guardiansofadelia.guild.GuildManager;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.npc.merchant.MerchantManager;
import io.github.lix3nn53.guardiansofadelia.quests.list.MainStoryQuests;
import io.github.lix3nn53.guardiansofadelia.quests.list.TutorialQuests;
import io.github.lix3nn53.guardiansofadelia.utilities.MyPacketListeners;
import io.github.lix3nn53.guardiansofadelia.utilities.PacketLimitter;
import io.github.lix3nn53.guardiansofadelia.utilities.SkillAPIHologramDestroyMechanic;
import io.github.lix3nn53.guardiansofadelia.utilities.SkillAPIHologramMechanic;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.CharacterSelectionScreenManager;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class GuardiansOfAdelia extends JavaPlugin implements SkillPlugin {

    private static CharacterSelectionScreenManager CHARACTER_SELECTION_SCREEN_MANAGER;

    private static GuardiansOfAdelia instance;

    public static GuardiansOfAdelia getInstance() {
        return instance;
    }

    public static CharacterSelectionScreenManager getCharacterSelectionScreenManager() {
        return CHARACTER_SELECTION_SCREEN_MANAGER;
    }

    public static void setCharacterSelectionScreenManager(CharacterSelectionScreenManager characterSelectionScreenManager) {
        CHARACTER_SELECTION_SCREEN_MANAGER = characterSelectionScreenManager;
    }

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Starting GuardiansOfAdelia..");

        //register events
        Bukkit.getPluginManager().registerEvents(new MyAsyncPlayerChatEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyBlockEvents(), this);
        Bukkit.getPluginManager().registerEvents(new MyChunkEvents(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityCombustEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityDamageByEntityEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityDamageEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityDeathEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityDismountEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityMountEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityPickupItemEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityRegainHealthEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityTargetLivingEntityEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyInventoryClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyInventoryCloseEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyItemDespawnEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyItemMergeEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyItemSpawnEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyNPCRightClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerAnimationEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerBedEnterEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerDeathEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerDropItemEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerEggThrowEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerInteractEntityEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerInteractEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerItemConsumeEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerItemHeldEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerJoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerMoveEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerPickupArrowEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerQuitEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerTakeLecternBookEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyProjectileLaunchEvent(), this);

        //init managers
        ConfigManager.init();
        ConfigManager.createConfigALL();
        ConfigManager.loadConfigALL();
        //CHARACTER_SELECTION_SCREEN_MANAGER is initialized at config loading
        //DATABASE SETUP is initialized at config loading

        MyPacketListeners.addPacketListeners();
        PacketLimitter.register();
        MerchantManager.init();
        MiniGameManager.initMinigames();

        //set command executors
        this.getCommand("guild").setExecutor(new CommandGuild());
        this.getCommand("lix").setExecutor(new CommandLix());
        this.getCommand("invite").setExecutor(new CommandInvite());
        this.getCommand("trade").setExecutor(new CommandTrade());
        this.getCommand("party").setExecutor(new CommandParty());
        this.getCommand("minigame").setExecutor(new CommandMinigame());

        for (World w : Bukkit.getServer().getWorlds()) {
            w.setDifficulty(Difficulty.HARD);
            w.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            w.setGameRule(GameRule.DISABLE_ELYTRA_MOVEMENT_CHECK, true);
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
            if (w.getName().equals("arena")) {
                w.setPVP(true);
                w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            } else if (w.getName().equals("world")) {
                w.setPVP(false);
                w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
            } else {
                w.setPVP(false);
                w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            }
            getLogger().info(w.getName() + " options set");
        }

        DatabaseManager.createTables();

        new BukkitRunnable() {
            @Override
            public void run() {
                Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
                for (Player player : onlinePlayers) {
                    UUID uuid = player.getUniqueId();
                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                        DatabaseManager.writeGuardianDataWithCurrentCharacter(player, guardianData);
                    }
                }
                List<Guild> activeGuilds = GuildManager.getActiveGuilds();
                for (Guild guild : activeGuilds) {
                    DatabaseManager.writeGuildData(guild);
                }
                getLogger().info("Write current data to database");
            }
        }.runTaskTimerAsynchronously(GuardiansOfAdelia.getInstance(), 20 * 60 * 5L, 20 * 60 * 5L);

        //DELAYED TASKS
        new BukkitRunnable() {
            @Override
            public void run() {
                SpawnerManager.startSpawners();
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), 10 * 5L);

        //REGISTER QUESTS FROM LISTS
        TutorialQuests.createQuests();
        MainStoryQuests.createQuests();
    }

    @Override
    public void onDisable() {
        DatabaseManager.onDisable();
        ConfigManager.writeConfigALL();
    }

    @Override
    public void registerSkills(SkillAPI skillAPI) {

    }

    @Override
    public void registerClasses(SkillAPI skillAPI) {

    }

    @Override
    public List<CustomEffectComponent> getComponents() {
        return ImmutableList.of(
                new SkillAPIHologramMechanic(),
                new SkillAPIHologramDestroyMechanic()
        );
    }
}
