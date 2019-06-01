package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

import net.md_5.bungee.api.ChatColor;

public enum ItemTier {
    COMMON,
    RARE,
    MYSTIC,
    LEGENDARY;

    public String getTierString() {
        ChatColor color = ChatColor.GREEN;
        String name = "Common";
        if (this == ItemTier.RARE) {
            color = ChatColor.BLUE;
            name = "Rare";
        } else if (this == ItemTier.MYSTIC) {
            color = ChatColor.DARK_PURPLE;
            name = "Mystic";
        } else if (this == ItemTier.LEGENDARY) {
            color = ChatColor.GOLD;
            name = "Legendary";
        }
        return color + name;
    }

    public ChatColor getTierColor() {
        ChatColor color = ChatColor.GREEN;
        if (this == ItemTier.RARE) {
            color = ChatColor.BLUE;
        } else if (this == ItemTier.MYSTIC) {
            color = ChatColor.DARK_PURPLE;
        } else if (this == ItemTier.LEGENDARY) {
            color = ChatColor.GOLD;
        }
        return color;
    }

    public double getBonusMultiplier() {
        double percent = 1;
        if (this == ItemTier.RARE) {
            percent = 1.15;
        } else if (this == ItemTier.MYSTIC) {
            percent = 1.3;
        } else if (this == ItemTier.LEGENDARY) {
            percent = 1.5;
        }
        return percent;
    }

    public int getMinNumberOfStats() {
        int number = 0;
        if (this == ItemTier.RARE) {
            number = 1;
        } else if (this == ItemTier.MYSTIC) {
            number = 2;
        } else if (this == ItemTier.LEGENDARY) {
            number = 3;
        }
        return number;
    }
}
