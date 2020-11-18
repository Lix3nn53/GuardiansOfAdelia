package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.checkpoint.Checkpoint;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.Dungeon;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.PortalColor;
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
    private static FileConfiguration dungeonsConfig;
    private static FileConfiguration dungeonGatesConfig;

    static void createConfigs() {
        createDungeonThemes();
        createDungeonGates();
        createDungeons();
    }

    static void loadConfigs() {
        loadDungeonThemes();
        loadDungeonGates();
        loadDungeons();
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

            DungeonTheme dungeonTheme = new DungeonTheme(code, name, gearTag, gearLevel, portalColor);

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

    private static void createDungeons() {
        String filePath = ConfigManager.DATA_FOLDER + File.separator + "dungeons";
        File customConfigFile = new File(filePath, "dungeons.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();

            try {
                customConfigFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        dungeonsConfig = new YamlConfiguration();
        try {
            dungeonsConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void loadDungeons() {
        HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
        for (String themeCode : dungeonThemes.keySet()) {
            for (int i = 1; i <= 2; i++) { //loading first 2 room of each dungeon
                String code = themeCode + i;
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
                String bossInternalName = dungeonsConfig.getString(code + ".bossInternalName");
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

                DungeonTheme dungeonTheme = dungeonThemes.get(themeCode);
                Dungeon dungeon = new Dungeon(levelReq, timeLimitInMinutes, dungeonTheme, i, locations, bossInternalName, checkpoints);
                MiniGameManager.addDungeon(themeCode, i, dungeon);
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
            dungeonsConfig.set(code + ".bossInternalName", dungeon.getBossInternalName());
        }
        try {
            dungeonsConfig.save(ConfigManager.DATA_FOLDER + "/dungeons.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
