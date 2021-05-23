package io.github.lix3nn53.guardiansofadelia.quests.task;

import io.github.lix3nn53.guardiansofadelia.quests.actions.Action;
import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class TaskInteract implements Task {

    private final int npcId;
    private List<Action> onCompleteActions = new ArrayList<>();
    private int completed = 0;

    public TaskInteract(final int npcId) {
        this.npcId = npcId;
    }

    public TaskInteract freshCopy() {
        TaskInteract taskCopy = new TaskInteract(this.npcId);
        taskCopy.setOnCompleteActions(this.onCompleteActions);
        return taskCopy;
    }

    public String getTablistInfoString() {
        ChatColor chatColor = getChatColor();

        NPCRegistry registry = CitizensAPI.getNPCRegistry();
        NPC npc = registry.getById(npcId);

        return chatColor + "Talk to " + ChatColor.stripColor(npc.getName());
    }

    public String getItemLoreString() {
        NPCRegistry registry = CitizensAPI.getNPCRegistry();
        NPC npc = registry.getById(npcId);
        ChatColor color;
        if (isCompleted()) {
            color = ChatColor.GREEN;
        } else {
            color = ChatColor.YELLOW;
        }
        return color + "Talk to " + ChatColor.stripColor(npc.getName());
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

    public boolean progress(int npcId, Player player, int questID, int taskIndex, boolean ignorePrevent) {
        if (npcId == this.npcId) {
            if (progress(player, questID, taskIndex, ignorePrevent)) {
                NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
                MessageUtils.sendCenteredMessage(player, ChatColor.LIGHT_PURPLE + "Quest Interact" + ChatColor.GRAY + " with " + npcRegistry.getById(npcId).getName());
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
