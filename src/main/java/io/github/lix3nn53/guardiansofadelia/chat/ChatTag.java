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
    SHADOW;

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
        }
        return ChatColor.GRAY;
    }

    public int getRequiredQuest() {
        switch (this) {
            case SENIOR:
                return 27;
            case LORD:
                return 51;
            case HERO:
                return 70;
            case TITAN:
                return 92;
            case BRAVE:
                return 999;
            case GLADIATOR:
                return 999;
            case CHAMPION:
                return 999;
            case DRAGON:
                return 999;
            case MAGICIAN:
                return 999;
            case SAGE:
                return 999;
            case WIZARD:
                return 999;
            case ETERNAL:
                return 999;
            case THIEF:
                return 999;
            case RINGLEADER:
                return 999;
            case TRICKSTER:
                return 999;
            case SHADOW:
                return 999;
        }
        return 1;
    }
}
