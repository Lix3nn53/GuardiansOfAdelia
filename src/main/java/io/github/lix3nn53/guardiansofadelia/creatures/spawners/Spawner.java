package io.github.lix3nn53.guardiansofadelia.creatures.spawners;

import io.github.lix3nn53.guardiansofadelia.creatures.AdeliaEntity;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class Spawner {

    private final Location location;
    private final AdeliaEntity adeliaEntity;
    private final int amountPerSpawn;
    private final int maxAmount;
    private int spawnedEntityAmount = 0;

    public Spawner(Location location, AdeliaEntity adeliaEntity, int amountPerSpawn, int maxAmount) {
        this.location = location;
        this.adeliaEntity = adeliaEntity;
        this.amountPerSpawn = amountPerSpawn;
        this.maxAmount = maxAmount;
    }

    public void spawn() {
        for (int i = 0; i < amountPerSpawn; i++) {
            if (spawnedEntityAmount < maxAmount) {
                Entity entity = adeliaEntity.getMob(location);
                spawnedEntityAmount++;
                SpawnerManager.onSpawnerMobSpawn(entity, this);
            }
        }
    }

    public void onSpawnedEntityDeath() {
        spawnedEntityAmount--;
    }

    public AdeliaEntity getAdeliaEntity() {
        return adeliaEntity;
    }

    public Location getLocation() {
        return location;
    }

    public int getAmountPerSpawn() {
        return amountPerSpawn;
    }

    public int getMaxAmount() {
        return maxAmount;
    }
}
