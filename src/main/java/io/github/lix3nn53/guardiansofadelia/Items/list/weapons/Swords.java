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
        int customModelDataId = 10000001;
        int level = 1;
        RPGClass rpgClass = RPGClass.KNIGHT;
        int damage = 10;
        AttackSpeed attackSpeed = AttackSpeed.NORMAL;

        if (placementNumber == 2) {
            name = "Steel Sword";
            customModelDataId = 10000002;
            level = 10;
            damage = 18;
        } else if (placementNumber == 3) {
            name = "Steel Sword";
            customModelDataId = 10000003;
            level = 20;
            damage = 30;
        } else if (placementNumber == 4) {
            name = "Broad Sword";
            customModelDataId = 10000004;
            level = 30;
            damage = 75;
        } else if (placementNumber == 5) {
            name = "Heroic Sword";
            customModelDataId = 10000005;
            level = 40;
            damage = 120;
        } else if (placementNumber == 6) {
            name = "Sword of Doom2RENAME";
            customModelDataId = 10000006;
            level = 50;
            damage = 190;
        } else if (placementNumber == 7) {
            name = "Fire Spirit Sword";
            customModelDataId = 10000008;
            level = 60;
            damage = 270;
        } else if (placementNumber == 8) {
            name = "Leaf Fairy Sword";
            customModelDataId = 10000010;
            level = 70;
            damage = 360;
        } else if (placementNumber == 9) {
            name = "Water Fairy Sword";
            customModelDataId = 10000012;
            level = 80;
            damage = 450;
        } else if (placementNumber == 10) {
            name = "Hellas Sword";
            customModelDataId = 10000014;
            level = 90;
            damage = 600;
        }

        final WeaponMelee axe = new WeaponMelee(name, tier, itemTag, material, customModelDataId, level, rpgClass, damage, bonusPercent,
                attackSpeed, minStatValue, maxStatValue, minNumberofStats);
        return axe.getItemStack();
    }
}
