package io.github.lix3nn53.guardiansofadelia.Items;

import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.inventory.ItemStack;

public enum GearLevel {
    ZERO,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE;

    public static GearLevel getGearLevelOfItem(ItemStack itemStack) {
        if (PersistentDataContainerUtil.hasInteger(itemStack, "reqLevel")) {
            int reqLevel = PersistentDataContainerUtil.getInteger(itemStack, "reqLevel");
            if (reqLevel < 10) {
                return ZERO;
            } else if (reqLevel < 20) {
                return ONE;
            } else if (reqLevel < 30) {
                return TWO;
            } else if (reqLevel < 40) {
                return THREE;
            } else if (reqLevel < 50) {
                return FOUR;
            } else if (reqLevel < 60) {
                return FIVE;
            } else if (reqLevel < 70) {
                return SIX;
            } else if (reqLevel < 80) {
                return SEVEN;
            } else if (reqLevel < 90) {
                return EIGHT;
            } else if (reqLevel < 100) {
                return NINE;
            }
        }
        return ZERO;
    }

    public int getMinLevel() {
        switch (this) {
            case ONE:
                return 10;
            case TWO:
                return 20;
            case THREE:
                return 30;
            case FOUR:
                return 40;
            case FIVE:
                return 50;
            case SIX:
                return 60;
            case SEVEN:
                return 70;
            case EIGHT:
                return 80;
            case NINE:
                return 90;
        }
        return 1;
    }

    public int getMaxLevel() {
        switch (this) {
            case ONE:
                return 19;
            case TWO:
                return 29;
            case THREE:
                return 39;
            case FOUR:
                return 49;
            case FIVE:
                return 59;
            case SIX:
                return 69;
            case SEVEN:
                return 79;
            case EIGHT:
                return 89;
            case NINE:
                return 99;
        }
        return 9;
    }

    public int getMinStatValue() {
        switch (this) {
            case ONE:
                return 5;
            case TWO:
                return 10;
            case THREE:
                return 15;
            case FOUR:
                return 20;
            case FIVE:
                return 25;
            case SIX:
                return 30;
            case SEVEN:
                return 35;
            case EIGHT:
                return 40;
            case NINE:
                return 50;
        }
        return 1;
    }

    public int getMaxStatValue() {
        switch (this) {
            case ONE:
                return 10;
            case TWO:
                return 15;
            case THREE:
                return 20;
            case FOUR:
                return 25;
            case FIVE:
                return 30;
            case SIX:
                return 35;
            case SEVEN:
                return 40;
            case EIGHT:
                return 45;
            case NINE:
                return 50;
        }
        return 5;
    }

    public int getWeaponAndPassiveNo() {
        switch (this) {
            case ONE:
                return 2;
            case TWO:
                return 3;
            case THREE:
                return 4;
            case FOUR:
                return 5;
            case FIVE:
                return 6;
            case SIX:
                return 7;
            case SEVEN:
                return 8;
            case EIGHT:
                return 9;
            case NINE:
                return 10;
        }
        return 1;
    }

    public int getArmorNo() {
        switch (this) {
            case ONE:
                return 2;
            case TWO:
                return 1;
            case THREE:
                return 2;
            case FOUR:
                return 3;
            case FIVE:
                return 4;
            case SIX:
                return 5;
            case SEVEN:
                return 6;
            case EIGHT:
                return 7;
            case NINE:
                return 8;
        }
        return 1;
    }

    public int getConsumableNo() {
        switch (this) {
            case ONE:
                return 2;
            case TWO:
                return 3;
            case THREE:
                return 4;
            case FOUR:
                return 5;
            case FIVE:
                return 6;
            case SIX:
                return 7;
            case SEVEN:
                return 8;
            case EIGHT:
                return 9;
            case NINE:
                return 10;
        }
        return 1;
    }
}
