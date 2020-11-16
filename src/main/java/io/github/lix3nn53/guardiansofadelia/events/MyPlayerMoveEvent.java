package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.StatusEffectManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.TriggerListener;
import org.bukkit.ChatColor;
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

        if (player.isOnGround()) { //depricated because info comes from client and may therefore not be accurate.
            TriggerListener.onPlayerLandGround(player);
        }

        boolean rootedXZ = StatusEffectManager.isRootedXZ(player);

        if (rootedXZ) {
            Location from = event.getFrom();
            Location to = event.getTo();

            if (to == null) return;

            double x = from.getX();
            double z = from.getZ();

            double toX = to.getX();
            double toZ = to.getZ();

            if (x != toX || z != toZ) {
                event.setCancelled(true);
                player.sendTitle("", ChatColor.RED + "Rooted..", 0, 20, 0);
                return;
            }

            boolean rootedY = StatusEffectManager.isRootedY(player);
            if (rootedY) {
                double y = from.getY();
                double toY = to.getY();

                if (y != toY) {
                    event.setCancelled(true);
                    player.sendTitle("", ChatColor.RED + "Rooted..", 0, 20, 0);
                }
            }
        }
    }

}
