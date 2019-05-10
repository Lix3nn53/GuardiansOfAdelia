package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityMountEvent;

public class MyEntityMountEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(EntityMountEvent e) {
        Entity entity = e.getEntity();
        if (entity.getType().equals(EntityType.PLAYER)) {
            Entity mount = e.getMount();
            if (mount instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                if (PetManager.isPet(livingEntity)) {
                    Player owner = PetManager.getOwner(livingEntity);
                    Player player = (Player) entity;
                    if (!player.equals(owner)) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
