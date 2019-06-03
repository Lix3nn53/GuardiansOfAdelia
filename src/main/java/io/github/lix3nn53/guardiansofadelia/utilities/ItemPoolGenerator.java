package io.github.lix3nn53.guardiansofadelia.utilities;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.consumables.Consumable;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorType;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.Armors;
import io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems.PassiveItemList;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.Weapons;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemPoolGenerator {

    public static List<ItemStack> generateWeapons(ItemTier tier, String itemTag, GearLevel gearLevel) {
        int minNumberofStats = tier.getMinNumberOfStats();
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
        int minNumberofStats = tier.getMinNumberOfStats();
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
        int minNumberofStats = tier.getMinNumberOfStats();
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

    public static List<ItemStack> generateConsumables(int potionLevel) {
        List<ItemStack> temp = new ArrayList<>();

        for (Consumable consumable : Consumable.values()) {
            temp.add(consumable.getItemStack(potionLevel));
        }

        return temp;
    }

    public static List<ItemStack> generateConsumables(String tag, int potionLevel) {
        List<ItemStack> temp = new ArrayList<>();

        for (Consumable consumable : Consumable.values()) {
            temp.add(consumable.getItemStack(tag, potionLevel));
        }

        return temp;
    }
}
