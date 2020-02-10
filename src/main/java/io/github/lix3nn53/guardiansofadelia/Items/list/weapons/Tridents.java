package io.github.lix3nn53.guardiansofadelia.Items.list.weapons;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.AttackSpeed;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponRanged;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Tridents {

    private final static HashMap<Integer, List<WeaponItemTemplate>> weaponItemTemplates = new HashMap<>();

    public static ItemStack get(int gearLevel, int itemIndex, ItemTier tier, String itemTag, double bonusPercent, int minStatValue,
                                int maxStatValue, int minNumberofStats) {
        Material material = Material.TRIDENT;
        RPGClass rpgClass = RPGClass.MONK;
        AttackSpeed attackSpeed = AttackSpeed.NORMAL;

        List<WeaponItemTemplate> templates = weaponItemTemplates.get(gearLevel);
        WeaponItemTemplate template = templates.get(itemIndex);

        String name = template.getName();
        int customModelData = template.getCustomModelData();
        int level = template.getLevel();
        int meleeDamage = template.getDamage();

        int rangedDamage = (int) ((meleeDamage * 0.8) + 0.5); //do not add bonusPercent to rangedDamage because WeaponRanged constructor already does that
        meleeDamage = (int) ((meleeDamage * bonusPercent) + 0.5); //add bonusPercent to meleeDamage because WeaponRanged constructor adds only to rangedDamage

        final WeaponRanged weapon = new WeaponRanged(name, tier, itemTag, material, customModelData, level, rpgClass, meleeDamage, rangedDamage, bonusPercent,
                attackSpeed, minStatValue, maxStatValue, minNumberofStats);
        ItemStack itemStack = weapon.getItemStack();
        itemStack.addEnchantment(Enchantment.LOYALTY, 3);
        return itemStack;
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
