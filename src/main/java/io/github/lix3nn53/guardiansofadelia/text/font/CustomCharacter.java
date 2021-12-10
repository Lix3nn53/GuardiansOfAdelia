package io.github.lix3nn53.guardiansofadelia.text.font;

import com.google.common.base.Preconditions;
import net.md_5.bungee.api.ChatColor;

import java.util.HashMap;
import java.util.Map;

public final class CustomCharacter {

    private static final Map<String, CustomCharacter> BY_NAME = new HashMap<>();

    public static final CustomCharacter MENU_54 = new CustomCharacter("MENU_54", '섃', 1, 20, ChatColor.WHITE);
    public static final CustomCharacter LOGO = new CustomCharacter("LOGO", 'ꍋ', -3, 18, ChatColor.WHITE);

    private final Character character;
    private int negativeSpacesBefore;
    private int negativeSpacesAfter;
    private ChatColor color;

    CustomCharacter(String name, Character c, int negativeSpacesBefore, int negativeSpacesAfter, ChatColor color) {
        this.character = c;
        this.negativeSpacesBefore = negativeSpacesBefore;
        this.negativeSpacesAfter = negativeSpacesAfter;
        this.color = color;
        BY_NAME.put(name, this);
    }

    public static CustomCharacter[] values() {
        return BY_NAME.values().toArray(new CustomCharacter[0]);
    }

    public static CustomCharacter valueOf(String name) {
        Preconditions.checkNotNull(name, "Name is null");
        CustomCharacter defined = BY_NAME.get(name);
        Preconditions.checkArgument(defined != null, "No enum constant " + CustomCharacter.class.getName() + "." + name);
        return defined;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        if (negativeSpacesBefore != 0) {
            Character c = negativeSpacesBefore > 0 ? NegativeSpace.NEGATIVE_8.toCharacter() : ' ';
            for (int i = 0; i < Math.abs(negativeSpacesBefore); i++) {
                result.append(c);
            }
        }

        result.append(character);

        if (negativeSpacesAfter != 0) {
            Character c = negativeSpacesAfter > 0 ? NegativeSpace.NEGATIVE_8.toCharacter() : ' ';
            for (int i = 0; i < Math.abs(negativeSpacesAfter); i++) {
                result.append(c);
            }
        }

        return this.color + result.toString();
    }

    public void setNegativeSpacesBefore(int negativeSpacesBefore) {
        this.negativeSpacesBefore = negativeSpacesBefore;
    }

    public void setNegativeSpacesAfter(int negativeSpacesAfter) {
        this.negativeSpacesAfter = negativeSpacesAfter;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }
}
