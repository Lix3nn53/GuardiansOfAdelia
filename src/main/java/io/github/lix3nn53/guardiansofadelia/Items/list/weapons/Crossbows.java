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
        int customModelDataId = 1;
        int level = 1;
        RPGClass rpgClass = RPGClass.HUNTER;
        int rangedDamage = 12;
        AttackSpeed attackSpeed = AttackSpeed.SLOW;

        if (placementNumber == 2) {
            name = "Light Crossbow";
            customModelDataId = 2;
            level = 10;
            rangedDamage = 24;
        } else if (placementNumber == 3) {
            name = "Crossbow";
            customModelDataId = 3;
            level = 20;
            rangedDamage = 60;
        } else if (placementNumber == 4) {
            name = "Battle Crossbow";
            customModelDataId = 4;
            level = 30;
            rangedDamage = 150;
        } else if (placementNumber == 5) {
            name = "Satet Crossbow";
            customModelDataId = 5;
            level = 40;
            rangedDamage = 240;
        } else if (placementNumber == 6) {
            name = "Leaf Fairy Crossbow";
            customModelDataId = 6;
            level = 50;
            rangedDamage = 380;
        } else if (placementNumber == 7) {
            name = "Crossbow of Doom";
            customModelDataId = 8;
            level = 60;
            rangedDamage = 540;
        } else if (placementNumber == 8) {
            name = "Unicorn Crossbow";
            customModelDataId = 10;
            level = 70;
            rangedDamage = 720;
        } else if (placementNumber == 9) {
            name = "Zephyr Crossbow";
            customModelDataId = 12;
            level = 80;
            rangedDamage = 900;
        } else if (placementNumber == 10) {
            name = "Arcade Crossbow";
            customModelDataId = 14;
            level = 90;
            rangedDamage = 1200;
        }

        int damage = rangedDamage / 4;

        final WeaponRanged weapon = new WeaponRanged(name, tier, itemTag, material, customModelDataId, level, rpgClass, damage, rangedDamage, bonusPercent,
                attackSpeed, minStatValue, maxStatValue, minNumberofStats);
        return weapon.getItemStack();
    }
}
