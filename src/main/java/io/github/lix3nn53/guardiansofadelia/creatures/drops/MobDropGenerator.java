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
        double dropRate = 0.06;

        if (BoostPremiumManager.isBoostActive(BoostPremium.LOOT)) {
            dropRate *= 2D;
        }

        if (random < dropRate) {
            int gearLevel = dropTableNumber;

            ItemTier tier = ItemTier.RARE;
            int minNumberOfStats = tier.getMinNumberOfStatsNormal();
            int minStatValue = GearLevel.getMinStatValue(gearLevel);
            int maxStatValue = GearLevel.getMaxStatValue(gearLevel);

            Random rand = new Random();

            // Obtain a number between [1 - 7].
            int rpgClassRandom = rand.nextInt(7);
            RPGClass rpgClass = RPGClass.values()[rpgClassRandom + 1]; //+1 to ignore NO_CLASS

            // Obtain a number between [0 - 2].
            int dropType = rand.nextInt(3);

            if (dropType == 0) {
                ItemStack droppedItem = Weapons.getWeapon(rpgClass, gearLevel, 0, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                drops.add(droppedItem);
            } else if (dropType == 1) {
                if (gearLevel < 3) {
                    rpgClass = RPGClass.NO_CLASS;
                }
                // Obtain a number between [0 - 3].
                int armorTypeRandom = rand.nextInt(4);
                ArmorType armorType = ArmorType.values()[armorTypeRandom];
                ItemStack droppedItem = Armors.getArmor(armorType, rpgClass, gearLevel, 0, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                drops.add(droppedItem);
            } else if (dropType == 2) {
                minNumberOfStats = tier.getMinNumberOfStatsPassive();

                int passiveType = rand.nextInt(4);
                RPGSlotType rpgSlotType = RPGSlotType.values()[passiveType + 1]; //+1 to ignore parrot

                ItemStack droppedItem = PassiveItemList.get(gearLevel, 0, rpgSlotType, tier, "", minStatValue, maxStatValue, minNumberOfStats);
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
