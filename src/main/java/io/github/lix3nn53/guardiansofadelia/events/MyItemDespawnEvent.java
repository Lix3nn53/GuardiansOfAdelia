package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.creatures.drops.DropProtectionManager;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.inventory.ItemStack;

public class MyItemDespawnEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(ItemDespawnEvent event) {
        Item entity = event.getEntity();
        ItemStack itemStack = entity.getItemStack();

        DropProtectionManager.onItemDespawn(itemStack);
    }
}
