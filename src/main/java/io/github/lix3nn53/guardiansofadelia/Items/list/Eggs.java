package io.github.lix3nn53.guardiansofadelia.Items.list;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.Egg;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Set;

public class Eggs {

    private static final HashMap<String, Integer> keyToCustomModelData = new HashMap<>();
    private static final HashMap<String, ItemTier> keyToItemTier = new HashMap<>();
    private static final HashMap<String, Double> keyToMountSpeed = new HashMap<>();

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

    public static boolean isMythicMob(String key) {
        MythicMob mythicMob = MythicMobs.inst().getMobManager().getMythicMob(key);
        if (mythicMob == null) {
            GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "Eggs mythicMob null: " + key);

            return false;
        }
        String displayName = mythicMob.getDisplayName().get();
        GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.GREEN + "Eggs MM: " + key + "-" + displayName);

        return true;
    }

    public static void add(String key, int customModelData, ItemTier itemTier) {
        boolean mythicMob = isMythicMob(key);
        if (!mythicMob) return;

        keyToCustomModelData.put(key, customModelData);
        keyToItemTier.put(key, itemTier);
    }

    public static void add(String key, int customModelData, ItemTier itemTier, double mountSpeed) {
        boolean mythicMob = isMythicMob(key);
        if (!mythicMob) return;

        keyToCustomModelData.put(key, customModelData);
        keyToItemTier.put(key, itemTier);
        keyToMountSpeed.put(key, mountSpeed);
    }

    public static double getMountSpeed(String key) {
        return keyToMountSpeed.get(key);
    }
}
