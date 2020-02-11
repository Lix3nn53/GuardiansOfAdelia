package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.checkpoint.Checkpoint;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.Dungeon;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DungeonConfiguration {

    private static FileConfiguration dungeonsConfig;
    private static FileConfiguration dungeonGatesConfig;

    static void createConfigs() {
        createDungeonGates();
        createDungeons();
    }

    static void loadConfigs() {
        loadDungeonGates();
        loadDungeons();
    }

    private static void createDungeonGates() {
        File customConfigFile = new File(ConfigManager.DATA_FOLDER, "dungeonGates.yml");
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

    private static void createDungeons() {
        File customConfigFile = new File(ConfigManager.DATA_FOLDER, "dungeons.yml");
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
            dungeonsConfig.save(ConfigManager.DATA_FOLDER + "/dungeons.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
