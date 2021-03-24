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

    private final static HashMap<GearLevel, List<ShieldSet>> gearLevelToShields = new HashMap<>();

    public static List<ItemStack> get(ShieldGearType gearType, GearLevel gearLevel, ItemTier tier, boolean noStats, String gearSet) {
        List<ShieldSet> sets = gearLevelToShields.get(gearLevel);

        int minNumberOfStats = noStats ? 0 : tier.getMinNumberOfElements(false);
        int minStatValue = noStats ? 0 : gearLevel.getMinStatValue(false, true);
        int maxStatValue = noStats ? 0 : gearLevel.getMaxStatValue(false, true);

        Material material = gearType.getMaterial();

        ArrayList<ItemStack> itemStacks = new ArrayList<>();

        for (ShieldSet template : sets) {
            String name = template.getName(gearType);
            int customModelData = template.getCustomModelData();
            int defense = template.getDefense(gearType);
            int health = template.getHealth(gearType);
            int level = template.getRequiredLevel();

            final Shield shield = new Shield(name, tier, material, customModelData, level,
                    gearType, health,
                    defense, minStatValue, maxStatValue, minNumberOfStats, gearSet);

            itemStacks.add(shield.getItemStack());
        }

        return itemStacks;
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
