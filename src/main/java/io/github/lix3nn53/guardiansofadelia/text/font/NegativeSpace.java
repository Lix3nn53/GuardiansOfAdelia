package io.github.lix3nn53.guardiansofadelia.text.font;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

public final class NegativeSpace {

    private static final Map<String, NegativeSpace> BY_NAME = new HashMap<>();

    public static final NegativeSpace NEGATIVE_8 = new NegativeSpace("NEGATIVE_8", '\uF808');
    public static final NegativeSpace NEGATIVE_7 = new NegativeSpace("NEGATIVE_7", '\uF807');
    public static final NegativeSpace NEGATIVE_6 = new NegativeSpace("NEGATIVE_6", '\uF806');
    public static final NegativeSpace NEGATIVE_5 = new NegativeSpace("NEGATIVE_5", '\uF805');
    public static final NegativeSpace NEGATIVE_4 = new NegativeSpace("NEGATIVE_4", '\uF804');
    public static final NegativeSpace NEGATIVE_3 = new NegativeSpace("NEGATIVE_3", '\uF803');
    public static final NegativeSpace NEGATIVE_2 = new NegativeSpace("NEGATIVE_2", '\uF802');
    public static final NegativeSpace NEGATIVE_1 = new NegativeSpace("NEGATIVE_1", '\uF801');

    public static final NegativeSpace NEGATIVE_16 = new NegativeSpace("NEGATIVE_16", '\uF809');
    public static final NegativeSpace NEGATIVE_32 = new NegativeSpace("NEGATIVE_32", '\uF80A');
    public static final NegativeSpace NEGATIVE_64 = new NegativeSpace("NEGATIVE_64", '\uF80B');
    public static final NegativeSpace NEGATIVE_128 = new NegativeSpace("NEGATIVE_128", '\uF80C');
    public static final NegativeSpace NEGATIVE_256 = new NegativeSpace("NEGATIVE_256", '\uF80D');
    public static final NegativeSpace NEGATIVE_512 = new NegativeSpace("NEGATIVE_512", '\uF80E');
    public static final NegativeSpace NEGATIVE_1024 = new NegativeSpace("NEGATIVE_1024", '\uF80F');

    public static final NegativeSpace POSITIVE_8 = new NegativeSpace("POSITIVE_8", '\uF828');
    public static final NegativeSpace POSITIVE_7 = new NegativeSpace("POSITIVE_7", '\uF827');
    public static final NegativeSpace POSITIVE_6 = new NegativeSpace("POSITIVE_6", '\uF826');
    public static final NegativeSpace POSITIVE_5 = new NegativeSpace("POSITIVE_5", '\uF825');
    public static final NegativeSpace POSITIVE_4 = new NegativeSpace("POSITIVE_4", '\uF824');
    public static final NegativeSpace POSITIVE_3 = new NegativeSpace("POSITIVE_3", '\uF823');
    public static final NegativeSpace POSITIVE_2 = new NegativeSpace("POSITIVE_2", '\uF822');
    public static final NegativeSpace POSITIVE_1 = new NegativeSpace("POSITIVE_1", '\uF821');

    public static final NegativeSpace POSITIVE_16 = new NegativeSpace("POSITIVE_16", '\uF829');
    public static final NegativeSpace POSITIVE_32 = new NegativeSpace("POSITIVE_32", '\uF82A');
    public static final NegativeSpace POSITIVE_64 = new NegativeSpace("POSITIVE_64", '\uF82B');
    public static final NegativeSpace POSITIVE_128 = new NegativeSpace("POSITIVE_128", '\uF82C');
    public static final NegativeSpace POSITIVE_256 = new NegativeSpace("POSITIVE_256", '\uF82D');
    public static final NegativeSpace POSITIVE_512 = new NegativeSpace("POSITIVE_512", '\uF82E');
    public static final NegativeSpace POSITIVE_1024 = new NegativeSpace("POSITIVE_1024", '\uF82F');

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
