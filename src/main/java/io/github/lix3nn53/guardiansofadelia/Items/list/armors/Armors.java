package io.github.lix3nn53.guardiansofadelia.Items.list.armors;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.inventory.ItemStack;

public class Armors {

    public static ItemStack getArmor(ArmorType armorType, RPGClass rpgClass, int placementNumber, ItemTier tier, String itemTag, int minStatValue,
                                     int maxStatValue, int minNumberOfStats) {
        if (armorType.equals(ArmorType.HELMET)) {
            return Helmets.get(rpgClass, placementNumber, tier, itemTag, tier.getBonusPercent(), minStatValue, maxStatValue, minNumberOfStats);
        } else if (armorType.equals(ArmorType.CHESTPLATE)) {
            return Chestplates.get(rpgClass, placementNumber, tier, itemTag, tier.getBonusPercent(), minStatValue, maxStatValue, minNumberOfStats);
        } else if (armorType.equals(ArmorType.LEGGINGS)) {
            return Leggings.get(rpgClass, placementNumber, tier, itemTag, tier.getBonusPercent(), minStatValue, maxStatValue, minNumberOfStats);
        } else if (armorType.equals(ArmorType.BOOTS)) {
            return Boots.get(rpgClass, placementNumber, tier, itemTag, tier.getBonusPercent(), minStatValue, maxStatValue, minNumberOfStats);
        }
        return null;
    }

}
