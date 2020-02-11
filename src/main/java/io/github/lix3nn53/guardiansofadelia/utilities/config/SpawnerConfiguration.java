package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.creatures.AdeliaEntity;
import io.github.lix3nn53.guardiansofadelia.creatures.spawners.Spawner;
import io.github.lix3nn53.guardiansofadelia.creatures.spawners.SpawnerManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class SpawnerConfiguration {

    private static FileConfiguration spawnersConfig;
    private static FileConfiguration dungeonSpawnersConfig;

    static void createConfigs() {
        createDungeonSpawners();
        createSpawners();
    }

    static void loadConfigs() {
        loadDungeonSpawners();
        loadSpawners();
    }

    static void writeConfigs() {
        writeDungeonSpawners();
        writeSpawners();
    }

    private static void createSpawners() {
        File customConfigFile = new File(ConfigManager.DATA_FOLDER, "spawners.yml");
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
            spawnersConfig.save(ConfigManager.DATA_FOLDER + "/spawners.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDungeonSpawners() {
        File customConfigFile = new File(ConfigManager.DATA_FOLDER, "dungeonSpawners.yml");
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
            dungeonSpawnersConfig.save(ConfigManager.DATA_FOLDER + "/dungeonSpawners.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
