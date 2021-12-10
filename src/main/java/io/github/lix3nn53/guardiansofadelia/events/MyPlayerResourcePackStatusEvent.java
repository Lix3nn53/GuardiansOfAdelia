package io.github.lix3nn53.guardiansofadelia.events;


import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.locale.Translation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class MyPlayerResourcePackStatusEvent implements Listener {

    @EventHandler
    public void onPlayerResourcePackStatusEvent(PlayerResourcePackStatusEvent e) {
        Player p = e.getPlayer();
        PlayerResourcePackStatusEvent.Status status = e.getStatus();

        GuardianData guardianData = GuardianDataManager.getGuardianData(p);

        String lang = Translation.DEFAULT_LANG;
        if (guardianData != null) {
            String language = guardianData.getLanguage();
            if (language != null) {
                lang = language;
            }
        }

        if (status.equals(PlayerResourcePackStatusEvent.Status.ACCEPTED)) {
            p.sendMessage(ChatPalette.GREEN_DARK + Translation.t(lang, "general.resource.accepted"));
        } else if (status.equals(PlayerResourcePackStatusEvent.Status.DECLINED)) {
            p.sendMessage(ChatPalette.RED + Translation.t(lang, "general.resource.declined"));
        } else if (status.equals(PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD)) {
            p.sendMessage(ChatPalette.RED + Translation.t(lang, "general.resource.failed"));
        } else if (status.equals(PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED)) {
            p.sendMessage(ChatPalette.GREEN_DARK + Translation.t(lang, "general.resource.loaded"));
        }
    }
}
