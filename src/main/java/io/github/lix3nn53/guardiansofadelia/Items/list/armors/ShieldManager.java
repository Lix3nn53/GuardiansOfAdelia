package io.github.lix3nn53.guardiansofadelia.Items.list.armors;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.RPGGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.Shield;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShieldManager {

    private final static HashMap<String, List<ShieldItemTemplate>> gearTypeAndLevelToShields = new HashMap<>();

    public static ItemStack get(RPGGearType gearType, int gearLevel, int itemIndex, ItemTier tier, String itemTag, double healthBonus, int minStatValue,
                                int maxStatValue, int minNumberOfStats) {
        String key = gearType.toString() + gearLevel;
        Material material = Material.SHIELD;

        List<ShieldItemTemplate> shieldItemTemplates = gearTypeAndLevelToShields.get(key);
        ShieldItemTemplate template = shieldItemTemplates.get(itemIndex);

        String name = template.getName();
        int customModelData = template.getCustomModelData();
        int defense = template.getDefense();
        int magicDefense = template.getMagicDefense();
        int health = template.getHealth();
        int level = template.getLevel();

        health = (int) ((health * healthBonus) + 0.5);

        final Shield shield = new Shield(name, tier, itemTag, material, customModelData, level,
                RPGGearType.SHIELD, health,
                defense, magicDefense, minStatValue, maxStatValue, minNumberOfStats);
        return shield.getItemStack();
    }

    public static void add(ShieldItemTemplate shieldItemTemplate) {
        RPGGearType gearType = shieldItemTemplate.getGearType();
        int gearLevel = GearLevel.getGearLevel(shieldItemTemplate.getLevel());
        String key = gearType.toString() + gearLevel;

        List<ShieldItemTemplate> list = new ArrayList<>();
        if (gearTypeAndLevelToShields.containsKey(key)) {
            list = gearTypeAndLevelToShields.get(key);
        }

        list.add(shieldItemTemplate);
        gearTypeAndLevelToShields.put(key, list);
    }
}
