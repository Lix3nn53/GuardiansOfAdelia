package io.github.lix3nn53.guardiansofadelia.Items.list.weapons;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.GearWeapon;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponAttackSpeed;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponGearType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeaponManager {

    private final static HashMap<Integer, List<WeaponSet>> gearLevelToWeapons = new HashMap<>();

    public static ItemStack get(WeaponGearType gearType, int gearLevel, int itemIndex, ItemTier tier, boolean noStats, String gearSet) {
        GuardiansOfAdelia.getInstance().getLogger().info(gearLevel + " " + itemIndex + " " + gearType.toString() + " " + tier.toString() + " " + gearSet);
        int minNumberOfStats = noStats ? 0 : tier.getMinNumberOfElements(false);
        int minStatValue = noStats ? 0 : GearLevel.getMinStatValue(gearLevel, false, true);
        int maxStatValue = noStats ? 0 : GearLevel.getMaxStatValue(gearLevel, false, true);

        List<WeaponSet> weaponSetList = gearLevelToWeapons.get(gearLevel);

        WeaponAttackSpeed weaponAttackSpeed = gearType.getAttackSpeed();

        WeaponSet template = weaponSetList.get(itemIndex);

        Material material = gearType.getMaterial();
        String name = template.getName(gearType);
        int customModelData = template.getCustomModelData();
        int level = template.getRequiredLevel();
        int elementDamage = template.getElementDamage(gearType);

        return new GearWeapon(name, tier, material, customModelData, level, gearType, elementDamage,
                weaponAttackSpeed, minStatValue, maxStatValue, minNumberOfStats, gearSet).getItemStack();
    }

    public static void add(WeaponSet weaponSet) {
        int gearLevel = GearLevel.getGearLevel(weaponSet.getRequiredLevel());

        List<WeaponSet> list = new ArrayList<>();
        if (gearLevelToWeapons.containsKey(gearLevel)) {
            list = gearLevelToWeapons.get(gearLevel);
        }
        list.add(weaponSet);

        gearLevelToWeapons.put(gearLevel, list);
    }
}
