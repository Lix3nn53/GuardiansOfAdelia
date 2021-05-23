package io.github.lix3nn53.guardiansofadelia.jobs.gathering;

public enum GatheringToolTier {
    WOODEN,
    STONE,
    IRON,
    GOLDEN,
    DIAMOND;

    public GatheringToolTier getNext() {
        switch (this) {
            case WOODEN:
                return STONE;
            case STONE:
                return IRON;
            case IRON:
                return GOLDEN;
            case GOLDEN:
                return DIAMOND;
            case DIAMOND:
                return null;
        }
        return null;
    }

    public int getDurability() {
        switch (this) {
            case WOODEN:
                return 10;
            case STONE:
                return 15;
            case IRON:
                return 20;
            case GOLDEN:
                return 30;
            case DIAMOND:
                return 50;
        }
        return 8;
    }

    @Override
    public String toString() {
        String s = this.name();
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    public String toString(GatheringToolType gatheringToolType) {
        switch (gatheringToolType) {
            case AXE:
            case HOE:
            case PICKAXE:
                return this.toString();
            case FISHING_ROD:
                switch (this) {
                    case WOODEN:
                        return "Damaged";
                    case STONE:
                        return "Weak";
                    case IRON:
                        return "Normal";
                    case GOLDEN:
                        return "Good";
                    case DIAMOND:
                        return "Super";
                }
            case BOTTLE:
                switch (this) {
                    case WOODEN:
                        return "Little";
                    case STONE:
                        return "Small";
                    case IRON:
                        return "Normal";
                    case GOLDEN:
                        return "Big";
                    case DIAMOND:
                        return "Huge";
                }
        }

        return "ERROR GatheringToolType toString";
    }
}
