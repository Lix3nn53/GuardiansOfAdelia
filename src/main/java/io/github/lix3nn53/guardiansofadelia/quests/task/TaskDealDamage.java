package io.github.lix3nn53.guardiansofadelia.quests.task;

import io.github.lix3nn53.guardiansofadelia.quests.actions.Action;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class TaskDealDamage implements Task {

    private final int damageNeeded;
    private final String mobName;
    private final List<Action> onCompleteActions = new ArrayList<>();
    private int progress;


    public TaskDealDamage(final String mobName, final int damageNeeded) {
        this.mobName = mobName;
        this.damageNeeded = damageNeeded;
        progress = 0;
    }

    public String getObjectiveString() {
        ChatColor color;
        if (isCompleted()) {
            color = ChatColor.GREEN;
        } else {
            color = ChatColor.YELLOW;
        }
        String lore = color + "Deal " + progress + "/" + damageNeeded + " damage to " + mobName;
        return lore;
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

    @Override
    public int getProgress() {
        return this.progress;
    }

    @Override
    public void setProgress(int progress) {
        this.progress = progress;
    }

    public boolean progress(LivingEntity livingEntity, int damageDeal, Player player) {
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
}
