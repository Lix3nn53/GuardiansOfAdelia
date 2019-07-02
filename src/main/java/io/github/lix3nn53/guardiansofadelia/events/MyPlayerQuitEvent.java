package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.TriggerListener;
import io.github.lix3nn53.guardiansofadelia.guild.GuildManager;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;
import io.github.lix3nn53.guardiansofadelia.utilities.PacketLimitter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class MyPlayerQuitEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        PacketLimitter.onQuit(player);

        GuardianDataManager.onPlayerQuit(player);

        GuildManager.onPlayerQuit(player);
        GuardiansOfAdelia.getCharacterSelectionScreenManager().clear(player);
        MiniGameManager.onQuit(player);
        PartyManager.onPlayerQuit(player);
        PetManager.onPlayerQuit(player);
        SkillDataManager.onPlayerQuit(player);
        TriggerListener.onPlayerQuit(player);
    }

}
