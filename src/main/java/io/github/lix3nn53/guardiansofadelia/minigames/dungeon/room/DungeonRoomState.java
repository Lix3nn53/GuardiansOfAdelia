package io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room;

import java.util.HashMap;
import java.util.List;

public class DungeonRoomState {
    private int currentWave = 1;

    public int getCurrentWave() {
        return currentWave;
    }

    public void onNextWaveStart() {
        currentWave++;
    }

    public void reset() {
        currentWave = 1;
    }

    public static boolean isClear(HashMap<Integer, List<DungeonRoomSpawnerState>> wavesToSpawnerStates) {
        for (List<DungeonRoomSpawnerState> spawnerStates : wavesToSpawnerStates.values()) {
            for (DungeonRoomSpawnerState spawnerState : spawnerStates) {
                if (!spawnerState.isClear()) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean isNextWave(List<DungeonRoomSpawnerState> spawnerStates) {
        for (DungeonRoomSpawnerState spawnerState : spawnerStates) {
            if (!spawnerState.isClear()) {
                return false;
            }
        }

        return true;
    }
}
