package io.github.lix3nn53.guardiansofadelia.guardian.attribute;

public enum AttributeType {
    FIRE,
    LIGHTNING,
    EARTH,
    WATER,
    WIND;

    public String getDescription() {
        switch (this) {
            case FIRE:
                return getIncrementPerPoint() + " bonus physical damage per point";
            case LIGHTNING:
                return getIncrementPerPoint() + " bonus magic damage per point";
            case EARTH:
                return getIncrementPerPoint() + " bonus max health per point";
            case WATER:
                return getIncrementPerPoint() + " bonus max mana per point";
            case WIND:
                return getIncrementPerPoint() * 100 + "% bonus critical chance per point(max 40%)";
        }
        return "description";
    }

    public double getIncrementPerPoint() {
        switch (this) {
            case FIRE:
                return 1;
            case LIGHTNING:
                return 1;
            case EARTH:
                return 10;
            case WATER:
                return 1;
            case WIND:
                return 0.001;
        }
        return 1;
    }
}
