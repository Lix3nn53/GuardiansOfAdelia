package io.github.lix3nn53.guardiansofadelia.guardian.element;


import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;

public enum ElementType {
    FIRE,
    WATER,
    EARTH,
    AIR,
    LIGHTNING;

    private String getCustomName() {
        switch (this) {
            case FIRE:
                return getChatPalette() + "Fire";
            case WATER:
                return getChatPalette() + "Water";
            case EARTH:
                return getChatPalette() + "Earth";
            case AIR:
                return getChatPalette() + "Air";
            case LIGHTNING:
                return getChatPalette() + "Lightning";
        }
        return "elementCustomName";
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

    public String getFullName() {
        return getChatPalette() + getIcon() + " " + getCustomName();
    }
}
