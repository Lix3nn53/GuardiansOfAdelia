package io.github.lix3nn53.guardiansofadelia.quests.task;

import io.github.lix3nn53.guardiansofadelia.quests.actions.Action;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class TaskCollect implements Task {

    private final List<String> nameOfMobsItemDropsFrom;
    private final double chance;
    private final int amountNeeded;
    private final ItemStack itemStack;
    private List<Action> onCompleteActions = new ArrayList<>();
    private int progress;

    public TaskCollect(final List<String> nameOfMobsItemDropsFrom, final double chance, final ItemStack itemStack, final int amountNeeded) {
        this.nameOfMobsItemDropsFrom = nameOfMobsItemDropsFrom;
        this.chance = chance;
        this.itemStack = itemStack.clone();
        this.amountNeeded = amountNeeded;
        progress = 0;
    }

    public TaskCollect freshCopy() {
        TaskCollect taskCopy = new TaskCollect(this.nameOfMobsItemDropsFrom, this.chance, this.itemStack, this.amountNeeded);
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
        String lore = color + "Collect " + progress + "/" + amountNeeded + " " + itemStack.getItemMeta().getDisplayName();
        return lore;
    }

    public String getObjectiveStringNoProgress() {
        ChatColor color;
        if (isCompleted()) {
            color = ChatColor.GREEN;
        } else {
            color = ChatColor.YELLOW;
        }
        String lore = color + "Collect " + amountNeeded + " " + itemStack.getItemMeta().getDisplayName();
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

    public ItemStack getItemStack() {
        return itemStack.clone();
    }

    public List<String> getNameOfMobsItemDropsFrom() {
        return nameOfMobsItemDropsFrom;
    }

    public double getChance() {
        return chance;
    }
}
