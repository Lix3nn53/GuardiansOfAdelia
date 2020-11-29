package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityMountEvent;

public class MyEntityMountEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(EntityMountEvent e) {
        Entity rider = e.getEntity();
        if (rider.getType().equals(EntityType.PLAYER)) {
            Entity mount = e.getMount();
            if (mount instanceof LivingEntity) {
                LivingEntity livingMount = (LivingEntity) mount;
                if (PetManager.isPet(livingMount)) {
                    Player owner = PetManager.getOwner(livingMount);
                    Player player = (Player) rider;
                    if (!player.equals(owner)) {
                        e.setCancelled(true);
                    }
                }
            }
        } else {
            NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
            if (npcRegistry.isNPC(rider)) {
                e.setCancelled(true);
            }
        }
    }
}
