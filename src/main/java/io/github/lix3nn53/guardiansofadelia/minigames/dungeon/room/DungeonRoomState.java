package io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room;

import org.bukkit.Bukkit;

public class DungeonRoomState {
    private int currentWave = 1;
    private int activeMobs = 0;

    public int getCurrentWave() {
        return currentWave;
    }

    public void onMobSpawn(int count) {
        activeMobs += count;
    }

    public void onMobKill(int count) {
        activeMobs -= count;
    }

    public void onNextWaveStart() {
        currentWave++;
    }

    public boolean isClear() {
        Bukkit.getPlayer("Lix3nn").sendMessage("activeMobs: " + activeMobs);
        return activeMobs == 0;
    }

    public void reset() {
        currentWave = 1;
        activeMobs = 0;
    }
}
