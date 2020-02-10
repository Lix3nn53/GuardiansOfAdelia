package io.github.lix3nn53.guardiansofadelia.Items;

import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.inventory.ItemStack;

public class GearLevel {

    public static int getGearLevel(ItemStack itemStack) {
        if (PersistentDataContainerUtil.hasInteger(itemStack, "reqLevel")) {
            int reqLevel = PersistentDataContainerUtil.getInteger(itemStack, "reqLevel");

            return reqLevel / 10;
        }
        return 0;
    }

    public static int getGearLevel(int reqLevel) {
        return reqLevel / 10;
    }

    public static int getMinLevel(int gearLevel) {
        return gearLevel * 10;
    }

    public static int getMaxLevel(int gearLevel) {
        return (gearLevel * 10) + 9;
    }

    public static int getMinStatValue(int gearLevel) {
        return (int) (gearLevel * 1.2);
    }

    public static int getMaxStatValue(int gearLevel) {
        return (int) (gearLevel * 2.4);
    }
}
