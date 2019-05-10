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

        if (entity.getType().equals(EntityType.WOLF) || entity.getType().equals(EntityType.HORSE)) {
            PetManager.onPetDeath(entity);
        } else if (!entity.getType().equals(EntityType.PLAYER)) {
            SpawnerManager.onMobDeath(entity);

            DropManager.onMobDeath(entity, event);
        }
    }

}
