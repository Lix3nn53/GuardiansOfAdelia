package io.github.lix3nn53.guardiansofadelia.events;

import org.bukkit.ChatColor;
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
            p.sendMessage(ChatColor.GREEN + "Resource pack accepted");
        } else if (status.equals(PlayerResourcePackStatusEvent.Status.DECLINED)) {
            p.sendMessage(ChatColor.RED + "Resource pack declined");
        } else if (status.equals(PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD)) {
            p.sendMessage(ChatColor.RED + "Resource pack download failed");
        } else if (status.equals(PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED)) {
            p.sendMessage(ChatColor.GREEN + "Resource pack successfully loaded!");
        }
    }
}
