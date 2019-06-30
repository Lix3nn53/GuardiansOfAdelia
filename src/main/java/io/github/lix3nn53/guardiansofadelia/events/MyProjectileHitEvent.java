package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.ProjectileListener;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class MyProjectileHitEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(ProjectileHitEvent event) {
        Projectile entity = event.getEntity();
        Entity hitEntity = event.getHitEntity();

        ProjectileListener.onSkillProjectileLand(entity, hitEntity);

        if (entity instanceof Arrow) {
            Arrow arrow = (Arrow) entity;
            if (arrow.getPierceLevel() > 0) {
                GuardiansOfAdelia.getInstance().getLogger().info("pierce Level on hit event: " + arrow.getPierceLevel());
            }
        }
    }
}
