package io.github.lix3nn53.guardiansofadelia.Items.list.weapons;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.AttackSpeed;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponMelee;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

class Axes {

    public static ItemStack get(int placementNumber, ItemTier tier, String itemTag, double bonusPercent, int minStatValue,
                                int maxStatValue, double chanceToGetEachStat, int minNumberofStats) {
        String name = "Short Axe";
        Material material = Material.DIAMOND_AXE;
        int durability = 7;
        int level = 1;
        RPGClass rpgClass = RPGClass.WARRIOR;
        int damage = 71;
        AttackSpeed attackSpeed = AttackSpeed.SLOW;
        int itemID = 1;

        if (placementNumber == 2) {
            name = "Battle Axe";
            durability = 10;
            level = 10;
            damage = 118;
            itemID = 2;
        } else if (placementNumber == 3) {
            name = "Valor Axe";
            durability = 9;
            level = 20;
            damage = 172;
            itemID = 3;
        } else if (placementNumber == 4) {
            name = "Frozen Axe";
            durability = 1;
            level = 30;
            damage = 274;
            itemID = 4;
        } else if (placementNumber == 5) {
            name = "Sword of Doom";
            durability = 2;
            level = 40;
            damage = 377;
            itemID = 5;
        } else if (placementNumber == 6) {
            name = "Shadow Axe";
            durability = 4;
            level = 50;
            damage = 479;
            itemID = 6;
        } else if (placementNumber == 7) {
            name = "Claymore";
            durability = 6;
            level = 60;
            damage = 597;
            itemID = 7;
        } else if (placementNumber == 8) {
            name = "Dragon Sword";
            durability = 3;
            level = 70;
            damage = 732;
            itemID = 8;
        } else if (placementNumber == 9) {
            name = "Soul Reaper";
            durability = 8;
            level = 80;
            damage = 912;
            itemID = 9;
        } else if (placementNumber == 10) {
            name = "Titanic Axe";
            durability = 5;
            level = 90;
            damage = 1594;
            itemID = 10;
        }

        final WeaponMelee axe = new WeaponMelee(name, tier, itemTag, material, durability, level, rpgClass, damage, bonusPercent,
                attackSpeed, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats, itemID);
        return axe.getItemStack();
    }

}
