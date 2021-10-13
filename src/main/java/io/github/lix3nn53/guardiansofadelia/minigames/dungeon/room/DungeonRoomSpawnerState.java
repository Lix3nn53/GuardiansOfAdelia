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
    private final List<Entity> entityList = new ArrayList<>();
    private BukkitTask secureSpawnRunner;

    public void onSpawn(List<Entity> entity) {
        entityList.addAll(entity);
    }

    public void onMobKill(Entity entity) {
        entityList.remove(entity);

        if (entityList.size() <= 0) {
            if (secureSpawnRunner != null) secureSpawnRunner.cancel();
        }
    }

    public boolean isClear() {
        return entityList.size() == 0;
    }

    public void startSecureSpawnerRunner(String mobCode, int mobLevel, Location dungeonStart, DungeonRoomSpawner spawner, int roomNo, int spawnerIndex) {
        secureSpawnRunner = new BukkitRunnable() {
            @Override
            public void run() {
                if (entityList.size() <= 0) {
                    cancel();
                    return;
                }

                List<Entity> entities = spawner.secureSpawn(mobCode, mobLevel, dungeonStart, roomNo, spawnerIndex, entityList);

                entityList.addAll(entities);
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 20 * 15L, 20 * 15L);
    }

    public void stopSecureSpawnerRunner() {
        if (secureSpawnRunner != null) secureSpawnRunner.cancel();
    }

    public void clearSpawned() {
        for (Entity entity : entityList) {
            entity.remove();
        }

        entityList.clear();
    }
}
