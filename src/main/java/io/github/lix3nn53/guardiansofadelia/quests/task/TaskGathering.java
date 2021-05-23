package io.github.lix3nn53.guardiansofadelia.quests.task;

import io.github.lix3nn53.guardiansofadelia.jobs.gathering.Ingredient;
import io.github.lix3nn53.guardiansofadelia.quests.actions.Action;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class TaskGathering implements Task {

    private final int amountNeeded;
    private final Ingredient ingredient;
    private List<Action> onCompleteActions = new ArrayList<>();
    private int progress;

    public TaskGathering(final Ingredient ingredient, final int amountNeeded) {
        this.ingredient = ingredient;
        this.amountNeeded = amountNeeded;
        progress = 0;
    }

    public TaskGathering freshCopy() {
        TaskGathering taskCopy = new TaskGathering(this.ingredient, this.amountNeeded);
        taskCopy.setOnCompleteActions(this.onCompleteActions);
        return taskCopy;
    }

    public String getTablistInfoString() {
        ChatColor chatColor = getChatColor();

        return chatColor + "Gather " + getProgress() + "/" + getRequiredProgress() + " " + ChatColor.stripColor(ingredient.getItemStack(1).getItemMeta().getDisplayName());
    }

    public String getItemLoreString() {
        ChatColor color;
        if (isCompleted()) {
            color = ChatColor.GREEN;
        } else {
            color = ChatColor.YELLOW;
        }
        return color + "Gather " + amountNeeded + " " + ChatColor.stripColor(ingredient.getItemStack(1).getItemMeta().getDisplayName());
    }

    @Override
    public boolean isCompleted() {
        return progress >= amountNeeded;
    }

    @Override
    public boolean progress(Player player, int questID, int taskIndex, boolean ignorePrevent) {
        if (this.progress < this.amountNeeded) {
            this.progress++;
            if (isCompleted()) {
                boolean prevent = false;
                if (!ignorePrevent) {
                    for (Action action : onCompleteActions) {
                        boolean b = action.preventTaskCompilation();
                        if (b) {
                            prevent = true;
                            action.perform(player, questID, taskIndex);
                            break;
                        }
                    }
                }

                if (prevent) {
                    this.progress--;
                    return false;
                }

                for (Action action : onCompleteActions) {
                    action.perform(player, questID, taskIndex);
                }
            }
            return true;
        }
        return false;
    }

    public void progressBy(Player player, int progress, int questID, int taskIndex, boolean ignorePrevent) {
        this.progress += progress;
        if (isCompleted()) {
            boolean prevent = false;
            if (!ignorePrevent) {
                for (Action action : onCompleteActions) {
                    boolean b = action.preventTaskCompilation();
                    if (b) {
                        prevent = true;
                        action.perform(player, questID, taskIndex);
                        break;
                    }
                }
            }

            if (prevent) {
                this.progress--;
                return;
            }

            for (Action action : onCompleteActions) {
                action.perform(player, questID, taskIndex);
            }
        }
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
    public int getRequiredProgress() {
        return amountNeeded;
    }

    public void setProgress(Player player, int progress, int questID, int taskIndex) {
        this.progress = progress;
        if (isCompleted()) {
            for (Action action : onCompleteActions) {
                action.perform(player, questID, taskIndex);
            }
        }
    }

    @Override
    public void addOnCompleteAction(Action action) {
        onCompleteActions.add(action);
    }

    public void setOnCompleteActions(List<Action> onCompleteActions) {
        this.onCompleteActions = onCompleteActions;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public ChatColor getChatColor() {
        if (isCompleted()) return ChatColor.GREEN;

        return ChatColor.RED;
    }
}
