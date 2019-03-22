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

    public double getBonusPercent() {
        double percent = 1;
        if (this == ItemTier.RARE) {
            percent = 0.15;
        } else if (this == ItemTier.MYSTIC) {
            percent = 0.3;
        } else if (this == ItemTier.LEGENDARY) {
            percent = 0.5;
        }
        return percent;
    }

    public double getChanceToGetEachStat() {
        double percent = 0.25;
        if (this == ItemTier.RARE) {
            percent = 0.3;
        } else if (this == ItemTier.MYSTIC) {
            percent = 0.5;
        } else if (this == ItemTier.LEGENDARY) {
            percent = 0.7;
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
