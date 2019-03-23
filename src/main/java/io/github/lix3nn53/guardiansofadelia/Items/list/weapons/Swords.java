package io.github.lix3nn53.guardiansofadelia.Items.list.weapons;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.AttackSpeed;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponMelee;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

class Swords {

    public static ItemStack get(int placementNumber, ItemTier tier, String itemTag, double bonusPercent, int minStatValue,
                                int maxStatValue, int minNumberofStats) {
        String name = "Short Sword";
        Material material = Material.DIAMOND_SWORD;
        int durability = 1;
        int level = 1;
        RPGClass rpgClass = RPGClass.KNIGHT;
        int damage = 53;
        AttackSpeed attackSpeed = AttackSpeed.NORMAL;
        int itemID = 501;

        if (placementNumber == 2) {
            name = "Steel Sword";
            durability = 4;
            level = 10;
            damage = 74;
            itemID = 502;
        } else if (placementNumber == 3) {
            name = "Spear";
            durability = 6;
            level = 20;
            damage = 102;
            itemID = 503;
        } else if (placementNumber == 4) {
            name = "Broad Sword";
            durability = 7;
            level = 30;
            damage = 168;
            itemID = 504;
        } else if (placementNumber == 5) {
            name = "Heroic Sword";
            durability = 2;
            level = 40;
            damage = 228;
            itemID = 505;
        } else if (placementNumber == 6) {
            name = "Sword of Doom2RENAME";
            durability = 3;
            level = 50;
            damage = 274;
            itemID = 506;
        } else if (placementNumber == 7) {
            name = "Fire Spirit Sword";
            durability = 10;
            level = 60;
            damage = 342;
            itemID = 507;
        } else if (placementNumber == 8) {
            name = "Leaf Fairy Sword";
            durability = 5;
            level = 70;
            damage = 426;
            itemID = 508;
        } else if (placementNumber == 9) {
            name = "Water Fairy Sword";
            durability = 8;
            level = 80;
            damage = 533;
            itemID = 509;
        } else if (placementNumber == 10) {
            name = "Hellas Sword";
            durability = 9;
            level = 90;
            damage = 918;
            itemID = 510;
        }

        final WeaponMelee axe = new WeaponMelee(name, tier, itemTag, material, durability, level, rpgClass, damage, bonusPercent,
                attackSpeed, minStatValue, maxStatValue, minNumberofStats, itemID);
        return axe.getItemStack();
    }
}
