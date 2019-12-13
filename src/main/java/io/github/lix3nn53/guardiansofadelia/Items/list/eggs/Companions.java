package io.github.lix3nn53.guardiansofadelia.Items.list.eggs;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.PetCompanion;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.Companion;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Companions {

    public static ItemStack get(Companion companion, GearLevel gearLevel) {

        ItemTier itemTier = companion.getItemTier();

        PetCompanion petCompanion = new PetCompanion(companion, itemTier, "", Material.STONE_HOE, companion.getEggCustomModelData(),
                gearLevel.getMinLevel(), getBaseDamage(gearLevel), getBaseHealth(gearLevel));

        return petCompanion.getItemStack();
    }

    private static int getBaseDamage(GearLevel gearLevel) {
        if (gearLevel.equals(GearLevel.ONE)) {
            return 20;
        } else if (gearLevel.equals(GearLevel.TWO)) {
            return 40;
        } else if (gearLevel.equals(GearLevel.THREE)) {
            return 60;
        } else if (gearLevel.equals(GearLevel.FOUR)) {
            return 90;
        } else if (gearLevel.equals(GearLevel.FIVE)) {
            return 120;
        } else if (gearLevel.equals(GearLevel.SIX)) {
            return 180;
        } else if (gearLevel.equals(GearLevel.SEVEN)) {
            return 240;
        } else if (gearLevel.equals(GearLevel.EIGHT)) {
            return 310;
        } else if (gearLevel.equals(GearLevel.NINE)) {
            return 400;
        }

        return 10; //GearLevel.ZERO
    }

    private static int getBaseHealth(GearLevel gearLevel) {
        if (gearLevel.equals(GearLevel.ONE)) {
            return 120;
        } else if (gearLevel.equals(GearLevel.TWO)) {
            return 240;
        } else if (gearLevel.equals(GearLevel.THREE)) {
            return 500;
        } else if (gearLevel.equals(GearLevel.FOUR)) {
            return 900;
        } else if (gearLevel.equals(GearLevel.FIVE)) {
            return 1400;
        } else if (gearLevel.equals(GearLevel.SIX)) {
            return 2000;
        } else if (gearLevel.equals(GearLevel.SEVEN)) {
            return 2800;
        } else if (gearLevel.equals(GearLevel.EIGHT)) {
            return 4000;
        } else if (gearLevel.equals(GearLevel.NINE)) {
            return 6000;
        }

        return 60; //GearLevel.ZERO
    }
}
