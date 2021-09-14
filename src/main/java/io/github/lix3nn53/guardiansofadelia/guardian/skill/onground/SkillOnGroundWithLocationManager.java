package io.github.lix3nn53.guardiansofadelia.guardian.skill.onground;

import io.github.lix3nn53.guardiansofadelia.utilities.LocationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SkillOnGroundWithLocationManager {

    private static final HashMap<String, List<SkillOnGroundWithLocation>> chunkKeyToHologram = new HashMap<>();

    public static void onChunkLoad(String chunkKey) {
        if (chunkKeyToHologram.containsKey(chunkKey)) {
            List<SkillOnGroundWithLocation> holograms = chunkKeyToHologram.get(chunkKey);
            for (SkillOnGroundWithLocation hologram : holograms) {
                hologram.activate(40);
            }
        }
    }

    public static void remove(SkillOnGroundWithLocation hologram) {
        String chunkKey = LocationUtils.getChunkKey(hologram.getLocation());
        if (chunkKeyToHologram.containsKey(chunkKey)) {
            List<SkillOnGroundWithLocation> holograms = chunkKeyToHologram.get(chunkKey);
            holograms.remove(hologram);
            if (holograms.isEmpty()) {
                chunkKeyToHologram.remove(chunkKey);
            } else {
                chunkKeyToHologram.put(chunkKey, holograms);
            }
        }
        hologram.deactivate();
    }

    public static void add(SkillOnGroundWithLocation hologram) {
        String chunkKey = LocationUtils.getChunkKey(hologram.getLocation());

        List<SkillOnGroundWithLocation> holograms;
        if (chunkKeyToHologram.containsKey(chunkKey)) {
            holograms = chunkKeyToHologram.get(chunkKey);
        } else {
            holograms = new ArrayList<>();
        }

        holograms.add(hologram);

        chunkKeyToHologram.put(chunkKey, holograms);
    }
}
