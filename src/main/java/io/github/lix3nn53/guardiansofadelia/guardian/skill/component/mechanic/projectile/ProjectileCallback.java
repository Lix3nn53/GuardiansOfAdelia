package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;

import java.util.ArrayList;

public interface ProjectileCallback {

    /**
     * The callback for the projectiles that applies child components
     *
     * @param projectile projectile calling back for
     * @param hit        the entity hit by the projectile, if any
     * @return targets
     */
    ArrayList<LivingEntity> callback(Projectile projectile, Entity hit);
}
