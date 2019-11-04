package io.github.lix3nn53.guardiansofadelia.quests.task;

import io.github.lix3nn53.guardiansofadelia.quests.actions.Action;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class TaskKill implements Task {

    private final int amountNeeded;

    private final String mobName;
    private List<Action> onCompleteActions = new ArrayList<>();
    private int progress;

    public TaskKill(final String mobName, final int amountNeeded) {
        this.mobName = mobName;
        this.amountNeeded = amountNeeded;
        progress = 0;
    }

    public TaskKill freshCopy() {
        TaskKill taskCopy = new TaskKill(this.mobName, this.amountNeeded);
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
        String lore = color + "Kill " + progress + "/" + amountNeeded + " " + mobName;
        return lore;
    }

    public String getObjectiveStringNoProgress() {
        ChatColor color;
        if (isCompleted()) {
            color = ChatColor.GREEN;
        } else {
            color = ChatColor.YELLOW;
        }
        String lore = color + "Kill " + amountNeeded + " " + mobName;
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

    public boolean progress(LivingEntity livingEntity, Player player) {
        if (livingEntity.isCustomNameVisible()) {
            String customName = livingEntity.getCustomName();
            if (customName.equals(this.mobName)) {
                return progress(player);
            }
        }
        return false;
    }

    @Override
    public void addOnCompleteAction(Action action) {
        onCompleteActions.add(action);
    }

    public void setOnCompleteActions(List<Action> onCompleteActions) {
        this.onCompleteActions = onCompleteActions;
    }
}
