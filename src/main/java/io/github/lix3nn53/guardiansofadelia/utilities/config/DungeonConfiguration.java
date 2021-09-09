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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DungeonConfiguration {

    private static FileConfiguration dungeonThemesConfig;
    private static FileConfiguration dungeonInstancesConfig;
    private static FileConfiguration dungeonGatesConfig;
    private static final String filePath = ConfigManager.DATA_FOLDER + File.separator + "dungeons";

    public static void createConfigs() {
        dungeonThemesConfig = ConfigurationUtils.createConfig(filePath, "dungeonThemes.yml");
        dungeonInstancesConfig = ConfigurationUtils.createConfig(filePath, "dungeonInstances.yml");
        dungeonGatesConfig = ConfigurationUtils.createConfig(filePath, "dungeonGates.yml");
    }

    public static void loadConfigs() {
        loadDungeonThemes();
        loadDungeonGates();
        loadInstances();
    }

    public static void writeConfigs() {
        writeDungeonThemes("dungeonThemes.yml");
        writeInstances("dungeonInstances.yml");
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

            // Checkpoints
            List<Vector> checkpoints = new ArrayList<>();
            int checkpointCount = section.getInt("checkpoints.count");
            for (int checkpointNumber = 1; checkpointNumber <= checkpointCount; checkpointNumber++) {

                double xC = section.getDouble("checkpoints.loc" + checkpointNumber + ".x");
                double yC = section.getDouble("checkpoints.loc" + checkpointNumber + ".y");
                double zC = section.getDouble("checkpoints.loc" + checkpointNumber + ".z");

                Vector vector = new Vector(xC, yC, zC);

                checkpoints.add(vector);
            }

            Vector prizeChestCenterOffset = new Vector(0, 0, 0);
            if (section.contains("prizeChestCenter.x")) {
                double x = section.getDouble("prizeChestCenter.x");
                double y = section.getDouble("prizeChestCenter.y");
                double z = section.getDouble("prizeChestCenter.z");
                prizeChestCenterOffset = new Vector(x, y, z);
            }

            DungeonTheme dungeonTheme = new DungeonTheme(code, name, gearTag, gearLevel, portalColor, levelReq, timeLimitInMinutes,
                    bossInternalName, dungeonRooms, startingRooms, checkpoints, prizeChestCenterOffset);

            MiniGameManager.addDungeonTheme(code, dungeonTheme);
        }
    }

    private static void writeDungeonThemes(String fileName) {
        HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();

        int i = 1;
        for (String code : dungeonThemes.keySet()) {
            DungeonTheme theme = dungeonThemes.get(code);

            dungeonThemesConfig.set("theme" + i + ".code", code);
            dungeonThemesConfig.set("theme" + i + ".name", theme.getName().replaceAll("§", "&"));
            dungeonThemesConfig.set("theme" + i + ".gearTag", theme.getGearTag());
            dungeonThemesConfig.set("theme" + i + ".gearLevel", theme.getGearLevel().ordinal());
            dungeonThemesConfig.set("theme" + i + ".portalColor", theme.getPortalColor().name());

            dungeonThemesConfig.set("theme" + i + ".levelReq", theme.getLevelReq());
            dungeonThemesConfig.set("theme" + i + ".timeLimitInMinutes", theme.getTimeLimitInMinutes());
            dungeonThemesConfig.set("theme" + i + ".bossInternalName", theme.getBossInternalName());

            Set<Integer> roomKeys = theme.getDungeonRoomKeys();
            int roomIndex = 1;
            for (int key : roomKeys) {
                DungeonRoom dungeonRoom = theme.getDungeonRoom(key);

                List<DungeonRoomDoor> doors = dungeonRoom.getDoors();
                int doorIndex = 1;
                for (DungeonRoomDoor door : doors) {

                    dungeonThemesConfig.set("theme" + i + ".room" + roomIndex + ".door" + doorIndex + ".material", door.getMaterial().name());

                    BoundingBox boundingBox = door.getBoundingBox();

                    dungeonThemesConfig.set("theme" + i + ".room" + roomIndex + ".door" + doorIndex + ".box" + ".x1", boundingBox.getMinX());
                    dungeonThemesConfig.set("theme" + i + ".room" + roomIndex + ".door" + doorIndex + ".box" + ".y1", boundingBox.getMinY());
                    dungeonThemesConfig.set("theme" + i + ".room" + roomIndex + ".door" + doorIndex + ".box" + ".z1", boundingBox.getMinZ());
                    dungeonThemesConfig.set("theme" + i + ".room" + roomIndex + ".door" + doorIndex + ".box" + ".x2", boundingBox.getMaxX());
                    dungeonThemesConfig.set("theme" + i + ".room" + roomIndex + ".door" + doorIndex + ".box" + ".y2", boundingBox.getMaxY());
                    dungeonThemesConfig.set("theme" + i + ".room" + roomIndex + ".door" + doorIndex + ".box" + ".z2", boundingBox.getMaxZ());

                    doorIndex++;
                }

                HashMap<Integer, List<DungeonRoomSpawner>> waveToSpawners = dungeonRoom.getWaveToSpawners();
                for (int waveIndex : waveToSpawners.keySet()) {
                    List<DungeonRoomSpawner> dungeonRoomSpawners = waveToSpawners.get(waveIndex);

                    int spawnerIndex = 1;
                    for (DungeonRoomSpawner spawner : dungeonRoomSpawners) {
                        dungeonThemesConfig.set("theme" + i + ".room" + roomIndex + ".wave" + waveIndex + ".spawner" + spawnerIndex + ".mobCode", spawner.getMobCode());
                        dungeonThemesConfig.set("theme" + i + ".room" + roomIndex + ".wave" + waveIndex + ".spawner" + spawnerIndex + ".mobLevel", spawner.getMobLevel());
                        dungeonThemesConfig.set("theme" + i + ".room" + roomIndex + ".wave" + waveIndex + ".spawner" + spawnerIndex + ".amount", spawner.getAmount());

                        Vector offset = spawner.getOffset();

                        dungeonThemesConfig.set("theme" + i + ".room" + roomIndex + ".wave" + waveIndex + ".spawner" + spawnerIndex + ".offset" + ".x", offset.getX());
                        dungeonThemesConfig.set("theme" + i + ".room" + roomIndex + ".wave" + waveIndex + ".spawner" + spawnerIndex + ".offset" + ".y", offset.getY());
                        dungeonThemesConfig.set("theme" + i + ".room" + roomIndex + ".wave" + waveIndex + ".spawner" + spawnerIndex + ".offset" + ".z", offset.getZ());

                        spawnerIndex++;
                    }
                }

                dungeonThemesConfig.set("theme" + i + ".room" + roomIndex + ".nextRooms", dungeonRoom.getNextRooms());

                roomIndex++;
            }

            dungeonThemesConfig.set("theme" + i + ".startingRooms", theme.getStartingRooms());

            // Checkpoints
            List<Vector> checkpointOffsets = theme.getCheckpointOffsets();
            dungeonThemesConfig.set("theme" + i + ".checkpoints.count", checkpointOffsets.size());
            int checkpointNumber = 1;
            for (Vector checkpointOffset : checkpointOffsets) {
                dungeonThemesConfig.set("theme" + i + ".checkpoints.loc" + checkpointNumber + ".x", checkpointOffset.getX());
                dungeonThemesConfig.set("theme" + i + ".checkpoints.loc" + checkpointNumber + ".y", checkpointOffset.getY());
                dungeonThemesConfig.set("theme" + i + ".checkpoints.loc" + checkpointNumber + ".z", checkpointOffset.getZ());

                checkpointNumber++;
            }

            Vector prizeChestCenterOffset = theme.getPrizeChestCenterOffset();
            dungeonThemesConfig.set("theme" + i + ".prizeChestCenter.x", prizeChestCenterOffset.getX());
            dungeonThemesConfig.set("theme" + i + ".prizeChestCenter.y", prizeChestCenterOffset.getY());
            dungeonThemesConfig.set("theme" + i + ".prizeChestCenter.z", prizeChestCenterOffset.getZ());

            i++;
        }

        try {
            dungeonThemesConfig.save(filePath + File.separator + fileName);
        } catch (IOException e) {
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
            MiniGameManager.addDungeonPortal(location, code);
        }
    }

    private static void loadInstances() {
        HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
        for (String themeCode : dungeonThemes.keySet()) {
            int roomCount = ConfigurationUtils.getChildComponentCount(dungeonInstancesConfig, themeCode);

            for (int i = 1; i <= roomCount; i++) {
                String code = themeCode + i;
                String worldString = dungeonInstancesConfig.getString(code + ".start.world");
                World world = Bukkit.getWorld(worldString);
                double x = dungeonInstancesConfig.getDouble(code + ".start.x");
                double y = dungeonInstancesConfig.getDouble(code + ".start.y");
                double z = dungeonInstancesConfig.getDouble(code + ".start.z");
                float yaw = (float) dungeonInstancesConfig.getDouble(code + ".start.yaw");
                float pitch = (float) dungeonInstancesConfig.getDouble(code + ".start.pitch");
                Location start = new Location(world, x, y, z, yaw, pitch);

                List<Location> locations = new ArrayList<>();
                locations.add(start);

                DungeonTheme dungeonTheme = dungeonThemes.get(themeCode);

                List<Vector> checkpointOffsets = dungeonTheme.getCheckpointOffsets();

                List<Checkpoint> checkpoints = new ArrayList<>();
                for (Vector offset : checkpointOffsets) {
                    Location add = start.clone().add(offset);
                    Checkpoint checkpoint = new Checkpoint(add);
                    checkpoints.add(checkpoint);
                }

                DungeonInstance dungeonInstance = new DungeonInstance(dungeonTheme, i, locations, checkpoints);
                MiniGameManager.addDungeonInstance(themeCode, i, dungeonInstance);
            }
        }
    }

    private static void writeInstances(String fileName) {
        HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();

        for (String themeCode : dungeonThemes.keySet()) {
            for (int i = 1; i < 999; i++) {
                if (MiniGameManager.dungeonInstanceExists(themeCode, i)) {
                    DungeonInstance dungeonInstance = MiniGameManager.getDungeonInstance(themeCode, i);

                    String code = themeCode + i;

                    Location startLocation = dungeonInstance.getStartLocation(1);
                    dungeonInstancesConfig.set(code + ".start.world", startLocation.getWorld().getName());
                    dungeonInstancesConfig.set(code + ".start.x", startLocation.getX());
                    dungeonInstancesConfig.set(code + ".start.y", startLocation.getY());
                    dungeonInstancesConfig.set(code + ".start.z", startLocation.getZ());
                    dungeonInstancesConfig.set(code + ".start.yaw", startLocation.getYaw());
                    dungeonInstancesConfig.set(code + ".start.pitch", startLocation.getPitch());
                } else {
                    break;
                }
            }
        }

        try {
            dungeonInstancesConfig.save(filePath + File.separator + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
