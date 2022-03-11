package io.github.lix3nn53.guardiansofadelia.quests.task;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.quests.actions.Action;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.locale.Translation;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class TaskDungeon implements Task {

    private final int minDarkness;

    private final DungeonTheme dungeonTheme;
    private List<Action> onCompleteActions = new ArrayList<>();
    private int progress;

    public TaskDungeon(final String dungeonTheme, final int minDarkness) {
        HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
        if (!dungeonThemes.containsKey(dungeonTheme)) {
            GuardiansOfAdelia.getInstance().getLogger().warning("TaskDungeon: WRONG THEME");
        }

        this.dungeonTheme = dungeonThemes.get(dungeonTheme);
        this.minDarkness = minDarkness;

        progress = 0;
    }

    public TaskDungeon freshCopy() {
        TaskDungeon taskCopy = new TaskDungeon(this.dungeonTheme.getCode(), this.minDarkness);
        taskCopy.setOnCompleteActions(this.onCompleteActions);
        return taskCopy;
    }

    public String getTablistInfoString(String language) {
        ChatPalette chatPalette = getChatPalette();

        return chatPalette + Translation.t(language, "crafting.task.dungeon.l1") +
                ChatColor.stripColor(dungeonTheme.getName()) + Translation.t(language, "crafting.task.dungeon.l2") +
                this.minDarkness + Translation.t(language, "crafting.task.dungeon.l3");
    }

    public String getItemLoreString(GuardianData guardianData) {
        ChatPalette color;
        if (isCompleted()) {
            color = ChatPalette.GREEN_DARK;
        } else {
            color = ChatPalette.YELLOW;
        }

        return color + Translation.t(guardianData, "crafting.task.dungeon.l1") +
                dungeonTheme.getName() + Translation.t(guardianData, "crafting.task.dungeon.l2") +
                this.minDarkness + Translation.t(guardianData, "crafting.task.dungeon.l3");
    }

    @Override
    public boolean isCompleted() {
        return progress >= 1;
    }

    @Override
    public boolean progress(Player player, int questID, int taskIndex, boolean ignorePrevent) {
        if (this.progress < 1) {
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
        return 1;
    }

    public boolean progress(Player player, String dungeonTheme, int darkness, int questID, int taskIndex, boolean ignorePrevent) {
        if (dungeonTheme.equals(this.dungeonTheme.getCode()) && this.minDarkness >= darkness) {
            return progress(player, questID, taskIndex, ignorePrevent);
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
