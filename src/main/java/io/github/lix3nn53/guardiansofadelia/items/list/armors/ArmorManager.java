package io.github.lix3nn53.guardiansofadelia.items.list.armors;

import io.github.lix3nn53.guardiansofadelia.items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.GearArmor;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ItemTier;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArmorManager {

    private final static HashMap<GearLevel, List<ArmorSet>> gearLevelToArmorSets = new HashMap<>();

    public static List<ItemStack> get(ArmorSlot armorSlot, ArmorGearType gearType, GearLevel gearLevel, ItemTier tier, boolean noStats, String gearSet) {
        List<ArmorSet> sets = gearLevelToArmorSets.get(gearLevel);

        int minNumberOfStats = noStats ? 0 : tier.getMinNumberOfElements(false);
        int minStatValue = noStats ? 0 : gearLevel.getMinStatValue(false, true);
        int maxStatValue = noStats ? 0 : gearLevel.getMaxStatValue(false, true);

        ArrayList<ItemStack> itemStacks = new ArrayList<>();

        for (ArmorSet armorSet : sets) {
            String name = armorSet.getName(armorSlot);
            Material material = armorSet.getMaterial(armorSlot, gearType);
            int level = armorSet.getReqLevel(armorSlot);
            int health = armorSet.getHealth(armorSlot, gearType);
            int defense = armorSet.getDefense(armorSlot, gearType);

            final GearArmor gearArmor = new GearArmor(name, tier, material, level,
                    gearType, health,
                    defense, minStatValue, maxStatValue, minNumberOfStats, gearSet);

            itemStacks.add(gearArmor.getItemStack());
        }

        return itemStacks;
    }

    public static void add(ArmorSet armorSet) {
        GearLevel gearLevel = GearLevel.getGearLevel(armorSet.getBaseRequiredLevel());

        List<ArmorSet> list = new ArrayList<>();
        if (gearLevelToArmorSets.containsKey(gearLevel)) {
            list = gearLevelToArmorSets.get(gearLevel);
        }

        list.add(armorSet);
        gearLevelToArmorSets.put(gearLevel, list);
    }
}
