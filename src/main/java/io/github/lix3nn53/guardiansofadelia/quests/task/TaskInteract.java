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
    private final List<Action> onCompleteActions = new ArrayList<>();
    private int completed = 0;

    public TaskInteract(final int npcId) {
        this.npcId = npcId;
    }

    public String getObjectiveString() {
        NPCRegistry registry = CitizensAPI.getNPCRegistry();
        NPC npc = registry.getById(npcId);
        ChatColor color;
        if (isCompleted()) {
            color = ChatColor.GREEN;
        } else {
            color = ChatColor.YELLOW;
        }
        String lore = color + "Talk to " + npc.getName();
        return lore;
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
            return progress(player);
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
    public void addOnCompleteAction(Action action) {
        onCompleteActions.add(action);
    }
}
