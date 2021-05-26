package io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DungeonRoom {

    private final List<DungeonRoomDoor> doors = new ArrayList<>();
    private final HashMap<Integer, List<DungeonRoomSpawner>> waveToSpawners = new HashMap<>();
    /**
     * Rooms to start after this room ends
     */
    private final List<Integer> nextRooms = new ArrayList<>();

    public void onDungeonStart() {
        for (DungeonRoomDoor door : doors) {
            door.close();
        }
    }

    public void onRoomStart(DungeonRoomState state) {
        for (DungeonRoomDoor door : doors) {
            door.close();
        }

        List<DungeonRoomSpawner> spawners = waveToSpawners.get(1);
        for (DungeonRoomSpawner spawner : spawners) {
            List<Entity> spawned = spawner.spawn();
            state.onMobSpawn(spawned.size());
        }
    }

    /**
     * @param players
     * @param roomNo
     * @return true if room completed, false otherwise
     */
    public boolean onMobKill(DungeonRoomState state, List<Player> players, int roomNo, String mobCode) {
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

        state.onMobKill(1);

        if (state.isClear()) {
            if (waveToSpawners.containsKey(currentWave + 1)) {
                state.onNextWaveStart();

                for (Player player : players) {
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Room-" + roomNo + " wave-" + currentWave + " incoming!");
                }

                for (DungeonRoomSpawner spawner : spawners) {
                    List<Entity> spawned = spawner.spawn();
                    state.onMobSpawn(spawned.size());
                }
            } else {
                return true;
            }
        }

        return false;
    }

    public List<Integer> onRoomEnd() {
        for (DungeonRoomDoor door : doors) {
            door.open();
        }

        return nextRooms;
    }
}
