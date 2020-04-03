package io.github.lix3nn53.guardiansofadelia.Items.list.armors;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.GearArmor;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.RPGGearType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArmorManager {

    private final static HashMap<String, List<ArmorSet>> gearTypeAndLevelToArmorSets = new HashMap<>();

    public static ItemStack get(ArmorType armorType, RPGGearType gearType, int gearLevel, int itemIndex, ItemTier tier, String itemTag, int minStatValue,
                                int maxStatValue, int minNumberOfStats) {

        String key = gearType.toString() + gearLevel;
        List<ArmorSet> templates = gearTypeAndLevelToArmorSets.get(key);

        ArmorSet armorSet = templates.get(itemIndex);

        String name = armorSet.getName(armorType);
        Material material = armorSet.getMaterial(armorType);
        int level = armorSet.getReqLevel(armorType);
        int health = armorSet.getHealth(armorType);
        int defense = armorSet.getDefense(armorType);
        int magicDefense = armorSet.getMagicDefense(armorType);

        final GearArmor gearArmor = new GearArmor(name, tier, itemTag, material, level,
                gearType, health,
                defense, magicDefense, minStatValue, maxStatValue, minNumberOfStats);

        return gearArmor.getItemStack();
    }

    public static void add(ArmorSet armorSet) {
        RPGGearType gearType = armorSet.getGearType();
        int gearLevel = GearLevel.getGearLevel(armorSet.getBaseReqLevel());

        String key = gearType.toString() + gearLevel;

        List<ArmorSet> list = new ArrayList<>();
        if (gearTypeAndLevelToArmorSets.containsKey(key)) {
            list = gearTypeAndLevelToArmorSets.get(key);
        }

        list.add(armorSet);
        gearTypeAndLevelToArmorSets.put(key, list);
    }
}
