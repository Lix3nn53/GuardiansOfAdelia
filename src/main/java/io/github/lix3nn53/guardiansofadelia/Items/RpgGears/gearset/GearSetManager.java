package io.github.lix3nn53.guardiansofadelia.Items.RpgGears.gearset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GearSetManager {

    private static final HashMap<GearSet, List<GearSetEffect>> gearSetToGearSetEffects = new HashMap<>();

    public static boolean hasEffect(GearSet gearSet) {
        boolean b = gearSetToGearSetEffects.containsKey(gearSet);

        if (b) return true; // No need to search for lower piece effects

        int pieceCount = gearSet.getPieceCount();

        if (pieceCount > 2) { // Apply lower piece count effects too
            for (int i = pieceCount - 1; i >= 2; i--) {
                GearSet gearSetLower = new GearSet(gearSet.getName(), i);

                boolean b1 = gearSetToGearSetEffects.containsKey(gearSetLower);
                if (b1) return true;
            }
        }

        return false;
    }

    public static List<GearSetEffect> getEffects(GearSet gearSet) {
        List<GearSetEffect> list = new ArrayList<>();

        if (gearSetToGearSetEffects.containsKey(gearSet)) {
            list.addAll(gearSetToGearSetEffects.get(gearSet));
        }

        int pieceCount = gearSet.getPieceCount();
        if (pieceCount > 2) { // Apply lower piece count effects too
            for (int i = pieceCount - 1; i >= 2; i--) {
                GearSet gearSetLower = new GearSet(gearSet.getName(), i);

                if (gearSetToGearSetEffects.containsKey(gearSetLower)) {
                    list.addAll(gearSetToGearSetEffects.get(gearSetLower));
                }
            }
        }

        return list;
    }

    public static List<GearSetEffect> getEffectsWithoutLower(GearSet gearSet) {
        return gearSetToGearSetEffects.get(gearSet);
    }

    public static void addEffect(GearSet gearSet, GearSetEffect gearSetEffect) {
        List<GearSetEffect> gearSetEffects = new ArrayList<>();
        if (gearSetToGearSetEffects.containsKey(gearSet)) {
            gearSetEffects = gearSetToGearSetEffects.get(gearSet);
        }

        gearSetEffects.add(gearSetEffect);

        gearSetToGearSetEffects.put(gearSet, gearSetEffects);
    }
}
