package io.github.lix3nn53.guardiansofadelia.Items.list.eggs;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.PetCompanion;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.Companion;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Companions {

    public static ItemStack get(Companion companion, int gearLevel) {

        ItemTier itemTier = companion.getItemTier();

        PetCompanion petCompanion = new PetCompanion(companion, itemTier, "", Material.STONE_HOE, companion.getEggCustomModelData(),
                GearLevel.getMinLevel(gearLevel), getBaseDamage(gearLevel), getBaseHealth(gearLevel));

        return petCompanion.getItemStack();
    }

    private static int getBaseDamage(int gearLevel) {
        if (gearLevel == 1) {
            return 20;
        } else if (gearLevel == 2) {
            return 40;
        } else if (gearLevel == 3) {
            return 60;
        } else if (gearLevel == 4) {
            return 90;
        } else if (gearLevel == 5) {
            return 120;
        } else if (gearLevel == 6) {
            return 180;
        } else if (gearLevel == 7) {
            return 240;
        } else if (gearLevel == 8) {
            return 310;
        } else if (gearLevel == 9) {
            return 400;
        }

        return 10; //GearLevel.ZERO
    }

    private static int getBaseHealth(int gearLevel) {
        if (gearLevel == 1) {
            return 120;
        } else if (gearLevel == 2) {
            return 240;
        } else if (gearLevel == 3) {
            return 500;
        } else if (gearLevel == 4) {
            return 900;
        } else if (gearLevel == 5) {
            return 1400;
        } else if (gearLevel == 6) {
            return 2000;
        } else if (gearLevel == 7) {
            return 2800;
        } else if (gearLevel == 8) {
            return 4000;
        } else if (gearLevel == 9) {
            return 6000;
        }

        return 60; //GearLevel.ZERO
    }
}
