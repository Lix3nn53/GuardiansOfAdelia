package io.github.lix3nn53.guardiansofadelia.utilities;

import io.github.lix3nn53.guardiansofadelia.Items.Consumable;
import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
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
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemPoolGenerator {

    public static List<ItemStack> generateWeapons(ItemTier tier, GearLevel gearLevel, String gearSet, boolean melee, boolean noStats) {
        List<ItemStack> temp = new ArrayList<>();

        for (WeaponGearType weaponGearType : WeaponGearType.values()) {
            boolean isMelee = weaponGearType.isMelee();
            if (melee != isMelee) continue;
            List<ItemStack> itemStack = WeaponManager.get(weaponGearType, gearLevel, tier, noStats, gearSet);
            temp.addAll(itemStack);
        }

        return temp;
    }

    public static List<ItemStack> generatePassives(ItemTier tier, GearLevel gearLevel, String gearSet, boolean noStats) {
        List<ItemStack> temp = new ArrayList<>();

        for (RPGSlotType rpgSlotType : RPGSlotType.values()) {
            if (!(rpgSlotType.equals(RPGSlotType.PARROT) || rpgSlotType.equals(RPGSlotType.EARRING)
                    || rpgSlotType.equals(RPGSlotType.NECKLACE) || rpgSlotType.equals(RPGSlotType.GLOVE)
                    || rpgSlotType.equals(RPGSlotType.RING))) {
                continue;
            }
            List<ItemStack> itemStack = PassiveManager.get(gearLevel, rpgSlotType, tier, noStats, gearSet);

            temp.addAll(itemStack);
        }

        return temp;
    }

    public static List<ItemStack> generateArmors(ItemTier tier, GearLevel gearLevel, String gearSet, boolean heavy, boolean noStats) {
        List<ItemStack> temp = new ArrayList<>();

        for (ArmorGearType armorGearType : ArmorGearType.values()) {
            boolean isHeavy = armorGearType.isHeavy();
            if (heavy != isHeavy) continue;
            for (ArmorSlot armorSlot : ArmorSlot.values()) {
                List<ItemStack> itemStack = ArmorManager.get(armorSlot, armorGearType, gearLevel, tier, noStats, gearSet);

                temp.addAll(itemStack);
            }
        }

        if (heavy) {
            for (ShieldGearType shieldGearType : ShieldGearType.values()) {
                List<ItemStack> itemStack = ShieldManager.get(shieldGearType, gearLevel, tier, false, gearSet);

                temp.addAll(itemStack);
            }
        }

        return temp;
    }

    public static List<ItemStack> generatePotions(GearLevel gearLevel) {
        int potionLevel = gearLevel.ordinal() + 1;

        List<ItemStack> temp = new ArrayList<>();

        temp.add(Consumable.POTION_INSTANT_HEALTH.getItemStack(potionLevel, 10));
        temp.add(Consumable.POTION_INSTANT_MANA.getItemStack(potionLevel, 10));
        temp.add(Consumable.POTION_INSTANT_HYBRID.getItemStack(potionLevel, 10));
        temp.add(Consumable.POTION_REGENERATION_HEALTH.getItemStack(potionLevel, 10));

        return temp;
    }

    public static List<ItemStack> generateFoods(GearLevel gearLevel) {
        int potionLevel = gearLevel.ordinal() + 1;

        List<ItemStack> temp = new ArrayList<>();

        temp.add(Consumable.BUFF_ELEMENT_DAMAGE.getItemStack(potionLevel, 10));
        temp.add(Consumable.BUFF_ELEMENT_DEFENSE.getItemStack(potionLevel, 10));

        return temp;
    }

    public static List<ItemStack> generateEggs(GearLevel gearLevel) {
        List<ItemStack> temp = new ArrayList<>();

        for (String key : Eggs.getKeys()) {
            // Check if skin
            // TODO check if skin?
            /*for (PetSkin c : PetSkin.values()) {
                if (c.name().equals(key)) {
                }
            }*/
            temp.add(Eggs.get(key, gearLevel, 1));
        }

        return temp;
    }
}
