package io.github.lix3nn53.guardiansofadelia.guardian.element;


import io.github.lix3nn53.guardiansofadelia.locale.Translation;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;

public enum ElementType {
    FIRE,
    WATER,
    EARTH,
    AIR,
    LIGHTNING;

    private String getCustomName(String lang) {
        switch (this) {
            case FIRE:
                return getChatPalette() + Translation.t(lang, "element.fire");
            case WATER:
                return getChatPalette() + Translation.t(lang, "element.water");
            case EARTH:
                return getChatPalette() + Translation.t(lang, "element.earth");
            case AIR:
                return getChatPalette() + Translation.t(lang, "element.air");
            case LIGHTNING:
                return getChatPalette() + Translation.t(lang, "element.lightning");
        }
        return "elementCustomNameErr";
    }

    public ChatPalette getChatPalette() {
        switch (this) {
            case FIRE:
                return ChatPalette.RED;
            case WATER:
                return ChatPalette.BLUE;
            case EARTH:
                return ChatPalette.GREEN_DARK;
            case AIR:
                return ChatPalette.WHITE;
            case LIGHTNING:
                return ChatPalette.PURPLE;
        }

        return null;
    }

    public String getIcon() {
        switch (this) {
            case FIRE:
                return "\uD83D\uDD25";
            case WATER:
                return "\uD83C\uDF0A";
            case EARTH:
                return "◎";
            case AIR:
                return "๑";
            case LIGHTNING:
                return "⚡";
        }

        return null;
    }

    public String getFullName(String lang) {
        return getChatPalette() + getIcon() + " " + getCustomName(lang);
    }
}
