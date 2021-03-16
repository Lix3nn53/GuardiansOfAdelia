package io.github.lix3nn53.guardiansofadelia.Items;

import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.inventory.ItemStack;

/**
 * Every 10 level is a GearLevel
 * level 1-9 is gearLevel 1
 * level 10-19 is gearLevel 2
 * level 20-29 is gearLevel 3 and so on
 */
public class GearLevel {

    private static final double MIN_STAT_MULTIPLIER = 0.2;
    private static final double MAX_STAT_MULTIPLIER = 0.8;

    private static final double NORMAL_ITEM_MULTIPLIER = 0.5;

    public static int getGearLevel(ItemStack itemStack) {
        if (PersistentDataContainerUtil.hasInteger(itemStack, "reqLevel")) {
            int reqLevel = PersistentDataContainerUtil.getInteger(itemStack, "reqLevel");

            return (reqLevel / 10) + 1;
        }

        return 1;
    }

    public static int getGearLevel(int reqLevel) {
        return (reqLevel / 10) + 1;
    }

    public static int getMinLevel(int gearLevel) {
        return gearLevel * 10;
    }

    public static int getMaxLevel(int gearLevel) {
        return (gearLevel * 10) + 9;
    }

    public static int getMinStatValue(int gearLevel, boolean isPassive) {
        double v = gearLevel * MIN_STAT_MULTIPLIER;

        if (!isPassive) {
            v = v * NORMAL_ITEM_MULTIPLIER;
        }

        return (int) (v + 0.5);
    }

    public static int getMaxStatValue(int gearLevel, boolean isPassive) {
        double v = gearLevel * MAX_STAT_MULTIPLIER;

        if (!isPassive) {
            v = v * NORMAL_ITEM_MULTIPLIER;
        }

        return (int) (v + 0.5);
    }
}
