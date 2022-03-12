package io.github.lix3nn53.guardiansofadelia.quests.actions;

import io.github.lix3nn53.guardiansofadelia.utilities.managers.DoNotGetAwayData;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.DoNotGetAwayManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class DoNotGetAway implements Action {

    private final DoNotGetAwayData doNotGetAwayData;

    public DoNotGetAway(Location center, float distance, String onLeave) {
        this.doNotGetAwayData = new DoNotGetAwayData(center, distance, onLeave);
    }

    @Override
    public void perform(Player player, int questID, int taskIndex) {
        DoNotGetAwayManager.addPlayer(player, doNotGetAwayData);
    }

    @Override
    public boolean preventTaskCompilation() {
        return false;
    }
}
