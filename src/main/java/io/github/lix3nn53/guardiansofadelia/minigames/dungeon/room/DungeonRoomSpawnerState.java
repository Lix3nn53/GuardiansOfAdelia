package io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class DungeonRoomSpawnerState {
    // State
    private List<Entity> currentMobs = new ArrayList<>(); // just to be able to remove them
    private int mobsLeftToKill; // the actual count to determine if the spawner is completed
    private BukkitTask secureSpawnRunner; // to respawn mobs when something went wrong with first spawned mobs

    public void onFirstSpawn(List<Entity> spawned) {
        currentMobs.addAll(spawned);
        mobsLeftToKill = spawned.size();
    }

    public void onMobKill(Entity entity) {
        currentMobs.remove(entity);
        mobsLeftToKill--;

        if (mobsLeftToKill <= 0) {
            if (secureSpawnRunner != null) secureSpawnRunner.cancel();
        }
    }

    public boolean isClear() {
        return mobsLeftToKill <= 0;
    }

    public void startSecureSpawnerRunner(String mobCode, int mobLevel, Location dungeonStart, DungeonRoomSpawner spawner, int roomNo, int spawnerIndex) {
        secureSpawnRunner = new BukkitRunnable() {
            @Override
            public void run() {
                if (mobsLeftToKill <= 0) {
                    cancel();
                    return;
                }

                List<Entity> validCurrent = new ArrayList<>();
                for (Entity e : currentMobs) {
                    if (e.isValid()) validCurrent.add(e);
                }
                currentMobs = validCurrent;

                if (mobsLeftToKill - currentMobs.size() <= 0) return;

                List<Entity> entities = spawner.secureSpawn(mobCode, mobLevel, dungeonStart, roomNo, spawnerIndex, mobsLeftToKill);

                currentMobs.addAll(entities);
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 20 * 15L, 20 * 15L);
    }

    public void stopSecureSpawnerRunner() {
        if (secureSpawnRunner != null) secureSpawnRunner.cancel();
    }

    public void clearSpawned() {
        for (Entity entity : currentMobs) {
            entity.remove();
        }

        currentMobs.clear();
    }
}
