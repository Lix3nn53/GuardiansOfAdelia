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

    public static ItemStack get(ArmorSlot armorSlot, ArmorGearType gearType, GearLevel gearLevel, ItemTier tier,
                                boolean noStats, boolean gearSet, int setIndex) {
        List<ArmorSet> sets = gearLevelToArmorSets.get(gearLevel);
        ArmorSet template = sets.get(setIndex);

        int minNumberOfStats = noStats ? 0 : tier.getMinNumberOfElements(false);
        int minStatValue = noStats ? 0 : gearLevel.getMinStatValue(false, true);
        int maxStatValue = noStats ? 0 : gearLevel.getMaxStatValue(false, true);

        ArrayList<ItemStack> itemStacks = new ArrayList<>();

        String name = template.getName(armorSlot);
        Material material = template.getMaterial(armorSlot, gearType);
        int level = template.getReqLevel(armorSlot);
        int health = template.getHealth(armorSlot, gearType);
        int defense = template.getDefense(armorSlot, gearType);

        final GearArmor gearArmor = new GearArmor(name, tier, material, level,
                gearType, health,
                defense, minStatValue, maxStatValue, minNumberOfStats, gearSet);

        return gearArmor.getItemStack();
    }

    public static List<ItemStack> getAll(ArmorSlot armorSlot, ArmorGearType gearType, GearLevel gearLevel, ItemTier tier,
                                         boolean noStats, boolean gearSet) {
        int count = countAt(gearLevel);

        List<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ItemStack itemStack = get(armorSlot, gearType, gearLevel, tier, noStats, gearSet, i);

            list.add(itemStack);
        }

        return list;
    }

    public static int countAt(GearLevel gearLevel) {
        return gearLevelToArmorSets.get(gearLevel).size();
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
