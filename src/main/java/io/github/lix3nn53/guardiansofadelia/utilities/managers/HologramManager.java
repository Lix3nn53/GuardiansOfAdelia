package io.github.lix3nn53.guardiansofadelia.utilities.managers;

import io.github.lix3nn53.guardiansofadelia.creatures.spawners.SpawnerManager;
import io.github.lix3nn53.guardiansofadelia.utilities.hologram.Hologram;
import org.bukkit.entity.ArmorStand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HologramManager {

    private static HashMap<String, List<Hologram>> chunkKeyToHologram = new HashMap<>();

    public static void onChunkLoad(String chunkKey) {
        if (chunkKeyToHologram.containsKey(chunkKey)) {
            List<Hologram> holograms = chunkKeyToHologram.get(chunkKey);
            for (Hologram hologram : holograms) {
                hologram.respawn();
            }
        }
    }

    public static void removeHologram(Hologram hologram) {
        String chunkKey = SpawnerManager.getChunkKey(hologram.getLocation());
        if (chunkKeyToHologram.containsKey(chunkKey)) {
            List<Hologram> holograms = chunkKeyToHologram.get(chunkKey);
            holograms.remove(hologram);
            if (holograms.isEmpty()) {
                chunkKeyToHologram.remove(chunkKey);
            } else {
                chunkKeyToHologram.put(chunkKey, holograms);
            }
        }
    }

    public static void addHologram(Hologram hologram) {
        String chunkKey = SpawnerManager.getChunkKey(hologram.getLocation());
        if (chunkKeyToHologram.containsKey(chunkKey)) {
            List<Hologram> holograms = chunkKeyToHologram.get(chunkKey);
            holograms.add(hologram);
            chunkKeyToHologram.put(chunkKey, holograms);
        } else {
            List<Hologram> holograms = new ArrayList<>();
            holograms.add(hologram);
            chunkKeyToHologram.put(chunkKey, holograms);
        }
    }

    public static Hologram getHologramFromArmorStand(ArmorStand armorStand) {
        for (String key : chunkKeyToHologram.keySet()) {
            List<Hologram> holograms = chunkKeyToHologram.get(key);
            for (Hologram hologram : holograms) {
                if (hologram.getArmorStand() != null) {
                    if (hologram.getArmorStand().equals(armorStand)) {
                        return hologram;
                    }
                }
            }
        }
        return null;
    }
}
