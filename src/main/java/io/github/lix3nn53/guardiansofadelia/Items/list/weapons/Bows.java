package io.github.lix3nn53.guardiansofadelia.Items.list.weapons;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.AttackSpeed;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponRanged;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

class Bows {

    private final static List<WeaponItemTemplate> weaponItemTemplates = new ArrayList<>();

    public static ItemStack get(int placementNumber, ItemTier tier, String itemTag, double bonusPercent, int minStatValue,
                                int maxStatValue, int minNumberofStats) {
        Material material = Material.BOW;
        RPGClass rpgClass = RPGClass.ARCHER;
        AttackSpeed attackSpeed = AttackSpeed.SLOW;

        WeaponItemTemplate template = weaponItemTemplates.get(placementNumber - 1);

        String name = template.getName();
        int customModelData = template.getCustomModelData();
        int level = template.getLevel();
        int rangedDamage = template.getDamage();

        int damage = rangedDamage / 4;

        final WeaponRanged weapon = new WeaponRanged(name, tier, itemTag, material, customModelData, level, rpgClass, damage, rangedDamage, bonusPercent,
                attackSpeed, minStatValue, maxStatValue, minNumberofStats);
        return weapon.getItemStack();
    }

    protected static void add(WeaponItemTemplate weaponItemTemplate) {
        weaponItemTemplates.add(weaponItemTemplate);
    }
}
