package io.github.lix3nn53.guardiansofadelia.utilities;

import io.github.lix3nn53.guardiansofadelia.Items.Consumable;
import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorType;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.Armors;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.Shields;
import io.github.lix3nn53.guardiansofadelia.Items.list.eggs.Companions;
import io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems.PassiveItemList;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.Weapons;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.Companion;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemPoolGenerator {

    public static List<ItemStack> generateWeapons(ItemTier tier, String itemTag, GearLevel gearLevel) {
        int minNumberofStats = tier.getMinNumberOfStatsNormal();
        int minStatValue = gearLevel.getMinStatValue();
        int maxStatValue = gearLevel.getMaxStatValue();

        //7*3 weapon
        List<ItemStack> temp = new ArrayList<>();

        temp.add(Weapons.getWeapon(RPGClass.WARRIOR, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.ARCHER, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.ROGUE, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.PALADIN, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.MAGE, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.KNIGHT, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.MONK, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.HUNTER, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));

        return temp;
    }

    public static List<ItemStack> generatePassives(ItemTier tier, String itemTag, GearLevel gearLevel) {
        int minNumberofStats = tier.getMinNumberOfStatsPassive();
        int minStatValue = gearLevel.getMinStatValue();
        int maxStatValue = gearLevel.getMaxStatValue();

        List<ItemStack> temp = new ArrayList<>();

        temp.add(PassiveItemList.get(gearLevel.getWeaponAndPassiveNo(), RPGSlotType.RING, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(PassiveItemList.get(gearLevel.getWeaponAndPassiveNo(), RPGSlotType.GLOVE, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(PassiveItemList.get(gearLevel.getWeaponAndPassiveNo(), RPGSlotType.NECKLACE, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(PassiveItemList.get(gearLevel.getWeaponAndPassiveNo(), RPGSlotType.EARRING, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(PassiveItemList.get(gearLevel.getWeaponAndPassiveNo(), RPGSlotType.PARROT, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));

        return temp;
    }

    public static List<ItemStack> generateArmors(ItemTier tier, String itemTag, GearLevel gearLevel) {
        int minNumberofStats = tier.getMinNumberOfStatsNormal();
        int minStatValue = gearLevel.getMinStatValue();
        int maxStatValue = gearLevel.getMaxStatValue();

        List<ItemStack> temp = new ArrayList<>();

        if (gearLevel.equals(GearLevel.ZERO) || gearLevel.equals(GearLevel.ONE)) {
            temp.add(Armors.getArmor(ArmorType.HELMET, RPGClass.NO_CLASS, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            temp.add(Armors.getArmor(ArmorType.CHESTPLATE, RPGClass.NO_CLASS, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            temp.add(Armors.getArmor(ArmorType.LEGGINGS, RPGClass.NO_CLASS, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            temp.add(Armors.getArmor(ArmorType.BOOTS, RPGClass.NO_CLASS, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        } else {
            for (RPGClass rpgClass : RPGClass.values()) {
                if (rpgClass.equals(RPGClass.NO_CLASS)) {
                    continue;
                }
                temp.add(Armors.getArmor(ArmorType.HELMET, rpgClass, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
                temp.add(Armors.getArmor(ArmorType.CHESTPLATE, rpgClass, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
                temp.add(Armors.getArmor(ArmorType.LEGGINGS, rpgClass, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
                temp.add(Armors.getArmor(ArmorType.BOOTS, rpgClass, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            }
        }

        return temp;
    }

    public static List<ItemStack> generatePotions(int potionLevel) {
        List<ItemStack> temp = new ArrayList<>();

        temp.add(Consumable.POTION_INSTANT_HEALTH.getItemStack(potionLevel, 3));
        temp.add(Consumable.POTION_INSTANT_MANA.getItemStack(potionLevel, 3));
        temp.add(Consumable.POTION_INSTANT_HYBRID.getItemStack(potionLevel, 3));
        temp.add(Consumable.POTION_REGENERATION_HEALTH.getItemStack(potionLevel, 3));

        return temp;
    }

    public static List<ItemStack> generateFoods(int potionLevel) {
        List<ItemStack> temp = new ArrayList<>();

        temp.add(Consumable.BUFF_PHYSICAL_DAMAGE.getItemStack(potionLevel, 3));
        temp.add(Consumable.BUFF_PHYSICAL_DEFENSE.getItemStack(potionLevel, 3));
        temp.add(Consumable.BUFF_MAGICAL_DAMAGE.getItemStack(potionLevel, 3));
        temp.add(Consumable.BUFF_MAGICAL_DEFENSE.getItemStack(potionLevel, 3));

        return temp;
    }

    public static List<ItemStack> generateMeleeWeaponsNoStats(ItemTier tier, String itemTag, GearLevel gearLevel) {
        int minNumberofStats = 0;
        int minStatValue = 0;
        int maxStatValue = 0;

        //7*3 weapon
        List<ItemStack> temp = new ArrayList<>();

        temp.add(Weapons.getWeapon(RPGClass.WARRIOR, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.ROGUE, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.PALADIN, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.KNIGHT, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));

        return temp;
    }

    public static List<ItemStack> generateRangedWeaponsNoStats(ItemTier tier, String itemTag, GearLevel gearLevel) {
        int minNumberofStats = 0;
        int minStatValue = 0;
        int maxStatValue = 0;

        //7*3 weapon
        List<ItemStack> temp = new ArrayList<>();

        temp.add(Weapons.getWeapon(RPGClass.ARCHER, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.MAGE, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.MONK, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.HUNTER, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));

        return temp;
    }

    public static List<ItemStack> generateHeavyArmorsNoStats(ItemTier tier, String itemTag, GearLevel gearLevel) {
        int minNumberofStats = 0;
        int minStatValue = 0;
        int maxStatValue = 0;

        List<ItemStack> temp = new ArrayList<>();

        if (!(gearLevel.equals(GearLevel.ZERO) || gearLevel.equals(GearLevel.ONE))) {
            if (gearLevel.equals(GearLevel.NINE)) {
                temp.add(Shields.get(RPGClass.KNIGHT, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberofStats));
                temp.add(Shields.get(RPGClass.PALADIN, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberofStats));
            } else {
                temp.add(Armors.getArmor(ArmorType.HELMET, RPGClass.KNIGHT, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
                temp.add(Armors.getArmor(ArmorType.CHESTPLATE, RPGClass.KNIGHT, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
                temp.add(Armors.getArmor(ArmorType.LEGGINGS, RPGClass.KNIGHT, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
                temp.add(Armors.getArmor(ArmorType.BOOTS, RPGClass.KNIGHT, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
                temp.add(Shields.get(RPGClass.KNIGHT, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberofStats));

                temp.add(Armors.getArmor(ArmorType.HELMET, RPGClass.PALADIN, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
                temp.add(Armors.getArmor(ArmorType.CHESTPLATE, RPGClass.PALADIN, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
                temp.add(Armors.getArmor(ArmorType.LEGGINGS, RPGClass.PALADIN, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
                temp.add(Armors.getArmor(ArmorType.BOOTS, RPGClass.PALADIN, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
                temp.add(Shields.get(RPGClass.PALADIN, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberofStats));

                temp.add(Armors.getArmor(ArmorType.HELMET, RPGClass.WARRIOR, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
                temp.add(Armors.getArmor(ArmorType.CHESTPLATE, RPGClass.WARRIOR, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
                temp.add(Armors.getArmor(ArmorType.LEGGINGS, RPGClass.WARRIOR, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
                temp.add(Armors.getArmor(ArmorType.BOOTS, RPGClass.WARRIOR, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));

                temp.add(Armors.getArmor(ArmorType.HELMET, RPGClass.MONK, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
                temp.add(Armors.getArmor(ArmorType.CHESTPLATE, RPGClass.MONK, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
                temp.add(Armors.getArmor(ArmorType.LEGGINGS, RPGClass.MONK, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
                temp.add(Armors.getArmor(ArmorType.BOOTS, RPGClass.MONK, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            }
        } else {
            temp.add(Armors.getArmor(ArmorType.HELMET, RPGClass.NO_CLASS, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            temp.add(Armors.getArmor(ArmorType.CHESTPLATE, RPGClass.NO_CLASS, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            temp.add(Armors.getArmor(ArmorType.LEGGINGS, RPGClass.NO_CLASS, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            temp.add(Armors.getArmor(ArmorType.BOOTS, RPGClass.NO_CLASS, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            temp.add(Shields.get(RPGClass.KNIGHT, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberofStats));
            temp.add(Shields.get(RPGClass.PALADIN, gearLevel.getWeaponAndPassiveNo(), tier, itemTag, tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberofStats));
        }

        return temp;
    }

    public static List<ItemStack> generateLightArmorsNoStats(ItemTier tier, String itemTag, GearLevel gearLevel) {
        int minNumberofStats = 0;
        int minStatValue = 0;
        int maxStatValue = 0;

        List<ItemStack> temp = new ArrayList<>();

        if (!(gearLevel.equals(GearLevel.ZERO) || gearLevel.equals(GearLevel.ONE) || gearLevel.equals(GearLevel.NINE))) {
            temp.add(Armors.getArmor(ArmorType.HELMET, RPGClass.ARCHER, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            temp.add(Armors.getArmor(ArmorType.CHESTPLATE, RPGClass.ARCHER, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            temp.add(Armors.getArmor(ArmorType.LEGGINGS, RPGClass.ARCHER, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            temp.add(Armors.getArmor(ArmorType.BOOTS, RPGClass.ARCHER, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));

            temp.add(Armors.getArmor(ArmorType.HELMET, RPGClass.HUNTER, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            temp.add(Armors.getArmor(ArmorType.CHESTPLATE, RPGClass.HUNTER, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            temp.add(Armors.getArmor(ArmorType.LEGGINGS, RPGClass.HUNTER, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            temp.add(Armors.getArmor(ArmorType.BOOTS, RPGClass.HUNTER, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));

            temp.add(Armors.getArmor(ArmorType.HELMET, RPGClass.MAGE, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            temp.add(Armors.getArmor(ArmorType.CHESTPLATE, RPGClass.MAGE, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            temp.add(Armors.getArmor(ArmorType.LEGGINGS, RPGClass.MAGE, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            temp.add(Armors.getArmor(ArmorType.BOOTS, RPGClass.MAGE, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));

            temp.add(Armors.getArmor(ArmorType.HELMET, RPGClass.ROGUE, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            temp.add(Armors.getArmor(ArmorType.CHESTPLATE, RPGClass.ROGUE, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            temp.add(Armors.getArmor(ArmorType.LEGGINGS, RPGClass.ROGUE, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            temp.add(Armors.getArmor(ArmorType.BOOTS, RPGClass.ROGUE, gearLevel.getArmorNo(), tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        }

        return temp;
    }

    public static List<ItemStack> generatePassivesNoStats(ItemTier tier, String itemTag, GearLevel gearLevel) {
        int minNumberofStats = 0;
        int minStatValue = 0;
        int maxStatValue = 0;

        List<ItemStack> temp = new ArrayList<>();

        temp.add(PassiveItemList.get(gearLevel.getWeaponAndPassiveNo(), RPGSlotType.RING, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(PassiveItemList.get(gearLevel.getWeaponAndPassiveNo(), RPGSlotType.GLOVE, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(PassiveItemList.get(gearLevel.getWeaponAndPassiveNo(), RPGSlotType.NECKLACE, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(PassiveItemList.get(gearLevel.getWeaponAndPassiveNo(), RPGSlotType.EARRING, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(PassiveItemList.get(gearLevel.getWeaponAndPassiveNo(), RPGSlotType.PARROT, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));

        return temp;
    }

    public static List<ItemStack> generateEggs(GearLevel gearLevel) {
        List<ItemStack> temp = new ArrayList<>();

        for (Companion companion : Companion.values()) {
            if (companion.equals(Companion.BEE) || companion.equals(Companion.FOX_RED) || companion.equals(Companion.FOX_SNOW) || companion.equals(Companion.MINI_DRAGON)
                    || companion.equals(Companion.VEX) || companion.equals(Companion.ICE_CREAM)) continue;
            temp.add(Companions.get(companion, gearLevel));
        }

        /*TODO mount eggs are disabled, stay this way?
        for (Mount mount : Mount.values()) {
            temp.add(Mounts.get(mount, gearLevel));
        }*/

        return temp;
    }
}
