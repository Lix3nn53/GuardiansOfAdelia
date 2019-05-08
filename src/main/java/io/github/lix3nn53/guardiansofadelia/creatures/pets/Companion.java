package io.github.lix3nn53.guardiansofadelia.creatures.pets;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.ChatColor;
import org.bukkit.entity.Wolf;


public enum Companion {
    CHICKEN,
    SHEEP,
    COW,
    PIG,
    CAT_ALL_BLACK,
    CAT_BLACK,
    CAT_BRITISH_SHORTHAIR,
    CAT_CALICO,
    CAT_JELLIE,
    CAT_PERSIAN,
    CAT_RAGDOLL,
    CAT_RED,
    CAT_SIAMESE,
    CAT_TABBY,
    CAT_WHITE,
    RABBIT_BLACK,
    RABBIT_BLACK_AND_WHITE,
    RABBIT_BROWN,
    RABBIT_GOLD,
    RABBIT_SALT_AND_PEPPER,
    RABBIT_WHITE,
    CHOCOLATE,
    WOLF,
    TURTLE,
    POLAR_BEAR,
    PANDA,
    ICE_CREAM,
    FOX_RED,
    FOX_SNOW,
    VEX,
    MINI_DRAGON;

    public String getName() {
        switch (this) {
            case CHICKEN:
                return ChatColor.AQUA + "Chicken";
            case SHEEP:
                return ChatColor.WHITE + "Baby Sheep";
            case COW:
                return ChatColor.YELLOW + "Baby Cow";
            case PIG:
                return ChatColor.LIGHT_PURPLE + "Baby Pig";
            case CAT_ALL_BLACK:
                return ChatColor.GRAY + "AllBlack Cat";
            case CAT_BLACK:
                return ChatColor.GRAY + "Black Cat";
            case CAT_BRITISH_SHORTHAIR:
                return ChatColor.WHITE + "BritishShorthair Cat";
            case CAT_CALICO:
                return ChatColor.YELLOW + "Calico Cat";
            case CAT_JELLIE:
                return ChatColor.YELLOW + "Jellie Cat";
            case CAT_PERSIAN:
                return ChatColor.YELLOW + "Persian Cat";
            case CAT_RAGDOLL:
                return ChatColor.WHITE + "Ragdoll Cat";
            case CAT_RED:
                return ChatColor.GOLD + "Red Cat";
            case CAT_SIAMESE:
                return ChatColor.GRAY + "Siamese Cat";
            case CAT_TABBY:
                return ChatColor.YELLOW + "Tabby Cat";
            case CAT_WHITE:
                return ChatColor.WHITE + "White Cat";
            case RABBIT_BLACK:
                return ChatColor.GRAY + "Black Rabbit";
            case RABBIT_BLACK_AND_WHITE:
                return ChatColor.WHITE + "BlackAndWhite Rabbit";
            case RABBIT_BROWN:
                return ChatColor.YELLOW + "Brown Rabbit";
            case RABBIT_GOLD:
                return ChatColor.GOLD + "Gold Rabbit";
            case RABBIT_SALT_AND_PEPPER:
                return ChatColor.YELLOW + "SaltAndPepper Rabbit";
            case RABBIT_WHITE:
                return ChatColor.WHITE + "White Rabbit";
            case CHOCOLATE:
                return ChatColor.YELLOW + "Baby Chocolate";
            case WOLF:
                return ChatColor.WHITE + "Baby Wolf";
            case TURTLE:
                return ChatColor.GREEN + "Baby Turtle";
            case POLAR_BEAR:
                return ChatColor.AQUA + "Baby PolarBear";
            case PANDA:
                return ChatColor.GREEN + "Baby Panda";
            case ICE_CREAM:
                return ChatColor.LIGHT_PURPLE + "Ice Cream";
            case FOX_RED:
                return ChatColor.GOLD + "Red Fox";
            case FOX_SNOW:
                return ChatColor.AQUA + "Snow Fox";
            case VEX:
                return ChatColor.AQUA + "Vex";
            case MINI_DRAGON:
                return ChatColor.RED + "Baby Dragon";
        }
        return "NULL_COMPANION";
    }

    public void disguise(Wolf wolf) {
        switch (this) {
            case CHICKEN:
                break;
            case SHEEP:
                MobDisguise disguise = new MobDisguise(DisguiseType.SHEEP, false);
                disguise = disguise.setReplaceSounds(true);
                DisguiseAPI.disguiseToAll(wolf, disguise);
                break;
            case COW:
                break;
            case PIG:
                break;
            case CAT_ALL_BLACK:
                break;
            case CAT_BLACK:
                break;
            case CAT_BRITISH_SHORTHAIR:
                break;
            case CAT_CALICO:
                break;
            case CAT_JELLIE:
                break;
            case CAT_PERSIAN:
                break;
            case CAT_RAGDOLL:
                break;
            case CAT_RED:
                break;
            case CAT_SIAMESE:
                break;
            case CAT_TABBY:
                break;
            case CAT_WHITE:
                break;
            case RABBIT_BLACK:
                break;
            case RABBIT_BLACK_AND_WHITE:
                break;
            case RABBIT_BROWN:
                break;
            case RABBIT_GOLD:
                break;
            case RABBIT_SALT_AND_PEPPER:
                break;
            case RABBIT_WHITE:
                break;
            case CHOCOLATE:
                break;
            case WOLF:
                break;
            case TURTLE:
                break;
            case POLAR_BEAR:
                break;
            case PANDA:
                break;
            case ICE_CREAM:
                break;
            case FOX_RED:
                break;
            case FOX_SNOW:
                break;
            case VEX:
                break;
            case MINI_DRAGON:
                break;
        }
    }
}
