package io.github.lix3nn53.guardiansofadelia.Items.list.weapons;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.AttackSpeed;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponRanged;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

class Tridents {

    public static ItemStack get(int placementNumber, ItemTier tier, String itemTag, double bonusPercent, int minStatValue,
                                int maxStatValue, int minNumberofStats) {
        String name = "Wooden Spear";
        Material material = Material.TRIDENT;
        int durability = 7;
        int level = 1;
        RPGClass rpgClass = RPGClass.MONK;
        int meleeDamage = (int) ((8 * bonusPercent) + 0.5);
        int rangedDamage = 6; //do not add bonusPercent to rangedDamage because WeaponRanged constructor already does that

        AttackSpeed attackSpeed = AttackSpeed.NORMAL;
        int itemID = 601;

        if (placementNumber == 2) {
            name = "Steel Spear";
            durability = 8;
            level = 10;
            meleeDamage = 32;
            rangedDamage = 24;
            itemID = 602;
        } else if (placementNumber == 3) {
            name = "Crimson Spear";
            durability = 4;
            level = 20;
            meleeDamage = 80;
            rangedDamage = 64;
            itemID = 603;
        } else if (placementNumber == 4) {
            name = "Spear of Teva";
            durability = 5;
            level = 30;
            meleeDamage = 200;
            rangedDamage = 160;
            itemID = 604;
        } else if (placementNumber == 5) {
            name = "Seashell Spear";
            durability = 9;
            level = 40;
            meleeDamage = 320;
            rangedDamage = 260;
            itemID = 605;
        } else if (placementNumber == 6) {
            name = "Midian Spear";
            durability = 3;
            level = 50;
            meleeDamage = 530;
            rangedDamage = 420;
            itemID = 606;
        } else if (placementNumber == 7) {
            name = "RedEye SpearRENAME";
            durability = 10;
            level = 60;
            meleeDamage = 720;
            rangedDamage = 580;
            itemID = 607;
        } else if (placementNumber == 8) {
            name = "Nebula Spear";
            durability = 6;
            level = 70;
            meleeDamage = 960;
            rangedDamage = 760;
            itemID = 108;
        } else if (placementNumber == 9) {
            name = "Royal Spear";
            durability = 1;
            level = 80;
            meleeDamage = 1200;
            rangedDamage = 960;
            itemID = 609;
        } else if (placementNumber == 10) {
            name = "Ocean Spirit Spear";
            durability = 2;
            level = 90;
            meleeDamage = 1600;
            rangedDamage = 1280;
            itemID = 610;
        }

        final WeaponRanged weapon = new WeaponRanged(name, tier, itemTag, material, durability, level, rpgClass, meleeDamage, rangedDamage, bonusPercent,
                attackSpeed, minStatValue, maxStatValue, minNumberofStats, itemID);
        return weapon.getItemStack();
    }
}
