package io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.GearPassive;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PassiveManager {

    private final static HashMap<GearLevel, List<PassiveSet>> gearLevelToPassives = new HashMap<>();

    public static List<ItemStack> get(GearLevel gearLevel, RPGSlotType rpgSlotType, ItemTier tier, boolean noStats, String gearSetStr) {
        List<PassiveSet> sets = gearLevelToPassives.get(gearLevel);

        int minNumberOfAttr = noStats ? 0 : tier.getMinNumberOfAttributes(true);
        int minNumberOfElements = noStats ? 0 : tier.getMinNumberOfElements(true);
        int minAttrValue = noStats ? 0 : gearLevel.getMinStatValue(true, false);
        int maxAttrValue = noStats ? 0 : gearLevel.getMaxStatValue(true, false);
        int minElemValue = noStats ? 0 : gearLevel.getMinStatValue(true, true);
        int maxElemValue = noStats ? 0 : gearLevel.getMaxStatValue(true, true);

        ArrayList<ItemStack> itemStacks = new ArrayList<>();

        for (PassiveSet template : sets) {
            String name = template.getName(rpgSlotType);
            int level = template.getLevel(rpgSlotType);
            int customModelData = template.getCustomModelData(rpgSlotType);

            final GearPassive passive = new GearPassive(name, tier, customModelData, rpgSlotType, level, minAttrValue,
                    maxAttrValue, minNumberOfAttr, minElemValue, maxElemValue, minNumberOfElements, gearSetStr);

            itemStacks.add(passive.getItemStack());
        }

        return itemStacks;
    }

    public static void add(PassiveSet passiveSet) {
        GearLevel gearLevel = GearLevel.getGearLevel(passiveSet.getBaseLevel());

        List<PassiveSet> list = new ArrayList<>();
        if (gearLevelToPassives.containsKey(gearLevel)) {
            list = gearLevelToPassives.get(gearLevel);
        }

        list.add(passiveSet);
        gearLevelToPassives.put(gearLevel, list);
    }
}
