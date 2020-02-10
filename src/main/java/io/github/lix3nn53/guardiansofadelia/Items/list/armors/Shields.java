package io.github.lix3nn53.guardiansofadelia.Items.list.armors;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.Shield;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Shields {

    private final static HashMap<String, List<ShieldItemTemplate>> rpgClassToShieldItemTemplates = new HashMap<>();

    public static void add(RPGClass rpgClass, ShieldItemTemplate shieldItemTemplate) {
        int gearLevel = GearLevel.getGearLevel(shieldItemTemplate.getLevel());

        String key = rpgClass.name() + gearLevel;

        List<ShieldItemTemplate> list = new ArrayList<>();
        if (rpgClassToShieldItemTemplates.containsKey(key)) {
            list = rpgClassToShieldItemTemplates.get(key);
        }
        list.add(shieldItemTemplate);
        rpgClassToShieldItemTemplates.put(key, list);
    }

    public static ItemStack get(RPGClass rpgClass, int gearLevel, int itemIndex, ItemTier tier, String itemTag, double healthBonus, int minStatValue,
                                int maxStatValue, int minNumberOfStats) {
        Material material = Material.SHIELD;

        String key = rpgClass.name() + gearLevel;
        List<ShieldItemTemplate> shieldItemTemplates = rpgClassToShieldItemTemplates.get(key);
        ShieldItemTemplate template = shieldItemTemplates.get(itemIndex);

        String name = template.getName();
        int customModelData = template.getCustomModelData();
        int defense = template.getDefense();
        int magicDefense = template.getMagicDefense();
        int health = template.getHealth();
        int level = template.getLevel();

        health = (int) ((health * healthBonus) + 0.5);

        final Shield shield = new Shield(name, tier, itemTag, material, customModelData, level,
                rpgClass, health,
                defense, magicDefense, minStatValue, maxStatValue, minNumberOfStats);
        return shield.getItemStack();
    }
}
