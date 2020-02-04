package io.github.lix3nn53.guardiansofadelia.utilities.managers;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.creatures.AdeliaEntity;
import io.github.lix3nn53.guardiansofadelia.creatures.spawners.Spawner;
import io.github.lix3nn53.guardiansofadelia.creatures.spawners.SpawnerManager;
import io.github.lix3nn53.guardiansofadelia.database.ConnectionPool;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.checkpoint.Checkpoint;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.Dungeon;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.InstantTeleportPortal;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.Portal;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.PortalColor;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.PortalManager;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.hologram.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ConfigManager {

    private static File DATA_FOLDER;
    private static FileConfiguration spawnersConfig;
    private static FileConfiguration dungeonSpawnersConfig;
    private static FileConfiguration characterSelectionConfig;
    private static FileConfiguration townsConfig;
    private static FileConfiguration dungeonsConfig;
    private static FileConfiguration dungeonGatesConfig;
    private static FileConfiguration databaseConfig;
    private static FileConfiguration resourcePackConfig;
    private static FileConfiguration hologramsConfig;
    private static FileConfiguration teleportPortals;

    public static void init() {
        if (!GuardiansOfAdelia.getInstance().getDataFolder().exists()) {
            GuardiansOfAdelia.getInstance().getDataFolder().mkdirs();
        }
        DATA_FOLDER = GuardiansOfAdelia.getInstance().getDataFolder();
    }

    public static void createConfigALL() {
        createDatabaseConfig();
        createResourcePackConfig();
        createSpawners();
        createDungeonSpawners();
        createCharacterSelectionConfig();
        createTowns();
        createDungeonGates();
        createDungeons();
        createTeleportPortals();
        createHologramsConfig();
    }

    public static void loadConfigALL() {
        loadDatabaseConfig();
        loadResourcePackConfig();
        loadSpawners();
        loadDungeonSpawners();
        loadCharacterSelectionConfig();
        loadTowns();
        loadDungeonGates();
        loadDungeons();
        loadTeleportPortals();
        loadHologramsConfig();
    }

    public static void writeConfigALL() {
        writeSpawners();
        writeDungeonSpawners();
    }

    private static void createDatabaseConfig() {
        File customConfigFile = new File(DATA_FOLDER, "database.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            GuardiansOfAdelia.getInstance().saveResource("database.yml", false);
        }

        databaseConfig = new YamlConfiguration();
        try {
            databaseConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void loadDatabaseConfig() {
        String hostname = databaseConfig.getString("hostname");
        String port = databaseConfig.getString("port");
        String database = databaseConfig.getString("database");
        String username = databaseConfig.getString("username");
        String password = databaseConfig.getString("password");
        int minimumConnections = databaseConfig.getInt("minimumConnections");
        int maximumConnections = databaseConfig.getInt("maximumConnections");
        int connectionTimeout = databaseConfig.getInt("connectionTimeout");
        String testQuery = databaseConfig.getString("testQuery");

        ConnectionPool.init(hostname, port, database, username, password, minimumConnections, maximumConnections, connectionTimeout, testQuery);
    }

    private static void createResourcePackConfig() {
        File customConfigFile = new File(DATA_FOLDER, "resourcepack.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            GuardiansOfAdelia.getInstance().saveResource("resourcepack.yml", false);
        }

        resourcePackConfig = new YamlConfiguration();
        try {
            resourcePackConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void createHologramsConfig() {
        File customConfigFile = new File(DATA_FOLDER, "holograms.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            GuardiansOfAdelia.getInstance().saveResource("holograms.yml", false);
        }

        hologramsConfig = new YamlConfiguration();
        try {
            hologramsConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void loadResourcePackConfig() {
        GuardiansOfAdelia.ResourcePackURL = resourcePackConfig.getString("url");
    }

    public static void loadHologramsConfig() {
        int hologramCount = hologramsConfig.getInt("HologramCount");
        for (int i = 1; i <= hologramCount; i++) {
            String worldString = hologramsConfig.getString("Hologram" + i + ".world");
            if (worldString == null) continue;

            World world = Bukkit.getWorld(worldString);
            double x = hologramsConfig.getDouble("Hologram" + i + ".x");
            double y = hologramsConfig.getDouble("Hologram" + i + ".y");
            double z = hologramsConfig.getDouble("Hologram" + i + ".z");
            Location location = new Location(world, x, y, z);

            List<String> texts = hologramsConfig.getStringList("Hologram" + i + ".texts");

            for (int index = texts.size() - 1; index >= 0; index--) {
                String text = texts.get(index);

                String s = ChatColor.translateAlternateColorCodes('&', text);
                Hologram hologram = new Hologram(location.clone(), s);
                location.add(0, 0.4, 0);

                HologramManager.addHologram(hologram);
            }
        }
    }

    private static void createSpawners() {
        File customConfigFile = new File(DATA_FOLDER, "spawners.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            GuardiansOfAdelia.getInstance().saveResource("spawners.yml", false);
        }

        spawnersConfig = new YamlConfiguration();
        try {
            spawnersConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void loadSpawners() {
        int spawnerNumber = spawnersConfig.getInt("SpawnerNumber");
        for (int i = 1; i <= spawnerNumber; i++) {
            String worldString = spawnersConfig.getString("Spawners.s" + i + ".world");
            if (worldString == null) continue;

            World world = Bukkit.getWorld(worldString);
            double x = spawnersConfig.getDouble("Spawners.s" + i + ".x");
            double y = spawnersConfig.getDouble("Spawners.s" + i + ".y");
            double z = spawnersConfig.getDouble("Spawners.s" + i + ".z");
            Location location = new Location(world, x, y, z);
            String adeliaEntityString = spawnersConfig.getString("Spawners.s" + i + ".mob");
            AdeliaEntity adeliaEntity = AdeliaEntity.valueOf(adeliaEntityString);
            int amount = spawnersConfig.getInt("Spawners.s" + i + ".amount");
            int maxamount = spawnersConfig.getInt("Spawners.s" + i + ".maxamount");
            Spawner spawner = new Spawner(location, adeliaEntity, amount, maxamount);
            SpawnerManager.addSpawner(spawner);
        }
    }

    private static void writeSpawners() {
        int i = 1;
        List<Spawner> spawnerList = SpawnerManager.getSpawners();
        int nonIncludedCount = 0;
        for (Spawner spawner : spawnerList) {
            Location location = spawner.getLocation();
            if (location.getWorld().getName().equals("dungeons")) { //only dungeon spawners are seperate
                nonIncludedCount++;
                continue;
            }
            spawnersConfig.set("Spawners.s" + i + ".x", location.getX());
            spawnersConfig.set("Spawners.s" + i + ".y", location.getY());
            spawnersConfig.set("Spawners.s" + i + ".z", location.getZ());
            spawnersConfig.set("Spawners.s" + i + ".world", Objects.requireNonNull(location.getWorld()).getName());
            spawnersConfig.set("Spawners.s" + i + ".mob", spawner.getAdeliaEntity().name());
            spawnersConfig.set("Spawners.s" + i + ".amount", spawner.getAmountPerSpawn());
            spawnersConfig.set("Spawners.s" + i + ".maxamount", spawner.getMaxAmount());
            i++;
        }
        spawnersConfig.set("SpawnerNumber", spawnerList.size() - nonIncludedCount);
        try {
            spawnersConfig.save(DATA_FOLDER + "/spawners.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDungeonSpawners() {
        File customConfigFile = new File(DATA_FOLDER, "dungeonSpawners.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            GuardiansOfAdelia.getInstance().saveResource("dungeonSpawners.yml", false);
        }

        dungeonSpawnersConfig = new YamlConfiguration();
        try {
            dungeonSpawnersConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void loadDungeonSpawners() {
        int spawnerNumber = dungeonSpawnersConfig.getInt("SpawnerNumber");
        for (int i = 1; i <= spawnerNumber; i++) {
            String worldString = dungeonSpawnersConfig.getString("Spawners.s" + i + ".world");
            if (worldString == null) continue;

            World world = Bukkit.getWorld(worldString);
            double x = dungeonSpawnersConfig.getDouble("Spawners.s" + i + ".x");
            double y = dungeonSpawnersConfig.getDouble("Spawners.s" + i + ".y");
            double z = dungeonSpawnersConfig.getDouble("Spawners.s" + i + ".z");
            Location location = new Location(world, x, y, z);
            String adeliaEntityString = dungeonSpawnersConfig.getString("Spawners.s" + i + ".mob");
            AdeliaEntity adeliaEntity = AdeliaEntity.valueOf(adeliaEntityString);
            int amount = dungeonSpawnersConfig.getInt("Spawners.s" + i + ".amount");
            int maxamount = dungeonSpawnersConfig.getInt("Spawners.s" + i + ".maxamount");
            Spawner spawner = new Spawner(location, adeliaEntity, amount, maxamount);
            SpawnerManager.addSpawner(spawner);
        }
    }

    private static void writeDungeonSpawners() {
        int i = 1;
        List<Spawner> spawnerList = SpawnerManager.getSpawners();
        int nonIncludedCount = 0;
        for (Spawner spawner : spawnerList) {
            Location location = spawner.getLocation();
            if (!location.getWorld().getName().equals("dungeons")) {
                nonIncludedCount++;
                continue;
            }
            dungeonSpawnersConfig.set("Spawners.s" + i + ".x", location.getX());
            dungeonSpawnersConfig.set("Spawners.s" + i + ".y", location.getY());
            dungeonSpawnersConfig.set("Spawners.s" + i + ".z", location.getZ());
            dungeonSpawnersConfig.set("Spawners.s" + i + ".world", Objects.requireNonNull(location.getWorld()).getName());
            dungeonSpawnersConfig.set("Spawners.s" + i + ".mob", spawner.getAdeliaEntity().name());
            dungeonSpawnersConfig.set("Spawners.s" + i + ".amount", spawner.getAmountPerSpawn());
            dungeonSpawnersConfig.set("Spawners.s" + i + ".maxamount", spawner.getMaxAmount());
            i++;
        }
        dungeonSpawnersConfig.set("SpawnerNumber", spawnerList.size() - nonIncludedCount);
        try {
            dungeonSpawnersConfig.save(DATA_FOLDER + "/dungeonSpawners.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createCharacterSelectionConfig() {
        File customConfigFile = new File(DATA_FOLDER, "characterSelection.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            GuardiansOfAdelia.getInstance().saveResource("characterSelection.yml", false);
        }

        characterSelectionConfig = new YamlConfiguration();
        try {
            characterSelectionConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void loadCharacterSelectionConfig() {
        List<Location> locationList = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            String worldName = characterSelectionConfig.getString("characterSelectionHologram" + i + ".world");
            double x = characterSelectionConfig.getDouble("characterSelectionHologram" + i + ".x");
            double y = characterSelectionConfig.getDouble("characterSelectionHologram" + i + ".y");
            double z = characterSelectionConfig.getDouble("characterSelectionHologram" + i + ".z");
            float yaw = (float) characterSelectionConfig.getDouble("characterSelectionHologram" + i + ".yaw");
            float pitch = (float) characterSelectionConfig.getDouble("characterSelectionHologram" + i + ".pitch");
            Location location = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
            locationList.add(location);
        }

        CharacterSelectionScreenManager.setArmorStandLocationBases(locationList);

        String worldNameCenter = characterSelectionConfig.getString("characterSelection" + "Center" + ".world");
        double xCenter = characterSelectionConfig.getDouble("characterSelection" + "Center" + ".x");
        double yCenter = characterSelectionConfig.getDouble("characterSelection" + "Center" + ".y");
        double zCenter = characterSelectionConfig.getDouble("characterSelection" + "Center" + ".z");
        float yawCenter = (float) characterSelectionConfig.getDouble("characterSelection" + "Center" + ".yaw");
        float pitchCenter = (float) characterSelectionConfig.getDouble("characterSelection" + "Center" + ".pitch");
        Location locationCenter = new Location(Bukkit.getWorld(worldNameCenter), xCenter, yCenter, zCenter, yawCenter, pitchCenter);
        CharacterSelectionScreenManager.setCharacterSelectionCenter(locationCenter);

        String worldNameTuto = characterSelectionConfig.getString("tutorialStart" + ".world");
        double xTuto = characterSelectionConfig.getDouble("tutorialStart" + ".x");
        double yTuto = characterSelectionConfig.getDouble("tutorialStart" + ".y");
        double zTuto = characterSelectionConfig.getDouble("tutorialStart" + ".z");
        float yawTuto = (float) characterSelectionConfig.getDouble("tutorialStart" + ".yaw");
        float pitchTuto = (float) characterSelectionConfig.getDouble("tutorialStart" + ".pitch");
        Location locationTuto = new Location(Bukkit.getWorld(worldNameTuto), xTuto, yTuto, zTuto, yawTuto, pitchTuto);
        CharacterSelectionScreenManager.setTutorialStart(locationTuto);
    }

    private static void writeCharacterSelectionConfig() {
        Location characterSelectionCenter = CharacterSelectionScreenManager.getCharacterSelectionCenter();
        characterSelectionConfig.set("characterSelection" + "Center" + ".world", characterSelectionCenter.getWorld().getName());
        characterSelectionConfig.set("characterSelection" + "Center" + ".x", characterSelectionCenter.getX());
        characterSelectionConfig.set("characterSelection" + "Center" + ".y", characterSelectionCenter.getY());
        characterSelectionConfig.set("characterSelection" + "Center" + ".z", characterSelectionCenter.getZ());
        characterSelectionConfig.set("characterSelection" + "Center" + ".yaw", characterSelectionCenter.getYaw());
        characterSelectionConfig.set("characterSelection" + "Center" + ".pitch", characterSelectionCenter.getPitch());

        Location tutorialStart = CharacterSelectionScreenManager.getTutorialStart();
        characterSelectionConfig.set("tutorialStart" + ".world", tutorialStart.getWorld().getName());
        characterSelectionConfig.set("tutorialStart" + ".x", tutorialStart.getX());
        characterSelectionConfig.set("tutorialStart" + ".y", tutorialStart.getY());
        characterSelectionConfig.set("tutorialStart" + ".z", tutorialStart.getZ());
        characterSelectionConfig.set("tutorialStart" + ".yaw", tutorialStart.getYaw());
        characterSelectionConfig.set("tutorialStart" + ".pitch", tutorialStart.getPitch());

        HashMap<Integer, List<ArmorStand>> characterNoToArmorStands = CharacterSelectionScreenManager.getCharacterNoToArmorStands();
        for (Integer i : characterNoToArmorStands.keySet()) {
            Location location = characterNoToArmorStands.get(i).get(0).getLocation();
            characterSelectionConfig.set("characterSelectionHologram" + i + ".world", location.getWorld().getName());
            characterSelectionConfig.set("characterSelectionHologram" + i + ".x", location.getX());
            characterSelectionConfig.set("characterSelectionHologram" + i + ".y", location.add(0.0, -0.4, 0.0).getY());
            characterSelectionConfig.set("characterSelectionHologram" + i + ".z", location.getZ());
            characterSelectionConfig.set("characterSelectionHologram" + i + ".yaw", location.getYaw());
            characterSelectionConfig.set("characterSelectionHologram" + i + ".pitch", location.getPitch());
        }
        try {
            characterSelectionConfig.save(DATA_FOLDER + "/characterSelection.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createTowns() {
        File customConfigFile = new File(DATA_FOLDER, "towns.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            GuardiansOfAdelia.getInstance().saveResource("towns.yml", false);
        }

        townsConfig = new YamlConfiguration();
        try {
            townsConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void loadTowns() {
        for (int i = 1; i < 6; i++) {
            String townName = townsConfig.getString("town" + i + ".name");
            String worldName = townsConfig.getString("town" + i + ".world");
            double x = townsConfig.getDouble("town" + i + ".x");
            double y = townsConfig.getDouble("town" + i + ".y");
            double z = townsConfig.getDouble("town" + i + ".z");
            float yaw = (float) townsConfig.getDouble("town" + i + ".yaw");
            float pitch = (float) townsConfig.getDouble("town" + i + ".pitch");
            Location location = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
            Town town = new Town(townName, i, location);
            TownManager.addTown(town);
        }
    }

    private static void writeTowns() {
        List<Town> towns = TownManager.getTowns();
        for (Town town : towns) {
            Location location = town.getLocation();
            characterSelectionConfig.set("town" + town.getNo() + ".name", town.getName());
            characterSelectionConfig.set("town" + town.getNo() + ".world", location.getWorld().getName());
            characterSelectionConfig.set("town" + town.getNo() + ".x", location.getX());
            characterSelectionConfig.set("town" + town.getNo() + ".y", location.add(0.0, -0.4, 0.0).getY());
            characterSelectionConfig.set("town" + town.getNo() + ".z", location.getZ());
            characterSelectionConfig.set("town" + town.getNo() + ".yaw", location.getYaw());
            characterSelectionConfig.set("town" + town.getNo() + ".pitch", location.getPitch());
        }
        try {
            characterSelectionConfig.save(DATA_FOLDER + "/characterSelection.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDungeons() {
        File customConfigFile = new File(DATA_FOLDER, "dungeons.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            GuardiansOfAdelia.getInstance().saveResource("dungeons.yml", false);
        }

        dungeonsConfig = new YamlConfiguration();
        try {
            dungeonsConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void loadDungeons() {
        for (DungeonTheme dungeonTheme : DungeonTheme.values()) {
            for (int i = 1; i <= 2; i++) { //loading first 2 room of each dungeon
                String code = dungeonTheme.toString() + i;
                String worldString = dungeonsConfig.getString(code + ".world");
                World world = Bukkit.getWorld(worldString);
                double x = dungeonsConfig.getDouble(code + ".x");
                double y = dungeonsConfig.getDouble(code + ".y");
                double z = dungeonsConfig.getDouble(code + ".z");
                float yaw = (float) dungeonsConfig.getDouble(code + ".yaw");
                float pitch = (float) dungeonsConfig.getDouble(code + ".pitch");
                Location location = new Location(world, x, y, z, yaw, pitch);

                int levelReq = dungeonsConfig.getInt(code + ".levelReq");
                int timeLimitInMinutes = dungeonsConfig.getInt(code + ".timeLimitInMinutes");
                String bossMobName = dungeonsConfig.getString(code + ".bossMobName");
                List<Location> locations = new ArrayList<>();
                locations.add(location);

                List<Checkpoint> checkpoints = new ArrayList<>();
                int checkpointCount = dungeonsConfig.getInt(code + ".checkpoints.count");

                for (int checkpointNumber = 1; checkpointNumber <= checkpointCount; checkpointNumber++) {
                    String worldStringC = dungeonsConfig.getString(code + ".checkpoints.loc" + checkpointNumber + ".world");
                    World worldC = Bukkit.getWorld(worldStringC);
                    double xC = dungeonsConfig.getDouble(code + ".checkpoints.loc" + checkpointNumber + ".x");
                    double yC = dungeonsConfig.getDouble(code + ".checkpoints.loc" + checkpointNumber + ".y");
                    double zC = dungeonsConfig.getDouble(code + ".checkpoints.loc" + checkpointNumber + ".z");
                    float yawC = (float) dungeonsConfig.getDouble(code + ".checkpoints.loc" + checkpointNumber + ".yaw");
                    float pitchC = (float) dungeonsConfig.getDouble(code + ".checkpoints.loc" + checkpointNumber + ".pitch");
                    Location locationC = new Location(worldC, xC, yC, zC, yawC, pitchC);

                    checkpoints.add(new Checkpoint(locationC));
                }

                Dungeon dungeon = new Dungeon(levelReq, timeLimitInMinutes, dungeonTheme, i, locations, bossMobName, checkpoints);
                MiniGameManager.addDungeon(dungeonTheme, i, dungeon);
            }
        }
    }

    private static void writeDungeons() {
        for (String code : MiniGameManager.getDungeons()) {
            Dungeon dungeon = MiniGameManager.getDungeon(code);
            Location startLocation = dungeon.getStartLocation(1);
            dungeonsConfig.set(code + ".world", startLocation.getWorld().getName());
            dungeonsConfig.set(code + ".x", startLocation.getX());
            dungeonsConfig.set(code + ".y", startLocation.getY());
            dungeonsConfig.set(code + ".z", startLocation.getZ());
            dungeonsConfig.set(code + ".yaw", startLocation.getYaw());
            dungeonsConfig.set(code + ".pitch", startLocation.getPitch());

            dungeonsConfig.set(code + ".levelReq", dungeon.getLevelReq());
            dungeonsConfig.set(code + ".timeLimitInMinutes", dungeon.getTimeLimitInMinutes());
            dungeonsConfig.set(code + ".bossMobName", dungeon.getBossMobName());
        }
        try {
            dungeonsConfig.save(DATA_FOLDER + "/dungeons.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDungeonGates() {
        File customConfigFile = new File(DATA_FOLDER, "dungeonGates.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            GuardiansOfAdelia.getInstance().saveResource("dungeonGates.yml", false);
        }

        dungeonGatesConfig = new YamlConfiguration();
        try {
            dungeonGatesConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void loadDungeonGates() {
        for (DungeonTheme dungeonTheme : DungeonTheme.values()) {
            String worldString = dungeonGatesConfig.getString(dungeonTheme.toString() + ".world");
            World world = Bukkit.getWorld(worldString);
            double x = dungeonGatesConfig.getDouble(dungeonTheme.toString() + ".x");
            double y = dungeonGatesConfig.getDouble(dungeonTheme.toString() + ".y");
            double z = dungeonGatesConfig.getDouble(dungeonTheme.toString() + ".z");
            float yaw = (float) dungeonGatesConfig.getDouble(dungeonTheme.toString() + ".yaw");
            float pitch = (float) dungeonGatesConfig.getDouble(dungeonTheme.toString() + ".pitch");
            Location location = new Location(world, x, y, z, yaw, pitch);
            MiniGameManager.addMinigamePortal(location, dungeonTheme);
        }
    }

    private static void createTeleportPortals() {
        File customConfigFile = new File(DATA_FOLDER, "teleportPortals.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            GuardiansOfAdelia.getInstance().saveResource("teleportPortals.yml", false);
        }

        teleportPortals = new YamlConfiguration();
        try {
            teleportPortals.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void loadTeleportPortals() {
        int portalNumber = teleportPortals.getInt("PortalNumber");
        for (int i = 1; i <= portalNumber; i++) {
            String worldString = teleportPortals.getString("p" + i + ".world");
            World world = Bukkit.getWorld(worldString);
            double x = teleportPortals.getDouble("p" + i + ".x");
            double y = teleportPortals.getDouble("p" + i + ".y");
            double z = teleportPortals.getDouble("p" + i + ".z");
            float yaw = (float) teleportPortals.getDouble("p" + i + ".yaw");
            float pitch = (float) teleportPortals.getDouble("p" + i + ".pitch");
            Location location = new Location(world, x, y, z, yaw, pitch);
            PortalColor portalColor = PortalColor.valueOf(teleportPortals.getString("p" + i + ".color"));
            Portal portal = new Portal(location, portalColor);

            PortalManager.addPortal(portal);

            String tpWorldString = teleportPortals.getString("p" + i + ".tpWorld");
            World tpWorld = Bukkit.getWorld(tpWorldString);
            double tpX = teleportPortals.getDouble("p" + i + ".tpX");
            double tpY = teleportPortals.getDouble("p" + i + ".tpY");
            double tpZ = teleportPortals.getDouble("p" + i + ".tpZ");
            float tpYaw = (float) teleportPortals.getDouble("p" + i + ".tpYaw");
            float tpPitch = (float) teleportPortals.getDouble("p" + i + ".tpPitch");
            Location tpLocation = new Location(tpWorld, tpX, tpY, tpZ, tpYaw, tpPitch);

            int requiredQuestNoAccepted = teleportPortals.getInt("p" + i + ".requiredQuestNoAccepted");
            int requiredQuestNoTurnedIn = teleportPortals.getInt("p" + i + ".requiredQuestNoTurnedIn");

            InstantTeleportPortal instantTeleportPortal = new InstantTeleportPortal(tpLocation, requiredQuestNoAccepted, requiredQuestNoTurnedIn);
            PortalManager.addInstantTeleportPortal(portal, instantTeleportPortal);
        }
    }
}
