package io.github.lix3nn53.guardiansofadelia.quests.task;

import io.github.lix3nn53.guardiansofadelia.quests.actions.Action;
import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
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

    public String getTablistInfoString() {
        ChatColor chatColor = getChatColor();

        return chatColor + "Go to " + blockLoc + " and click " + blockMat.toString() + " block " + completed + "/1";
    }

    public String getItemLoreString() {
        ChatColor color;
        if (isCompleted()) {
            color = ChatColor.GREEN;
        } else {
            color = ChatColor.YELLOW;
        }
        return color + "Go to " + blockLoc.toString() + " and click " + blockMat.toString() + " block";
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

    public boolean progress(Player player, Location targetBlockLoc) {
        double distanceSquared = targetBlockLoc.distanceSquared(this.blockLoc);

        int maxDistance = 81;

        player.sendMessage("distanceSquared: " + distanceSquared);

        if (distanceSquared <= maxDistance) {
            if (progress(player)) {
                MessageUtils.sendCenteredMessage(player, ChatColor.LIGHT_PURPLE + "Quest reach " + this.blockLoc.toString());
                return true;
            }
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
    public int getRequiredProgress() {
        return 1;
    }

    @Override
    public void addOnCompleteAction(Action action) {
        onCompleteActions.add(action);
    }

    public void setOnCompleteActions(List<Action> onCompleteActions) {
        this.onCompleteActions = onCompleteActions;
    }

    @Override
    public ChatColor getChatColor() {
        if (isCompleted()) return ChatColor.GREEN;

        return ChatColor.RED;
    }
}
