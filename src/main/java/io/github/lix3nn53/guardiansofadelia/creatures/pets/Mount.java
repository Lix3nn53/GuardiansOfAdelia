package io.github.lix3nn53.guardiansofadelia.creatures.pets;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
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

    public int getEggCustomModelData() {
        switch (this) {
            case BLACK:
                return 10000007;
            case BROWN:
                return 10000007;
            case CHESTNUT:
                return 10000007;
            case CREAMY:
                return 10000007;
            case DARK_BROWN:
                return 10000007;
            case GRAY:
                return 10000007;
            case WHITE:
                return 10000007;
        }
        return 10000007;
    }

    public ItemTier getItemTier() {
        switch (this) {
            case BLACK:
                return ItemTier.COMMON;
            case BROWN:
                return ItemTier.COMMON;
            case CHESTNUT:
                return ItemTier.COMMON;
            case CREAMY:
                return ItemTier.COMMON;
            case DARK_BROWN:
                return ItemTier.COMMON;
            case GRAY:
                return ItemTier.COMMON;
            case WHITE:
                return ItemTier.COMMON;
        }
        return ItemTier.COMMON;
    }
}
