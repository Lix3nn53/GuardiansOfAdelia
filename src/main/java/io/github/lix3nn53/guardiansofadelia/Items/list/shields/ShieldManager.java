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

    public static ItemStack get(ShieldGearType gearType, int gearLevel, int itemIndex, ItemTier tier, String itemTag, int minStatValue,
                                int maxStatValue, int minNumberOfStats) {
        Material material = Material.SHIELD;

        List<ShieldSet> shieldItemTemplates = gearLevelToShields.get(gearLevel);
        ShieldSet template = shieldItemTemplates.get(itemIndex);

        String name = template.getName(gearType);
        int customModelData = template.getCustomModelData();
        int defense = template.getDefense(gearType);
        int magicDefense = template.getMagicDefense(gearType);
        int health = template.getHealth(gearType);
        int level = template.getReqLevel();

        final Shield shield = new Shield(name, tier, itemTag, material, customModelData, level,
                gearType, health,
                defense, magicDefense, minStatValue, maxStatValue, minNumberOfStats);
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
