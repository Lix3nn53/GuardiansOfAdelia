package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.bossbar.HealthBar;
import io.github.lix3nn53.guardiansofadelia.bossbar.HealthBarManager;
import io.github.lix3nn53.guardiansofadelia.creatures.drops.DropManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.utilities.hologram.FakeIndicator;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;
import java.util.UUID;

public class MyEntityDamageByEntityEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity target = event.getEntity();
        double finalDamage = event.getFinalDamage();

        //player is attacker
        if (damager.getType().equals(EntityType.PLAYER)) {
            Player player = (Player) damager;

            //if living entity
            if (event.getEntity() instanceof LivingEntity) {
                LivingEntity livingTarget = (LivingEntity) target;
                //show bossbar
                HealthBar healthBar = new HealthBar(livingTarget, (int) (finalDamage + 0.5));
                HealthBarManager.showToPlayerFor10Seconds(player, healthBar);

                UUID uniqueId = player.getUniqueId();
                if (GuardianDataManager.hasGuardianData(uniqueId)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(uniqueId);
                    if (guardianData.hasActiveCharacter()) {
                        RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                        //progress deal damage tasks
                        List<Quest> questList = activeCharacter.getQuestList();
                        for (Quest quest : questList) {
                            quest.progressDealDamageTasks(player, livingTarget, (int) (finalDamage + 0.5));
                        }

                        //on Kill
                        if (finalDamage >= livingTarget.getHealth()) {
                            //progress kill tasks
                            for (Quest quest : questList) {
                                quest.progressKillTasks(player, livingTarget);
                            }
                        }
                    }
                }
            }

            //indicator
            String text = ChatColor.RED.toString() + (int) (finalDamage + 0.5) + " ➹";
            FakeIndicator.showPlayer(player, text, target.getLocation());

            DropManager.dealDamage(target, player, (int) (finalDamage + 0.5));
        }

        //player is target
        if (target.getType().equals(EntityType.PLAYER)) {
            Player player = (Player) target;

            if (PartyManager.inParty(player)) {
                Party party = PartyManager.getParty(player);
                party.getBoard().updateHP(player.getName(), (int) (player.getHealth() - finalDamage + 0.5));
            }
        }
    }

}
