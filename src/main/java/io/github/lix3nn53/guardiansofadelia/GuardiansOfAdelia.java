package io.github.lix3nn53.guardiansofadelia;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.PluginChannelListener;
import io.github.lix3nn53.guardiansofadelia.commands.*;
import io.github.lix3nn53.guardiansofadelia.commands.admin.*;
import io.github.lix3nn53.guardiansofadelia.creatures.mythicmobs.mechanics.MMCustomMechanicsRegister;
import io.github.lix3nn53.guardiansofadelia.database.DatabaseManager;
import io.github.lix3nn53.guardiansofadelia.events.*;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guild.Guild;
import io.github.lix3nn53.guardiansofadelia.guild.GuildManager;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.sounds.MySongLoopEvent;
import io.github.lix3nn53.guardiansofadelia.sounds.MySongNextEvent;
import io.github.lix3nn53.guardiansofadelia.utilities.MyPacketListeners;
import io.github.lix3nn53.guardiansofadelia.utilities.config.ConfigManager;
import io.github.lix3nn53.guardiansofadelia.utilities.shutdown.AutomaticShutdown;
import io.github.lix3nn53.guardiansofadelia.utilities.signmenu.SignMenuFactory;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.List;

public class GuardiansOfAdelia extends JavaPlugin {

    public static String ResourcePackURL;
    public static PluginChannelListener pluginChannelListener;

    private static GuardiansOfAdelia instance;

    public static GuardiansOfAdelia getInstance() {
        return instance;
    }

    private static SignMenuFactory signMenuFactory;

    public static SignMenuFactory getSignMenuFactory() {
        return signMenuFactory;
    }

    @Override
    public void onDisable() {
        DatabaseManager.onDisable();
        ConfigManager.writeConfigALL();
    }

    private void startGlobalRegen() {
        double manaPercent = 0.1;
        double maxManaPercent = 0.4;
        // double healPercent = 0.1;
        // double maxHealthPercent = 1.0;

        new BukkitRunnable() {
            @Override
            public void run() {
                Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
                for (Player player : onlinePlayers) {
                    if (GuardianDataManager.hasGuardianData(player)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                            RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();

                            double currentMana = rpgCharacterStats.getCurrentMana();
                            double maxMana = rpgCharacterStats.getTotalMaxMana();
                            if (currentMana < (maxMana * maxManaPercent)) {
                                double nextMana = currentMana + (maxMana * manaPercent);
                                if (nextMana > maxMana) {
                                    nextMana = maxMana;
                                }
                                rpgCharacterStats.setCurrentMana((int) nextMana);
                            }

                            /*double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                            double currentHealth = player.getHealth();

                            if (currentHealth < (maxHealth * maxHealthPercent)) {
                                double nextHealth = currentHealth + (maxHealth * healPercent);
                                if (nextHealth > maxHealth) {
                                    nextHealth = maxHealth;
                                }
                                player.setHealth(nextHealth);
                            }*/
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(GuardiansOfAdelia.getInstance(), 100L, 20 * 5L);
    }

    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }

        Bukkit.getLogger().info("BUNGEE MESSAGE");

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("webPurchase")) {
            // Use the code sample in the 'Response' sections below to read
            // the data.
            Bukkit.getLogger().info("webPurchase");
            String argument = in.readUTF();
            Bukkit.getLogger().info(argument);

        }
    }

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Starting GuardiansOfAdelia...");

        //register events
        Bukkit.getPluginManager().registerEvents(new MyAsyncPlayerChatEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyBlockEvents(), this);
        Bukkit.getPluginManager().registerEvents(new MyChunkEvents(), this);
        Bukkit.getPluginManager().registerEvents(new MyCanceledEvents(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityDamageByEntityEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityDamageEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityDeathEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityDismountEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityMountEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityPickupItemEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityRegainHealthEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityShootBowEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityTargetLivingEntityEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyInventoryClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyInventoryCloseEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyItemDespawnEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyItemSpawnEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyMythicMobDeathEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyMythicMobSpawnEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyNPCRightClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerAnimationEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerCommandPreprocessEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerDeathEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerDropItemEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerEggThrowEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerFishEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerInteractEntityEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerInteractEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerItemConsumeEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerItemHeldEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerJoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerMoveEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerPickupArrowEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerPortalEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerQuitEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerResourcePackStatusEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerSwapHandItemsEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerTeleportEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyProjectileHitEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyProjectileLaunchEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyVehicleDestroyEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MySongNextEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MySongLoopEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MMCustomMechanicsRegister(), this);

        //init managers
        ConfigManager.init();
        ConfigManager.createConfigALL();
        ConfigManager.loadConfigALL();
        //CHARACTER_SELECTION_SCREEN_MANAGER is initialized at config loading
        //DATABASE SETUP is initialized at config loading

        MyPacketListeners.addPacketListeners();
        MiniGameManager.initMinigames();

        // Set command executors
        this.getCommand("character").setExecutor(new CommandCharacter());
        this.getCommand("chat").setExecutor(new CommandChat());
        this.getCommand("destroyitem").setExecutor(new CommandDestroyItem());
        this.getCommand("guild").setExecutor(new CommandGuild());
        this.getCommand("invite").setExecutor(new CommandInvite());
        this.getCommand("trade").setExecutor(new CommandTrade());
        this.getCommand("party").setExecutor(new CommandParty());
        this.getCommand("rp").setExecutor(new CommandResourcePack());
        this.getCommand("reward").setExecutor(new CommandReward());
        this.getCommand("safe-stop").setExecutor(new CommandSafeStop());
        this.getCommand("minigame").setExecutor(new CommandMinigame());
        // Admin commands
        this.getCommand("admin").setExecutor(new CommandAdmin());
        this.getCommand("admindungeon").setExecutor(new CommandAdminDungeon());
        this.getCommand("adminitem").setExecutor(new CommandAdminItem());
        this.getCommand("adminjob").setExecutor(new CommandAdminJob());
        this.getCommand("adminquest").setExecutor(new CommandAdminQuest());
        this.getCommand("adminreward").setExecutor(new CommandAdminReward());
        this.getCommand("test").setExecutor(new CommandTest());

        for (World w : Bukkit.getServer().getWorlds()) {
            w.setDifficulty(Difficulty.HARD);
            w.setPVP(false);
            w.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            w.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, true); //DEFAULT
            w.setGameRule(GameRule.DISABLE_ELYTRA_MOVEMENT_CHECK, true);
            w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true); //DEFAULT
            w.setGameRule(GameRule.DO_ENTITY_DROPS, false);
            w.setGameRule(GameRule.DO_FIRE_TICK, false);
            w.setGameRule(GameRule.DO_LIMITED_CRAFTING, false); //DEFAULT
            w.setGameRule(GameRule.DO_MOB_LOOT, false);
            w.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            w.setGameRule(GameRule.DO_TILE_DROPS, false);
            w.setGameRule(GameRule.DO_WEATHER_CYCLE, true); //DEFAULT
            w.setGameRule(GameRule.KEEP_INVENTORY, true);
            w.setGameRule(GameRule.LOG_ADMIN_COMMANDS, true); //DEFAULT
            w.setGameRule(GameRule.MOB_GRIEFING, false);
            w.setGameRule(GameRule.NATURAL_REGENERATION, false);
            w.setGameRule(GameRule.REDUCED_DEBUG_INFO, false); //DEFAULT
            w.setGameRule(GameRule.SEND_COMMAND_FEEDBACK, true); //DEFAULT
            w.setGameRule(GameRule.SHOW_DEATH_MESSAGES, true); //DEFAULT
            w.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, false);
            w.setGameRule(GameRule.DISABLE_RAIDS, true);
            w.setGameRule(GameRule.DO_INSOMNIA, false);
            w.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
            w.setGameRule(GameRule.DROWNING_DAMAGE, true); //DEFAULT
            w.setGameRule(GameRule.FALL_DAMAGE, true); //DEFAULT
            w.setGameRule(GameRule.FIRE_DAMAGE, true); //DEFAULT
            w.setGameRule(GameRule.DO_PATROL_SPAWNING, false);
            w.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
            w.setGameRule(GameRule.FORGIVE_DEAD_PLAYERS, false); //Makes angered neutral mobs stop being angry when the targeted player dies nearby
            w.setGameRule(GameRule.UNIVERSAL_ANGER, true); //Makes angered neutral mobs attack any nearby player, not just the player that angered them. Works best if forgiveDeadPlayers is disabled.
            w.setGameRule(GameRule.RANDOM_TICK_SPEED, 0); //DISABLED
            w.setGameRule(GameRule.SPAWN_RADIUS, 10); //DEFAULT
            w.setGameRule(GameRule.MAX_ENTITY_CRAMMING, 24); //DEFAULT
            w.setGameRule(GameRule.MAX_COMMAND_CHAIN_LENGTH, 0); //DISABLED
            w.setGameRule(GameRule.FREEZE_DAMAGE, false); //DISABLED
            w.setTime(3000);

            if (w.getName().equals("arena")) {
                w.setPVP(true);
                w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            } else if (w.getName().equals("tutorial")) {
                w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                if (w.getName().equals("tutorial")) {
                    w.setTime(18000);
                }
            }
            getLogger().info("World(" + w.getName() + ") options set");
            getLogger().info("World(" + w.getName() + ") view distance: " + w.getViewDistance());
        }

        DatabaseManager.createTables();

        //save loop
        new BukkitRunnable() {
            @Override
            public void run() {
                Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
                for (Player player : onlinePlayers) {
                    if (GuardianDataManager.hasGuardianData(player)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(player);
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

        // health & mana regen loop
        startGlobalRegen();

        //DELAYED TASKS
        /*new BukkitRunnable() {
            @Override
            public void run() {
                CustomSoundtrack.startPlayLoopForEveryone();
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), 10 * 5L);*/


        //Automatic Shutdown
        AutomaticShutdown.onEnable();

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        // allow to send to BungeeCord
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "BungeeCord", pluginChannelListener = new PluginChannelListener());
        // gets a Message from Bungee

        signMenuFactory = new SignMenuFactory(this);
    }
}
