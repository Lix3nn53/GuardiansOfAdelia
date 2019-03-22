package io.github.lix3nn53.guardiansofadelia.quests.actions;

import org.bukkit.entity.Player;

public class SendTitleAction implements Action {

    private final String s;
    private final String s1;

    public SendTitleAction(String s, String s1) {
        this.s = s;
        this.s1 = s1;
    }

    @Override
    public void perform(Player player) {
        player.sendTitle(s, s1, 25, 35, 25);
    }
}
