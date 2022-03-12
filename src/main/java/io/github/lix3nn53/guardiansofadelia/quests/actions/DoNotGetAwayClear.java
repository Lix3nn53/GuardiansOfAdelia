package io.github.lix3nn53.guardiansofadelia.quests.actions;

import io.github.lix3nn53.guardiansofadelia.utilities.managers.DoNotGetAwayManager;
import org.bukkit.entity.Player;

public class DoNotGetAwayClear implements Action {

    @Override
    public void perform(Player player, int questID, int taskIndex) {
        DoNotGetAwayManager.removePlayer(player);
    }

    @Override
    public boolean preventTaskCompilation() {
        return false;
    }
}
