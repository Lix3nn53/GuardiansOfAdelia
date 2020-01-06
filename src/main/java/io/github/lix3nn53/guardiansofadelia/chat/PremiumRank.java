package io.github.lix3nn53.guardiansofadelia.chat;

import org.bukkit.ChatColor;

public enum PremiumRank {
    NONE,
    HERO,
    LEGEND,
    TITAN;

    public ChatColor getChatColor() {
        switch (this) {
            case HERO:
                return ChatColor.GREEN;
            case LEGEND:
                return ChatColor.GOLD;
            case TITAN:
                return ChatColor.LIGHT_PURPLE;
        }
        return ChatColor.GRAY;
    }
}
