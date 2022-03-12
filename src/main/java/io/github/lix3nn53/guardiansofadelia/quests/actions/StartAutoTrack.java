package io.github.lix3nn53.guardiansofadelia.quests.actions;

import io.github.lix3nn53.guardiansofadelia.utilities.managers.CompassManager;
import org.bukkit.entity.Player;

public class StartAutoTrack implements Action {

    @Override
    public void perform(Player player, int questID, int taskIndex) {
        CompassManager.startAutoTrackQuest(player, questID);
    }

    @Override
    public boolean preventTaskCompilation() {
        return false;
    }
}
