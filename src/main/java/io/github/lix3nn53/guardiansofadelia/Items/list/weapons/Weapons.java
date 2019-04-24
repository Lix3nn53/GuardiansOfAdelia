package io.github.lix3nn53.guardiansofadelia.Items.list.weapons;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.inventory.ItemStack;

public class Weapons {

    public static ItemStack getWeapon(RPGClass rpgClass, int placementNumber, ItemTier tier, String itemTag, int minStatValue,
                                      int maxStatValue, int minNumberOfStats) {
        if (rpgClass.equals(RPGClass.WARRIOR)) {
            return Axes.get(placementNumber, tier, itemTag, tier.getBonusPercent(), minStatValue, maxStatValue, minNumberOfStats);
        } else if (rpgClass.equals(RPGClass.ARCHER)) {
            return Bows.get(placementNumber, tier, itemTag, tier.getBonusPercent(), minStatValue, maxStatValue, minNumberOfStats);
        } else if (rpgClass.equals(RPGClass.NINJA)) {
            return Daggers.get(placementNumber, tier, itemTag, tier.getBonusPercent(), minStatValue, maxStatValue, minNumberOfStats);
        } else if (rpgClass.equals(RPGClass.PALADIN)) {
            return Hammers.get(placementNumber, tier, itemTag, tier.getBonusPercent(), minStatValue, maxStatValue, minNumberOfStats);
        } else if (rpgClass.equals(RPGClass.MAGE)) {
            return Staffs.get(placementNumber, tier, itemTag, tier.getBonusPercent(), minStatValue, maxStatValue, minNumberOfStats);
        } else if (rpgClass.equals(RPGClass.KNIGHT)) {
            return Swords.get(placementNumber, tier, itemTag, tier.getBonusPercent(), minStatValue, maxStatValue, minNumberOfStats);
        } else if (rpgClass.equals(RPGClass.MONK)) {
            return Tridents.get(placementNumber, tier, itemTag, tier.getBonusPercent(), minStatValue, maxStatValue, minNumberOfStats);
        }
        return null;
    }

}
