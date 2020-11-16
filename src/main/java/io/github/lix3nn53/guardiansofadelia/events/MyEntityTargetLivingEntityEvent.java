package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.utilities.EntityUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class MyEntityTargetLivingEntityEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(EntityTargetLivingEntityEvent e) {
        LivingEntity target = e.getTarget();
        if (target != null) {
            EntityType type = target.getType();
            if (!(type.equals(EntityType.PLAYER) || PetManager.isPet(target))) { //Monsters only target player or pet
                e.setCancelled(true);
            } else if (type.equals(EntityType.PLAYER)) { //if target is player
                Entity entity = e.getEntity();
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    if (PetManager.isPet(livingEntity)) { //pet attack player
                        Player owner = PetManager.getOwner(livingEntity);

                        boolean b = EntityUtils.canAttack(owner, target);
                        if (!b) e.setCancelled(true);
                    }
                }
            }
        }
    }
}
