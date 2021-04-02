package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public enum ItemTier {
    COMMON,
    RARE,
    MYSTIC,
    LEGENDARY;

    public String getTierString() {
        String name = "Common";
        if (this == ItemTier.RARE) {
            name = "Rare";
        } else if (this == ItemTier.MYSTIC) {
            name = "Mystic";
        } else if (this == ItemTier.LEGENDARY) {
            name = "Legendary";
        }
        return getTierColor() + name;
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
            percent = 1.2;
        } else if (this == ItemTier.MYSTIC) {
            percent = 1.4;
        } else if (this == ItemTier.LEGENDARY) {
            percent = 1.8;
        }
        return percent;
    }

    public int getMinNumberOfElements(boolean isPassive) {
        int number = 0;
        if (this == ItemTier.RARE) {
            number = 1;
        } else if (this == ItemTier.MYSTIC) {
            number = 2;
        } else if (this == ItemTier.LEGENDARY) {
            number = 3;
        }

        if (isPassive) {
            number++;
        }

        return number;
    }

    public int getMinNumberOfAttributes(boolean isPassive) {
        if (!isPassive) { // Only passive items give attribute bonus
            return 0;
        }

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

    public static ItemTier getItemTierOfItemStack(ItemStack itemStack) {
        if (PersistentDataContainerUtil.hasString(itemStack, "itemTier")) {
            return ItemTier.valueOf(PersistentDataContainerUtil.getString(itemStack, "itemTier"));
        }

        return COMMON;
    }
}
