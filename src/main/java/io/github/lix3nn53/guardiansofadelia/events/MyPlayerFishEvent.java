package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.jobs.GatheringType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class MyPlayerFishEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(PlayerFishEvent event) {
        PlayerFishEvent.State state = event.getState();

        if (state.equals(PlayerFishEvent.State.CAUGHT_FISH)) {
            event.setExpToDrop(0);
            Item caught = (Item) event.getCaught();

            Player player = event.getPlayer();

            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            ItemStack gatheredIngredient = GatheringType.FISHING.playerGather(player, itemInMainHand);
            if (gatheredIngredient != null) {
                caught.setItemStack(gatheredIngredient);
            } else {
                caught.remove();
            }
        }
    }
}
