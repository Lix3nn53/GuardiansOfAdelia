package io.github.lix3nn53.guardiansofadelia.creatures.spawners;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SpawnerManager {
    private static HashMap<Entity, Spawner> mobToSpawner = new HashMap<>();
    private static HashMap<String, List<Spawner>> chunkKeyToSpawners = new HashMap<>();
    private static List<Spawner> activeSpawners = new ArrayList<>();

    static void onSpawnerMobSpawn(Entity entity, Spawner spawner) {
        mobToSpawner.put(entity, spawner);
    }

    public static void onMobDeath(Entity entity) {
        Spawner remove = mobToSpawner.remove(entity);
        if (remove != null) {
            remove.onSpawnedEntityDeath();
        }
    }

    public static void addSpawner(Spawner spawner) {
        Location location = spawner.getLocation();
        String chunkKey = getChunkKey(location);
        if (chunkKeyToSpawners.containsKey(chunkKey)) {
            chunkKeyToSpawners.get(chunkKey).add(spawner);
        } else {
            List<Spawner> spawnerList = new ArrayList<>();
            spawnerList.add(spawner);
            chunkKeyToSpawners.put(chunkKey, spawnerList);
        }
    }

    public static void activateSpawnersOnChunk(Chunk chunk) {
        String chunkKey = getChunkKey(chunk.getBlock(0, 0, 0).getLocation());
        if (chunkKeyToSpawners.containsKey(chunkKey)) {
            activeSpawners.addAll(chunkKeyToSpawners.get(chunkKey));
        }
    }

    public static void deactivateSpawnersOnChunk(Chunk chunk) {
        String chunkKey = getChunkKey(chunk.getBlock(0, 0, 0).getLocation());
        if (chunkKeyToSpawners.containsKey(chunkKey)) {
            List<Spawner> spawners = chunkKeyToSpawners.get(chunkKey);
            activeSpawners.removeAll(spawners);
        }
    }

    public static void startSpawners() {
        new BukkitRunnable() {

            @Override
            public void run() {
                Iterator it = mobToSpawner.keySet().iterator();
                while (it.hasNext()) {
                    Entity entity = (Entity) it.next();
                    Spawner spawner = mobToSpawner.get(entity);
                    if (entity.isValid()) {
                        Location location = spawner.getLocation();
                        double v = location.distanceSquared(entity.getLocation());
                        if (v >= 1453) {
                            entity.teleport(location);
                            if (entity instanceof Monster) {
                                ((Monster) entity).setTarget(null);
                            }
                        }
                    } else {
                        it.remove();
                        entity.remove();
                        spawner.onSpawnedEntityDeath();
                    }
                }

                int howManyEachTime = 50;
                for (int i = 0; i < activeSpawners.size(); i += howManyEachTime) {
                    List<Spawner> sub = activeSpawners.subList(i, Math.min(activeSpawners.size(), i + howManyEachTime));

                    if (i > 0) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                for (Spawner spawner : sub) {
                                    spawner.spawn();
                                }
                            }
                        }.runTaskLater(GuardiansOfAdelia.getInstance(), i * 2L);
                    } else {
                        for (Spawner spawner : sub) {
                            spawner.spawn();
                        }
                    }
                }
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 20 * 40L, 20 * 20L);
    }

    public static List<Spawner> getSpawners() {
        List<Spawner> spawners = new ArrayList<>();
        for (List<Spawner> s : chunkKeyToSpawners.values()) {
            spawners.addAll(s);
        }
        return spawners;
    }

    public static String getChunkKey(Location location) {
        World world = location.getWorld();
        if (world == null) return null;
        return world.getName() + "|" + (location.getBlockX() >> 4) + "," + (location.getBlockZ() >> 4);
    }
}
