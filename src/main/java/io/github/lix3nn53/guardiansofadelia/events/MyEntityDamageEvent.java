package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class MyEntityDamageEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(EntityDamageEvent event) {
        EntityType entityType = event.getEntityType();
        if (entityType.equals(EntityType.PLAYER)) {
            Player player = (Player) event.getEntity();

            if (event.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
                event.setDamage(10000D);
            }

            double finalDamage = event.getFinalDamage();
            if (PartyManager.inParty(player)) {
                Party party = PartyManager.getParty(player);
                party.getBoard().updateHP(player.getName(), (int) (player.getHealth() - finalDamage + 0.5));
            }
        } else if (entityType.equals(EntityType.WOLF) || entityType.equals(EntityType.HORSE)) {
            Entity entity = event.getEntity();
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                if (PetManager.isPet(livingEntity)) {
                    PetManager.onPetTakeDamage(livingEntity, livingEntity.getHealth(), event.getFinalDamage());
                }
            }
        }
    }

}
