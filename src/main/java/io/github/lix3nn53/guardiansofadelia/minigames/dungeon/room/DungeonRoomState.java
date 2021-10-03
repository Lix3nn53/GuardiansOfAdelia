package io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room;

import java.util.List;

public class DungeonRoomState {
    private int currentWave = 1;
    private boolean isClear = false;

    public int getCurrentWave() {
        return currentWave;
    }

    public void onNextWaveStart() {
        currentWave++;
    }

    public static boolean isWaveCompleted(List<DungeonRoomSpawnerState> spawnerStates) {
        for (DungeonRoomSpawnerState spawnerState : spawnerStates) {
            if (!spawnerState.isClear()) {
                return false;
            }
        }

        return true;
    }

    public void reset() {
        currentWave = 1;
        isClear = false;
    }

    public boolean isClear() {
        return isClear;
    }

    public void onComplete() {
        isClear = true;
    }
}
