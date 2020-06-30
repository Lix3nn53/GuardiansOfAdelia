package io.github.lix3nn53.guardiansofadelia.creatures.drops;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ShieldGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorManager;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorType;
import io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems.PassiveManager;
import io.github.lix3nn53.guardiansofadelia.Items.list.shields.ShieldManager;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.WeaponManager;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.BoostPremiumManager;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.BoostPremium;
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

            Random rand = new Random();

            // Obtain a number to select between weapon, <armor or shield> and passive
            int dropType = rand.nextInt(3);

            if (dropType == 0) {
                WeaponGearType[] values = WeaponGearType.values();
                int gearRandom = rand.nextInt(values.length);

                WeaponGearType gearType = values[gearRandom];

                ItemStack droppedItem = WeaponManager.get(gearType, gearLevel, 0, tier, "", false);
                drops.add(droppedItem);
            } else if (dropType == 1) {
                double shieldChange = 0.2;
                double armorOrShieldRandom = Math.random(); //armor or shield?

                if (armorOrShieldRandom < shieldChange) { //shield
                    ShieldGearType[] values = ShieldGearType.values();
                    int gearRandom = rand.nextInt(values.length);

                    ShieldGearType gearType = values[gearRandom];

                    ItemStack droppedItem = ShieldManager.get(gearType, gearLevel, 0, tier, "", false);
                    drops.add(droppedItem);
                } else {
                    ArmorType[] armorTypes = ArmorType.values();
                    int armorTypeRandom = rand.nextInt(armorTypes.length);
                    ArmorType armorType = armorTypes[armorTypeRandom];

                    ArmorGearType[] armorGearTypes = ArmorGearType.values();
                    int armorGearTypeRandom = rand.nextInt(armorGearTypes.length);
                    ArmorGearType armorGearType = armorGearTypes[armorGearTypeRandom];

                    ItemStack droppedItem = ArmorManager.get(armorType, armorGearType, gearLevel, 0, tier, "", false);
                    drops.add(droppedItem);
                }
            } else if (dropType == 2) {
                int passiveType = rand.nextInt(4);
                RPGSlotType rpgSlotType = RPGSlotType.values()[passiveType + 1]; //+1 to ignore parrot

                ItemStack droppedItem = PassiveManager.get(gearLevel, 0, rpgSlotType, tier, "", false);
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
