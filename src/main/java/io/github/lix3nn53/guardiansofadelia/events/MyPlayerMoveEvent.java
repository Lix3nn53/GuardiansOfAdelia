package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.StatusEffectManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.TriggerListener;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MyPlayerMoveEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.hasMetadata("NPC")) return;

        PetManager.onPlayerMove(player);

        boolean onGround = player.isOnGround();
        if (onGround) { //depricated because info comes from client and may therefore not be accurate.
            TriggerListener.onPlayerLandGround(player);
        }

        boolean rootedXZ = StatusEffectManager.isRooted(player);

        if (rootedXZ) {
            Location from = event.getFrom();
            Location to = event.getTo();

            if (to == null) return;

            float x = (float) from.getX();
            float z = (float) from.getZ();

            float toX = (float) to.getX();
            float toZ = (float) to.getZ();

            if (x != toX || z != toZ) {
                event.setCancelled(true);
                return;
            }

            // boolean rootedY = StatusEffectManager.isRootedY(player);
            if (onGround) {
                float y = (float) from.getY();
                float toY = (float) to.getY();

                if (y != toY) {
                    event.setCancelled(true);
                }
            }
        }
    }

}
