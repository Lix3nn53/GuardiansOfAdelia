package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class ProjectileManager {

    private static HashMap<Entity, ProjectileMechanic> projectileToCallback = new HashMap<>();

    public static void onSkillProjectileShoot(ArrayList<Entity> projectiles, ProjectileMechanic projectileMechanic, int skillLevel, long delayTicks) {
        for (Entity projectile : projectiles) {
            PersistentDataContainerUtil.putInteger("skillLevel", skillLevel, projectile);
            projectileToCallback.put(projectile, projectileMechanic);
        }

        //how long can projectiles stay in air
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Entity projectile : projectiles) {
                    if (projectile.isValid()) {
                        projectile.remove();
                    }
                    projectileToCallback.remove(projectile);
                }
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), delayTicks);
    }

    public static void onSkillProjectileLand(Entity projectile, Entity hit) {
        if (projectile instanceof Projectile) {
            if (projectileToCallback.containsKey(projectile)) {
                ProjectileMechanic projectileMechanic = projectileToCallback.get(projectile);
                projectileMechanic.callback((Projectile) projectile, hit);
                projectileToCallback.remove(projectile);
            }
        }
    }
}
