package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.TriggerListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MyPlayerMoveEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.hasMetadata("NPC")) return;

        PetManager.onPlayerMove(player);

        if (player.isOnGround()) {
            TriggerListener.onPlayerLandGround(player);
        }
    }

}
