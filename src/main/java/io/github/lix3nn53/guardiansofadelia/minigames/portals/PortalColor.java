package io.github.lix3nn53.guardiansofadelia.minigames.portals;

public enum PortalColor {
    BLUE,
    GREEN,
    ORANGE,
    PURPLE,
    RED;

    public int getCustomModelData() {
        switch (this) {
            case GREEN:
                return 7;
            case ORANGE:
                return 8;
            case PURPLE:
                return 9;
            case RED:
                return 10;
        }
        return 6;
    }
}
