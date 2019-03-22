package io.github.lix3nn53.guardiansofadelia.creatures;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public final class Spawner {

    private final Location loc;
    private final String mobCode;
    private final int spawnAmount;
    private final int maxSpawnAmount;

    private List<Entity> spawnedEntities = new ArrayList<Entity>();

    public Spawner(Location loc, String mobCode, int spawnAmount, int maxSpawnAmount) {
        this.loc = loc;
        this.mobCode = mobCode;
        this.spawnAmount = spawnAmount;
        this.maxSpawnAmount = maxSpawnAmount;
    }

    public void spawn() {
        List<Entity> temporary = new ArrayList<Entity>();
        //save dead entities to temporary
        for (Entity en : spawnedEntities) {
            if (en.isDead()) {
                temporary.add(en);
            }
        }
        //remove entities on temporary from spawnedEntities
        for (Entity en : temporary) {
            spawnedEntities.remove(en);
        }
        if (spawnedEntities.size() < maxSpawnAmount) {
            for (int i = 0; i < spawnAmount; i++) {
                Entity entity = EntityList.getMob(loc, mobCode);
                spawnedEntities.add(entity);
            }
        }
    }
}
