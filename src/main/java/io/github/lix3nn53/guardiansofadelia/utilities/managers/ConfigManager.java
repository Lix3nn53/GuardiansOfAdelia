package io.github.lix3nn53.guardiansofadelia.utilities.managers;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.creatures.spawners.Spawner;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import org.bukkit.Bukkit;
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

public class ConfigManager {

    private File configFile;
    private FileConfiguration spawnersConfig;
    private FileConfiguration characterSelectionConfig;
    private FileConfiguration townsConfig;

    public ConfigManager() {
        if (!GuardiansOfAdelia.getInstance().getDataFolder().exists()) {
            GuardiansOfAdelia.getInstance().getDataFolder().mkdirs();
        }
        this.configFile = GuardiansOfAdelia.getInstance().getDataFolder();
    }

    public void createConfigALL() {
        createSpawners();
        createCharacterSelectionConfig();
        createTowns();
    }

    public void loadConfigALL() {
        loadSpawners();
        loadCharacterSelectionConfig();
        loadTowns();
    }

    public void writeConfigALL() {
        writeSpawners();
    }

    private void createSpawners() {
        File customConfigFile = new File(configFile, "spawners.yml");
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

    private void loadSpawners() {
        int spawnerNumber = spawnersConfig.getInt("SpawnerNumber");
        for (int i = 1; i <= spawnerNumber; i++) {
            String worldString = spawnersConfig.getString("Spawners.s" + i + ".world");
            World world = Bukkit.getWorld(worldString);
            double x = spawnersConfig.getDouble("Spawners.s" + i + ".x");
            double y = spawnersConfig.getDouble("Spawners.s" + i + ".y");
            double z = spawnersConfig.getDouble("Spawners.s" + i + ".z");
            Location location = new Location(world, x, y, z);
            String mobCode = spawnersConfig.getString("Spawners.s" + i + ".mob");
            int amount = spawnersConfig.getInt("Spawners.s" + i + ".amount");
            int maxamount = spawnersConfig.getInt("Spawners.s" + i + ".maxamount");
            Spawner spawner = new Spawner(location, mobCode, amount, maxamount);
            GuardiansOfAdelia.getSpawnerManager().addSpawner(spawner);
        }
    }

    private void writeSpawners() {
        int i = 1;
        List<Spawner> spawnerList = GuardiansOfAdelia.getSpawnerManager().getSpawners();
        for (Spawner spawner : spawnerList) {
            spawnersConfig.set("Spawners.s" + i + ".x", spawner.getLocation().getX());
            spawnersConfig.set("Spawners.s" + i + ".y", spawner.getLocation().getY());
            spawnersConfig.set("Spawners.s" + i + ".z", spawner.getLocation().getZ());
            spawnersConfig.set("Spawners.s" + i + ".world", spawner.getLocation().getWorld().getName());
            spawnersConfig.set("Spawners.s" + i + ".mob", spawner.getMobCode());
            spawnersConfig.set("Spawners.s" + i + ".amount", spawner.getAmountPerSpawn());
            spawnersConfig.set("Spawners.s" + i + ".maxamount", spawner.getMaxAmount());
        }
        spawnersConfig.set("SpawnerNumber", spawnerList.size());
        try {
            spawnersConfig.save(configFile + "/spawners.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createCharacterSelectionConfig() {
        File customConfigFile = new File(configFile, "characterSelection.yml");
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

    private void loadCharacterSelectionConfig() {
        List<Location> locationList = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            String worldName = characterSelectionConfig.getString("characterSelectionHologram" + i + ".world");
            double x = characterSelectionConfig.getDouble("characterSelectionHologram" + i + ".x");
            double y = characterSelectionConfig.getDouble("characterSelectionHologram" + i + ".y");
            double z = characterSelectionConfig.getDouble("characterSelectionHologram" + i + ".z");
            float yaw = (float) characterSelectionConfig.getDouble("characterSelectionHologram" + i + ".yaw");
            float pitch = (float) characterSelectionConfig.getDouble("characterSelectionHologram" + i + ".pitch");
            Location location = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
            locationList.add(location);
        }
        GuardiansOfAdelia.setCharacterSelectionScreenManager(new CharacterSelectionScreenManager(locationList));

        String worldNameCenter = characterSelectionConfig.getString("characterSelection" + "Center" + ".world");
        double xCenter = characterSelectionConfig.getDouble("characterSelection" + "Center" + ".x");
        double yCenter = characterSelectionConfig.getDouble("characterSelection" + "Center" + ".y");
        double zCenter = characterSelectionConfig.getDouble("characterSelection" + "Center" + ".z");
        float yawCenter = (float) characterSelectionConfig.getDouble("characterSelection" + "Center" + ".yaw");
        float pitchCenter = (float) characterSelectionConfig.getDouble("characterSelection" + "Center" + ".pitch");
        Location locationCenter = new Location(Bukkit.getWorld(worldNameCenter), xCenter, yCenter, zCenter, yawCenter, pitchCenter);
        GuardiansOfAdelia.getCharacterSelectionScreenManager().setCharacterSelectionCenter(locationCenter);

        String worldNameTuto = characterSelectionConfig.getString("tutorialStart" + ".world");
        double xTuto = characterSelectionConfig.getDouble("tutorialStart" + ".x");
        double yTuto = characterSelectionConfig.getDouble("tutorialStart" + ".y");
        double zTuto = characterSelectionConfig.getDouble("tutorialStart" + ".z");
        float yawTuto = (float) characterSelectionConfig.getDouble("tutorialStart" + ".yaw");
        float pitchTuto = (float) characterSelectionConfig.getDouble("tutorialStart" + ".pitch");
        Location locationTuto = new Location(Bukkit.getWorld(worldNameTuto), xTuto, yTuto, zTuto, yawTuto, pitchTuto);
        GuardiansOfAdelia.getCharacterSelectionScreenManager().setTutorialStart(locationTuto);
    }

    private void writeCharacterSelectionConfig() {
        CharacterSelectionScreenManager characterSelectionScreenManager = GuardiansOfAdelia.getCharacterSelectionScreenManager();
        Location characterSelectionCenter = characterSelectionScreenManager.getCharacterSelectionCenter();
        characterSelectionConfig.set("characterSelection" + "Center" + ".world", characterSelectionCenter.getWorld().getName());
        characterSelectionConfig.set("characterSelection" + "Center" + ".x", characterSelectionCenter.getX());
        characterSelectionConfig.set("characterSelection" + "Center" + ".y", characterSelectionCenter.getY());
        characterSelectionConfig.set("characterSelection" + "Center" + ".z", characterSelectionCenter.getZ());
        characterSelectionConfig.set("characterSelection" + "Center" + ".yaw", characterSelectionCenter.getYaw());
        characterSelectionConfig.set("characterSelection" + "Center" + ".pitch", characterSelectionCenter.getPitch());

        Location tutorialStart = characterSelectionScreenManager.getTutorialStart();
        characterSelectionConfig.set("tutorialStart" + ".world", tutorialStart.getWorld().getName());
        characterSelectionConfig.set("tutorialStart" + ".x", tutorialStart.getX());
        characterSelectionConfig.set("tutorialStart" + ".y", tutorialStart.getY());
        characterSelectionConfig.set("tutorialStart" + ".z", tutorialStart.getZ());
        characterSelectionConfig.set("tutorialStart" + ".yaw", tutorialStart.getYaw());
        characterSelectionConfig.set("tutorialStart" + ".pitch", tutorialStart.getPitch());

        HashMap<Integer, List<ArmorStand>> characterNoToArmorStands = characterSelectionScreenManager.getCharacterNoToArmorStands();
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
            characterSelectionConfig.save(configFile + "/characterSelection.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createTowns() {
        File customConfigFile = new File(configFile, "towns.yml");
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

    private void loadTowns() {
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
            GuardiansOfAdelia.getAdeliaTownManager().addTown(town);
        }
    }

    private void writeTowns() {
        List<Town> towns = GuardiansOfAdelia.getAdeliaTownManager().getTowns();
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
            characterSelectionConfig.save(configFile + "/characterSelection.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
