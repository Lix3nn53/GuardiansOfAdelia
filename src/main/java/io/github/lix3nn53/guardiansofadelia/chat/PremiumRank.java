package io.github.lix3nn53.guardiansofadelia.chat;


import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;

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
}
