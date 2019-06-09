package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.creatures.drops.DropManager;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.creatures.spawners.SpawnerManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class MyEntityDeathEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        event.getDrops().clear();
        event.setDroppedExp(0);

        EntityType entityType = entity.getType();
        if (entityType.equals(EntityType.WOLF) || entityType.equals(EntityType.HORSE)) {
            PetManager.onPetDeath(entity);
        } else if (!entityType.equals(EntityType.PLAYER)) {
            SpawnerManager.onMobDeath(entity);

            DropManager.onMobDeath(entity, event);
        }
    }

}
