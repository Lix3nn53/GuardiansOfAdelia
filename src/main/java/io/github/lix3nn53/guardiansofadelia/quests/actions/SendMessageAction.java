package io.github.lix3nn53.guardiansofadelia.quests.actions;

import org.bukkit.entity.Player;

public class SendMessageAction implements Action {

    private final String s;

    public SendMessageAction(String s) {
        this.s = s;
    }

    @Override
    public void perform(Player player) {
        player.sendMessage(s);
    }
}
