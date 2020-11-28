package io.github.lix3nn53.guardiansofadelia.rewards.chest;

import io.github.lix3nn53.guardiansofadelia.utilities.LocationUtils;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LootChestManager {

    private static final HashMap<String, List<LootChest>> chunkKeyToLootChests = new HashMap<>();

    public static void onChunkLoad(String chunkKey) {
        if (chunkKeyToLootChests.containsKey(chunkKey)) {
            List<LootChest> lootChests = chunkKeyToLootChests.get(chunkKey);

            for (LootChest lootChest : lootChests) {
                lootChest.startPlayingParticles();
            }
        }
    }

    public static void onChunkUnload(String chunkKey) {
        if (chunkKeyToLootChests.containsKey(chunkKey)) {
            List<LootChest> lootChests = chunkKeyToLootChests.get(chunkKey);

            for (LootChest lootChest : lootChests) {
                lootChest.stopPlayingParticles();
            }
        }
    }

    public static LootChest isLootChest(Location location) {
        String chunkKey = LocationUtils.getChunkKey(location);
        if (chunkKeyToLootChests.containsKey(chunkKey)) {
            List<LootChest> lootChests = chunkKeyToLootChests.get(chunkKey);

            for (LootChest lootChest : lootChests) {
                double v = lootChest.getLocation().distanceSquared(location);
                if (v < 4) {
                    return lootChest;
                }
            }
        }

        return null;
    }

    public static void addLootChest(LootChest lootChest) {
        String chunkKey = LocationUtils.getChunkKey(lootChest.getLocation());
        if (chunkKeyToLootChests.containsKey(chunkKey)) {
            List<LootChest> list = chunkKeyToLootChests.get(chunkKey);
            list.add(lootChest);
            chunkKeyToLootChests.put(chunkKey, list);
        } else {
            List<LootChest> holograms = new ArrayList<>();
            holograms.add(lootChest);
            chunkKeyToLootChests.put(chunkKey, holograms);
        }
    }

    public static HashMap<String, List<LootChest>> getChunkKeyToLootChests() {
        return chunkKeyToLootChests;
    }
}
