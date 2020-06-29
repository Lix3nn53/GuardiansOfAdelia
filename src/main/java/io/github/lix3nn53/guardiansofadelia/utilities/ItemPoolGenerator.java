package io.github.lix3nn53.guardiansofadelia.utilities;

import io.github.lix3nn53.guardiansofadelia.Items.Consumable;
import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.list.eggs.Companions;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.Companion;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemPoolGenerator {

    public static List<ItemStack> generateWeapons(ItemTier tier, String itemTag, int gearLevel, int itemIndex) {
        int minNumberofStats = tier.getMinNumberOfStatsNormal();
        int minStatValue = GearLevel.getMinStatValue(gearLevel);
        int maxStatValue = GearLevel.getMaxStatValue(gearLevel);

        //7*3 weapon
        List<ItemStack> temp = new ArrayList<>();

        /*temp.add(Weapons.getWeapon(RPGClass.WARRIOR, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.ARCHER, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.ROGUE, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.PALADIN, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.MAGE, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.KNIGHT, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.MONK, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.HUNTER, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));*/

        return temp;
    }

    public static List<ItemStack> generatePassives(ItemTier tier, String itemTag, int gearLevel, int itemIndex) {
        int minNumberofStats = tier.getMinNumberOfStatsPassive();
        int minStatValue = GearLevel.getMinStatValue(gearLevel);
        int maxStatValue = GearLevel.getMaxStatValue(gearLevel);

        List<ItemStack> temp = new ArrayList<>();

        /*temp.add(PassiveManager.get(gearLevel, itemIndex, RPGSlotType.RING, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(PassiveManager.get(gearLevel, itemIndex, RPGSlotType.GLOVE, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(PassiveManager.get(gearLevel, itemIndex, RPGSlotType.NECKLACE, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(PassiveManager.get(gearLevel, itemIndex, RPGSlotType.EARRING, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(PassiveManager.get(gearLevel, itemIndex, RPGSlotType.PARROT, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));*/

        return temp;
    }

    public static List<ItemStack> generateArmors(ItemTier tier, String itemTag, int gearLevel, int itemIndex) {
        int minNumberofStats = tier.getMinNumberOfStatsNormal();
        int minStatValue = GearLevel.getMinStatValue(gearLevel);
        int maxStatValue = GearLevel.getMaxStatValue(gearLevel);

        List<ItemStack> temp = new ArrayList<>();

        for (ArmorGearType armorGearType : ArmorGearType.values()) {
            /*temp.add(Armors.getArmor(ArmorType.HELMET, rpgClass, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            temp.add(Armors.getArmor(ArmorType.CHESTPLATE, rpgClass, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            temp.add(Armors.getArmor(ArmorType.LEGGINGS, rpgClass, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
            temp.add(Armors.getArmor(ArmorType.BOOTS, rpgClass, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));*/
        }

        return temp;
    }

    public static List<ItemStack> generatePotions(int potionLevel) {
        List<ItemStack> temp = new ArrayList<>();

        temp.add(Consumable.POTION_INSTANT_HEALTH.getItemStack(potionLevel, 10));
        temp.add(Consumable.POTION_INSTANT_MANA.getItemStack(potionLevel, 10));
        temp.add(Consumable.POTION_INSTANT_HYBRID.getItemStack(potionLevel, 10));
        temp.add(Consumable.POTION_REGENERATION_HEALTH.getItemStack(potionLevel, 10));

        return temp;
    }

    public static List<ItemStack> generateFoods(int potionLevel) {
        List<ItemStack> temp = new ArrayList<>();

        temp.add(Consumable.BUFF_PHYSICAL_DAMAGE.getItemStack(potionLevel, 10));
        temp.add(Consumable.BUFF_PHYSICAL_DEFENSE.getItemStack(potionLevel, 10));
        temp.add(Consumable.BUFF_MAGICAL_DAMAGE.getItemStack(potionLevel, 10));
        temp.add(Consumable.BUFF_MAGICAL_DEFENSE.getItemStack(potionLevel, 10));

        return temp;
    }

    public static List<ItemStack> generateMeleeWeaponsNoStats(ItemTier tier, String itemTag, int gearLevel, int itemIndex) {
        int minNumberofStats = 0;
        int minStatValue = 0;
        int maxStatValue = 0;

        //7*3 weapon
        List<ItemStack> temp = new ArrayList<>();

        /*temp.add(Weapons.getWeapon(RPGClass.WARRIOR, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.ROGUE, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.PALADIN, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.KNIGHT, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));*/

        return temp;
    }

    public static List<ItemStack> generateRangedWeaponsNoStats(ItemTier tier, String itemTag, int gearLevel, int itemIndex) {
        int minNumberofStats = 0;
        int minStatValue = 0;
        int maxStatValue = 0;

        //7*3 weapon
        List<ItemStack> temp = new ArrayList<>();

        /*temp.add(Weapons.getWeapon(RPGClass.ARCHER, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.MAGE, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.MONK, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Weapons.getWeapon(RPGClass.HUNTER, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));*/

        return temp;
    }

    public static List<ItemStack> generateHeavyArmorsNoStats(ItemTier tier, String itemTag, int gearLevel, int itemIndex) {
        int minNumberofStats = 0;
        int minStatValue = 0;
        int maxStatValue = 0;

        List<ItemStack> temp = new ArrayList<>();

       /* temp.add(Armors.getArmor(ArmorType.HELMET, RPGClass.KNIGHT, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Armors.getArmor(ArmorType.CHESTPLATE, RPGClass.KNIGHT, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Armors.getArmor(ArmorType.LEGGINGS, RPGClass.KNIGHT, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Armors.getArmor(ArmorType.BOOTS, RPGClass.KNIGHT, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(ShieldManager.get(RPGClass.KNIGHT, gearLevel, itemIndex, tier, itemTag, tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberofStats));

        temp.add(Armors.getArmor(ArmorType.HELMET, RPGClass.PALADIN, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Armors.getArmor(ArmorType.CHESTPLATE, RPGClass.PALADIN, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Armors.getArmor(ArmorType.LEGGINGS, RPGClass.PALADIN, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Armors.getArmor(ArmorType.BOOTS, RPGClass.PALADIN, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(ShieldManager.get(RPGClass.PALADIN, gearLevel, itemIndex, tier, itemTag, tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberofStats));

        temp.add(Armors.getArmor(ArmorType.HELMET, RPGClass.WARRIOR, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Armors.getArmor(ArmorType.CHESTPLATE, RPGClass.WARRIOR, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Armors.getArmor(ArmorType.LEGGINGS, RPGClass.WARRIOR, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Armors.getArmor(ArmorType.BOOTS, RPGClass.WARRIOR, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));

        temp.add(Armors.getArmor(ArmorType.HELMET, RPGClass.MONK, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Armors.getArmor(ArmorType.CHESTPLATE, RPGClass.MONK, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Armors.getArmor(ArmorType.LEGGINGS, RPGClass.MONK, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Armors.getArmor(ArmorType.BOOTS, RPGClass.MONK, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));*/

        return temp;
    }

    public static List<ItemStack> generateLightArmorsNoStats(ItemTier tier, String itemTag, int gearLevel, int itemIndex) {
        int minNumberofStats = 0;
        int minStatValue = 0;
        int maxStatValue = 0;

        List<ItemStack> temp = new ArrayList<>();

        /*temp.add(Armors.getArmor(ArmorType.HELMET, RPGClass.ARCHER, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Armors.getArmor(ArmorType.CHESTPLATE, RPGClass.ARCHER, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Armors.getArmor(ArmorType.LEGGINGS, RPGClass.ARCHER, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Armors.getArmor(ArmorType.BOOTS, RPGClass.ARCHER, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));

        temp.add(Armors.getArmor(ArmorType.HELMET, RPGClass.HUNTER, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Armors.getArmor(ArmorType.CHESTPLATE, RPGClass.HUNTER, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Armors.getArmor(ArmorType.LEGGINGS, RPGClass.HUNTER, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Armors.getArmor(ArmorType.BOOTS, RPGClass.HUNTER, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));

        temp.add(Armors.getArmor(ArmorType.HELMET, RPGClass.MAGE, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Armors.getArmor(ArmorType.CHESTPLATE, RPGClass.MAGE, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Armors.getArmor(ArmorType.LEGGINGS, RPGClass.MAGE, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Armors.getArmor(ArmorType.BOOTS, RPGClass.MAGE, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));

        temp.add(Armors.getArmor(ArmorType.HELMET, RPGClass.ROGUE, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Armors.getArmor(ArmorType.CHESTPLATE, RPGClass.ROGUE, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Armors.getArmor(ArmorType.LEGGINGS, RPGClass.ROGUE, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(Armors.getArmor(ArmorType.BOOTS, RPGClass.ROGUE, gearLevel, itemIndex, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));*/

        return temp;
    }

    public static List<ItemStack> generatePassivesNoStats(ItemTier tier, String itemTag, int gearLevel, int itemIndex) {
        int minNumberofStats = 0;
        int minStatValue = 0;
        int maxStatValue = 0;

        List<ItemStack> temp = new ArrayList<>();

        /*temp.add(PassiveManager.get(gearLevel, itemIndex, RPGSlotType.RING, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(PassiveManager.get(gearLevel, itemIndex, RPGSlotType.GLOVE, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(PassiveManager.get(gearLevel, itemIndex, RPGSlotType.NECKLACE, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(PassiveManager.get(gearLevel, itemIndex, RPGSlotType.EARRING, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));
        temp.add(PassiveManager.get(gearLevel, itemIndex, RPGSlotType.PARROT, tier, itemTag, minStatValue, maxStatValue, minNumberofStats));*/

        return temp;
    }

    public static List<ItemStack> generateEggs(int gearLevel) {
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
