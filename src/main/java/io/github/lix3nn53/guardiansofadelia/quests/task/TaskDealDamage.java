package io.github.lix3nn53.guardiansofadelia.quests.task;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.quests.actions.Action;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.locale.Translation;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.MobManager;
import net.md_5.bungee.api.ChatColor;
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
        MobManager mobManager = MythicBukkit.inst().getMobManager();
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

    public String getTablistInfoString(String language) {
        ChatPalette chatPalette = getChatPalette();

        return chatPalette + Translation.t(language, "quest.task.dealDamage.l1") + getProgress() + "/" + getRequiredProgress() + Translation.t(language, "quest.task.dealDamage.l2") + ChatColor.stripColor(mobName);
    }

    public String getItemLoreString(GuardianData guardianData) {
        ChatPalette color;
        if (isCompleted()) {
            color = ChatPalette.GREEN_DARK;
        } else {
            color = ChatPalette.YELLOW;
        }

        return color + Translation.t(guardianData, "quest.task.dealDamage.l1") + damageNeeded + Translation.t(guardianData, "quest.task.dealDamage.l2") + ChatColor.stripColor(mobName);
    }

    @Override
    public boolean isCompleted() {
        return progress >= damageNeeded;
    }

    @Override
    public boolean progress(Player player, int questID, int taskIndex, boolean ignorePrevent) {
        if (this.progress < this.damageNeeded) {
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
                    return false;
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
        return damageNeeded;
    }

    public boolean progress(String internalName, int damageDeal, Player player, int questID, int taskIndex, boolean ignorePrevent) {
        if (internalName.equals(this.internalName)) {
            progressBy(player, damageDeal, questID, taskIndex, ignorePrevent);
            return true;
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
    public ChatPalette getChatPalette() {
        if (isCompleted()) return ChatPalette.GREEN_DARK;

        return ChatPalette.RED;
    }
}
