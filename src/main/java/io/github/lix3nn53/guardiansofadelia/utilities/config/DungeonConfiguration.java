package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.checkpoint.Checkpoint;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonInstance;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoom;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoomDoor;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoomSpawner;
import io.github.lix3nn53.guardiansofadelia.transportation.portals.PortalColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DungeonConfiguration {

    private static FileConfiguration dungeonThemesConfig;
    private static FileConfiguration dungeonRoomsConfig;
    private static FileConfiguration dungeonGatesConfig;
    private static final String filePath = ConfigManager.DATA_FOLDER + File.separator + "dungeons";

    static void createConfigs() {
        dungeonThemesConfig = ConfigurationUtils.createConfig(filePath, "dungeonThemes.yml");
        dungeonRoomsConfig = ConfigurationUtils.createConfig(filePath, "dungeonInstances.yml");
        dungeonGatesConfig = ConfigurationUtils.createConfig(filePath, "dungeonGates.yml");
    }

    static void loadConfigs() {
        loadDungeons();
        loadDungeonThemes();
        loadDungeonGates();
    }

    private static void loadDungeonThemes() {
        int themeCount = ConfigurationUtils.getChildComponentCount(dungeonThemesConfig, "theme");

        for (int i = 1; i <= themeCount; i++) {
            ConfigurationSection section = dungeonThemesConfig.getConfigurationSection("theme" + i);
            String code = section.getString("code");
            String name = section.getString("name");
            String gearTag = section.getString("gearTag");
            int gearLevelIndex = section.getInt("gearLevel");
            GearLevel gearLevel = GearLevel.values()[gearLevelIndex];
            String portalColorStr = section.getString("portalColor");
            PortalColor portalColor = PortalColor.valueOf(portalColorStr);

            int levelReq = section.getInt("levelReq");
            int timeLimitInMinutes = section.getInt("timeLimitInMinutes");
            String bossInternalName = section.getString("bossInternalName");

            HashMap<Integer, DungeonRoom> dungeonRooms = new HashMap<>();
            for (int roomIndex = 1; roomIndex <= 999; roomIndex++) {
                if (!section.contains("room" + roomIndex)) break;

                List<DungeonRoomDoor> dungeonRoomDoors = new ArrayList<>();
                for (int doorIndex = 1; doorIndex <= 999; doorIndex++) {
                    if (!section.contains("room" + roomIndex + ".door" + doorIndex)) break;

                    String materialStr = section.getString("room" + roomIndex + ".door" + doorIndex + ".material");
                    Material doorMaterial = Material.valueOf(materialStr);

                    double x1 = section.getDouble("room" + roomIndex + ".door" + doorIndex + ".box" + ".x1");
                    double y1 = section.getDouble("room" + roomIndex + ".door" + doorIndex + ".box" + ".y1");
                    double z1 = section.getDouble("room" + roomIndex + ".door" + doorIndex + ".box" + ".z1");
                    double x2 = section.getDouble("room" + roomIndex + ".door" + doorIndex + ".box" + ".x2");
                    double y2 = section.getDouble("room" + roomIndex + ".door" + doorIndex + ".box" + ".y2");
                    double z2 = section.getDouble("room" + roomIndex + ".door" + doorIndex + ".box" + ".z2");

                    BoundingBox boundingBox = new BoundingBox(x1, y1, z1, x2, y2, z2);

                    DungeonRoomDoor dungeonRoomDoor = new DungeonRoomDoor(doorMaterial, boundingBox);
                    dungeonRoomDoors.add(dungeonRoomDoor);
                }

                HashMap<Integer, List<DungeonRoomSpawner>> waveToSpawners = new HashMap<>();
                for (int waveIndex = 1; waveIndex <= 999; waveIndex++) {
                    if (!section.contains("room" + roomIndex + ".wave" + waveIndex)) break;

                    List<DungeonRoomSpawner> spawners = new ArrayList<>();
                    for (int spawnerIndex = 1; spawnerIndex <= 999; spawnerIndex++) {
                        if (!section.contains("room" + roomIndex + ".wave" + waveIndex + ".spawner" + spawnerIndex))
                            break;

                        String mobCode = section.getString("room" + roomIndex + ".wave" + waveIndex + ".spawner" + spawnerIndex + ".mobCode");
                        int mobLevel = section.getInt("room" + roomIndex + ".wave" + waveIndex + ".spawner" + spawnerIndex + ".mobLevel");
                        int amount = section.getInt("room" + roomIndex + ".wave" + waveIndex + ".spawner" + spawnerIndex + ".amount");

                        double x = section.getDouble("room" + roomIndex + ".wave" + waveIndex + ".spawner" + spawnerIndex + ".offset" + ".x");
                        double y = section.getDouble("room" + roomIndex + ".wave" + waveIndex + ".spawner" + spawnerIndex + ".offset" + ".y");
                        double z = section.getDouble("room" + roomIndex + ".wave" + waveIndex + ".spawner" + spawnerIndex + ".offset" + ".z");

                        Vector offset = new Vector(x, y, z);

                        DungeonRoomSpawner spawner = new DungeonRoomSpawner(mobCode, mobLevel, amount, offset);
                        spawners.add(spawner);
                    }

                    waveToSpawners.put(waveIndex, spawners);
                }

                List<Integer> nextRooms = section.getIntegerList("room" + roomIndex + ".nextRooms");

                DungeonRoom dungeonRoom = new DungeonRoom(dungeonRoomDoors, waveToSpawners, nextRooms);
                dungeonRooms.put(roomIndex, dungeonRoom);
            }

            List<Integer> startingRooms = section.getIntegerList("startingRooms");


            DungeonTheme dungeonTheme = new DungeonTheme(code, name, gearTag, gearLevel, portalColor, levelReq, timeLimitInMinutes,
                    bossInternalName, dungeonRooms, startingRooms);

            MiniGameManager.addDungeonTheme(code, dungeonTheme);
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

    private static void loadDungeons() {
        HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
        for (String themeCode : dungeonThemes.keySet()) {
            int roomCount = ConfigurationUtils.getChildComponentCount(dungeonRoomsConfig, themeCode);

            for (int i = 1; i <= roomCount; i++) {
                String code = themeCode + i;
                String worldString = dungeonRoomsConfig.getString(code + ".start.world");
                World world = Bukkit.getWorld(worldString);
                double x = dungeonRoomsConfig.getDouble(code + ".start.x");
                double y = dungeonRoomsConfig.getDouble(code + ".start.y");
                double z = dungeonRoomsConfig.getDouble(code + ".start.z");
                float yaw = (float) dungeonRoomsConfig.getDouble(code + ".start.yaw");
                float pitch = (float) dungeonRoomsConfig.getDouble(code + ".start.pitch");
                Location start = new Location(world, x, y, z, yaw, pitch);

                List<Location> locations = new ArrayList<>();
                locations.add(start);

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
                DungeonInstance dungeonInstance = new DungeonInstance(dungeonTheme, i, locations, checkpoints);
                MiniGameManager.addDungeon(themeCode, i, dungeonInstance);
            }
        }
    }
}
