package io.github.lix3nn53.guardiansofadelia.creatures.pets;

import org.bukkit.ChatColor;
import org.bukkit.entity.Horse;


public enum Mount {
    BLACK,
    BROWN,
    CHESTNUT,
    CREAMY,
    DARK_BROWN,
    GRAY,
    WHITE;

    public String getName() {
        switch (this) {
            case BLACK:
                return ChatColor.GRAY + "Black Horse";
            case BROWN:
                return ChatColor.YELLOW + "Brown Horse";
            case CHESTNUT:
                return ChatColor.YELLOW + "Chestnut Horse";
            case CREAMY:
                return ChatColor.YELLOW + "Creamy Horse";
            case DARK_BROWN:
                return ChatColor.YELLOW + "DarkBrown Horse";
            case GRAY:
                return ChatColor.GRAY + "Gray Horse";
            case WHITE:
                return ChatColor.WHITE + "White Horse";
        }
        return "NULL_MOUNT";
    }

    public Horse.Color getColor() {
        switch (this) {
            case BLACK:
                return Horse.Color.BLACK;
            case BROWN:
                return Horse.Color.BROWN;
            case CHESTNUT:
                return Horse.Color.CHESTNUT;
            case CREAMY:
                return Horse.Color.CREAMY;
            case DARK_BROWN:
                return Horse.Color.DARK_BROWN;
            case GRAY:
                return Horse.Color.GRAY;
            case WHITE:
                return Horse.Color.WHITE;
        }
        return Horse.Color.BLACK;
    }
}
