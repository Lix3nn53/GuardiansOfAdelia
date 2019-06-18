package io.github.lix3nn53.guardiansofadelia.Items.list.weapons;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.AttackSpeed;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponRanged;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

class Tridents {

    public static ItemStack get(int placementNumber, ItemTier tier, String itemTag, double bonusPercent, int minStatValue,
                                int maxStatValue, int minNumberofStats) {
        String name = "Wooden Spear";
        Material material = Material.TRIDENT;
        int customModelDataId = 10000001;
        int level = 1;
        RPGClass rpgClass = RPGClass.MONK;
        int meleeDamage = 8;
        int rangedDamage = 6; //do not add bonusPercent to rangedDamage because WeaponRanged constructor already does that

        AttackSpeed attackSpeed = AttackSpeed.NORMAL;

        if (placementNumber == 2) {
            name = "Steel Spear";
            customModelDataId = 10000002;
            level = 10;
            meleeDamage = 32;
            rangedDamage = 24;
        } else if (placementNumber == 3) {
            name = "Crimson Spear";
            customModelDataId = 10000003;
            level = 20;
            meleeDamage = 80;
            rangedDamage = 64;
        } else if (placementNumber == 4) {
            name = "Spear of Teva";
            customModelDataId = 10000004;
            level = 30;
            meleeDamage = 200;
            rangedDamage = 160;
        } else if (placementNumber == 5) {
            name = "Seashell Spear";
            customModelDataId = 10000005;
            level = 40;
            meleeDamage = 320;
            rangedDamage = 260;
        } else if (placementNumber == 6) {
            name = "Midian Spear";
            customModelDataId = 10000006;
            level = 50;
            meleeDamage = 530;
            rangedDamage = 420;
        } else if (placementNumber == 7) {
            name = "RedEye SpearRENAME";
            customModelDataId = 10000008;
            level = 60;
            meleeDamage = 720;
            rangedDamage = 580;
        } else if (placementNumber == 8) {
            name = "Nebula Spear";
            customModelDataId = 10000010;
            level = 70;
            meleeDamage = 960;
            rangedDamage = 760;
        } else if (placementNumber == 9) {
            name = "Royal Spear";
            customModelDataId = 10000012;
            level = 80;
            meleeDamage = 1200;
            rangedDamage = 960;
        } else if (placementNumber == 10) {
            name = "Ocean Spirit Spear";
            customModelDataId = 10000014;
            level = 90;
            meleeDamage = 1600;
            rangedDamage = 1280;
        }

        meleeDamage = (int) ((meleeDamage * bonusPercent) + 0.5); //add bonusPercent to meleeDamage because WeaponRanged constructor adds only to rangedDamage

        final WeaponRanged weapon = new WeaponRanged(name, tier, itemTag, material, customModelDataId, level, rpgClass, meleeDamage, rangedDamage, bonusPercent,
                attackSpeed, minStatValue, maxStatValue, minNumberofStats);
        ItemStack itemStack = weapon.getItemStack();
        itemStack.addEnchantment(Enchantment.LOYALTY, 3);
        return itemStack;
    }
}
