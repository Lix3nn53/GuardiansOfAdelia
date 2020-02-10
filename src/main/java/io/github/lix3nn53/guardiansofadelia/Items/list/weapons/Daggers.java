package io.github.lix3nn53.guardiansofadelia.Items.list.weapons;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.AttackSpeed;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponMelee;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Daggers {

    private final static HashMap<Integer, List<WeaponItemTemplate>> weaponItemTemplates = new HashMap<>();

    public static ItemStack get(int gearLevel, int itemIndex, ItemTier tier, String itemTag, double bonusPercent, int minStatValue,
                                int maxStatValue, int minNumberofStats) {
        Material material = Material.DIAMOND_HOE;
        RPGClass rpgClass = RPGClass.ROGUE;
        AttackSpeed attackSpeed = AttackSpeed.FAST;

        List<WeaponItemTemplate> templates = weaponItemTemplates.get(gearLevel);
        WeaponItemTemplate template = templates.get(itemIndex);

        String name = template.getName();
        int customModelData = template.getCustomModelData();
        int level = template.getLevel();
        int meleeDamage = template.getDamage();

        final WeaponMelee weapon = new WeaponMelee(name, tier, itemTag, material, customModelData, level, rpgClass, meleeDamage, bonusPercent,
                attackSpeed, minStatValue, maxStatValue, minNumberofStats);
        return weapon.getItemStack();
    }

    protected static void add(WeaponItemTemplate weaponItemTemplate, int placementNumber) {
        List<WeaponItemTemplate> list = new ArrayList<>();
        if (weaponItemTemplates.containsKey(placementNumber)) {
            list = weaponItemTemplates.get(placementNumber);
        }
        list.add(weaponItemTemplate);
        weaponItemTemplates.put(placementNumber, list);
    }
}
