package io.github.lix3nn53.guardiansofadelia.utilities;

import org.bukkit.ChatColor;

public enum StaffRank {
    NONE,
    OWNER,
    ADMIN,
    DEVELOPER,
    BUILDER,
    SUPPORT,
    YOUTUBER,
    TRAINEE;

    public ChatColor getChatColor() {
        switch (this) {
            case OWNER:
                return ChatColor.RED;
            case ADMIN:
                return ChatColor.LIGHT_PURPLE;
            case DEVELOPER:
                return ChatColor.GOLD;
            case BUILDER:
                return ChatColor.BLUE;
            case SUPPORT:
                return ChatColor.AQUA;
            case YOUTUBER:
                return ChatColor.RED;
            case TRAINEE:
                return ChatColor.GREEN;
        }
        return ChatColor.GRAY;
    }
}
