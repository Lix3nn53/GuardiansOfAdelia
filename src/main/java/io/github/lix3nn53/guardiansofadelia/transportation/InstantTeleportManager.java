package io.github.lix3nn53.guardiansofadelia.transportation;

import java.util.HashMap;

public class InstantTeleportManager {
    public static final HashMap<Integer, InstantTeleportGuiItem> questNoToTeleport = new HashMap<>();

    public static boolean contains(int questNo) {
        return questNoToTeleport.containsKey(questNo);
    }

    public static InstantTeleportGuiItem getTeleport(int questNo) {
        return questNoToTeleport.get(questNo);
    }

    public static void addTeleport(int questNo, InstantTeleportGuiItem instantTeleportGuiItem) {
        questNoToTeleport.put(questNo, instantTeleportGuiItem);
    }
}
