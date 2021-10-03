package io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class DungeonRoomSpawnerState {
    // State
    BukkitTask secureSpawnRunner;
    private int mobsLeftToKill;

    public DungeonRoomSpawnerState(int mobsLeftToKill) {
        this.mobsLeftToKill = mobsLeftToKill;
    }

    public void onMobKill(int count) {
        mobsLeftToKill -= count;

        if (mobsLeftToKill <= 0) {
            secureSpawnRunner.cancel();
        }
    }

    public boolean isClear() {
        return mobsLeftToKill == 0;
    }

    public void reset(int mobsLeftToKill) {
        this.mobsLeftToKill = mobsLeftToKill;
        secureSpawnRunner.cancel();
    }

    public void startSecureSpawnerRunner(Location dungeonStart, DungeonRoomSpawner spawner, int spawnerIndex) {
        secureSpawnRunner = new BukkitRunnable() {
            @Override
            public void run() {
                if (mobsLeftToKill <= 0) {
                    cancel();
                    return;
                }

                spawner.secureSpawn(dungeonStart, spawnerIndex, mobsLeftToKill);
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 20 * 15L, 20 * 15L);
    }
}
