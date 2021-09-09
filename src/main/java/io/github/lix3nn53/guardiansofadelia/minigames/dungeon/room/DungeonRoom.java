package io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DungeonRoom {

    private final List<DungeonRoomDoor> doors;
    private final HashMap<Integer, List<DungeonRoomSpawner>> waveToSpawners;
    /**
     * Rooms to start after this room ends
     */
    private final List<Integer> nextRooms;

    public DungeonRoom(List<DungeonRoomDoor> doors, HashMap<Integer, List<DungeonRoomSpawner>> waveToSpawners, List<Integer> nextRooms) {
        this.doors = doors;
        this.waveToSpawners = waveToSpawners;
        this.nextRooms = nextRooms;
    }

    public void onDungeonStart(Location dungeonStart) {
        for (DungeonRoomDoor door : doors) {
            door.close(dungeonStart);
        }
    }

    public void onRoomStart(DungeonRoomState state, Location dungeonStart) {
        for (DungeonRoomDoor door : doors) {
            door.close(dungeonStart);
        }

        List<DungeonRoomSpawner> spawners = waveToSpawners.get(1);
        for (DungeonRoomSpawner spawner : spawners) {
            List<Entity> spawned = spawner.spawn(dungeonStart);
            state.onMobSpawn(spawned.size());
        }
    }

    /**
     * @param players
     * @param roomNo
     * @return true if room completed, false otherwise
     */
    public boolean onMobKill(DungeonRoomState state, List<Player> players, int roomNo, String mobCode, Location dungeonStart) {
        for (Player player : players) {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "DEBUG-1");
        }

        boolean thisRoomsMob = false;

        int currentWave = state.getCurrentWave();

        List<DungeonRoomSpawner> spawners = waveToSpawners.get(currentWave);
        for (DungeonRoomSpawner spawner : spawners) {
            String spawnerMobCode = spawner.getMobCode();
            if (spawnerMobCode.equals(mobCode)) {
                thisRoomsMob = true;
                break;
            }
        }

        if (!thisRoomsMob) {
            return false;
        }

        for (Player player : players) {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "DEBUG-2");
        }

        state.onMobKill(1);

        if (state.isClear()) {
            currentWave++;

            if (waveToSpawners.containsKey(currentWave)) {
                state.onNextWaveStart();

                for (Player player : players) {
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Room-" + roomNo + " wave-" + currentWave + " incoming!");
                }

                spawners = waveToSpawners.get(currentWave);

                for (DungeonRoomSpawner spawner : spawners) {
                    List<Entity> spawned = spawner.spawn(dungeonStart);
                    state.onMobSpawn(spawned.size());
                }
            } else {
                for (Player player : players) {
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Room-" + roomNo + " completed!");
                }

                return true;
            }
        }

        return false;
    }

    public List<Integer> onRoomEnd(Location dungeonStart) {
        for (DungeonRoomDoor door : doors) {
            door.open(dungeonStart);
        }

        return nextRooms;
    }

    public void addSpawner(int wave, DungeonRoomSpawner spawner) {
        List<DungeonRoomSpawner> spawners = new ArrayList<>();
        if (waveToSpawners.containsKey(wave)) {
            spawners = waveToSpawners.get(wave);
        }

        spawners.add(spawner);
        waveToSpawners.put(wave, spawners);
    }

    public void addDoor(DungeonRoomDoor door) {
        doors.add(door);
    }

    public List<DungeonRoomDoor> getDoors() {
        return doors;
    }

    public HashMap<Integer, List<DungeonRoomSpawner>> getWaveToSpawners() {
        return waveToSpawners;
    }

    public List<Integer> getNextRooms() {
        return nextRooms;
    }
}
