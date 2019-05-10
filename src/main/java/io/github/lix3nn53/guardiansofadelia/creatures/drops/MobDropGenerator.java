package io.github.lix3nn53.guardiansofadelia.creatures.drops;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.utilities.ItemPoolGenerator;
import io.github.lix3nn53.guardiansofadelia.utilities.persistentDataContainerUtil;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class MobDropGenerator {

    public static List<ItemStack> getDrops(Entity entity) {
        int dropTableNumber = getDropTableNumber(entity);
        List<ItemStack> dropTable = new ArrayList<ItemStack>();

        if (dropTableNumber == 0) {
            dropTable.addAll(ItemPoolGenerator.generateWeapons(ItemTier.RARE, "", GearLevel.ZERO));
            dropTable.addAll(ItemPoolGenerator.generateArmors(ItemTier.RARE, "", GearLevel.ZERO));
            dropTable.addAll(ItemPoolGenerator.generatePassives(ItemTier.RARE, "", GearLevel.ZERO));
        } else if (dropTableNumber == 1) {
            dropTable.addAll(ItemPoolGenerator.generateWeapons(ItemTier.RARE, "", GearLevel.ONE));
            dropTable.addAll(ItemPoolGenerator.generateArmors(ItemTier.RARE, "", GearLevel.ONE));
            dropTable.addAll(ItemPoolGenerator.generatePassives(ItemTier.RARE, "", GearLevel.ONE));
        } else if (dropTableNumber == 2) {
            dropTable.addAll(ItemPoolGenerator.generateWeapons(ItemTier.RARE, "", GearLevel.TWO));
            dropTable.addAll(ItemPoolGenerator.generateArmors(ItemTier.RARE, "", GearLevel.TWO));
            dropTable.addAll(ItemPoolGenerator.generatePassives(ItemTier.RARE, "", GearLevel.TWO));
        } else if (dropTableNumber == 3) {
            dropTable.addAll(ItemPoolGenerator.generateWeapons(ItemTier.RARE, "", GearLevel.THREE));
            dropTable.addAll(ItemPoolGenerator.generateArmors(ItemTier.RARE, "", GearLevel.THREE));
            dropTable.addAll(ItemPoolGenerator.generatePassives(ItemTier.RARE, "", GearLevel.THREE));
        } else if (dropTableNumber == 4) {
            dropTable.addAll(ItemPoolGenerator.generateWeapons(ItemTier.RARE, "", GearLevel.FOUR));
            dropTable.addAll(ItemPoolGenerator.generateArmors(ItemTier.RARE, "", GearLevel.FOUR));
            dropTable.addAll(ItemPoolGenerator.generatePassives(ItemTier.RARE, "", GearLevel.FOUR));
        } else if (dropTableNumber == 5) {
            dropTable.addAll(ItemPoolGenerator.generateWeapons(ItemTier.RARE, "", GearLevel.FIVE));
            dropTable.addAll(ItemPoolGenerator.generateArmors(ItemTier.RARE, "", GearLevel.FIVE));
            dropTable.addAll(ItemPoolGenerator.generatePassives(ItemTier.RARE, "", GearLevel.FIVE));
        } else if (dropTableNumber == 6) {
            dropTable.addAll(ItemPoolGenerator.generateWeapons(ItemTier.RARE, "", GearLevel.SIX));
            dropTable.addAll(ItemPoolGenerator.generateArmors(ItemTier.RARE, "", GearLevel.SIX));
            dropTable.addAll(ItemPoolGenerator.generatePassives(ItemTier.RARE, "", GearLevel.SIX));
        } else if (dropTableNumber == 7) {
            dropTable.addAll(ItemPoolGenerator.generateWeapons(ItemTier.RARE, "", GearLevel.SEVEN));
            dropTable.addAll(ItemPoolGenerator.generateArmors(ItemTier.RARE, "", GearLevel.SEVEN));
            dropTable.addAll(ItemPoolGenerator.generatePassives(ItemTier.RARE, "", GearLevel.SEVEN));
        } else if (dropTableNumber == 8) {
            dropTable.addAll(ItemPoolGenerator.generateWeapons(ItemTier.RARE, "", GearLevel.EIGHT));
            dropTable.addAll(ItemPoolGenerator.generateArmors(ItemTier.RARE, "", GearLevel.EIGHT));
            dropTable.addAll(ItemPoolGenerator.generatePassives(ItemTier.RARE, "", GearLevel.EIGHT));
        } else if (dropTableNumber == 9) {
            dropTable.addAll(ItemPoolGenerator.generateWeapons(ItemTier.RARE, "", GearLevel.NINE));
            dropTable.addAll(ItemPoolGenerator.generateArmors(ItemTier.RARE, "", GearLevel.NINE));
            dropTable.addAll(ItemPoolGenerator.generatePassives(ItemTier.RARE, "", GearLevel.NINE));
        }

        List<ItemStack> drops = new ArrayList<>();

        Collections.shuffle(dropTable);
        double random = Math.random();
        if (random < 0.1D) {
            drops.add(dropTable.get(0));
        }

        return drops;
    }

    private static int getDropTableNumber(Entity entity) {
        if (persistentDataContainerUtil.hasInteger(entity, "dropTableNumber")) {
            return persistentDataContainerUtil.getInteger(entity, "dropTableNumber");
        }
        return -1;
    }
}
