package io.github.lix3nn53.guardiansofadelia.guardian.attribute;

public enum AttributeType {
    STRENGTH,
    INTELLIGENCE,
    ENDURANCE,
    SPIRIT,
    DEXTERITY;

    public String getDescription() {
        switch (this) {
            case STRENGTH:
                return getIncrementPerPoint() + " bonus physical damage per point";
            case INTELLIGENCE:
                return getIncrementPerPoint() + " bonus magic damage per point";
            case ENDURANCE:
                return getIncrementPerPoint() + " bonus max health per point";
            case SPIRIT:
                return getIncrementPerPoint() + " bonus max mana per point";
            case DEXTERITY:
                return getIncrementPerPoint() * 100 + "% bonus critical chance per point(max 40%)";
        }
        return "description";
    }

    public double getIncrementPerPoint() {
        switch (this) {
            case STRENGTH:
                return 1;
            case INTELLIGENCE:
                return 1;
            case ENDURANCE:
                return 10;
            case SPIRIT:
                return 1;
            case DEXTERITY:
                return 0.001;
        }
        return 1;
    }
}
