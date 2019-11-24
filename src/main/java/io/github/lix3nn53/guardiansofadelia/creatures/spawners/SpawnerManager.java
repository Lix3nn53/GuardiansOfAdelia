package io.github.lix3nn53.guardiansofadelia.creatures.spawners;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class SpawnerManager {
    private static HashMap<Entity, Spawner> mobToSpawner = new HashMap<>();
    private static HashMap<String, Set<Spawner>> chunkKeyToSpawners = new HashMap<>();
    private static Set<Spawner> activeSpawners = new HashSet<>();

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
            Set<Spawner> spawnerList = new HashSet<>();
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
            Set<Spawner> spawners = chunkKeyToSpawners.get(chunkKey);
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
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    entity.teleport(location);
                                    if (entity instanceof Monster) {
                                        ((Monster) entity).setTarget(null);
                                    }
                                }
                            }.runTask(GuardiansOfAdelia.getInstance());
                        }
                    } else {
                        it.remove();
                        spawner.onSpawnedEntityDeath();
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                entity.remove();
                            }
                        }.runTask(GuardiansOfAdelia.getInstance());
                    }
                }

                List<Spawner> activeSpawnerList = new ArrayList<>(activeSpawners);
                int howManyEachTime = 50;
                for (int i = 0; i < activeSpawnerList.size(); i += howManyEachTime) {
                    List<Spawner> sub = activeSpawnerList.subList(i, Math.min(activeSpawnerList.size(), i + howManyEachTime));

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (Spawner spawner : sub) {
                                spawner.spawn();
                            }
                        }
                    }.runTaskLater(GuardiansOfAdelia.getInstance(), i * 2L);
                }
            }
        }.runTaskTimerAsynchronously(GuardiansOfAdelia.getInstance(), 20 * 40L, 20 * 20L);
    }

    public static List<Spawner> getSpawners() {
        List<Spawner> spawners = new ArrayList<>();
        for (Set<Spawner> s : chunkKeyToSpawners.values()) {
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
