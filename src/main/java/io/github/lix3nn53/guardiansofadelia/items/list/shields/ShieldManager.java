package io.github.lix3nn53.guardiansofadelia.items.list.shields;

import io.github.lix3nn53.guardiansofadelia.items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.GearShield;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ShieldGearType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShieldManager {

    private final static HashMap<GearLevel, List<ShieldSet>> gearLevelToShields = new HashMap<>();

    public static ItemStack get(ShieldGearType gearType, GearLevel gearLevel, ItemTier tier, boolean noStats,
                                boolean gearSet, int setIndex) {
        List<ShieldSet> sets = gearLevelToShields.get(gearLevel);
        ShieldSet template = sets.get(setIndex);

        int minNumberOfStats = noStats ? 0 : tier.getMinNumberOfElements(false);
        int minStatValue = noStats ? 0 : gearLevel.getMinStatValue(false, true);
        int maxStatValue = noStats ? 0 : gearLevel.getMaxStatValue(false, true);

        Material material = gearType.getMaterial();

        String name = template.getName(gearType);
        int customModelData = template.getCustomModelData();
        int defense = template.getDefense(gearType);
        int health = template.getHealth(gearType);
        int level = template.getRequiredLevel();

        final GearShield shield = new GearShield(name, tier, material, customModelData, level,
                gearType, health,
                defense, minStatValue, maxStatValue, minNumberOfStats, gearSet);

        return shield.getItemStack();
    }

    public static List<ItemStack> getAll(ShieldGearType gearType, GearLevel gearLevel, ItemTier tier, boolean noStats,
                                         boolean gearSet) {
        int count = countAt(gearLevel);

        List<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ItemStack itemStack = get(gearType, gearLevel, tier, noStats, gearSet, i);

            list.add(itemStack);
        }

        return list;
    }

    public static int countAt(GearLevel gearLevel) {
        return gearLevelToShields.get(gearLevel).size();
    }

    public static void add(ShieldSet shieldSet) {
        GearLevel gearLevel = GearLevel.getGearLevel(shieldSet.getRequiredLevel());

        List<ShieldSet> list = new ArrayList<>();
        if (gearLevelToShields.containsKey(gearLevel)) {
            list = gearLevelToShields.get(gearLevel);
        }

        list.add(shieldSet);
        gearLevelToShields.put(gearLevel, list);
    }
}
