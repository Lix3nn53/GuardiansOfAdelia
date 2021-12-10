package io.github.lix3nn53.guardiansofadelia.quests.task;

import io.github.lix3nn53.guardiansofadelia.commands.admin.CommandAdmin;
import io.github.lix3nn53.guardiansofadelia.quests.actions.Action;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
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
        ChatPalette chatPalette = getChatPalette();

        return chatPalette + "Go to x: " + blockLoc.getBlockX() + " y: " + blockLoc.getBlockY() + " z: " + blockLoc.getBlockZ() + " in "
                + blockLoc.getWorld().getName() + " and click " + blockMat.toString();
    }

    public String getItemLoreString() {
        ChatPalette color;
        if (isCompleted()) {
            color = ChatPalette.GREEN_DARK;
        } else {
            color = ChatPalette.YELLOW;
        }
        return color + "Go to x: " + blockLoc.getBlockX() + " y: " + blockLoc.getBlockY() + " z: " + blockLoc.getBlockZ() + " in "
                + blockLoc.getWorld().getName() + " and click " + blockMat.toString();
    }

    @Override
    public boolean isCompleted() {
        return completed > 0;
    }

    @Override
    public boolean progress(Player player, int questID, int taskIndex, boolean ignorePrevent) {
        if (this.completed == 0) {
            this.completed = 1;
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
                    this.completed = 0;
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

    public boolean progress(Player player, Location targetBlockLoc, int questID, int taskIndex, boolean ignorePrevent) {
        float distanceSquared = (float) targetBlockLoc.distanceSquared(this.blockLoc);

        int maxDistance = 81;

        if (CommandAdmin.DEBUG_MODE) player.sendMessage("distanceSquared: " + distanceSquared);

        if (distanceSquared <= maxDistance) {
            if (progress(player, questID, taskIndex, ignorePrevent)) {
                MessageUtils.sendCenteredMessage(player, ChatPalette.PURPLE_LIGHT + "Quest reach " + this.blockLoc);
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
    public ChatPalette getChatPalette() {
        if (isCompleted()) return ChatPalette.GREEN_DARK;

        return ChatPalette.RED;
    }

    public Location getBlockLoc() {
        return blockLoc;
    }
}
