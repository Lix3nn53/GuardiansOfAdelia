package io.github.lix3nn53.guardiansofadelia.guardian.character;

import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import org.bukkit.ChatColor;

public enum RPGClass {
    NO_CLASS,
    ARCHER,
    KNIGHT,
    MAGE,
    MONK,
    ROGUE,
    PALADIN,
    WARRIOR,
    HUNTER;

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

    public int getClassIconCustomModelData() {
        if (this == RPGClass.KNIGHT) {
            return 9;
        } else if (this == RPGClass.MAGE) {
            return 10;
        } else if (this == RPGClass.MONK) {
            return 15;
        } else if (this == RPGClass.ROGUE) {
            return 11;
        } else if (this == RPGClass.PALADIN) {
            return 8;
        } else if (this == RPGClass.WARRIOR) {
            return 12;
        } else if (this == RPGClass.HUNTER) {
            return 16;
        }
        return 7;
    }

    public int getAttributeBonusForLevel(AttributeType attributeType, int level) {
        int tier = 1;
        if (this == RPGClass.ARCHER) {
            if (attributeType.equals(AttributeType.FIRE)) {
                tier = 6;
            } else if (attributeType.equals(AttributeType.WATER)) {
                tier = 3;
            } else if (attributeType.equals(AttributeType.EARTH)) {
                tier = 6;
            } else if (attributeType.equals(AttributeType.LIGHTNING)) {
                tier = 3;
            } else if (attributeType.equals(AttributeType.WIND)) {
                tier = 1;
            }
        } else if (this == RPGClass.KNIGHT) {
            if (attributeType.equals(AttributeType.FIRE)) {
                tier = 5;
            } else if (attributeType.equals(AttributeType.WATER)) {
                tier = 3;
            } else if (attributeType.equals(AttributeType.EARTH)) {
                tier = 10;
            } else if (attributeType.equals(AttributeType.LIGHTNING)) {
                tier = 1;
            } else if (attributeType.equals(AttributeType.WIND)) {
                tier = 1;
            }
        } else if (this == RPGClass.MAGE) {
            if (attributeType.equals(AttributeType.FIRE)) {
                tier = 1;
            } else if (attributeType.equals(AttributeType.WATER)) {
                tier = 4;
            } else if (attributeType.equals(AttributeType.EARTH)) {
                tier = 6;
            } else if (attributeType.equals(AttributeType.LIGHTNING)) {
                tier = 8;
            } else if (attributeType.equals(AttributeType.WIND)) {
                tier = 1;
            }
        } else if (this == RPGClass.MONK) {
            if (attributeType.equals(AttributeType.FIRE)) {
                tier = 6;
            } else if (attributeType.equals(AttributeType.WATER)) {
                tier = 4;
            } else if (attributeType.equals(AttributeType.EARTH)) {
                tier = 7;
            } else if (attributeType.equals(AttributeType.LIGHTNING)) {
                tier = 2;
            } else if (attributeType.equals(AttributeType.WIND)) {
                tier = 1;
            }
        } else if (this == RPGClass.ROGUE) {
            if (attributeType.equals(AttributeType.FIRE)) {
                tier = 8;
            } else if (attributeType.equals(AttributeType.WATER)) {
                tier = 3;
            } else if (attributeType.equals(AttributeType.EARTH)) {
                tier = 7;
            } else if (attributeType.equals(AttributeType.LIGHTNING)) {
                tier = 1;
            } else if (attributeType.equals(AttributeType.WIND)) {
                tier = 2;
            }
        } else if (this == RPGClass.PALADIN) {
            if (attributeType.equals(AttributeType.FIRE)) {
                tier = 4;
            } else if (attributeType.equals(AttributeType.WATER)) {
                tier = 4;
            } else if (attributeType.equals(AttributeType.EARTH)) {
                tier = 8;
            } else if (attributeType.equals(AttributeType.LIGHTNING)) {
                tier = 3;
            } else if (attributeType.equals(AttributeType.WIND)) {
                tier = 1;
            }
        } else if (this == RPGClass.WARRIOR) {
            if (attributeType.equals(AttributeType.FIRE)) {
                tier = 9;
            } else if (attributeType.equals(AttributeType.WATER)) {
                tier = 1;
            } else if (attributeType.equals(AttributeType.EARTH)) {
                tier = 8;
            } else if (attributeType.equals(AttributeType.LIGHTNING)) {
                tier = 1;
            } else if (attributeType.equals(AttributeType.WIND)) {
                tier = 1;
            }
        } else if (this == RPGClass.HUNTER) {
            if (attributeType.equals(AttributeType.FIRE)) {
                tier = 9;
            } else if (attributeType.equals(AttributeType.WATER)) {
                tier = 1;
            } else if (attributeType.equals(AttributeType.EARTH)) {
                tier = 7;
            } else if (attributeType.equals(AttributeType.LIGHTNING)) {
                tier = 1;
            } else if (attributeType.equals(AttributeType.WIND)) {
                tier = 2;
            }
        }

        double multiplier = 1.4 + (tier * 0.025);

        return (level * (tier - 1) / 2) + (int) ((Math.pow(level, multiplier) / 9) + 0.5);
    }
}
