package io.github.lix3nn53.guardiansofadelia.quests.actions;

import org.bukkit.entity.Player;

public interface Action {

    void perform(Player player, int questID, int taskIndex);

    boolean preventTaskCompilation(); //For 2 step actions like WeaponSelectOneOfAction
}
