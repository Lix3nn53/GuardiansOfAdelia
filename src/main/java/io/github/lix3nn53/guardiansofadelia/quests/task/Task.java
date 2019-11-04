package io.github.lix3nn53.guardiansofadelia.quests.task;

import io.github.lix3nn53.guardiansofadelia.quests.actions.Action;
import org.bukkit.entity.Player;

public interface Task {

    String getObjectiveString();

    String getObjectiveStringNoProgress();

    boolean isCompleted();

    boolean progress(Player player);

    int getProgress();

    void setProgress(int progress);

    void addOnCompleteAction(Action action);

    Task freshCopy();
}
