package io.github.lix3nn53.guardiansofadelia.pets;

import org.bukkit.entity.*;

public enum PetType {
    COMPANION,
    MOUNT;

    public static boolean isCompanion(Entity entity) {
        return entity instanceof Wolf;
    }

    public static boolean isMount(Entity entity) {
        return entity instanceof Horse || entity instanceof Donkey || entity instanceof Mule;
    }
}
