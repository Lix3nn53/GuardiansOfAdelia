package io.github.lix3nn53.guardiansofadelia.Items.list.weapons;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.AttackSpeed;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponMelee;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

class Hammers {

    public static ItemStack get(int placementNumber, ItemTier tier, String itemTag, double bonusPercent, int minStatValue,
                                int maxStatValue, double chanceToGetEachStat, int minNumberofStats) {
        String name = "Short Hammer";
        Material material = Material.DIAMOND_PICKAXE;
        int durability = 8;
        int level = 1;
        RPGClass rpgClass = RPGClass.PALADIN;
        int damage = 62;
        AttackSpeed attackSpeed = AttackSpeed.SLOW;
        int itemID = 301;

        if (placementNumber == 2) {
            name = "Stout Mace";
            durability = 1;
            level = 10;
            damage = 102;
            itemID = 302;
        } else if (placementNumber == 3) {
            name = "Steel Hammer";
            durability = 6;
            level = 20;
            damage = 153;
            itemID = 303;
        } else if (placementNumber == 4) {
            name = "War Hammer";
            durability = 7;
            level = 30;
            damage = 242;
            itemID = 304;
        } else if (placementNumber == 5) {
            name = "Wind Hammer";
            durability = 11;
            level = 40;
            damage = 324;
            itemID = 305;
        } else if (placementNumber == 6) {
            name = "Mace of Doom";
            durability = 5;
            level = 50;
            damage = 386;
            itemID = 306;
        } else if (placementNumber == 7) {
            name = "Volcano Hammer";
            durability = 2;
            level = 60;
            damage = 476;
            itemID = 307;
        } else if (placementNumber == 8) {
            name = "Emerald Hammer";
            durability = 3;
            level = 70;
            damage = 591;
            itemID = 308;
        } else if (placementNumber == 9) {
            name = "Lightbringer Hammer";
            durability = 12;
            level = 80;
            damage = 732;
            itemID = 309;
        } else if (placementNumber == 10) {
            name = "Guardian Angel Hammer";
            durability = 4;
            level = 90;
            damage = 1248;
            itemID = 310;
        }

        final WeaponMelee weapon = new WeaponMelee(name, tier, itemTag, material, durability, level, rpgClass, damage, bonusPercent,
                attackSpeed, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats, itemID);
        return weapon.getItemStack();
    }
}
