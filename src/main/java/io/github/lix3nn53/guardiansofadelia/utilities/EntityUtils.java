package io.github.lix3nn53.guardiansofadelia.utilities;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class EntityUtils {

    public static LivingEntity create(Location loc, String name, double hp, EntityType type) {
        LivingEntity entity = (LivingEntity) loc.getWorld().spawnEntity(loc, type);
        entity.setCustomName(name);
        entity.setCustomNameVisible(true);
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(hp);
        entity.setHealth(hp);
        entity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.7D);
        entity.setCanPickupItems(false);

        return entity;
    }
}
