package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.Items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.utilities.SkillAPIUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class MyPlayerItemHeldEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        RPGClass rpgClass = SkillAPIUtils.getRPGClass(player);

        if (rpgClass.equals(RPGClass.ARCHER)) {
            PlayerInventory inventory = player.getInventory();
            int newSlot = event.getNewSlot();
            ItemStack item = inventory.getItem(newSlot);

            if (item != null) {
                if (item.getType().equals(Material.BOW)) {
                    ItemStack arrow = OtherItems.getArrow(2);
                    inventory.setItemInOffHand(arrow);
                } else {
                    inventory.setItemInOffHand(new ItemStack(Material.AIR));
                }
            } else {
                inventory.setItemInOffHand(new ItemStack(Material.AIR));
            }
        }
    }

}
