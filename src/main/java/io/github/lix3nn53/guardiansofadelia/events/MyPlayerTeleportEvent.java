package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.commands.admin.CommandAdmin;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class MyPlayerTeleportEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(PlayerTeleportEvent event) {
        PlayerTeleportEvent.TeleportCause cause = event.getCause();

        if (cause.equals(PlayerTeleportEvent.TeleportCause.SPECTATE)) {
            event.setCancelled(true);
            return;
        }

        Location from = event.getFrom();
        Location to = event.getTo();

        String fromName = from.getWorld().getName();
        String toName = to.getWorld().getName();

        if (!fromName.equals(toName)) {
            Player player = event.getPlayer();

            InventoryUtils.removeAllFromInventoryByMaterial(player.getInventory(), Material.COMPASS);

            if (PetManager.hasPet(player)) {
                LivingEntity pet = PetManager.getPet(player);
                pet.remove();
                PetManager.respawnPet(player);
            }

            if (PetManager.hasCompanion(player)) {
                if (CommandAdmin.DEBUG_MODE) player.sendMessage("PLAYER TELEPORT COMPANION");
                PetManager.removeCompanions(player);
            }
        }
    }
}
