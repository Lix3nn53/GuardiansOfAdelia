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
                return "bonus physical damage";
            case LIGHTNING:
                return "bonus magic damage";
            case EARTH:
                return "bonus health";
            case WATER:
                return "bonus mana";
            case WIND:
                return "bonus critical chance";
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
                return 0.01;
        }
        return 1;
    }
}
