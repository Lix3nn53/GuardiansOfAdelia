package io.github.lix3nn53.guardiansofadelia.events;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MyItemSpawnEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(ItemSpawnEvent event) {
        Item itemDrop = event.getEntity();
        ItemStack itemStack = itemDrop.getItemStack();

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.hasDisplayName()) {
            itemDrop.setCustomName(itemMeta.getDisplayName());
            itemDrop.setCustomNameVisible(true);
        }
    }
}
