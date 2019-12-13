package io.github.lix3nn53.guardiansofadelia.Items.list.eggs;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.PetMount;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.Mount;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Mounts {

    public static ItemStack get(Mount mount, GearLevel gearLevel) {

        ItemTier itemTier = mount.getItemTier();

        PetMount petCompanion = new PetMount(mount, itemTier, "", Material.STONE_HOE, mount.getEggCustomModelData(), gearLevel.getMinLevel(),
                getBaseHealth(gearLevel), getBaseSpeed(gearLevel), getBaseJump(gearLevel));

        return petCompanion.getItemStack();
    }

    private static int getBaseHealth(GearLevel gearLevel) {
        if (gearLevel.equals(GearLevel.ONE)) {
            return 180;
        } else if (gearLevel.equals(GearLevel.TWO)) {
            return 360;
        } else if (gearLevel.equals(GearLevel.THREE)) {
            return 750;
        } else if (gearLevel.equals(GearLevel.FOUR)) {
            return 1200;
        } else if (gearLevel.equals(GearLevel.FIVE)) {
            return 1900;
        } else if (gearLevel.equals(GearLevel.SIX)) {
            return 2800;
        } else if (gearLevel.equals(GearLevel.SEVEN)) {
            return 4000;
        } else if (gearLevel.equals(GearLevel.EIGHT)) {
            return 6000;
        } else if (gearLevel.equals(GearLevel.NINE)) {
            return 9000;
        }

        return 100; //GearLevel.ZERO
    }

    private static double getBaseSpeed(GearLevel gearLevel) {
        if (gearLevel.equals(GearLevel.ONE)) {
            return 0.24;
        } else if (gearLevel.equals(GearLevel.TWO)) {
            return 0.26;
        } else if (gearLevel.equals(GearLevel.THREE)) {
            return 0.28;
        } else if (gearLevel.equals(GearLevel.FOUR)) {
            return 0.3;
        } else if (gearLevel.equals(GearLevel.FIVE)) {
            return 0.32;
        } else if (gearLevel.equals(GearLevel.SIX)) {
            return 0.34;
        } else if (gearLevel.equals(GearLevel.SEVEN)) {
            return 0.36;
        } else if (gearLevel.equals(GearLevel.EIGHT)) {
            return 0.38;
        } else if (gearLevel.equals(GearLevel.NINE)) {
            return 0.4;
        }

        return 0.22; //GearLevel.ZERO
    }

    private static double getBaseJump(GearLevel gearLevel) {
        if (gearLevel.equals(GearLevel.ONE)) {
            return 0.85;
        } else if (gearLevel.equals(GearLevel.TWO)) {
            return 0.9;
        } else if (gearLevel.equals(GearLevel.THREE)) {
            return 0.95;
        } else if (gearLevel.equals(GearLevel.FOUR)) {
            return 1.0;
        } else if (gearLevel.equals(GearLevel.FIVE)) {
            return 1.05;
        } else if (gearLevel.equals(GearLevel.SIX)) {
            return 1.1;
        } else if (gearLevel.equals(GearLevel.SEVEN)) {
            return 1.15;
        } else if (gearLevel.equals(GearLevel.EIGHT)) {
            return 1.2;
        } else if (gearLevel.equals(GearLevel.NINE)) {
            return 1.25;
        }

        return 0.8; //GearLevel.ZERO
    }
}
