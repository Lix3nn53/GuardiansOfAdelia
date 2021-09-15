package io.github.lix3nn53.guardiansofadelia.utilities;

import com.google.common.base.Preconditions;
import net.md_5.bungee.api.ChatColor;

import java.util.HashMap;
import java.util.Map;

public final class ChatPalette {

    public static final ChatPalette BROWN_DARK = new ChatPalette("#714150", "BROWN_DARK");
    public static final ChatPalette BROWN = new ChatPalette("#93535a", "BROWN");
    public static final ChatPalette BROWN_LIGHT = new ChatPalette("#b16f68", "BROWN_LIGHT");
    public static final ChatPalette BROWN_LIGHTER = new ChatPalette("#ce977e", "BROWN_LIGHTER");
    public static final ChatPalette CREAM_LIGHT = new ChatPalette("#eea160", "CREAM_LIGHT");
    public static final ChatPalette CREAM_LIGHTER = new ChatPalette("#f4cca1", "CREAM_LIGHTER");
    public static final ChatPalette GREEN_LIGHT = new ChatPalette("#3dd63d", "GREEN_LIGHT");
    public static final ChatPalette GREEN = new ChatPalette("#82c33c", "GREEN");
    public static final ChatPalette GREEN_DARK = new ChatPalette("#29a33d", "GREEN_DARK");
    public static final ChatPalette BLUE_LIGHT = new ChatPalette("#8aebf1", "BLUE_LIGHT");
    public static final ChatPalette BLUE = new ChatPalette("#28ccdf", "BLUE");
    public static final ChatPalette BLUE_DARK = new ChatPalette("#3978a8", "BLUE_DARK");
    public static final ChatPalette BLUE_DARKER = new ChatPalette("#394778", "BLUE_DARKER");
    public static final ChatPalette PURPLE = new ChatPalette("#703d8f", "PURPLE");
    public static final ChatPalette PURPLE_LIGHT = new ChatPalette("#964093", "PURPLE_LIGHT");
    public static final ChatPalette PINK = new ChatPalette("#d16195", "PINK");
    public static final ChatPalette PINK_LIGHT = new ChatPalette("#ffaeb6", "PINK_LIGHT");
    public static final ChatPalette YELLOW = new ChatPalette("#ffff4d", "YELLOW");
    public static final ChatPalette GOLD = new ChatPalette("#f4ae0b", "GOLD");
    public static final ChatPalette ORANGE = new ChatPalette("#f47e1b", "ORANGE");
    public static final ChatPalette RED = new ChatPalette("#e6482e", "RED");
    public static final ChatPalette RED_DARK = new ChatPalette("#c32222", "RED_DARK");
    public static final ChatPalette BLACK = new ChatPalette("#302c2e", "BLACK");
    public static final ChatPalette GRAY_DARK = new ChatPalette("#5a5353", "GRAY_DARK");
    public static final ChatPalette GRAY = new ChatPalette("#cfc6b8", "GRAY");
    public static final ChatPalette WHITE = new ChatPalette("#dff6f5", "WHITE");
    private static final Map<String, ChatPalette> BY_NAME = new HashMap<>();
    private final String toString;
    private final ChatColor chatColor;

    ChatPalette(String hex, String name) {
        this.chatColor = ChatColor.of(hex);
        this.toString = this.chatColor.toString();
        BY_NAME.put(name, this);
    }

    public static ChatPalette[] values() {
        return BY_NAME.values().toArray(new ChatPalette[0]);
    }

    public static ChatPalette valueOf(String name) {
        Preconditions.checkNotNull(name, "Name is null");
        ChatPalette defined = BY_NAME.get(name);
        Preconditions.checkArgument(defined != null, "No enum constant " + ChatPalette.class.getName() + "." + name);
        return defined;
    }

    @Override
    public String toString() {
        return toString;
    }

    public ChatColor toChatColor() {
        return chatColor;
    }
}
