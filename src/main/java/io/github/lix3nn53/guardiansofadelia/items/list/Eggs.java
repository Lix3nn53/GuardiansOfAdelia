package io.github.lix3nn53.guardiansofadelia.items.list;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.Egg;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Set;

public class Eggs {

    private static final HashMap<String, Integer> keyToCustomModelData = new HashMap<>();
    private static final HashMap<String, ItemTier> keyToItemTier = new HashMap<>();

    public static ItemStack get(String key, GearLevel gearLevel, int petLevel) {
        ItemTier itemTier = keyToItemTier.get(key);
        int customModelData = keyToCustomModelData.get(key);

        Egg egg = new Egg(key, itemTier, Material.STONE_HOE, customModelData,
                gearLevel.getMinLevel(), petLevel, "");

        return egg.getItemStack();
    }

    public static Set<String> getKeys() {
        return keyToCustomModelData.keySet();
    }

    public static boolean isMythicMob(String key) {
        MythicMob mythicMob = MythicBukkit.inst().getMobManager().getMythicMob(key);
        if (mythicMob == null) {
            GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.RED + "Eggs mythicMob null: " + key);

            return false;
        }
        String displayName = mythicMob.getDisplayName().get();
        GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.GREEN_DARK + "Eggs MM: " + key + "-" + displayName);

        return true;
    }

    public static void add(String key, int customModelData, ItemTier itemTier) {
        boolean mythicMob = isMythicMob(key);
        if (!mythicMob) return;

        keyToCustomModelData.put(key, customModelData);
        keyToItemTier.put(key, itemTier);
    }
}
