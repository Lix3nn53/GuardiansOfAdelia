package io.github.lix3nn53.guardiansofadelia.events;


import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class MyPlayerResourcePackStatusEvent implements Listener {

    @EventHandler
    public void onPlayerResourcePackStatusEvent(PlayerResourcePackStatusEvent e) {
        Player p = e.getPlayer();
        PlayerResourcePackStatusEvent.Status status = e.getStatus();
        if (status.equals(PlayerResourcePackStatusEvent.Status.ACCEPTED)) {
            p.sendMessage(ChatPalette.GREEN_DARK + "Resource pack accepted");
        } else if (status.equals(PlayerResourcePackStatusEvent.Status.DECLINED)) {
            p.sendMessage(ChatPalette.RED + "Resource pack declined");
        } else if (status.equals(PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD)) {
            p.sendMessage(ChatPalette.RED + "Resource pack download failed");
        } else if (status.equals(PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED)) {
            p.sendMessage(ChatPalette.GREEN_DARK + "Resource pack successfully loaded!");
        }
    }
}
