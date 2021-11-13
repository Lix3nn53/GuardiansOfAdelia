package io.github.lix3nn53.guardiansofadelia.transportation;

import java.util.HashMap;

public class InstantTeleportManager {
    public static final HashMap<Integer, InstantTeleportGuiItem> codeToTeleport = new HashMap<>();

    public static InstantTeleportGuiItem getTeleport(int code) {
        return codeToTeleport.get(code);
    }

    public static void addTeleport(int code, InstantTeleportGuiItem instantTeleportGuiItem) {
        codeToTeleport.put(code, instantTeleportGuiItem);
    }
}
