package io.github.lix3nn53.guardiansofadelia.chat;


import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;

public enum StaffRank {
    NONE,
    OWNER,
    ADMIN,
    DEVELOPER,
    BUILDER,
    SUPPORT,
    YOUTUBER,
    TRAINEE;

    public ChatPalette getChatPalette() {
        switch (this) {
            case OWNER:
                return ChatPalette.RED_DARK;
            case ADMIN:
                return ChatPalette.PURPLE_LIGHT;
            case DEVELOPER:
                return ChatPalette.GOLD;
            case BUILDER:
                return ChatPalette.BLUE;
            case SUPPORT:
                return ChatPalette.BLUE_LIGHT;
            case YOUTUBER:
                return ChatPalette.RED;
            case TRAINEE:
                return ChatPalette.GREEN_DARK;
        }
        return ChatPalette.GRAY;
    }
}
