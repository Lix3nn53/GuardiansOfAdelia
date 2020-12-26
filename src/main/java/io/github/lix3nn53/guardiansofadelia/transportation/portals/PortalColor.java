package io.github.lix3nn53.guardiansofadelia.transportation.portals;

public enum PortalColor {
    BLUE,
    GREEN,
    ORANGE,
    PURPLE,
    RED;

    public int getCustomModelData() {
        switch (this) {
            case GREEN:
                return 2;
            case ORANGE:
                return 3;
            case PURPLE:
                return 4;
            case RED:
                return 5;
        }
        return 1;
    }
}
