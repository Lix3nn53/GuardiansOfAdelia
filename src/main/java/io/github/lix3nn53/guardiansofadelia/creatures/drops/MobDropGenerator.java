package io.github.lix3nn53.guardiansofadelia.creatures.drops;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.utilities.ItemPoolGenerator;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

class MobDropGenerator {

    private static HashMap<Integer, List<ItemStack>> dropTables = new HashMap<>();

    static {
        List<ItemStack> dropTable0 = new ArrayList<ItemStack>();
        dropTable0.addAll(ItemPoolGenerator.generateWeapons(ItemTier.RARE, "", GearLevel.ZERO));
        dropTable0.addAll(ItemPoolGenerator.generateArmors(ItemTier.RARE, "", GearLevel.ZERO));
        dropTable0.addAll(ItemPoolGenerator.generatePassives(ItemTier.RARE, "", GearLevel.ZERO));
        dropTables.put(0, dropTable0);

        List<ItemStack> dropTable1 = new ArrayList<ItemStack>();
        dropTable1.addAll(ItemPoolGenerator.generateWeapons(ItemTier.RARE, "", GearLevel.ONE));
        dropTable1.addAll(ItemPoolGenerator.generateArmors(ItemTier.RARE, "", GearLevel.ONE));
        dropTable1.addAll(ItemPoolGenerator.generatePassives(ItemTier.RARE, "", GearLevel.ONE));
        dropTables.put(1, dropTable1);

        List<ItemStack> dropTable2 = new ArrayList<ItemStack>();
        dropTable2.addAll(ItemPoolGenerator.generateWeapons(ItemTier.RARE, "", GearLevel.TWO));
        dropTable2.addAll(ItemPoolGenerator.generateArmors(ItemTier.RARE, "", GearLevel.TWO));
        dropTable2.addAll(ItemPoolGenerator.generatePassives(ItemTier.RARE, "", GearLevel.TWO));
        dropTables.put(2, dropTable2);

        List<ItemStack> dropTable3 = new ArrayList<ItemStack>();
        dropTable3.addAll(ItemPoolGenerator.generateWeapons(ItemTier.RARE, "", GearLevel.THREE));
        dropTable3.addAll(ItemPoolGenerator.generateArmors(ItemTier.RARE, "", GearLevel.THREE));
        dropTable3.addAll(ItemPoolGenerator.generatePassives(ItemTier.RARE, "", GearLevel.THREE));
        dropTables.put(3, dropTable3);

        List<ItemStack> dropTable4 = new ArrayList<ItemStack>();
        dropTable4.addAll(ItemPoolGenerator.generateWeapons(ItemTier.RARE, "", GearLevel.FOUR));
        dropTable4.addAll(ItemPoolGenerator.generateArmors(ItemTier.RARE, "", GearLevel.FOUR));
        dropTable4.addAll(ItemPoolGenerator.generatePassives(ItemTier.RARE, "", GearLevel.FOUR));
        dropTables.put(4, dropTable4);

        List<ItemStack> dropTable5 = new ArrayList<ItemStack>();
        dropTable5.addAll(ItemPoolGenerator.generateWeapons(ItemTier.RARE, "", GearLevel.FIVE));
        dropTable5.addAll(ItemPoolGenerator.generateArmors(ItemTier.RARE, "", GearLevel.FIVE));
        dropTable5.addAll(ItemPoolGenerator.generatePassives(ItemTier.RARE, "", GearLevel.FIVE));
        dropTables.put(5, dropTable5);

        List<ItemStack> dropTable6 = new ArrayList<ItemStack>();
        dropTable6.addAll(ItemPoolGenerator.generateWeapons(ItemTier.RARE, "", GearLevel.SIX));
        dropTable6.addAll(ItemPoolGenerator.generateArmors(ItemTier.RARE, "", GearLevel.SIX));
        dropTable6.addAll(ItemPoolGenerator.generatePassives(ItemTier.RARE, "", GearLevel.SIX));
        dropTables.put(6, dropTable6);

        List<ItemStack> dropTable7 = new ArrayList<ItemStack>();
        dropTable7.addAll(ItemPoolGenerator.generateWeapons(ItemTier.RARE, "", GearLevel.SEVEN));
        dropTable7.addAll(ItemPoolGenerator.generateArmors(ItemTier.RARE, "", GearLevel.SEVEN));
        dropTable7.addAll(ItemPoolGenerator.generatePassives(ItemTier.RARE, "", GearLevel.SEVEN));
        dropTables.put(7, dropTable7);

        List<ItemStack> dropTable8 = new ArrayList<ItemStack>();
        dropTable8.addAll(ItemPoolGenerator.generateWeapons(ItemTier.RARE, "", GearLevel.EIGHT));
        dropTable8.addAll(ItemPoolGenerator.generateArmors(ItemTier.RARE, "", GearLevel.EIGHT));
        dropTable8.addAll(ItemPoolGenerator.generatePassives(ItemTier.RARE, "", GearLevel.EIGHT));
        dropTables.put(8, dropTable8);

        List<ItemStack> dropTable9 = new ArrayList<ItemStack>();
        dropTable9.addAll(ItemPoolGenerator.generateWeapons(ItemTier.RARE, "", GearLevel.NINE));
        dropTable9.addAll(ItemPoolGenerator.generateArmors(ItemTier.RARE, "", GearLevel.NINE));
        dropTable9.addAll(ItemPoolGenerator.generatePassives(ItemTier.RARE, "", GearLevel.NINE));
        dropTables.put(9, dropTable9);
    }

    public static List<ItemStack> getDrops(Entity entity) {
        List<ItemStack> drops = new ArrayList<>();

        double random = Math.random();
        if (random < 0.9D) {
            int dropTableNumber = getDropTableNumber(entity);

            if (dropTables.containsKey(dropTableNumber)) {
                List<ItemStack> dropTable = dropTables.get(dropTableNumber);

                if (!dropTable.isEmpty()) {
                    Collections.shuffle(dropTable);
                    drops.add(dropTable.get(0));
                }
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
