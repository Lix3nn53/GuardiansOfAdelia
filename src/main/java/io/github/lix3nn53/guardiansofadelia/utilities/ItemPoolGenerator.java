package io.github.lix3nn53.guardiansofadelia.utilities;

import io.github.lix3nn53.guardiansofadelia.Items.Consumable;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ShieldGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.Items.list.Eggs;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorManager;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorSlot;
import io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems.PassiveManager;
import io.github.lix3nn53.guardiansofadelia.Items.list.shields.ShieldManager;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.WeaponManager;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.PetSkin;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemPoolGenerator {

    public static List<ItemStack> generateWeapons(ItemTier tier, String itemTag, int gearLevel, int itemIndex) {
        List<ItemStack> temp = new ArrayList<>();

        for (WeaponGearType weaponGearType : WeaponGearType.values()) {
            ItemStack itemStack = WeaponManager.get(weaponGearType, gearLevel, itemIndex, tier, itemTag, false, null);
            temp.add(itemStack);
        }

        return temp;
    }

    public static List<ItemStack> generatePassives(ItemTier tier, String itemTag, int gearLevel, int itemIndex) {
        List<ItemStack> temp = new ArrayList<>();

        for (RPGSlotType rpgSlotType : RPGSlotType.values()) {
            if (!(rpgSlotType.equals(RPGSlotType.PARROT) || rpgSlotType.equals(RPGSlotType.EARRING)
                    || rpgSlotType.equals(RPGSlotType.NECKLACE) || rpgSlotType.equals(RPGSlotType.GLOVE)
                    || rpgSlotType.equals(RPGSlotType.RING))) {
                continue;
            }
            ItemStack itemStack = PassiveManager.get(gearLevel, itemIndex, rpgSlotType, tier, itemTag, false);

            temp.add(itemStack);
        }

        return temp;
    }

    public static List<ItemStack> generateArmors(ItemTier tier, String itemTag, int gearLevel, int itemIndex) {
        List<ItemStack> temp = new ArrayList<>();

        for (ArmorGearType armorGearType : ArmorGearType.values()) {
            for (ArmorSlot armorSlot : ArmorSlot.values()) {
                ItemStack itemStack = ArmorManager.get(armorSlot, armorGearType, gearLevel, itemIndex, tier, itemTag, false, null);

                temp.add(itemStack);
            }
        }

        for (ShieldGearType shieldGearType : ShieldGearType.values()) {
            ItemStack itemStack = ShieldManager.get(shieldGearType, gearLevel, itemIndex, tier, itemTag, false, null);

            temp.add(itemStack);
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
        List<ItemStack> temp = new ArrayList<>();

        for (WeaponGearType weaponGearType : WeaponGearType.values()) {
            if (!weaponGearType.isMelee()) continue;
            ItemStack itemStack = WeaponManager.get(weaponGearType, gearLevel, itemIndex, tier, itemTag, true, null);
            temp.add(itemStack);
        }

        return temp;
    }

    public static List<ItemStack> generateRangedWeaponsNoStats(ItemTier tier, String itemTag, int gearLevel, int itemIndex) {
        List<ItemStack> temp = new ArrayList<>();

        for (WeaponGearType weaponGearType : WeaponGearType.values()) {
            if (weaponGearType.isMelee()) continue;
            ItemStack itemStack = WeaponManager.get(weaponGearType, gearLevel, itemIndex, tier, itemTag, true, null);
            temp.add(itemStack);
        }

        return temp;
    }

    public static List<ItemStack> generateHeavyArmorsNoStats(ItemTier tier, String itemTag, int gearLevel, int itemIndex) {
        List<ItemStack> temp = new ArrayList<>();

        for (ArmorGearType armorGearType : ArmorGearType.values()) {
            for (ArmorSlot armorSlot : ArmorSlot.values()) {
                if (!armorGearType.isHeavy()) continue;
                ItemStack itemStack = ArmorManager.get(armorSlot, armorGearType, gearLevel, itemIndex, tier, itemTag, true, null);

                temp.add(itemStack);
            }
        }

        for (ShieldGearType shieldGearType : ShieldGearType.values()) {
            ItemStack itemStack = ShieldManager.get(shieldGearType, gearLevel, itemIndex, tier, itemTag, true, null);

            temp.add(itemStack);
        }

        return temp;
    }

    public static List<ItemStack> generateLightArmorsNoStats(ItemTier tier, String itemTag, int gearLevel, int itemIndex) {
        List<ItemStack> temp = new ArrayList<>();

        for (ArmorGearType armorGearType : ArmorGearType.values()) {
            for (ArmorSlot armorSlot : ArmorSlot.values()) {
                if (armorGearType.isHeavy()) continue;
                ItemStack itemStack = ArmorManager.get(armorSlot, armorGearType, gearLevel, itemIndex, tier, itemTag, true, null);

                temp.add(itemStack);
            }
        }

        return temp;
    }

    public static List<ItemStack> generatePassivesNoStats(ItemTier tier, String itemTag, int gearLevel, int itemIndex) {
        List<ItemStack> temp = new ArrayList<>();

        for (RPGSlotType rpgSlotType : RPGSlotType.values()) {
            if (!(rpgSlotType.equals(RPGSlotType.PARROT) || rpgSlotType.equals(RPGSlotType.EARRING)
                    || rpgSlotType.equals(RPGSlotType.NECKLACE) || rpgSlotType.equals(RPGSlotType.GLOVE)
                    || rpgSlotType.equals(RPGSlotType.RING))) {
                continue;
            }
            ItemStack itemStack = PassiveManager.get(gearLevel, itemIndex, rpgSlotType, tier, itemTag, true);

            temp.add(itemStack);
        }

        return temp;
    }

    public static List<ItemStack> generateEggs(int gearLevel) {
        List<ItemStack> temp = new ArrayList<>();

        for (String key : Eggs.getKeys()) {
            // Check if skin
            for (PetSkin c : PetSkin.values()) {
                if (c.name().equals(key)) {
                }
            }
            temp.add(Eggs.get(key, gearLevel, 1));
        }

        return temp;
    }
}
