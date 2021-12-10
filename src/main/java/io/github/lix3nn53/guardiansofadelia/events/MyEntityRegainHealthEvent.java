package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.bossbar.HealthBarManager;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class MyEntityRegainHealthEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(EntityRegainHealthEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) return;

        LivingEntity livingEntity = (LivingEntity) event.getEntity();
        float health = (float) livingEntity.getHealth();
        float amount = (float) event.getAmount();

        HealthBarManager.onLivingTargetHealthChange(livingEntity, (int) (-amount + 0.5), ChatPalette.GREEN_LIGHT, "heal");

        if (livingEntity instanceof Player) {
            Player player = (Player) livingEntity;

            if (PartyManager.inParty(player)) {
                Party party = PartyManager.getParty(player);
                party.getBoard().updateHP(player.getName(), (int) (player.getHealth() + event.getAmount() + 0.5));
            }
        } else {
            PetManager.onHeal(livingEntity, health, amount);
        }
    }
}
