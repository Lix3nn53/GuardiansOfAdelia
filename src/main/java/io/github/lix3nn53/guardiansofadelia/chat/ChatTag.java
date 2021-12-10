package io.github.lix3nn53.guardiansofadelia.chat;


import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;

public enum ChatTag {
    NOVICE, //MAIN-QUEST-LINE TAGS
    SENIOR,
    LORD,
    GUARDIAN,
    SUPREME,
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

    public ChatPalette getChatPalette() {
        switch (this) {
            case SENIOR:
                return ChatPalette.YELLOW;
            case LORD:
                return ChatPalette.GREEN_DARK;
            case GUARDIAN:
                return ChatPalette.GOLD;
            case SUPREME:
                return ChatPalette.BLUE_LIGHT;
            case BRAVE:
                return ChatPalette.GRAY;
            case GLADIATOR:
                return ChatPalette.YELLOW;
            case CHAMPION:
                return ChatPalette.GOLD;
            case DRAGON:
                return ChatPalette.RED;
            case MAGICIAN:
                return ChatPalette.GRAY;
            case SAGE:
                return ChatPalette.YELLOW;
            case WIZARD:
                return ChatPalette.PURPLE_LIGHT;
            case ETERNAL:
                return ChatPalette.GOLD;
            case THIEF:
                return ChatPalette.GRAY;
            case RINGLEADER:
                return ChatPalette.YELLOW;
            case TRICKSTER:
                return ChatPalette.PURPLE_LIGHT;
            case SHADOW:
                return ChatPalette.PURPLE;
        }
        return ChatPalette.GRAY;
    }

    public int getRequiredQuest() {
        switch (this) {
            case SENIOR:
                return 27;
            case LORD:
                return 51;
            case GUARDIAN:
                return 70;
            case SUPREME:
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
