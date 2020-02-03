package io.github.lix3nn53.guardiansofadelia.minigames.checkpoint;

import io.github.lix3nn53.guardiansofadelia.creatures.spawners.SpawnerManager;
import org.bukkit.entity.ArmorStand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CheckpointManager {

    private static HashMap<String, List<Checkpoint>> chunkKeyToCheckpoint = new HashMap<>();

    public static void onChunkLoad(String chunkKey) {
        if (chunkKeyToCheckpoint.containsKey(chunkKey)) {
            List<Checkpoint> checkpoints = chunkKeyToCheckpoint.get(chunkKey);
            for (Checkpoint checkpoint : checkpoints) {
                checkpoint.createModel();
            }
        }
    }

    public static void removeCheckpoint(Checkpoint checkpoint) {
        String chunkKey = SpawnerManager.getChunkKey(checkpoint.getLocation());
        if (chunkKeyToCheckpoint.containsKey(chunkKey)) {
            List<Checkpoint> checkpoints = chunkKeyToCheckpoint.get(chunkKey);
            checkpoints.remove(checkpoint);
            if (checkpoints.isEmpty()) {
                chunkKeyToCheckpoint.remove(chunkKey);
            } else {
                chunkKeyToCheckpoint.put(chunkKey, checkpoints);
            }
        }
    }

    public static void addCheckpoint(Checkpoint checkpoint) {
        String chunkKey = SpawnerManager.getChunkKey(checkpoint.getLocation());
        if (chunkKeyToCheckpoint.containsKey(chunkKey)) {
            List<Checkpoint> checkpoints = chunkKeyToCheckpoint.get(chunkKey);
            checkpoints.add(checkpoint);
            chunkKeyToCheckpoint.put(chunkKey, checkpoints);
        } else {
            List<Checkpoint> checkpoints = new ArrayList<>();
            checkpoints.add(checkpoint);
            chunkKeyToCheckpoint.put(chunkKey, checkpoints);
        }
    }

    public static Checkpoint getCheckpointFromArmorStand(ArmorStand armorStand) {
        for (String key : chunkKeyToCheckpoint.keySet()) {
            List<Checkpoint> checkpoints = chunkKeyToCheckpoint.get(key);
            for (Checkpoint checkpoint : checkpoints) {
                if (checkpoint.getArmorStand() != null) {
                    if (checkpoint.getArmorStand().equals(armorStand)) {
                        return checkpoint;
                    }
                }
            }
        }
        return null;
    }
}
