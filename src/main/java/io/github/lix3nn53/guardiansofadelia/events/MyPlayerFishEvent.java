package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringManager;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringModel;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.Ingredient;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MyPlayerFishEvent implements Listener {

    private static final int range = 16;

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(PlayerFishEvent event) {
        PlayerFishEvent.State state = event.getState();

        if (state.equals(PlayerFishEvent.State.FISHING)) {
            Player player = event.getPlayer();
            ItemStack itemInMainHand = player.getEquipment().getItemInMainHand();
            List<Entity> nearbyEntities = player.getNearbyEntities(range, range, range);
            boolean b = false;
            for (Entity entity : nearbyEntities) {
                if (entity.getType().equals(EntityType.ARMOR_STAND)) {
                    ArmorStand armorStand = (ArmorStand) entity;

                    GatheringModel gatheringModel = GatheringManager.getGatheringModelFromArmorStand(armorStand);
                    if (gatheringModel == null) continue;

                    b = GatheringManager.canStartGathering(player, itemInMainHand, gatheringModel);

                    if (b) {
                        break;
                    }
                }
            }
            if (!b) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You are not in a fishing area");
            }
        } else if (state.equals(PlayerFishEvent.State.CAUGHT_FISH)) {
            event.setExpToDrop(0);
            Item caught = (Item) event.getCaught();

            Player player = event.getPlayer();
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

            List<Entity> nearbyEntities = player.getNearbyEntities(range, range, range);
            boolean b = false;
            GatheringModel gatheringModel = null;
            for (Entity entity : nearbyEntities) {
                if (entity.getType().equals(EntityType.ARMOR_STAND)) {
                    ArmorStand armorStand = (ArmorStand) entity;

                    gatheringModel = GatheringManager.getGatheringModelFromArmorStand(armorStand);
                    if (gatheringModel == null) continue;

                    b = GatheringManager.canStartGathering(player, itemInMainHand, gatheringModel);

                    if (b) {
                        break;
                    }
                }
            }
            if (!b) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You are not in a fishing area");
                return;
            }

            List<Ingredient> ingredients = GatheringManager.getIngredients(gatheringModel);
            ItemStack gatheredIngredient = GatheringManager.finishGathering(player, itemInMainHand, ingredients);
            if (gatheredIngredient != null) {
                caught.setItemStack(gatheredIngredient);
            } else {
                caught.remove();
            }
        }
    }
}
