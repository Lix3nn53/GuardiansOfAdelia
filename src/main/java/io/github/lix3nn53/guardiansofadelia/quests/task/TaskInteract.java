package io.github.lix3nn53.guardiansofadelia.quests.task;

import io.github.lix3nn53.guardiansofadelia.quests.actions.Action;
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

        return chatColor + "Talk to " + npc.getName();
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
        return color + "Talk to " + npc.getName();
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

    public boolean progress(int npcId, Player player) {
        if (npcId == this.npcId) {
            if (progress(player)) {
                NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
                player.sendMessage(ChatColor.YELLOW + "Quest interact with " + npcRegistry.getById(npcId).getName() + "");
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
        return ChatColor.GREEN;
    }
}
