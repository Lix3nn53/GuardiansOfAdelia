package io.github.lix3nn53.guardiansofadelia.Items.list.weapons;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.AttackSpeed;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponRanged;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

class Crossbows {

    public static ItemStack get(int placementNumber, ItemTier tier, String itemTag, double bonusPercent, int minStatValue,
                                int maxStatValue, int minNumberofStats) {
        String name = "Short Crossbow";
        Material material = Material.CROSSBOW;
        int customModelDataId = 10000001;
        int level = 1;
        RPGClass rpgClass = RPGClass.HUNTER;
        int rangedDamage = 62;
        AttackSpeed attackSpeed = AttackSpeed.SLOW;
        int itemID = 701;

        if (placementNumber == 2) {
            name = "Light Crossbow";
            customModelDataId = 10000002;
            level = 10;
            rangedDamage = 86;
            itemID = 702;
        } else if (placementNumber == 3) {
            name = "Crossbow";
            customModelDataId = 10000003;
            level = 20;
            rangedDamage = 124;
            itemID = 703;
        } else if (placementNumber == 4) {
            name = "Battle Crossbow";
            customModelDataId = 10000004;
            level = 30;
            rangedDamage = 201;
            itemID = 704;
        } else if (placementNumber == 5) {
            name = "Satet Crossbow";
            customModelDataId = 10000005;
            level = 40;
            rangedDamage = 271;
            itemID = 705;
        } else if (placementNumber == 6) {
            name = "Leaf Fairy Crossbow";
            customModelDataId = 10000006;
            level = 50;
            rangedDamage = 324;
            itemID = 706;
        } else if (placementNumber == 7) {
            name = "Crossbow of Doom";
            customModelDataId = 10000008;
            level = 60;
            rangedDamage = 402;
            itemID = 707;
        } else if (placementNumber == 8) {
            name = "Unicorn Crossbow";
            customModelDataId = 10000010;
            level = 70;
            rangedDamage = 498;
            itemID = 708;
        } else if (placementNumber == 9) {
            name = "Zephyr Crossbow";
            customModelDataId = 10000012;
            level = 80;
            rangedDamage = 624;
            itemID = 709;
        } else if (placementNumber == 10) {
            name = "Arcade Crossbow";
            customModelDataId = 10000014;
            level = 90;
            rangedDamage = 1072;
            itemID = 710;
        }

        int damage = rangedDamage / 4;

        final WeaponRanged weapon = new WeaponRanged(name, tier, itemTag, material, customModelDataId, level, rpgClass, damage, rangedDamage, bonusPercent,
                attackSpeed, minStatValue, maxStatValue, minNumberofStats, itemID);
        return weapon.getItemStack();
    }
}
