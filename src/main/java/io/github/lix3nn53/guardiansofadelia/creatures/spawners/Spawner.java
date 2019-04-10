package io.github.lix3nn53.guardiansofadelia.creatures.spawners;

import io.github.lix3nn53.guardiansofadelia.creatures.EntityList;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class Spawner {

    private final Location location;
    private final String mobCode;
    private final int amountPerSpawn;
    private final int maxAmount;
    private int spawnedEntityAmount = 0;

    public Spawner(Location location, String mobCode, int amountPerSpawn, int maxAmount) {
        this.location = location;
        this.mobCode = mobCode;
        this.amountPerSpawn = amountPerSpawn;
        this.maxAmount = maxAmount;
    }

    public void spawn() {
        for (int i = 0; i < amountPerSpawn; i++) {
            if (spawnedEntityAmount < maxAmount) {
                Entity entity = EntityList.getMob(location, mobCode);
                spawnedEntityAmount++;
                SpawnerManager.onSpawnerMobSpawn(entity, this);
            }
        }
    }

    public void onSpawnedEntityDeath() {
        spawnedEntityAmount--;
    }

    public void resetEntityCount() {
        spawnedEntityAmount = 0;
    }

    public String getMobCode() {
        return mobCode;
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
