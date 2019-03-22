package io.github.lix3nn53.guardiansofadelia.quests.task;

import io.github.lix3nn53.guardiansofadelia.quests.actions.Action;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class TaskCollect implements Task {

    private final int amountNeeded;
    private final String itemName;
    private final List<Action> onCompleteActions = new ArrayList<>();
    private int progress;

    public TaskCollect(final String itemName, final int amountNeeded) {
        this.itemName = itemName;
        this.amountNeeded = amountNeeded;
        progress = 0;
    }

    public String getObjectiveString() {
        ChatColor color;
        if (isCompleted()) {
            color = ChatColor.GREEN;
        } else {
            color = ChatColor.YELLOW;
        }
        String lore = color + "Collect " + progress + "/" + amountNeeded + " " + itemName;
        return lore;
    }

    @Override
    public boolean isCompleted() {
        return progress >= amountNeeded;
    }

    @Override
    public boolean progress(Player player) {
        if (progress < amountNeeded) {
            progress++;
            if (isCompleted()) {
                for (Action action : onCompleteActions) {
                    action.perform(player);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int getProgress() {
        return this.progress;
    }

    @Override
    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public void addOnCompleteAction(Action action) {
        onCompleteActions.add(action);
    }
}
