package io.github.lix3nn53.guardiansofadelia.items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
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

    public ChatPalette getTierColor() {
        ChatPalette color = ChatPalette.GREEN_DARK;
        if (this == ItemTier.RARE) {
            color = ChatPalette.BLUE;
        } else if (this == ItemTier.MYSTIC) {
            color = ChatPalette.PURPLE;
        } else if (this == ItemTier.LEGENDARY) {
            color = ChatPalette.GOLD;
        }
        return color;
    }

    public float getBonusMultiplier() {
        float percent = 1;
        if (this == ItemTier.RARE) {
            percent = 1.2f;
        } else if (this == ItemTier.MYSTIC) {
            percent = 1.4f;
        } else if (this == ItemTier.LEGENDARY) {
            percent = 1.8f;
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
