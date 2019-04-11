package io.github.lix3nn53.guardiansofadelia.minigames;

import net.md_5.bungee.api.ChatColor;

public enum MinigameType {
    DUNGEON;

    public String getName() {
        if (this.equals(MinigameType.DUNGEON)) {
            return ChatColor.AQUA + "Dungeon";
        }
        return "minigame";
    }
}
