package io.github.lix3nn53.guardiansofadelia.quests.task;

import io.github.lix3nn53.guardiansofadelia.quests.actions.Action;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.mobs.MobManager;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class TaskDealDamage implements Task {

    private final int damageNeeded;
    private final String mobName;
    private final String internalName;
    private List<Action> onCompleteActions = new ArrayList<>();
    private int progress = 0;


    public TaskDealDamage(final String internalName, final int damageNeeded) {
        MobManager mobManager = MythicMobs.inst().getMobManager();
        MythicMob mythicMob = mobManager.getMythicMob(internalName);

        this.mobName = mythicMob.getDisplayName().get();
        this.internalName = internalName;
        this.damageNeeded = damageNeeded;
    }

    public TaskDealDamage freshCopy() {
        TaskDealDamage taskCopy = new TaskDealDamage(this.internalName, this.damageNeeded);
        taskCopy.setOnCompleteActions(this.onCompleteActions);
        return taskCopy;
    }

    public String getTablistInfoString() {
        ChatColor chatColor = getChatColor();

        return chatColor + "Deal " + getProgress() + "/" + getRequiredProgress() + " damage to " + mobName;
    }

    public String getItemLoreString() {
        ChatColor color;
        if (isCompleted()) {
            color = ChatColor.GREEN;
        } else {
            color = ChatColor.YELLOW;
        }

        return color + "Deal " + damageNeeded + " damage to " + mobName;
    }

    @Override
    public boolean isCompleted() {
        return progress >= damageNeeded;
    }

    @Override
    public boolean progress(Player player) {
        if (progress < damageNeeded) {
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

    public boolean progress(Player player, int damageDeal) {
        if (progress < damageNeeded) {
            progress += damageDeal;
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

    @Override
    public int getRequiredProgress() {
        return damageNeeded;
    }

    public boolean progress(String internalName, int damageDeal, Player player) {
        if (internalName.equals(this.internalName)) {
            return progress(player, damageDeal);
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

    @Override
    public ChatColor getChatColor() {
        if (isCompleted()) return ChatColor.GREEN;

        return ChatColor.RED;
    }
}
