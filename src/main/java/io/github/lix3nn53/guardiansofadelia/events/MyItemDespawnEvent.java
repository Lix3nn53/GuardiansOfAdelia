package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.creatures.drops.DropManager;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.QuestHologram;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class MyItemDespawnEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(ItemDespawnEvent event) {
        Item entity = event.getEntity();
        ItemStack itemStack = entity.getItemStack();
        Material type = itemStack.getType();
        if (type.equals(Material.STONE_PICKAXE)) {
            //quest icon holograms
            Collection<QuestHologram> npcNoToHologram = QuestNPCManager.getNpcNoToHologram().values();
            for (QuestHologram questHologram : npcNoToHologram) {
                ArmorStand armorStand = questHologram.getHolo().getArmorStand();
                if (!armorStand.getPassengers().isEmpty()) {
                    if (armorStand.getPassengers().get(0).equals(entity)) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }

        DropManager.onItemDespawn(itemStack);
    }
}
