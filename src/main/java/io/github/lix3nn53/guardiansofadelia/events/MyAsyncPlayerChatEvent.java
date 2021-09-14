package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.chat.ChatManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MyAsyncPlayerChatEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        boolean allowOnNormalChat = ChatManager.onChat(player, event.getMessage());
        if (allowOnNormalChat) {
            String format = ChatManager.getFormat(player);
            event.setFormat(format);
        } else {
            event.setCancelled(true);
        }
    }
}
