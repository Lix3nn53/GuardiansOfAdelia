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
                return getIncrement() + " bonus physical damage per point";
            case LIGHTNING:
                return getIncrement() + " bonus magic damage per point";
            case EARTH:
                return getIncrement() + " bonus max health per point";
            case WATER:
                return getIncrement() + " bonus max mana per point";
            case WIND:
                return getIncrement() * 100 + "% bonus critical chance per point";
        }
        return "description";
    }

    public double getIncrement() {
        switch (this) {
            case FIRE:
                return 1;
            case LIGHTNING:
                return 1;
            case EARTH:
                return 5;
            case WATER:
                return 5;
            case WIND:
                return 0.005;
        }
        return 1;
    }
}
