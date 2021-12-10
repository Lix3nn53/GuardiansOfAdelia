package io.github.lix3nn53.guardiansofadelia.quests.task;

import io.github.lix3nn53.guardiansofadelia.quests.actions.Action;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import org.bukkit.entity.Player;

public interface Task {

    String getTablistInfoString();

    String getItemLoreString();

    boolean isCompleted();

    /**
     * @param player
     * @param questID
     * @param taskIndex
     * @param ignorePrevent For 2 step actions like WeaponSelectOneOfAction
     * @return
     */
    boolean progress(Player player, int questID, int taskIndex, boolean ignorePrevent);

    int getProgress();

    void setProgress(int progress);

    int getRequiredProgress();

    ChatPalette getChatPalette();

    void addOnCompleteAction(Action action);

    Task freshCopy();
}
