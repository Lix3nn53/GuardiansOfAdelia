package io.github.lix3nn53.guardiansofadelia.Items.list.armors;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
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

    public static ItemStack get(ArmorSlot armorSlot, ArmorGearType gearType, int gearLevel, int setIndex, ItemTier tier, String itemTag, boolean noStats, String gearSet) {
        GuardiansOfAdelia.getInstance().getLogger().info(gearLevel + " " + setIndex + " " + armorSlot.toString() + " " + gearType.toString() + " " + tier.toString() + " " + itemTag);
        int minNumberOfStats = noStats ? 0 : tier.getMinNumberOfStatsNormal();
        int minStatValue = noStats ? 0 : GearLevel.getMinStatValue(gearLevel);
        int maxStatValue = noStats ? 0 : GearLevel.getMaxStatValue(gearLevel);

        List<ArmorSet> sets = gearLevelToArmorSets.get(gearLevel);

        ArmorSet armorSet = sets.get(setIndex);

        String name = armorSet.getName(armorSlot);
        Material material = armorSet.getMaterial(armorSlot, gearType);
        int level = armorSet.getReqLevel(armorSlot);
        int health = armorSet.getHealth(armorSlot, gearType);
        int defense = armorSet.getDefense(armorSlot, gearType);
        int magicDefense = armorSet.getMagicDefense(armorSlot, gearType);

        final GearArmor gearArmor = new GearArmor(name, tier, itemTag, material, level,
                gearType, health,
                defense, magicDefense, minStatValue, maxStatValue, minNumberOfStats, gearSet);

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
