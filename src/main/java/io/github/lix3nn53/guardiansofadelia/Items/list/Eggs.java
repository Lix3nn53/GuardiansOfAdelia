package io.github.lix3nn53.guardiansofadelia.Items.list;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.Egg;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Set;

public class Eggs {

    private static final HashMap<String, Integer> keyToCustomModelData = new HashMap<>();
    private static final HashMap<String, ItemTier> keyToItemTier = new HashMap<>();

    public static ItemStack get(String key, int gearLevel, int petLevel) {
        ItemTier itemTier = keyToItemTier.get(key);
        int customModelData = keyToCustomModelData.get(key);

        Egg egg = new Egg(key, itemTier, "", Material.STONE_HOE, customModelData,
                GearLevel.getMinLevel(gearLevel), petLevel);

        return egg.getItemStack();
    }

    public static Set<String> getKeys() {
        return keyToCustomModelData.keySet();
    }

    public static void add(String key, int customModelData, ItemTier itemTier) {
        keyToCustomModelData.put(key, customModelData);
        keyToItemTier.put(key, itemTier);
    }
}
