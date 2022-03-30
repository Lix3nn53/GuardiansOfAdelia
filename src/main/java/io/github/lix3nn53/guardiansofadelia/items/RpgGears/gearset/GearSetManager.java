package io.github.lix3nn53.guardiansofadelia.items.RpgGears.gearset;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ItemTier;

import java.util.*;

public class GearSetManager {

    private static final HashMap<ItemTier, HashMap<GearSet, List<GearSetEffect>>> tierToGearSets = new HashMap<>();

    /**
     * @param gearSet
     * @return ItemTier of GearSet, null if GearSet does not exist
     */
    private static ItemTier tierOf(GearSet gearSet) {
        for (Map.Entry<ItemTier, HashMap<GearSet, List<GearSetEffect>>> entry : tierToGearSets.entrySet()) {
            if (entry.getValue().containsKey(gearSet)) {
                return entry.getKey();
            }
        }

        return null;
    }

    public static boolean hasEffect(GearSet gearSet) {
        ItemTier itemTier = tierOf(gearSet);

        if (itemTier != null) return true; // No need to search for lower piece effects

        int pieceCount = gearSet.getPieceCount();

        if (pieceCount > 2) { // Apply lower piece count effects too
            for (int i = pieceCount - 1; i >= 2; i--) {
                GearSet gearSetLower = new GearSet(gearSet.getName(), i);

                itemTier = tierOf(gearSetLower);
                if (itemTier != null) return true;
            }
        }

        return false;
    }

    public static List<GearSetEffect> getEffects(GearSet gearSet) {
        List<GearSetEffect> list = new ArrayList<>();

        ItemTier itemTier = tierOf(gearSet);
        if (itemTier != null) {
            HashMap<GearSet, List<GearSetEffect>> gearSetMap = tierToGearSets.get(itemTier);
            List<GearSetEffect> gearSetEffects = gearSetMap.get(gearSet);
            list.addAll(gearSetEffects);
        }

        int pieceCount = gearSet.getPieceCount();
        if (pieceCount > 2) { // Apply lower piece count effects too
            for (int i = pieceCount - 1; i >= 2; i--) {
                GearSet gearSetLower = new GearSet(gearSet.getName(), i);

                itemTier = tierOf(gearSetLower);
                if (itemTier != null) {
                    HashMap<GearSet, List<GearSetEffect>> gearSetMap = tierToGearSets.get(itemTier);
                    List<GearSetEffect> gearSetEffects = gearSetMap.get(gearSet);
                    list.addAll(gearSetEffects);
                }
            }
        }

        return list;
    }

    public static List<GearSetEffect> getEffectsWithoutLower(GearSet gearSet) {
        ItemTier itemTier = tierOf(gearSet);

        return tierToGearSets.get(itemTier).get(gearSet);
    }

    public static void addEffect(ItemTier itemTier, GearSet gearSet, GearSetEffect gearSetEffect) {
        HashMap<GearSet, List<GearSetEffect>> gearSetMap = new HashMap<>();
        if (tierToGearSets.containsKey(itemTier)) {
            gearSetMap = tierToGearSets.get(itemTier);
        }
        List<GearSetEffect> gearSetEffects = new ArrayList<>();
        if (gearSetMap.containsKey(gearSet)) {
            gearSetEffects = gearSetMap.get(gearSet);
        }

        gearSetEffects.add(gearSetEffect);

        gearSetMap.put(gearSet, gearSetEffects);
        tierToGearSets.put(itemTier, gearSetMap);
    }

    public static GearSet getRandom(ItemTier itemTier) {
        HashMap<GearSet, List<GearSetEffect>> gearSetMap = tierToGearSets.get(itemTier);

        if (gearSetMap == null) return null;

        Set<GearSet> gearSets = gearSetMap.keySet();
        int size = gearSets.size();

        int item = GuardiansOfAdelia.RANDOM.nextInt(size); // In real life, the Random object should be rather more shared than this
        int i = 0;
        for (GearSet obj : gearSets) {
            if (i == item)
                return obj;
            i++;
        }

        return null;
    }
}
