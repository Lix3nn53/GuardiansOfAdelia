package io.github.lix3nn53.guardiansofadelia.quests.task;

import io.github.lix3nn53.guardiansofadelia.quests.actions.Action;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import net.md_5.bungee.api.ChatColor;
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

    public String getTablistInfoString() {
        ChatPalette chatPalette = getChatPalette();

        return chatPalette + "Give " + getProgress() + "/" + getRequiredProgress() + " " + ChatColor.stripColor(item.getItemMeta().getDisplayName()) + " to " + ChatColor.stripColor(entityName);
    }

    public String getItemLoreString() {
        ChatPalette color;
        if (isCompleted()) {
            color = ChatPalette.GREEN_DARK;
        } else {
            color = ChatPalette.YELLOW;
        }
        return color + "Give " + amountNeeded + " " + ChatColor.stripColor(item.getItemMeta().getDisplayName()) + " to " + ChatColor.stripColor(entityName);
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
                    return true;
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

    @Override
    public void addOnCompleteAction(Action action) {
        onCompleteActions.add(action);
    }

    public void setOnCompleteActions(List<Action> onCompleteActions) {
        this.onCompleteActions = onCompleteActions;
    }

    @Override
    public ChatPalette getChatPalette() {
        if (isCompleted()) return ChatPalette.GREEN_DARK;

        return ChatPalette.RED;
    }

    public ItemStack getItem() {
        return item;
    }

    public String getEntityName() {
        return entityName;
    }
}
