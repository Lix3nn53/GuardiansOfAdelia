package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringManager;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringToolTier;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringType;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
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
            String toolTier = PersistentDataContainerUtil.getString(itemInMainHand, "toolTier");
            GatheringToolTier gatheringToolTier = GatheringToolTier.valueOf(toolTier);
            ItemStack gatheredIngredient = GatheringManager.finishGathering(player, itemInMainHand, GatheringType.FISHING, gatheringToolTier);
            if (gatheredIngredient != null) {
                caught.setItemStack(gatheredIngredient);
            } else {
                caught.remove();
            }
        }
    }
}
