package io.github.lix3nn53.guardiansofadelia.Items.list.weapons;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.AttackSpeed;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponMelee;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

class Hammers {

    public static ItemStack get(int placementNumber, ItemTier tier, String itemTag, double bonusPercent, int minStatValue,
                                int maxStatValue, int minNumberofStats) {
        String name = "Short Hammer";
        Material material = Material.DIAMOND_PICKAXE;
        int customModelDataId = 1;
        int level = 1;
        RPGClass rpgClass = RPGClass.PALADIN;
        int damage = 10;
        AttackSpeed attackSpeed = AttackSpeed.SLOW;

        if (placementNumber == 2) {
            name = "Stout Mace";
            customModelDataId = 2;
            level = 10;
            damage = 18;
        } else if (placementNumber == 3) {
            name = "Steel Hammer";
            customModelDataId = 3;
            level = 20;
            damage = 30;
        } else if (placementNumber == 4) {
            name = "War Hammer";
            customModelDataId = 4;
            level = 30;
            damage = 60;
        } else if (placementNumber == 5) {
            name = "Wind Hammer";
            customModelDataId = 5;
            level = 40;
            damage = 100;
        } else if (placementNumber == 6) {
            name = "Mace of Doom";
            customModelDataId = 6;
            level = 50;
            damage = 150;
        } else if (placementNumber == 7) {
            name = "Volcano Hammer";
            customModelDataId = 8;
            level = 60;
            damage = 220;
        } else if (placementNumber == 8) {
            name = "Emerald Hammer";
            customModelDataId = 10;
            level = 70;
            damage = 290;
        } else if (placementNumber == 9) {
            name = "Lightbringer Hammer";
            customModelDataId = 12;
            level = 80;
            damage = 360;
        } else if (placementNumber == 10) {
            name = "Guardian Angel Hammer";
            customModelDataId = 14;
            level = 90;
            damage = 480;
        }

        final WeaponMelee weapon = new WeaponMelee(name, tier, itemTag, material, customModelDataId, level, rpgClass, damage, bonusPercent,
                attackSpeed, minStatValue, maxStatValue, minNumberofStats);
        return weapon.getItemStack();
    }
}
