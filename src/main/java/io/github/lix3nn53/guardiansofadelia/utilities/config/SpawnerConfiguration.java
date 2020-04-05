package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.creatures.AdeliaEntity;
import io.github.lix3nn53.guardiansofadelia.creatures.AdeliaEntityManager;
import io.github.lix3nn53.guardiansofadelia.creatures.spawners.Spawner;
import io.github.lix3nn53.guardiansofadelia.creatures.spawners.SpawnerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class SpawnerConfiguration {

    private static HashMap<String, FileConfiguration> worldNameToConfiguration = new HashMap<>();

    static void createConfigs() {
        createSpawners("world");
        createSpawners("dungeons");
    }

    static void loadConfigs() {
        loadSpawners("world");
        loadSpawners("dungeons");
    }

    static void writeConfigs() {
        writeSpawners("world");
        writeSpawners("dungeons");
    }

    private static void createSpawners(String worldName) {
        String fileName = worldName + ".yml";
        String filePath = ConfigManager.DATA_FOLDER + File.separator + "spawners";
        File customConfigFile = new File(filePath, fileName);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();

            try {
                customConfigFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        try {
            yamlConfiguration.load(customConfigFile);
            worldNameToConfiguration.put(worldName, yamlConfiguration);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void loadSpawners(String worldName) {
        FileConfiguration spawnersConfig = worldNameToConfiguration.get(worldName);

        int spawnerNumber = spawnersConfig.getInt("SpawnerCount");
        int debug = 0;
        for (int i = 1; i <= spawnerNumber; i++) {
            World world = Bukkit.getWorld(worldName);

            if (!spawnersConfig.contains("Spawners.s" + i + ".x")) {
                debugError(worldName, i);
                continue;
            }

            if (!spawnersConfig.contains("Spawners.s" + i + ".y")) {
                debugError(worldName, i);
                continue;
            }

            if (!spawnersConfig.contains("Spawners.s" + i + ".z")) {
                debugError(worldName, i);
                continue;
            }

            if (!spawnersConfig.contains("Spawners.s" + i + ".mob")) {
                debugError(worldName, i);
                continue;
            }

            if (!spawnersConfig.contains("Spawners.s" + i + ".amount")) {
                debugError(worldName, i);
                continue;
            }

            if (!spawnersConfig.contains("Spawners.s" + i + ".maxamount")) {
                debugError(worldName, i);
                continue;
            }

            double x = spawnersConfig.getDouble("Spawners.s" + i + ".x");
            double y = spawnersConfig.getDouble("Spawners.s" + i + ".y");
            double z = spawnersConfig.getDouble("Spawners.s" + i + ".z");
            Location location = new Location(world, x, y, z);
            String adeliaEntityString = spawnersConfig.getString("Spawners.s" + i + ".mob");
            AdeliaEntity adeliaEntity = AdeliaEntityManager.getEntity(adeliaEntityString);
            int amount = spawnersConfig.getInt("Spawners.s" + i + ".amount");
            int maxamount = spawnersConfig.getInt("Spawners.s" + i + ".maxamount");
            Spawner spawner = new Spawner(location, adeliaEntity, amount, maxamount);
            SpawnerManager.addSpawner(spawner);
            debug++;
        }
        GuardiansOfAdelia.getInstance().getLogger().info("Loaded " + debug + " spawners for world: " + worldName);
    }

    private static void writeSpawners(String worldName) {
        YamlConfiguration spawnersConfig = new YamlConfiguration();

        int i = 1;
        List<Spawner> spawnerList = SpawnerManager.getSpawners();

        int nonIncludedCount = 0;

        for (Spawner spawner : spawnerList) {
            Location location = spawner.getLocation();
            if (location.getWorld() == null) {
                GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "SPAWNER WRITE NULL WORLD");
                continue;
            }

            if (!location.getWorld().getName().equals(worldName)) {
                nonIncludedCount++;
                continue;
            }

            spawnersConfig.set("Spawners.s" + i + ".x", location.getX());
            spawnersConfig.set("Spawners.s" + i + ".y", location.getY());
            spawnersConfig.set("Spawners.s" + i + ".z", location.getZ());
            spawnersConfig.set("Spawners.s" + i + ".mob", spawner.getAdeliaEntity().getAdeliaEntityKey());
            spawnersConfig.set("Spawners.s" + i + ".amount", spawner.getAmountPerSpawn());
            spawnersConfig.set("Spawners.s" + i + ".maxamount", spawner.getMaxAmount());
            i++;
        }
        spawnersConfig.set("SpawnerCount", spawnerList.size() - nonIncludedCount);

        try {
            String fileName = worldName + ".yml";
            String filePath = ConfigManager.DATA_FOLDER + File.separator + "spawners";

            spawnersConfig.save(filePath + File.separator + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void debugError(String worldName, int i) {
        GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "Spawner config not valid");
        GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "worldName: " + worldName);
        GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "index: " + i);
    }
}
