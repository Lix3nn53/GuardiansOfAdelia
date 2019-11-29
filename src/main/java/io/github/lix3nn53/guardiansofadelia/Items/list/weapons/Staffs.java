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
        int customModelDataId = 10000001;
        int level = 1;
        RPGClass rpgClass = RPGClass.MAGE;
        int magicDamage = 10;
        AttackSpeed attackSpeed = AttackSpeed.SLOW;

        if (placementNumber == 2) {
            name = "Leaf Fairy Staff";
            customModelDataId = 10000002;
            level = 10;
            magicDamage = 20;
        } else if (placementNumber == 3) {
            name = "Zest Staff";
            customModelDataId = 10000003;
            level = 20;
            magicDamage = 50;
        } else if (placementNumber == 4) {
            name = "Dinah Wand";
            customModelDataId = 10000004;
            level = 30;
            magicDamage = 125;
        } else if (placementNumber == 5) {
            name = "Nether Fire Staff";
            customModelDataId = 10000005;
            level = 40;
            magicDamage = 200;
        } else if (placementNumber == 6) {
            name = "Water Fairy Staff";
            customModelDataId = 10000006;
            level = 50;
            magicDamage = 320;
        } else if (placementNumber == 7) {
            name = "Fairy Staff";
            customModelDataId = 10000008;
            level = 60;
            magicDamage = 450;
        } else if (placementNumber == 8) {
            name = "Ocean Staff";
            customModelDataId = 10000010;
            level = 70;
            magicDamage = 600;
        } else if (placementNumber == 9) {
            name = "Crystal of Swamp";
            customModelDataId = 10000012;
            level = 80;
            magicDamage = 750;
        } else if (placementNumber == 10) {
            name = "Neferti Staff";
            customModelDataId = 10000014;
            level = 90;
            magicDamage = 1000;
        }

        int damage = magicDamage / 4;

        final WeaponMagical weapon = new WeaponMagical(name, tier, itemTag, material, customModelDataId, level, rpgClass, damage, magicDamage, bonusPercent,
                attackSpeed, minStatValue, maxStatValue, minNumberofStats);
        return weapon.getItemStack();
    }
}
