package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.TriggerListener;
import io.github.lix3nn53.guardiansofadelia.guild.GuildManager;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.CharacterSelectionScreenManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class MyPlayerQuitEvent implements Listener {

    public static void onPlayerQuit(Player player) {
        GuardianData guardianData = GuardianDataManager.onPlayerQuit(player);
        if (guardianData != null) {
            guardianData.getActiveCharacter().getRpgCharacterStats().onQuit();
        }

        GuildManager.onPlayerQuit(player);
        CharacterSelectionScreenManager.clear(player);
        MiniGameManager.onQuit(player);
        PartyManager.onPlayerQuit(player);
        PetManager.onPlayerQuit(player);
        SkillDataManager.onPlayerQuit(player);
        TriggerListener.onPlayerQuit(player);
    }

    public static void onPlayerBackToCharacterSelection(Player player) {
        if (MiniGameManager.isInMinigame(player)) {
            player.sendMessage(ChatPalette.RED + "You are in a minigame, you can't go back to character selection.");
            return;
        }

        GuardianData guardianData = GuardianDataManager.onPlayerQuit(player);
        if (guardianData != null) {
            guardianData.getActiveCharacter().getRpgCharacterStats().onQuit();
        }

        // GuildManager.onPlayerQuit(player);
        CharacterSelectionScreenManager.clear(player);
        // MiniGameManager.onQuit(player);
        PartyManager.onPlayerQuit(player);
        PetManager.onPlayerQuit(player);
        SkillDataManager.onPlayerQuit(player);
        TriggerListener.onPlayerQuit(player);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        MyPlayerQuitEvent.onPlayerQuit(player);
    }

}
