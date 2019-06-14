package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

public class MyEntityShootBowEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(EntityShootBowEvent event) {
        ItemStack bow = event.getBow();
        if (bow != null) {
            if (!bow.getType().equals(Material.AIR)) {
                if (PersistentDataContainerUtil.hasInteger(bow, "rangedDamage")) {
                    int rangedDamage = PersistentDataContainerUtil.getInteger(bow, "rangedDamage");
                    Entity projectile = event.getProjectile();
                    float force = event.getForce();
                    rangedDamage = (int) ((rangedDamage * force) + 0.5);
                    PersistentDataContainerUtil.putInteger("rangedDamage", rangedDamage, projectile);
                }
            }
        }
    }
}
