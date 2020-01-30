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
        int customModelDataId = 1;
        int level = 1;
        RPGClass rpgClass = RPGClass.ROGUE;
        int damage = 8;
        AttackSpeed attackSpeed = AttackSpeed.FAST;

        if (placementNumber == 2) {
            name = "Steel Dagger";
            customModelDataId = 2;
            level = 10;
            damage = 12;
        } else if (placementNumber == 3) {
            name = "Katana";
            customModelDataId = 3;
            level = 20;
            damage = 20;
        } else if (placementNumber == 4) {
            name = "Crimson Dagger";
            customModelDataId = 4;
            level = 30;
            damage = 50;
        } else if (placementNumber == 5) {
            name = "Jade Dagger";
            customModelDataId = 5;
            level = 40;
            damage = 80;
        } else if (placementNumber == 6) {
            name = "Frozen Dagger";
            customModelDataId = 6;
            level = 50;
            damage = 128;
        } else if (placementNumber == 7) {
            name = "Shadow Claws";
            customModelDataId = 8;
            level = 60;
            damage = 180;
        } else if (placementNumber == 8) {
            name = "Dagger of Doom";
            customModelDataId = 10;
            level = 70;
            damage = 240;
        } else if (placementNumber == 9) {
            name = "Blue Wind Dagger";
            customModelDataId = 12;
            level = 80;
            damage = 300;
        } else if (placementNumber == 10) {
            name = "Twin Blades of Shadow";
            customModelDataId = 14;
            level = 90;
            damage = 400;
        }

        final WeaponMelee weapon = new WeaponMelee(name, tier, itemTag, material, customModelDataId, level, rpgClass, damage, bonusPercent,
                attackSpeed, minStatValue, maxStatValue, minNumberofStats);
        return weapon.getItemStack();
    }
}
