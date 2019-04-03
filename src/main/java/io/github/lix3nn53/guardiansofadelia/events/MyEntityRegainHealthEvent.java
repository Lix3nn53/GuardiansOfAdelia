package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class MyEntityRegainHealthEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(EntityRegainHealthEvent event) {
        if (event.getEntityType().equals(EntityType.PLAYER)) {
            Player player = (Player) event.getEntity();

            if (PartyManager.inParty(player)) {
                Party party = PartyManager.getParty(player);
                party.getBoard().updateHP(player.getName(), (int) (player.getHealth() + event.getAmount() + 0.5));
            }
        }
    }
}
