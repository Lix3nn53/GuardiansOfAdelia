package io.github.lix3nn53.guardiansofadelia.guardian.character;

import net.md_5.bungee.api.ChatColor;

public enum RPGClass {
    ARCHER,
    KNIGHT,
    MAGE,
    MONK,
    ROGUE,
    PALADIN,
    WARRIOR,
    HUNTER,
    NO_CLASS;

    public String getClassString() {
        ChatColor color = ChatColor.GREEN;
        String name = "Archer";
        if (this == RPGClass.KNIGHT) {
            color = ChatColor.AQUA;
            name = "Knight";
        } else if (this == RPGClass.MAGE) {
            color = ChatColor.LIGHT_PURPLE;
            name = "Mage";
        } else if (this == RPGClass.MONK) {
            color = ChatColor.GOLD;
            name = "Monk";
        } else if (this == RPGClass.ROGUE) {
            color = ChatColor.BLUE;
            name = "Rogue";
        } else if (this == RPGClass.PALADIN) {
            color = ChatColor.YELLOW;
            name = "Paladin";
        } else if (this == RPGClass.WARRIOR) {
            color = ChatColor.RED;
            name = "Warrior";
        } else if (this == RPGClass.HUNTER) {
            color = ChatColor.DARK_GREEN;
            name = "Hunter";
        }
        return color + name;
    }

    public ChatColor getClassColor() {
        ChatColor color = ChatColor.GREEN;
        if (this == RPGClass.KNIGHT) {
            color = ChatColor.AQUA;
        } else if (this == RPGClass.MAGE) {
            color = ChatColor.LIGHT_PURPLE;
        } else if (this == RPGClass.MONK) {
            color = ChatColor.GOLD;
        } else if (this == RPGClass.ROGUE) {
            color = ChatColor.BLUE;
        } else if (this == RPGClass.PALADIN) {
            color = ChatColor.YELLOW;
        } else if (this == RPGClass.WARRIOR) {
            color = ChatColor.RED;
        } else if (this == RPGClass.HUNTER) {
            color = ChatColor.DARK_GREEN;
        }
        return color;
    }

    public String getClassStringNoColor() {
        String name = "Archer";
        if (this == RPGClass.KNIGHT) {
            name = "Knight";
        } else if (this == RPGClass.MAGE) {
            name = "Mage";
        } else if (this == RPGClass.MONK) {
            name = "Monk";
        } else if (this == RPGClass.ROGUE) {
            name = "Rogue";
        } else if (this == RPGClass.PALADIN) {
            name = "Paladin";
        } else if (this == RPGClass.WARRIOR) {
            name = "Warrior";
        } else if (this == RPGClass.HUNTER) {
            name = "Hunter";
        }
        return name;
    }
}
