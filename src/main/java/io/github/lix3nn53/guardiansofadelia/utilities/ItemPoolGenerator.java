package io.github.lix3nn53.guardiansofadelia.utilities;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.consumables.BuffType;
import io.github.lix3nn53.guardiansofadelia.Items.consumables.PotionType;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.*;
import io.github.lix3nn53.guardiansofadelia.Items.list.consumables.BuffScrolls;
import io.github.lix3nn53.guardiansofadelia.Items.list.consumables.Potions;
import io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems.PassiveItemList;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.Weapons;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemPoolGenerator {

    public static List<ItemStack> generateWeapons(ItemTier tier, String itemTag, GearLevel gearLevel) {
        double chanceToGetEachStat = tier.getChanceToGetEachStat();
        int minNumberofStats = tier.getMinNumberOfStats();
        int minStatValue = gearLevel.getMinStatValue();
        int maxStatValue = gearLevel.getMaxStatValue();

        //7*3 weapon
        List<ItemStack> temp = new ArrayList<>();

        temp.add(Weapons.getWeapon(RPGClass.WARRIOR, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.ARCHER, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.NINJA, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.PALADIN, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.MAGE, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.KNIGHT, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.MONK, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));

        return temp;
    }

    public static List<ItemStack> generatePassives(ItemTier tier, String itemTag, GearLevel gearLevel) {
        double bonusPercent = tier.getBonusPercent();
        double chanceToGetEachStat = tier.getChanceToGetEachStat();
        int minNumberofStats = tier.getMinNumberOfStats();
        int minStatValue = gearLevel.getMinStatValue();
        int maxStatValue = gearLevel.getMaxStatValue();

        List<ItemStack> temp = new ArrayList<>();

        temp.add(PassiveItemList.get(gearLevel.getWeaponAndPassiveNo(), RPGSlotType.RING, tier, itemTag, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats, bonusPercent));
        temp.add(PassiveItemList.get(gearLevel.getWeaponAndPassiveNo(), RPGSlotType.GLOVE, tier, itemTag, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats, bonusPercent));
        temp.add(PassiveItemList.get(gearLevel.getWeaponAndPassiveNo(), RPGSlotType.NECKLACE, tier, itemTag, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats, bonusPercent));
        temp.add(PassiveItemList.get(gearLevel.getWeaponAndPassiveNo(), RPGSlotType.EARRING, tier, itemTag, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats, bonusPercent));
        temp.add(PassiveItemList.get(gearLevel.getWeaponAndPassiveNo(), RPGSlotType.PARROT, tier, itemTag, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats, bonusPercent));

        return temp;
    }

    public static List<ItemStack> generateArmors(ItemTier tier, String itemTag, GearLevel gearLevel) {
        double bonusPercent = tier.getBonusPercent();
        double chanceToGetEachStat = tier.getChanceToGetEachStat();
        int minNumberofStats = tier.getMinNumberOfStats();
        int minStatValue = gearLevel.getMinStatValue();
        int maxStatValue = gearLevel.getMaxStatValue();

        List<ItemStack> temp = new ArrayList<>();

        if (gearLevel.equals(GearLevel.ZERO) || gearLevel.equals(GearLevel.ONE)) {
            temp.add(Boots.get(RPGClass.NO_CLASS, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Chestplates.get(RPGClass.NO_CLASS, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Helmets.get(RPGClass.NO_CLASS, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Leggings.get(RPGClass.NO_CLASS, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
        } else {
            temp.add(Boots.get(RPGClass.MONK, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Chestplates.get(RPGClass.MONK, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Helmets.get(RPGClass.MONK, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Leggings.get(RPGClass.MONK, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));

            temp.add(Boots.get(RPGClass.KNIGHT, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Chestplates.get(RPGClass.KNIGHT, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Helmets.get(RPGClass.KNIGHT, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Leggings.get(RPGClass.KNIGHT, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Shields.get(RPGClass.KNIGHT, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));

            temp.add(Boots.get(RPGClass.MAGE, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Chestplates.get(RPGClass.MAGE, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Helmets.get(RPGClass.MAGE, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Leggings.get(RPGClass.MAGE, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));

            temp.add(Boots.get(RPGClass.PALADIN, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Chestplates.get(RPGClass.PALADIN, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Helmets.get(RPGClass.PALADIN, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Leggings.get(RPGClass.PALADIN, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Shields.get(RPGClass.PALADIN, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));

            temp.add(Boots.get(RPGClass.NINJA, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Chestplates.get(RPGClass.NINJA, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Helmets.get(RPGClass.NINJA, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Leggings.get(RPGClass.NINJA, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));

            temp.add(Boots.get(RPGClass.ARCHER, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Chestplates.get(RPGClass.ARCHER, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Helmets.get(RPGClass.ARCHER, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Leggings.get(RPGClass.ARCHER, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));

            temp.add(Boots.get(RPGClass.WARRIOR, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Chestplates.get(RPGClass.WARRIOR, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Helmets.get(RPGClass.WARRIOR, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
            temp.add(Leggings.get(RPGClass.WARRIOR, gearLevel.getArmorNo(), tier, itemTag, bonusPercent, minStatValue, maxStatValue, chanceToGetEachStat, minNumberofStats));
        }

        return temp;
    }

    public static List<ItemStack> generatePotions(ItemTier tier, String itemTag, GearLevel gearLevel) {
        int itemTier = gearLevel.getMinLevel();

        List<ItemStack> temp = new ArrayList<>();

        temp.add(Potions.getItemStack(PotionType.HEALTH, itemTier));
        temp.add(Potions.getItemStack(PotionType.MANA, itemTier));
        temp.add(Potions.getItemStack(PotionType.HYBRID, itemTier));
        temp.add(Potions.getItemStack(PotionType.REGENERATION, itemTier));

        return temp;
    }

    public static List<ItemStack> generateBuffScrolls(ItemTier tier, String itemTag, GearLevel gearLevel) {
        int itemTier = gearLevel.getMinLevel();

        List<ItemStack> temp = new ArrayList<>();

        temp.add(BuffScrolls.getItemStack(BuffType.PHYSICAL_DAMAGE, itemTier));
        temp.add(BuffScrolls.getItemStack(BuffType.PHYSICAL_DEFENSE, itemTier));
        temp.add(BuffScrolls.getItemStack(BuffType.MAGICAL_DAMAGE, itemTier));
        temp.add(BuffScrolls.getItemStack(BuffType.MAGICAL_DEFENSE, itemTier));

        return temp;
    }
}
