package io.github.lix3nn53.guardiansofadelia.creatures.drops;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
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

public class MobDropGenerator {

    private static final float DROP_RATE = 0.05f;

    public static List<ItemStack> getDrops(int entityLevel, boolean isDungeon) {
        List<ItemStack> drops = new ArrayList<>();

        float random = (float) Math.random();
        float dropRate = DROP_RATE;

        if (BoostPremiumManager.isBoostActive(BoostPremium.LOOT)) {
            dropRate = BoostPremium.LOOT.applyTo(dropRate);
        }

        if (isDungeon) {
            dropRate *= 1.2f;
        }

        if (random < dropRate) {
            GearLevel gearLevel = GearLevel.getGearLevel(entityLevel);

            //Tier
            float mysticTierChange = 0.05f;
            float rareTierChange = mysticTierChange + 0.4f;
            float tierRandom = (float) Math.random(); //Common or rare?

            ItemTier tier = ItemTier.COMMON;

            if (tierRandom < mysticTierChange) {
                tier = ItemTier.MYSTIC;
            } else if (tierRandom < rareTierChange) {
                tier = ItemTier.RARE;
            }

            // Obtain a number to select between weapon, <armor or shield> and passive
            int dropType = GuardiansOfAdelia.RANDOM.nextInt(3);

            if (dropType == 0) {
                WeaponGearType[] values = WeaponGearType.values();
                int gearRandom = GuardiansOfAdelia.RANDOM.nextInt(values.length);

                WeaponGearType gearType = values[gearRandom];

                List<ItemStack> pool = WeaponManager.getAll(gearType, gearLevel, tier, false, true);

                int i = GuardiansOfAdelia.RANDOM.nextInt(pool.size());
                ItemStack droppedItem = pool.get(i);

                drops.add(droppedItem);
            } else if (dropType == 1) {
                float shieldChance = 0.2f;
                float armorOrShieldRandom = (float) Math.random(); // armor or shield?

                if (armorOrShieldRandom < shieldChance) { // shield
                    ShieldGearType[] values = ShieldGearType.values();
                    int gearRandom = GuardiansOfAdelia.RANDOM.nextInt(values.length);

                    ShieldGearType gearType = values[gearRandom];

                    List<ItemStack> pool = ShieldManager.getAll(gearType, gearLevel, tier, false, true);

                    int i = GuardiansOfAdelia.RANDOM.nextInt(pool.size());
                    ItemStack droppedItem = pool.get(i);

                    drops.add(droppedItem);
                } else {
                    ArmorSlot[] armorSlots = ArmorSlot.values();
                    int armorTypeRandom = GuardiansOfAdelia.RANDOM.nextInt(armorSlots.length);
                    ArmorSlot armorSlot = armorSlots[armorTypeRandom];

                    ArmorGearType[] armorGearTypes = ArmorGearType.values();
                    int armorGearTypeRandom = GuardiansOfAdelia.RANDOM.nextInt(armorGearTypes.length);
                    ArmorGearType armorGearType = armorGearTypes[armorGearTypeRandom];

                    List<ItemStack> pool = ArmorManager.getAll(armorSlot, armorGearType, gearLevel, tier, false, true);

                    int i = GuardiansOfAdelia.RANDOM.nextInt(pool.size());
                    ItemStack droppedItem = pool.get(i);

                    drops.add(droppedItem);
                }
            } else { // dropType == 2
                int passiveType = GuardiansOfAdelia.RANDOM.nextInt(4);
                RPGSlotType rpgSlotType = RPGSlotType.values()[passiveType + 1]; // +1 to ignore parrot

                List<ItemStack> pool = PassiveManager.getAll(gearLevel, rpgSlotType, tier, false, true);

                int i = GuardiansOfAdelia.RANDOM.nextInt(pool.size());
                ItemStack droppedItem = pool.get(i);

                drops.add(droppedItem);
            }
        }
        return drops;
    }
}
