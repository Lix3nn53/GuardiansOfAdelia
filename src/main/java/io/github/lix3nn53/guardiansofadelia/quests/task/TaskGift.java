package io.github.lix3nn53.guardiansofadelia.quests.task;

import io.github.lix3nn53.guardiansofadelia.quests.actions.Action;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class TaskGift implements Task {

    private final int amountNeeded;
    private final ItemStack item;
    private final String entityName;
    private List<Action> onCompleteActions = new ArrayList<>();
    private int progress = 0;

    public TaskGift(final int amountNeeded, final ItemStack item, final String entityName) {
        this.amountNeeded = amountNeeded;
        this.item = item;
        this.entityName = entityName;
    }

    public TaskGift freshCopy() {
        TaskGift taskCopy = new TaskGift(this.amountNeeded, this.item, this.entityName);
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
        String lore = color + "Give " + progress + "/" + amountNeeded + " " + item.getItemMeta().getDisplayName() + " to " + entityName;
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

    public void setOnCompleteActions(List<Action> onCompleteActions) {
        this.onCompleteActions = onCompleteActions;
    }
}
