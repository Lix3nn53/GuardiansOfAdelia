package io.github.lix3nn53.guardiansofadelia.quests.task;

import io.github.lix3nn53.guardiansofadelia.Items.Ingredient;
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

        return chatColor + "Gather " + getProgress() + "/" + getRequiredProgress() + " " + ingredient.getItemStack(1).getItemMeta().getDisplayName();
    }

    public String getItemLoreString() {
        ChatColor color;
        if (isCompleted()) {
            color = ChatColor.GREEN;
        } else {
            color = ChatColor.YELLOW;
        }
        String lore = color + "Gather " + amountNeeded + " " + ingredient.getItemStack(1).getItemMeta().getDisplayName();
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

    public boolean progressBy(Player player, int increment) {
        if (progress < amountNeeded) {
            progress += increment;
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
    public int getRequiredProgress() {
        return amountNeeded;
    }

    @Override
    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setProgress(Player player, int progress) {
        this.progress = progress;
        if (isCompleted()) {
            for (Action action : onCompleteActions) {
                action.perform(player);
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
        return ChatColor.GOLD;
    }
}
