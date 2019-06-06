package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.Items.TeleportScroll;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class MyPlayerInteractEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            Player player = event.getPlayer();
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            Material itemInMainHandType = itemInMainHand.getType();
            if (itemInMainHandType.equals(Material.PAPER)) {
                if (PersistentDataContainerUtil.hasString(itemInMainHand, "teleportScroll")) {
                    String teleportScroll = PersistentDataContainerUtil.getString(itemInMainHand, "teleportScroll");
                    TeleportScroll teleportScrollLocation = TeleportScroll.valueOf(teleportScroll);
                    teleportScrollLocation.teleport(player, itemInMainHand);
                }
            } else {
                Block clickedBlock = event.getClickedBlock();
                Material clickedBlockType = clickedBlock.getType();
                if (clickedBlockType.equals(Material.CHEST) || clickedBlockType.equals(Material.BARREL) || clickedBlockType.equals(Material.ENDER_CHEST)
                        || clickedBlockType.equals(Material.TRAPPED_CHEST) || clickedBlockType.equals(Material.CHEST_MINECART) || clickedBlockType.equals(Material.CRAFTING_TABLE)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
