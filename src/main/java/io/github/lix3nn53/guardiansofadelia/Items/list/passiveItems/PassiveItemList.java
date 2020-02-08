package io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.GearPassive;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class PassiveItemList {

    private final static HashMap<RPGSlotType, List<PassiveItemTemplate>> rpgClassToPassiveTemplates = new HashMap<>();

    public static ItemStack get(int placementNumber, RPGSlotType rpgSlotType, ItemTier tier, String itemTag, int minStatValue,
                                int maxStatValue, int minNumberOfStats) {
        RPGClass rpgClass = RPGClass.NO_CLASS;

        PassiveItemTemplate template = rpgClassToPassiveTemplates.get(rpgSlotType).get(placementNumber - 1);

        String name = template.getName();
        int level = template.getLevel();
        int customModelData = template.getCustomModelData();

        final GearPassive passive = new GearPassive(name, tier, itemTag, customModelData, rpgSlotType, level, rpgClass, minStatValue,
                maxStatValue, minNumberOfStats, tier.getBonusMultiplier());

        return passive.getItemStack();
    }

    public static void put(RPGSlotType rpgSlotType, List<PassiveItemTemplate> passiveItemTemplates) {
        rpgClassToPassiveTemplates.put(rpgSlotType, passiveItemTemplates);
    }
}
