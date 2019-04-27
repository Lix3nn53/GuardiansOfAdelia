package io.github.lix3nn53.guardiansofadelia.Items.list.weapons;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.AttackSpeed;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponMagical;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

class Staffs {

    public static ItemStack get(int placementNumber, ItemTier tier, String itemTag, double bonusPercent, int minStatValue,
                                int maxStatValue, int minNumberofStats) {
        String name = "Short Staff";
        Material material = Material.DIAMOND_SHOVEL;
        int durability = 4;
        int level = 1;
        RPGClass rpgClass = RPGClass.MAGE;
        int magicDamage = 10;
        AttackSpeed attackSpeed = AttackSpeed.SLOW;
        int itemID = 401;

        if (placementNumber == 2) {
            name = "Leaf Fairy Staff";
            durability = 3;
            level = 10;
            magicDamage = 40;
            itemID = 402;
        } else if (placementNumber == 3) {
            name = "Zest Staff";
            durability = 7;
            level = 20;
            magicDamage = 100;
            itemID = 403;
        } else if (placementNumber == 4) {
            name = "Dinah Wand";
            durability = 1;
            level = 30;
            magicDamage = 250;
            itemID = 404;
        } else if (placementNumber == 5) {
            name = "Nether Fire Staff";
            durability = 5;
            level = 40;
            magicDamage = 400;
            itemID = 405;
        } else if (placementNumber == 6) {
            name = "Water Fairy Staff";
            durability = 2;
            level = 50;
            magicDamage = 650;
            itemID = 406;
        } else if (placementNumber == 7) {
            name = "Fairy Staff";
            durability = 10;
            level = 60;
            magicDamage = 900;
            itemID = 407;
        } else if (placementNumber == 8) {
            name = "Ocean Staff";
            durability = 16;
            level = 70;
            magicDamage = 1200;
            itemID = 408;
        } else if (placementNumber == 9) {
            name = "Crystal of Swamp";
            durability = 6;
            level = 80;
            magicDamage = 1500;
            itemID = 409;
        } else if (placementNumber == 10) {
            name = "Neferti Staff";
            durability = 11;
            level = 90;
            magicDamage = 2000;
            itemID = 410;
        }

        int damage = magicDamage / 4;

        final WeaponMagical weapon = new WeaponMagical(name, tier, itemTag, material, durability, level, rpgClass, damage, magicDamage, bonusPercent,
                attackSpeed, minStatValue, maxStatValue, minNumberofStats, itemID);
        return weapon.getItemStack();
    }
}
