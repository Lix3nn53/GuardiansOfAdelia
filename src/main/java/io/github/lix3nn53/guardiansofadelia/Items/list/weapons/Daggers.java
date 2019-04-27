package io.github.lix3nn53.guardiansofadelia.Items.list.weapons;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.AttackSpeed;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponMelee;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

class Daggers {

    public static ItemStack get(int placementNumber, ItemTier tier, String itemTag, double bonusPercent, int minStatValue,
                                int maxStatValue, int minNumberofStats) {
        String name = "Short Dagger";
        Material material = Material.DIAMOND_HOE;
        int durability = 9;
        int level = 1;
        RPGClass rpgClass = RPGClass.NINJA;
        int damage = 5;
        AttackSpeed attackSpeed = AttackSpeed.FAST;
        int itemID = 201;

        if (placementNumber == 2) {
            name = "Steel Dagger";
            durability = 4;
            level = 10;
            damage = 20;
            itemID = 202;
        } else if (placementNumber == 3) {
            name = "Katana";
            durability = 5;
            level = 20;
            damage = 50;
            itemID = 203;
        } else if (placementNumber == 4) {
            name = "Crimson Dagger";
            durability = 14;
            level = 30;
            damage = 125;
            itemID = 204;
        } else if (placementNumber == 5) {
            name = "Jade Dagger";
            durability = 7;
            level = 40;
            damage = 200;
            itemID = 205;
        } else if (placementNumber == 6) {
            name = "Frozen Dagger";
            durability = 10;
            level = 50;
            damage = 325;
            itemID = 206;
        } else if (placementNumber == 7) {
            name = "Shadow Claws";
            durability = 3;
            level = 60;
            damage = 450;
            itemID = 207;
        } else if (placementNumber == 8) {
            name = "Dagger of Doom";
            durability = 8;
            level = 70;
            damage = 600;
            itemID = 208;
        } else if (placementNumber == 9) {
            name = "Blue Wind Dagger";
            durability = 1;
            level = 80;
            damage = 750;
            itemID = 209;
        } else if (placementNumber == 10) {
            name = "Twin Blades of Shadow";
            durability = 2;
            level = 90;
            damage = 1000;
            itemID = 210;
        }

        final WeaponMelee weapon = new WeaponMelee(name, tier, itemTag, material, durability, level, rpgClass, damage, bonusPercent,
                attackSpeed, minStatValue, maxStatValue, minNumberofStats, itemID);
        return weapon.getItemStack();
    }
}
