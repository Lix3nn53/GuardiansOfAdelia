package io.github.lix3nn53.guardiansofadelia.chat;


import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacter;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacterChatTag;

public enum PremiumRank {
    NONE,
    HERO,
    LEGEND,
    TITAN;

    public ChatPalette getChatPalette() {
        switch (this) {
            case HERO:
                return ChatPalette.GREEN_DARK;
            case LEGEND:
                return ChatPalette.GOLD;
            case TITAN:
                return ChatPalette.PURPLE_LIGHT;
        }
        return ChatPalette.GRAY;
    }

    public CustomCharacter getCustomCharacter() {
        switch (this) {
            case HERO:
                return CustomCharacterChatTag.NOVICE;
            case LEGEND:
                return CustomCharacterChatTag.NOVICE;
            case TITAN:
                return CustomCharacterChatTag.NOVICE;
        }

        return null;
    }
}
