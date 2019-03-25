package io.github.lix3nn53.guardiansofadelia.quests.task;

import io.github.lix3nn53.guardiansofadelia.quests.actions.Action;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class TaskReach implements Task {

    private final Location blockLoc;
    private final Material blockMat;
    private List<Action> onCompleteActions = new ArrayList<>();
    private int completed = 0;

    public TaskReach(final Location blockLoc, final Material blockMat) {
        this.blockLoc = blockLoc;
        this.blockMat = blockMat;
    }

    public TaskReach freshCopy() {
        TaskReach taskCopy = new TaskReach(this.blockLoc, this.blockMat);
        taskCopy.setOnCompleteActions(this.onCompleteActions);
        return taskCopy;
    }

    public String getObjectiveString() {
        ChatColor color;
        if (isCompleted()) {
            color = ChatColor.GREEN;
        } else {
            color = ChatColor.YELLOW;
        }
        String lore = color + "Go to " + blockLoc + " and click " + blockMat.toString() + " block";
        return lore;
    }

    @Override
    public boolean isCompleted() {
        return completed > 0;
    }

    @Override
    public boolean progress(Player player) {
        if (completed == 0) {
            completed++;
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
        return this.completed;
    }

    @Override
    public void setProgress(int progress) {
        this.completed = progress;
    }

    @Override
    public void addOnCompleteAction(Action action) {
        onCompleteActions.add(action);
    }

    public void setOnCompleteActions(List<Action> onCompleteActions) {
        this.onCompleteActions = onCompleteActions;
    }
}
