package io.github.lix3nn53.guardiansofadelia;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.PluginChannelListener;
import io.github.lix3nn53.guardiansofadelia.commands.*;
import io.github.lix3nn53.guardiansofadelia.database.DatabaseManager;
import io.github.lix3nn53.guardiansofadelia.events.*;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guild.Guild;
import io.github.lix3nn53.guardiansofadelia.guild.GuildManager;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.npc.merchant.MerchantManager;
import io.github.lix3nn53.guardiansofadelia.quests.list.TutorialQuests;
import io.github.lix3nn53.guardiansofadelia.quests.list.mainstory.MainStoryQuests;
import io.github.lix3nn53.guardiansofadelia.utilities.MyPacketListeners;
import io.github.lix3nn53.guardiansofadelia.utilities.PacketLimitter;
import io.github.lix3nn53.guardiansofadelia.utilities.config.ConfigManager;
import io.github.lix3nn53.guardiansofadelia.utilities.shutdown.AutomaticShutdown;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class GuardiansOfAdelia extends JavaPlugin {

    public static String ResourcePackURL;
    public static PluginChannelListener pluginChannelListener;

    private static GuardiansOfAdelia instance;

    public static GuardiansOfAdelia getInstance() {
        return instance;
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
        Bukkit.getPluginManager().registerEvents(new MyEntityDropItemEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityMountEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityPickupItemEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityRegainHealthEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityShootBowEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyEntityTargetLivingEntityEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyExplosionPrimeEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyFoodLevelChangeEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyInventoryClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyInventoryCloseEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyItemDespawnEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyItemMergeEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyItemSpawnEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyNPCRightClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerAnimationEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerBedEnterEvent(), this);
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
        Bukkit.getPluginManager().registerEvents(new MyPlayerTakeLecternBookEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyPlayerTeleportEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyProjectileHitEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyProjectileLaunchEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MyVehicleDestroyEvent(), this);

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
        this.getCommand("character").setExecutor(new CommandCharacter());
        this.getCommand("chat").setExecutor(new CommandChat());
        this.getCommand("destroyitem").setExecutor(new CommandDestroyItem());
        this.getCommand("guild").setExecutor(new CommandGuild());
        this.getCommand("admin").setExecutor(new CommandLix());
        this.getCommand("invite").setExecutor(new CommandInvite());
        this.getCommand("trade").setExecutor(new CommandTrade());
        this.getCommand("party").setExecutor(new CommandParty());
        this.getCommand("rp").setExecutor(new CommandResourcePack());
        this.getCommand("reward").setExecutor(new CommandReward());
        this.getCommand("safe-stop").setExecutor(new CommandSafeStop());
        this.getCommand("minigame").setExecutor(new CommandMinigame());
        this.getCommand("job").setExecutor(new CommandJob());

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
            w.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, true);
            w.setGameRule(GameRule.DISABLE_RAIDS, true);
            w.setGameRule(GameRule.DO_PATROL_SPAWNING, false);
            w.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
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

        //startGlobalRegen(); //health & mana regen loop
        startGlobalManaRegen(0.4);

        //DELAYED TASKS
        /* TODO
        new BukkitRunnable() {
            @Override
            public void run() {
                CustomSoundtrack.startPlayLoopForEveryone();
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), 10 * 5L);
        */

        //REGISTER QUESTS FROM LISTS
        TutorialQuests.createQuests();
        MainStoryQuests.createQuests();

        //Automatic Shutdown
        AutomaticShutdown.onEnable();

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        // allow to send to BungeeCord
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "BungeeCord", pluginChannelListener = new PluginChannelListener());
        // gets a Message from Bungee
    }

    @Override
    public void onDisable() {
        DatabaseManager.onDisable();
        ConfigManager.writeConfigALL();
    }

    private void startGlobalRegen() {
        double healPercent = 0.05;
        double manaPercent = 0.05;

        new BukkitRunnable() {
            @Override
            public void run() {
                Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
                for (Player player : onlinePlayers) {
                    UUID uuid = player.getUniqueId();
                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                            RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();

                            double currentMana = rpgCharacterStats.getCurrentMana();
                            double maxMana = rpgCharacterStats.getTotalMaxMana();
                            if (currentMana < maxMana) {
                                double nextMana = currentMana + (maxMana * manaPercent);
                                if (nextMana > maxMana) {
                                    nextMana = maxMana;
                                }
                                rpgCharacterStats.setCurrentMana((int) nextMana);
                            }

                            double currentHealth = player.getHealth();
                            double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                            if (currentHealth < maxHealth) {
                                double nextHealth = currentHealth + (maxHealth * healPercent);
                                if (nextHealth > maxHealth) {
                                    nextHealth = maxHealth;
                                }
                                player.setHealth(nextHealth);
                            }
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(GuardiansOfAdelia.getInstance(), 100L, 160L);
    }

    private void startGlobalManaRegen(double maxManaPercent) {
        double manaPercent = 0.05;

        new BukkitRunnable() {
            @Override
            public void run() {
                Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
                for (Player player : onlinePlayers) {
                    UUID uuid = player.getUniqueId();
                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
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
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(GuardiansOfAdelia.getInstance(), 100L, 160L);
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
}
