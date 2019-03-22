package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.party.Party;
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
            GuardianData guardianData = GuardiansOfAdelia.getGuardianDataManager().getGuardianData(player.getUniqueId());
            if (guardianData.isInParty()) {
                Party party = guardianData.getParty();
                party.getBoard().updateHP(player.getName(), (int) (player.getHealth() + event.getAmount()));
            }

        }
    }
}
