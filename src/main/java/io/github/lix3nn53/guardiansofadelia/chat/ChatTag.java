package io.github.lix3nn53.guardiansofadelia.chat;

import org.bukkit.ChatColor;

public enum ChatTag {
    NOVICE, //MAIN-QUEST-LINE TAGS
    SENIOR,
    LORD,
    HERO,
    TITAN,
    BRAVE, //WARRIOR TAGS
    GLADIATOR,
    CHAMPION,
    DRAGON,
    MAGICIAN, //MAGE TAGS
    SAGE,
    WIZARD,
    ETERNAL,
    THIEF, //THIEF TAGS
    RINGLEADER,
    TRICKSTER,
    SHADOW,
    HOBBIT, //MAP-AREA-QUEST-LINE TAGS
    CANDY,
    SNOWMAN,
    GHOST,
    LAVAWALKER;

    public ChatColor getChatColor() {
        switch (this) {
            case SENIOR:
                return ChatColor.YELLOW;
            case LORD:
                return ChatColor.GREEN;
            case HERO:
                return ChatColor.GOLD;
            case TITAN:
                return ChatColor.AQUA;
            case BRAVE:
                return ChatColor.GRAY;
            case GLADIATOR:
                return ChatColor.YELLOW;
            case CHAMPION:
                return ChatColor.GOLD;
            case DRAGON:
                return ChatColor.RED;
            case MAGICIAN:
                return ChatColor.GRAY;
            case SAGE:
                return ChatColor.YELLOW;
            case WIZARD:
                return ChatColor.LIGHT_PURPLE;
            case ETERNAL:
                return ChatColor.GOLD;
            case THIEF:
                return ChatColor.GRAY;
            case RINGLEADER:
                return ChatColor.YELLOW;
            case TRICKSTER:
                return ChatColor.LIGHT_PURPLE;
            case SHADOW:
                return ChatColor.DARK_PURPLE;
            case HOBBIT:
                return ChatColor.GREEN;
            case CANDY:
                return ChatColor.LIGHT_PURPLE;
            case SNOWMAN:
                return ChatColor.AQUA;
            case GHOST:
                return ChatColor.YELLOW;
            case LAVAWALKER:
                return ChatColor.RED;
        }
        return ChatColor.GRAY;
    }
}
