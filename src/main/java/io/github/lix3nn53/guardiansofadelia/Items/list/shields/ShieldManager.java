package io.github.lix3nn53.guardiansofadelia.Items.list.shields;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.Shield;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ShieldGearType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShieldManager {

    private final static HashMap<Integer, List<ShieldSet>> gearLevelToShields = new HashMap<>();

    public static ItemStack get(ShieldGearType gearType, int gearLevel, int setIndex, ItemTier tier, boolean noStats, String gearSet) {
        int minNumberOfStats = noStats ? 0 : tier.getMinNumberOfStatsNormal();
        int minStatValue = noStats ? 0 : GearLevel.getMinStatValue(gearLevel, false);
        int maxStatValue = noStats ? 0 : GearLevel.getMaxStatValue(gearLevel, false);

        Material material = gearType.getMaterial();

        List<ShieldSet> sets = gearLevelToShields.get(gearLevel);
        ShieldSet template = sets.get(setIndex);

        String name = template.getName(gearType);
        int customModelData = template.getCustomModelData();
        int defense = template.getDefense(gearType);
        int health = template.getHealth(gearType);
        int level = template.getReqLevel();

        final Shield shield = new Shield(name, tier, material, customModelData, level,
                gearType, health,
                defense, minStatValue, maxStatValue, minNumberOfStats, gearSet);
        return shield.getItemStack();
    }

    public static void add(ShieldSet shieldSet) {
        int gearLevel = GearLevel.getGearLevel(shieldSet.getReqLevel());

        List<ShieldSet> list = new ArrayList<>();
        if (gearLevelToShields.containsKey(gearLevel)) {
            list = gearLevelToShields.get(gearLevel);
        }

        list.add(shieldSet);
        gearLevelToShields.put(gearLevel, list);
    }
}
