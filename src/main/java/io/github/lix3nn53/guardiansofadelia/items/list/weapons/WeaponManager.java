package io.github.lix3nn53.guardiansofadelia.items.list.weapons;

import io.github.lix3nn53.guardiansofadelia.items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.GearWeapon;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.WeaponAttackSpeed;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.WeaponGearType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeaponManager {

    private final static HashMap<GearLevel, List<WeaponSet>> gearLevelToWeapons = new HashMap<>();

    public static List<ItemStack> get(WeaponGearType gearType, GearLevel gearLevel, ItemTier tier, boolean noStats, String gearSet) {
        List<WeaponSet> sets = gearLevelToWeapons.get(gearLevel);

        int minNumberOfStats = noStats ? 0 : tier.getMinNumberOfElements(false);
        int minStatValue = noStats ? 0 : gearLevel.getMinStatValue(false, true);
        int maxStatValue = noStats ? 0 : gearLevel.getMaxStatValue(false, true);

        WeaponAttackSpeed weaponAttackSpeed = gearType.getAttackSpeed();

        ArrayList<ItemStack> itemStacks = new ArrayList<>();

        for (WeaponSet template : sets) {
            Material material = gearType.getMaterial();
            String name = template.getName(gearType);
            int customModelData = template.getCustomModelData();
            int level = template.getRequiredLevel();
            int elementDamage = template.getElementDamage(gearType);

            final GearWeapon weapon = new GearWeapon(name, tier, material, customModelData, level, gearType, elementDamage,
                    weaponAttackSpeed, minStatValue, maxStatValue, minNumberOfStats, gearSet);

            itemStacks.add(weapon.getItemStack());
        }

        return itemStacks;
    }

    public static void add(WeaponSet weaponSet) {
        GearLevel gearLevel = GearLevel.getGearLevel(weaponSet.getRequiredLevel());

        List<WeaponSet> list = new ArrayList<>();
        if (gearLevelToWeapons.containsKey(gearLevel)) {
            list = gearLevelToWeapons.get(gearLevel);
        }
        list.add(weaponSet);

        gearLevelToWeapons.put(gearLevel, list);
    }
}
