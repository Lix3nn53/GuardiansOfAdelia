package io.github.lix3nn53.guardiansofadelia.Items.list.weapons;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.AttackSpeed;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponMelee;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

class Axes {

    public static ItemStack get(int placementNumber, ItemTier tier, String itemTag, double bonusPercent, int minStatValue,
                                int maxStatValue, int minNumberofStats) {
        String name = "Short Axe";
        Material material = Material.DIAMOND_AXE;
        int durability = 1;
        int level = 1;
        RPGClass rpgClass = RPGClass.WARRIOR;
        int damage = 12;
        AttackSpeed attackSpeed = AttackSpeed.SLOW;

        if (placementNumber == 2) {
            name = "Battle Axe";
            durability = 2;
            level = 10;
            damage = 24;
        } else if (placementNumber == 3) {
            name = "Valor Axe";
            durability = 3;
            level = 20;
            damage = 60;
        } else if (placementNumber == 4) {
            name = "Frozen Axe";
            durability = 4;
            level = 30;
            damage = 150;
        } else if (placementNumber == 5) {
            name = "Sword of Doom";
            durability = 5;
            level = 40;
            damage = 240;
        } else if (placementNumber == 6) {
            name = "Shadow Axe";
            durability = 6;
            level = 50;
            damage = 380;
        } else if (placementNumber == 7) {
            name = "Claymore";
            durability = 8;
            level = 60;
            damage = 540;
        } else if (placementNumber == 8) {
            name = "Dragon Sword";
            durability = 10;
            level = 70;
            damage = 720;
        } else if (placementNumber == 9) {
            name = "Soul Reaper";
            durability = 12;
            level = 80;
            damage = 900;
        } else if (placementNumber == 10) {
            name = "Titanic Axe";
            durability = 14;
            level = 90;
            damage = 1200;
        }

        final WeaponMelee axe = new WeaponMelee(name, tier, itemTag, material, durability, level, rpgClass, damage, bonusPercent,
                attackSpeed, minStatValue, maxStatValue, minNumberofStats);
        return axe.getItemStack();
    }

}
