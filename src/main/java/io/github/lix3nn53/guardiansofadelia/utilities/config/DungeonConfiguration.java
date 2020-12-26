package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.checkpoint.Checkpoint;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonRoom;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.transportation.portals.PortalColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DungeonConfiguration {

    private static FileConfiguration dungeonThemesConfig;
    private static FileConfiguration dungeonRoomsConfig;
    private static FileConfiguration dungeonGatesConfig;

    static void createConfigs() {
        createDungeonThemes();
        createDungeonGates();
        createDungeonRooms();
    }

    static void loadConfigs() {
        loadDungeonThemes();
        loadDungeonGates();
        loadDungeonRooms();
    }

    private static void createDungeonThemes() {
        String filePath = ConfigManager.DATA_FOLDER + File.separator + "dungeons";
        File customConfigFile = new File(filePath, "dungeonThemes.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();

            try {
                customConfigFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        dungeonThemesConfig = new YamlConfiguration();
        try {
            dungeonThemesConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void loadDungeonThemes() {
        int themeCount = getChildComponentCount(dungeonThemesConfig, "theme");

        for (int i = 1; i <= themeCount; i++) {
            ConfigurationSection section = dungeonThemesConfig.getConfigurationSection("theme" + i);
            String code = section.getString("code");
            String name = section.getString("name");
            String gearTag = section.getString("gearTag");
            int gearLevel = section.getInt("gearLevel");
            String portalColorStr = section.getString("portalColor");
            PortalColor portalColor = PortalColor.valueOf(portalColorStr);

            int levelReq = section.getInt("levelReq");
            int timeLimitInMinutes = section.getInt("timeLimitInMinutes");
            String bossInternalName = section.getString("bossInternalName");

            DungeonTheme dungeonTheme = new DungeonTheme(code, name, gearTag, gearLevel, portalColor, levelReq, timeLimitInMinutes, bossInternalName);

            MiniGameManager.addDungeonTheme(code, dungeonTheme);
        }
    }

    private static int getChildComponentCount(ConfigurationSection configurationSection, String text) {
        int count = 0;
        while (true) {
            boolean contains = configurationSection.contains(text + (count + 1));
            if (contains) {
                count++;
            } else {
                break;
            }
        }

        return count;
    }

    private static void createDungeonGates() {
        String filePath = ConfigManager.DATA_FOLDER + File.separator + "dungeons";
        File customConfigFile = new File(filePath, "dungeonGates.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();

            try {
                customConfigFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        dungeonGatesConfig = new YamlConfiguration();
        try {
            dungeonGatesConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void loadDungeonGates() {
        HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
        for (String code : dungeonThemes.keySet()) {
            String worldString = dungeonGatesConfig.getString(code + ".world");
            World world = Bukkit.getWorld(worldString);
            double x = dungeonGatesConfig.getDouble(code + ".x");
            double y = dungeonGatesConfig.getDouble(code + ".y");
            double z = dungeonGatesConfig.getDouble(code + ".z");
            float yaw = (float) dungeonGatesConfig.getDouble(code + ".yaw");
            float pitch = (float) dungeonGatesConfig.getDouble(code + ".pitch");
            Location location = new Location(world, x, y, z, yaw, pitch);
            MiniGameManager.addMinigamePortal(location, code);
        }
    }

    private static void createDungeonRooms() {
        String filePath = ConfigManager.DATA_FOLDER + File.separator + "dungeons";
        File customConfigFile = new File(filePath, "dungeonRooms.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();

            try {
                customConfigFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        dungeonRoomsConfig = new YamlConfiguration();
        try {
            dungeonRoomsConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void loadDungeonRooms() {
        HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
        for (String themeCode : dungeonThemes.keySet()) {
            int roomCount = getChildComponentCount(dungeonRoomsConfig, themeCode);

            for (int i = 1; i <= roomCount; i++) {
                String code = themeCode + i;
                String worldString = dungeonRoomsConfig.getString(code + ".world");
                World world = Bukkit.getWorld(worldString);
                double x = dungeonRoomsConfig.getDouble(code + ".x");
                double y = dungeonRoomsConfig.getDouble(code + ".y");
                double z = dungeonRoomsConfig.getDouble(code + ".z");
                float yaw = (float) dungeonRoomsConfig.getDouble(code + ".yaw");
                float pitch = (float) dungeonRoomsConfig.getDouble(code + ".pitch");
                Location location = new Location(world, x, y, z, yaw, pitch);

                List<Location> locations = new ArrayList<>();
                locations.add(location);

                List<Checkpoint> checkpoints = new ArrayList<>();
                int checkpointCount = dungeonRoomsConfig.getInt(code + ".checkpoints.count");

                for (int checkpointNumber = 1; checkpointNumber <= checkpointCount; checkpointNumber++) {
                    String worldStringC = dungeonRoomsConfig.getString(code + ".checkpoints.loc" + checkpointNumber + ".world");
                    World worldC = Bukkit.getWorld(worldStringC);
                    double xC = dungeonRoomsConfig.getDouble(code + ".checkpoints.loc" + checkpointNumber + ".x");
                    double yC = dungeonRoomsConfig.getDouble(code + ".checkpoints.loc" + checkpointNumber + ".y");
                    double zC = dungeonRoomsConfig.getDouble(code + ".checkpoints.loc" + checkpointNumber + ".z");
                    float yawC = (float) dungeonRoomsConfig.getDouble(code + ".checkpoints.loc" + checkpointNumber + ".yaw");
                    float pitchC = (float) dungeonRoomsConfig.getDouble(code + ".checkpoints.loc" + checkpointNumber + ".pitch");
                    Location locationC = new Location(worldC, xC, yC, zC, yawC, pitchC);

                    checkpoints.add(new Checkpoint(locationC));
                }

                DungeonTheme dungeonTheme = dungeonThemes.get(themeCode);
                DungeonRoom dungeonRoom = new DungeonRoom(dungeonTheme, i, locations, checkpoints);
                MiniGameManager.addDungeon(themeCode, i, dungeonRoom);
            }
        }
    }
}
