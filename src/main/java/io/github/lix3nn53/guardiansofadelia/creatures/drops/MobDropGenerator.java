package io.github.lix3nn53.guardiansofadelia.creatures.drops;

import io.github.lix3nn53.guardiansofadelia.bungeelistener.BoostPremiumManager;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.BoostPremium;
import io.github.lix3nn53.guardiansofadelia.items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ShieldGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.items.list.armors.ArmorManager;
import io.github.lix3nn53.guardiansofadelia.items.list.armors.ArmorSlot;
import io.github.lix3nn53.guardiansofadelia.items.list.passiveItems.PassiveManager;
import io.github.lix3nn53.guardiansofadelia.items.list.shields.ShieldManager;
import io.github.lix3nn53.guardiansofadelia.items.list.weapons.WeaponManager;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MobDropGenerator {

    private static final float DROP_RATE = 0.05f;

    public static List<ItemStack> getDrops(int entityLevel, boolean isDungeon) {
        List<ItemStack> drops = new ArrayList<>();

        double random = Math.random();
        float dropRate = DROP_RATE;

        if (BoostPremiumManager.isBoostActive(BoostPremium.LOOT)) {
            dropRate = (float) BoostPremium.LOOT.applyTo(dropRate);
        }

        if (isDungeon) {
            dropRate *= 1.2f;
        }

        if (random < dropRate) {
            GearLevel gearLevel = GearLevel.getGearLevel(entityLevel);

            //Tier
            double mysticTierChange = 0.05;
            double rareTierChange = mysticTierChange + 0.4;
            double tierRandom = Math.random(); //Common or rare?

            ItemTier tier = ItemTier.COMMON;

            if (tierRandom < mysticTierChange) {
                tier = ItemTier.MYSTIC;
            } else if (tierRandom < rareTierChange) {
                tier = ItemTier.RARE;
            }

            // Obtain a number to select between weapon, <armor or shield> and passive
            Random rand = new Random();
            int dropType = rand.nextInt(3);

            if (dropType == 0) {
                WeaponGearType[] values = WeaponGearType.values();
                int gearRandom = rand.nextInt(values.length);

                WeaponGearType gearType = values[gearRandom];

                List<ItemStack> pool = WeaponManager.get(gearType, gearLevel, tier, false, null);

                int i = rand.nextInt(pool.size());
                ItemStack droppedItem = pool.get(i);

                drops.add(droppedItem);
            } else if (dropType == 1) {
                double shieldChange = 0.2;
                double armorOrShieldRandom = Math.random(); // armor or shield?

                if (armorOrShieldRandom < shieldChange) { // shield
                    ShieldGearType[] values = ShieldGearType.values();
                    int gearRandom = rand.nextInt(values.length);

                    ShieldGearType gearType = values[gearRandom];

                    List<ItemStack> pool = ShieldManager.get(gearType, gearLevel, tier, false, null);

                    int i = rand.nextInt(pool.size());
                    ItemStack droppedItem = pool.get(i);

                    drops.add(droppedItem);
                } else {
                    ArmorSlot[] armorSlots = ArmorSlot.values();
                    int armorTypeRandom = rand.nextInt(armorSlots.length);
                    ArmorSlot armorSlot = armorSlots[armorTypeRandom];

                    ArmorGearType[] armorGearTypes = ArmorGearType.values();
                    int armorGearTypeRandom = rand.nextInt(armorGearTypes.length);
                    ArmorGearType armorGearType = armorGearTypes[armorGearTypeRandom];

                    List<ItemStack> pool = ArmorManager.get(armorSlot, armorGearType, gearLevel, tier, false, null);

                    int i = rand.nextInt(pool.size());
                    ItemStack droppedItem = pool.get(i);

                    drops.add(droppedItem);
                }
            } else { // dropType == 2
                int passiveType = rand.nextInt(4);
                RPGSlotType rpgSlotType = RPGSlotType.values()[passiveType + 1]; // +1 to ignore parrot

                List<ItemStack> pool = PassiveManager.get(gearLevel, rpgSlotType, tier, false, null);

                int i = rand.nextInt(pool.size());
                ItemStack droppedItem = pool.get(i);

                drops.add(droppedItem);
            }
        }
        return drops;
    }
}
