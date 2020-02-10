package io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.GearPassive;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PassiveItemList {

    private final static HashMap<String, List<PassiveItemTemplate>> rpgClassToPassiveTemplates = new HashMap<>();

    public static ItemStack get(int gearLevel, int itemIndex, RPGSlotType rpgSlotType, ItemTier tier, String itemTag, int minStatValue,
                                int maxStatValue, int minNumberOfStats) {
        RPGClass rpgClass = RPGClass.NO_CLASS;

        List<PassiveItemTemplate> templates = rpgClassToPassiveTemplates.get(rpgSlotType.name() + gearLevel);
        PassiveItemTemplate template = templates.get(itemIndex);

        String name = template.getName();
        int level = template.getLevel();
        int customModelData = template.getCustomModelData();

        final GearPassive passive = new GearPassive(name, tier, itemTag, customModelData, rpgSlotType, level, rpgClass, minStatValue,
                maxStatValue, minNumberOfStats, tier.getBonusMultiplier());

        return passive.getItemStack();
    }

    public static void put(RPGSlotType rpgSlotType, PassiveItemTemplate passiveItemTemplate) {
        int gearLevel = GearLevel.getGearLevel(passiveItemTemplate.getLevel());
        String key = rpgSlotType.name() + gearLevel;

        List<PassiveItemTemplate> list = new ArrayList<>();
        if (rpgClassToPassiveTemplates.containsKey(key)) {
            list = rpgClassToPassiveTemplates.get(key);
        }
        list.add(passiveItemTemplate);
        rpgClassToPassiveTemplates.put(key, list);
    }
}
