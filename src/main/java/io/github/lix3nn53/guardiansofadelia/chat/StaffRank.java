package io.github.lix3nn53.guardiansofadelia.chat;


import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacter;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacterChatTag;

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

    public CustomCharacter getCustomCharacter() {
        switch (this) {
            case OWNER:
                return CustomCharacterChatTag.NOVICE;
            case ADMIN:
                return CustomCharacterChatTag.NOVICE;
            case DEVELOPER:
                return CustomCharacterChatTag.NOVICE;
            case BUILDER:
                return CustomCharacterChatTag.NOVICE;
            case SUPPORT:
                return CustomCharacterChatTag.NOVICE;
            case YOUTUBER:
                return CustomCharacterChatTag.NOVICE;
            case TRAINEE:
                return CustomCharacterChatTag.NOVICE;
        }

        return null;
    }
}
