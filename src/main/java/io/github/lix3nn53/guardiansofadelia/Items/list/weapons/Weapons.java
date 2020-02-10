package io.github.lix3nn53.guardiansofadelia.Items.list.weapons;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.inventory.ItemStack;

public class Weapons {

    public static ItemStack getWeapon(RPGClass rpgClass, int gearLevel, int index, ItemTier tier, String itemTag, int minStatValue,
                                      int maxStatValue, int minNumberOfStats) {
        if (rpgClass.equals(RPGClass.WARRIOR)) {
            return Axes.get(gearLevel, index, tier, itemTag, tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberOfStats);
        } else if (rpgClass.equals(RPGClass.ARCHER)) {
            return Bows.get(gearLevel, index, tier, itemTag, tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberOfStats);
        } else if (rpgClass.equals(RPGClass.ROGUE)) {
            return Daggers.get(gearLevel, index, tier, itemTag, tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberOfStats);
        } else if (rpgClass.equals(RPGClass.PALADIN)) {
            return Hammers.get(gearLevel, index, tier, itemTag, tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberOfStats);
        } else if (rpgClass.equals(RPGClass.MAGE)) {
            return Staffs.get(gearLevel, index, tier, itemTag, tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberOfStats);
        } else if (rpgClass.equals(RPGClass.KNIGHT)) {
            return Swords.get(gearLevel, index, tier, itemTag, tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberOfStats);
        } else if (rpgClass.equals(RPGClass.MONK)) {
            return Tridents.get(gearLevel, index, tier, itemTag, tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberOfStats);
        } else if (rpgClass.equals(RPGClass.HUNTER)) {
            return Crossbows.get(gearLevel, index, tier, itemTag, tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberOfStats);
        }
        return null;
    }

    public static void add(RPGClass rpgClass, WeaponItemTemplate weaponItemTemplate) {
        int gearLevel = GearLevel.getGearLevel(weaponItemTemplate.getLevel());

        if (rpgClass.equals(RPGClass.WARRIOR)) {
            Axes.add(weaponItemTemplate, gearLevel);
        } else if (rpgClass.equals(RPGClass.ARCHER)) {
            Bows.add(weaponItemTemplate, gearLevel);
        } else if (rpgClass.equals(RPGClass.ROGUE)) {
            Daggers.add(weaponItemTemplate, gearLevel);
        } else if (rpgClass.equals(RPGClass.PALADIN)) {
            Hammers.add(weaponItemTemplate, gearLevel);
        } else if (rpgClass.equals(RPGClass.MAGE)) {
            Staffs.add(weaponItemTemplate, gearLevel);
        } else if (rpgClass.equals(RPGClass.KNIGHT)) {
            Swords.add(weaponItemTemplate, gearLevel);
        } else if (rpgClass.equals(RPGClass.MONK)) {
            Tridents.add(weaponItemTemplate, gearLevel);
        } else if (rpgClass.equals(RPGClass.HUNTER)) {
            Crossbows.add(weaponItemTemplate, gearLevel);
        }
    }
}
