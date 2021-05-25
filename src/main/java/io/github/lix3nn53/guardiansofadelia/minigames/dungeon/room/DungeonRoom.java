package io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class DungeonRoom {

    Location center;
    List<DungeonRoomDoor> doors;
    private final HashMap<Integer, List<DungeonRoomSpawner>> waveToSpawners = new HashMap<>();

    private int currentRoom = 1;
    private int activeMobs = 0;

    public void onDungeonStart() {
        for (DungeonRoomDoor door : doors) {
            door.close();
        }
    }

    public void onRoomStart() {
        for (DungeonRoomDoor door : doors) {
            door.close();
        }

        List<DungeonRoomSpawner> spawners = waveToSpawners.get(1);
        for (DungeonRoomSpawner spawner : spawners) {
            List<Entity> spawned = spawner.spawn();
            activeMobs += spawned.size();
        }
    }

    /**
     * @param players
     * @param roomNo
     * @return true if room completed, false otherwise
     */
    public boolean onDungeonPlayerMobKill(List<Player> players, int roomNo) {
        activeMobs -= 1;

        if (activeMobs == 0) {
            if (waveToSpawners.containsKey(currentRoom + 1)) {
                currentRoom += 1;

                for (Player player : players) {
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Room-" + roomNo + " wave-" + currentRoom + " incoming!");
                }

                List<DungeonRoomSpawner> spawners = waveToSpawners.get(currentRoom);
                for (DungeonRoomSpawner spawner : spawners) {
                    List<Entity> spawned = spawner.spawn();
                    activeMobs += spawned.size();
                }
            } else {
                return true;
            }
        }

        return false;
    }

    public void onRoomEnd() {
        for (DungeonRoomDoor door : doors) {
            door.open();
        }
    }
}
