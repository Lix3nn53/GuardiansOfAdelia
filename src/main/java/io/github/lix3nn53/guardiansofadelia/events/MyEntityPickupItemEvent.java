package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.creatures.drops.DropManager;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class MyEntityPickupItemEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(EntityPickupItemEvent event) {
        ItemStack itemStack = event.getItem().getItemStack();
        Material type = itemStack.getType();
        if (type.equals(Material.STONE_PICKAXE)) {
            event.setCancelled(true);
        } else if (event.getEntityType().equals(EntityType.PLAYER)) {
            Player player = (Player) event.getEntity();
            DropManager dropManager = GuardiansOfAdelia.getDropManager();
            if (!dropManager.canPickUp(itemStack, player)) {
                event.setCancelled(true);
            }
        } else {
            event.setCancelled(true);
        }
    }

}
