package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.StatusEffectManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.TriggerListener;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.DoNotGetAwayManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MyPlayerMoveEvent implements Listener {

    private static boolean isOnGround(Player player, Location from) {
        Location blockLoc = new Location(from.getWorld(), from.getBlockX(), (int) (player.getBoundingBox().getMinY() - 0.5000001F), from.getBlockZ());

        return !blockLoc.getBlock().isPassable();
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.hasMetadata("NPC")) return;

        Location to = event.getTo();
        if (to == null) return;
        Location from = event.getFrom();

        boolean onGround = MyPlayerMoveEvent.isOnGround(player, from);
        if (onGround) { // deprecated because info comes from client and may therefore not be accurate.
            TriggerListener.onPlayerLandGround(player);
        }

        boolean rootedXZ = StatusEffectManager.isRooted(player);

        if (rootedXZ) {
            float x = (float) from.getX();
            float z = (float) from.getZ();

            float toX = (float) to.getX();
            float toZ = (float) to.getZ();

            if (x != toX) {
                to.setX(x);
            }

            if (z != toZ) {
                to.setZ(z);
            }

            // boolean rootedY = StatusEffectManager.isRootedY(player);
            if (onGround) {
                float y = (float) from.getY();
                float toY = (float) to.getY();

                if (y != toY) {
                    to.setY(y);
                }
            }
        }

        PetManager.onPlayerMove(player, to);
        DoNotGetAwayManager.onMove(player, to);
    }
}
