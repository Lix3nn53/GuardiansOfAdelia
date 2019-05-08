package io.github.lix3nn53.guardiansofadelia.creatures.pets;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Wolf;

public enum PetType {
    COMPANION,
    MOUNT;

    public static boolean isCompanion(Entity entity) {
        return entity instanceof Wolf;
    }

    public static boolean isMount(Entity entity) {
        return entity instanceof Horse;
    }
}
