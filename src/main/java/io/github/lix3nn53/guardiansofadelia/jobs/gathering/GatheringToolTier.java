package io.github.lix3nn53.guardiansofadelia.jobs.gathering;

public enum GatheringToolTier {
    WOODEN,
    STONE,
    IRON,
    GOLDEN,
    DIAMOND;

    public int getDurability() {
        switch (this) {
            case WOODEN:
                return 8;
            case STONE:
                return 12;
            case IRON:
                return 16;
            case GOLDEN:
                return 20;
            case DIAMOND:
                return 30;
        }
        return 8;
    }

    @Override
    public String toString() {
        String s = this.name();
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
