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
                return 10000007;
            case ORANGE:
                return 10000008;
            case PURPLE:
                return 10000009;
            case RED:
                return 10000010;
        }
        return 10000006;
    }
}
