package io.github.lix3nn53.guardiansofadelia.creatures.drops;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorType;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.Armors;
import io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems.PassiveItemList;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.Weapons;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.BoostPremiumManager;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.BoostPremium;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MobDropGenerator {

    public static List<ItemStack> getDrops(Entity entity) {
        List<ItemStack> drops = new ArrayList<>();

        int dropTableNumber = getDropTableNumber(entity);
        if (dropTableNumber < 0 || dropTableNumber >= 10) return drops;

        double random = Math.random();
        double dropRate = 0.1;

        if (BoostPremiumManager.isBoostActive(BoostPremium.LOOT)) {
            dropRate += 0.1;
        }

        if (random < dropRate) {
            GearLevel gearLevel = GearLevel.ZERO;

            if (dropTableNumber == 1) {
                gearLevel = GearLevel.ONE;
            } else if (dropTableNumber == 2) {
                gearLevel = GearLevel.TWO;
            } else if (dropTableNumber == 3) {
                gearLevel = GearLevel.THREE;
            } else if (dropTableNumber == 4) {
                gearLevel = GearLevel.FOUR;
            } else if (dropTableNumber == 5) {
                gearLevel = GearLevel.FIVE;
            } else if (dropTableNumber == 6) {
                gearLevel = GearLevel.SIX;
            } else if (dropTableNumber == 7) {
                gearLevel = GearLevel.SEVEN;
            } else if (dropTableNumber == 8) {
                gearLevel = GearLevel.EIGHT;
            } else if (dropTableNumber == 9) {
                gearLevel = GearLevel.NINE;
            }

            ItemTier tier = ItemTier.RARE;
            int minNumberOfStats = tier.getMinNumberOfStatsNormal();
            int minStatValue = gearLevel.getMinStatValue();
            int maxStatValue = gearLevel.getMaxStatValue();

            Random rand = new Random();

            // Obtain a number between [1 - 7].
            int rpgClassRandom = rand.nextInt(7);
            RPGClass rpgClass = RPGClass.values()[rpgClassRandom + 1]; //+1 to ignore NO_CLASS

            // Obtain a number between [0 - 2].
            int dropType = rand.nextInt(3);

            if (dropType == 0) {
                ItemStack droppedItem = Weapons.getWeapon(RPGClass.WARRIOR, gearLevel.getWeaponAndPassiveNo(), tier, "", minStatValue, maxStatValue, minNumberOfStats);
                drops.add(droppedItem);
            } else if (dropType == 1) {
                if (gearLevel.equals(GearLevel.ZERO) || gearLevel.equals(GearLevel.ONE)) {
                    rpgClass = RPGClass.NO_CLASS;
                }
                // Obtain a number between [0 - 3].
                int armorTypeRandom = rand.nextInt(4);
                ArmorType armorType = ArmorType.values()[armorTypeRandom];
                ItemStack droppedItem = Armors.getArmor(armorType, rpgClass, gearLevel.getArmorNo(), tier, "", minStatValue, maxStatValue, minNumberOfStats);
                drops.add(droppedItem);
            } else if (dropType == 2) {
                minNumberOfStats = tier.getMinNumberOfStatsPassive();
                ItemStack droppedItem = PassiveItemList.get(gearLevel.getWeaponAndPassiveNo(), RPGSlotType.RING, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                drops.add(droppedItem);
            }
        }
        return drops;
    }

    private static int getDropTableNumber(Entity entity) {
        if (PersistentDataContainerUtil.hasInteger(entity, "dropTableNumber")) {
            return PersistentDataContainerUtil.getInteger(entity, "dropTableNumber");
        }
        return -1;
    }
}
