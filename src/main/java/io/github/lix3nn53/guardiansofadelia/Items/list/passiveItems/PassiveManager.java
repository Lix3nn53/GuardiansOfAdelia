package io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.GearPassive;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PassiveManager {

    private final static HashMap<Integer, List<PassiveSet>> gearLevelToPassives = new HashMap<>();

    public static ItemStack get(int gearLevel, int setIndex, RPGSlotType rpgSlotType, ItemTier tier, boolean noStats, String gearSetStr) {
        GuardiansOfAdelia.getInstance().getLogger().info(gearLevel + " " + setIndex + " " + rpgSlotType.toString() + " " + tier.toString() + " " + gearSetStr);
        int minNumberOfStats = noStats ? 0 : tier.getMinNumberOfStatsPassive();
        int minStatValue = noStats ? 0 : GearLevel.getMinStatValue(gearLevel);
        int maxStatValue = noStats ? 0 : GearLevel.getMaxStatValue(gearLevel);

        List<PassiveSet> sets = gearLevelToPassives.get(gearLevel);

        PassiveSet template = sets.get(setIndex);

        String name = template.getName(rpgSlotType);
        int level = template.getLevel(rpgSlotType);
        int customModelData = template.getCustomModelData(rpgSlotType);

        final GearPassive passive = new GearPassive(name, tier, customModelData, rpgSlotType, level, minStatValue,
                maxStatValue, minNumberOfStats, gearSetStr);

        return passive.getItemStack();
    }

    public static void add(PassiveSet passiveSet) {
        int gearLevel = GearLevel.getGearLevel(passiveSet.getBaseLevel());

        List<PassiveSet> list = new ArrayList<>();
        if (gearLevelToPassives.containsKey(gearLevel)) {
            list = gearLevelToPassives.get(gearLevel);
        }

        list.add(passiveSet);
        gearLevelToPassives.put(gearLevel, list);
    }
}
