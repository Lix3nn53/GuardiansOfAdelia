package io.github.lix3nn53.guardiansofadelia.text.font;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

public final class NegativeSpace {

    private static final Map<String, NegativeSpace> BY_NAME = new HashMap<>();

    public static final NegativeSpace NEGATIVE_8 = new NegativeSpace("NEGATIVE_8", '\uF808');
    public static final NegativeSpace POSITIVE_8 = new NegativeSpace("POSITIVE_8", '\uF828');

    private final Character character;

    NegativeSpace(String name, Character c) {
        this.character = c;
        BY_NAME.put(name, this);
    }

    public static NegativeSpace[] values() {
        return BY_NAME.values().toArray(new NegativeSpace[0]);
    }

    public static NegativeSpace valueOf(String name) {
        Preconditions.checkNotNull(name, "Name is null");
        NegativeSpace defined = BY_NAME.get(name);
        Preconditions.checkArgument(defined != null, "No enum constant " + NegativeSpace.class.getName() + "." + name);
        return defined;
    }

    @Override
    public String toString() {
        return character.toString();
    }

    public Character toCharacter() {
        return character;
    }
}
