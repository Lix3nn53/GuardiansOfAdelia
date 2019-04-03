package io.github.lix3nn53.guardiansofadelia.events.myskillapievents;

import com.sucy.skill.api.enums.ExpSource;
import com.sucy.skill.api.event.PlayerExperienceGainEvent;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;
import io.github.lix3nn53.guardiansofadelia.utilities.SkillAPIUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.List;

public class MyPlayerExperienceGainEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(PlayerExperienceGainEvent event) {

        Player player = event.getPlayerData().getPlayer();

        if (event.getSource().equals(ExpSource.MOB)) {
            if (PartyManager.inParty(player)) {
                Party party = PartyManager.getParty(player);
                List<Player> members = party.getMembers();

                double exp = event.getExp();
                exp = exp * (1 - (0.05 * members.size()));

                for (Player member : members) {
                    if (!member.getUniqueId().equals(player.getUniqueId())) {
                        SkillAPIUtils.givePartyExp(member, exp);
                        member.sendMessage("Party exp share");
                    }
                }

                event.setExp(exp);
                player.sendMessage("Party exp share");
            }
        }
    }
}
