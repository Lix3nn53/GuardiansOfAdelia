package io.github.lix3nn53.guardiansofadelia.Items.list.weapons;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.AttackSpeed;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponRanged;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

class Bows {

    public static ItemStack get(int placementNumber, ItemTier tier, String itemTag, double bonusPercent, int minStatValue,
                                int maxStatValue, int minNumberofStats) {
        String name = "Short Bow";
        Material material = Material.BOW;
        int customModelDataId = 1;
        int level = 1;
        RPGClass rpgClass = RPGClass.ARCHER;
        int rangedDamage = 10;
        AttackSpeed attackSpeed = AttackSpeed.SLOW;

        if (placementNumber == 2) {
            name = "Light Bow";
            customModelDataId = 2;
            level = 10;
            rangedDamage = 16;
        } else if (placementNumber == 3) {
            name = "Crossbow";
            customModelDataId = 3;
            level = 20;
            rangedDamage = 32;
        } else if (placementNumber == 4) {
            name = "Battle Bow";
            customModelDataId = 4;
            level = 30;
            rangedDamage = 80;
        } else if (placementNumber == 5) {
            name = "Satet Bow";
            customModelDataId = 5;
            level = 40;
            rangedDamage = 128;
        } else if (placementNumber == 6) {
            name = "Leaf Fairy Bow";
            customModelDataId = 6;
            level = 50;
            rangedDamage = 205;
        } else if (placementNumber == 7) {
            name = "Crossbow of Doom";
            customModelDataId = 8;
            level = 60;
            rangedDamage = 290;
        } else if (placementNumber == 8) {
            name = "Unicorn Bow";
            customModelDataId = 10;
            level = 70;
            rangedDamage = 390;
        } else if (placementNumber == 9) {
            name = "Zephyr Bow";
            customModelDataId = 12;
            level = 80;
            rangedDamage = 480;
        } else if (placementNumber == 10) {
            name = "Arcade Bow";
            customModelDataId = 14;
            level = 90;
            rangedDamage = 640;
        }

        int damage = rangedDamage / 4;

        final WeaponRanged weapon = new WeaponRanged(name, tier, itemTag, material, customModelDataId, level, rpgClass, damage, rangedDamage, bonusPercent,
                attackSpeed, minStatValue, maxStatValue, minNumberofStats);
        return weapon.getItemStack();
    }
}
