package io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.GearPassive;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.RPGGearType;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PassiveManager {

    private final static HashMap<String, List<PassiveSet>> gearTypeAndLevelToPassives = new HashMap<>();

    public static ItemStack get(RPGGearType gearType, int gearLevel, int itemIndex, RPGSlotType rpgSlotType, ItemTier tier, String itemTag, int minStatValue,
                                int maxStatValue, int minNumberOfStats) {
        String key = gearType.toString() + gearLevel;

        List<PassiveSet> templates = gearTypeAndLevelToPassives.get(key);

        PassiveSet template = templates.get(itemIndex);

        String name = template.getName(rpgSlotType);
        int level = template.getLevel(rpgSlotType);
        int customModelData = template.getCustomModelData(rpgSlotType);

        final GearPassive passive = new GearPassive(name, tier, itemTag, customModelData, rpgSlotType, level, minStatValue,
                maxStatValue, minNumberOfStats, tier.getBonusMultiplier());

        return passive.getItemStack();
    }

    public static void add(PassiveSet passiveSet) {
        RPGGearType gearType = passiveSet.getGearType();
        int gearLevel = GearLevel.getGearLevel(passiveSet.getBaseLevel());

        String key = gearType.name() + gearLevel;

        List<PassiveSet> list = new ArrayList<>();
        if (gearTypeAndLevelToPassives.containsKey(key)) {
            list = gearTypeAndLevelToPassives.get(key);
        }

        list.add(passiveSet);
        gearTypeAndLevelToPassives.put(key, list);
    }
}
