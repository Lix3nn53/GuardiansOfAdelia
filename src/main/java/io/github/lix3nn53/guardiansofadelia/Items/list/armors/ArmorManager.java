package io.github.lix3nn53.guardiansofadelia.Items.list.armors;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.GearArmor;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArmorManager {

    private final static HashMap<Integer, List<ArmorSet>> gearLevelToArmorSets = new HashMap<>();

    public static ItemStack get(ArmorType armorType, ArmorGearType gearType, int gearLevel, int itemIndex, ItemTier tier, String itemTag, int minStatValue,
                                int maxStatValue, int minNumberOfStats) {
        List<ArmorSet> templates = gearLevelToArmorSets.get(gearLevel);

        ArmorSet armorSet = templates.get(itemIndex);

        String name = armorSet.getName(armorType);
        Material material = armorSet.getMaterial(armorType, gearType);
        int level = armorSet.getReqLevel(armorType);
        int health = armorSet.getHealth(armorType, gearType);
        int defense = armorSet.getDefense(armorType, gearType);
        int magicDefense = armorSet.getMagicDefense(armorType, gearType);

        final GearArmor gearArmor = new GearArmor(name, tier, itemTag, material, level,
                gearType, health,
                defense, magicDefense, minStatValue, maxStatValue, minNumberOfStats);

        return gearArmor.getItemStack();
    }

    public static void add(ArmorSet armorSet) {
        int gearLevel = GearLevel.getGearLevel(armorSet.getBaseReqLevel());

        List<ArmorSet> list = new ArrayList<>();
        if (gearLevelToArmorSets.containsKey(gearLevel)) {
            list = gearLevelToArmorSets.get(gearLevel);
        }

        list.add(armorSet);
        gearLevelToArmorSets.put(gearLevel, list);
    }
}
