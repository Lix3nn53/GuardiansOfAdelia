package io.github.lix3nn53.guardiansofadelia.Items.list.eggs;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.PetMount;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.Mount;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Mounts {

    public static ItemStack get(Mount mount, int gearLevel) {

        ItemTier itemTier = mount.getItemTier();

        PetMount petCompanion = new PetMount(mount, itemTier, "", Material.STONE_HOE, mount.getEggCustomModelData(), GearLevel.getMinLevel(gearLevel),
                getBaseHealth(gearLevel), getBaseSpeed(gearLevel), getBaseJump(gearLevel));

        return petCompanion.getItemStack();
    }

    private static int getBaseHealth(int gearLevel) {
        if (gearLevel == 1) {
            return 180;
        } else if (gearLevel == 2) {
            return 360;
        } else if (gearLevel == 3) {
            return 750;
        } else if (gearLevel == 4) {
            return 1200;
        } else if (gearLevel == 5) {
            return 1900;
        } else if (gearLevel == 6) {
            return 2800;
        } else if (gearLevel == 7) {
            return 4000;
        } else if (gearLevel == 8) {
            return 6000;
        } else if (gearLevel == 9) {
            return 9000;
        }

        return 100; //GearLevel.ZERO
    }

    private static double getBaseSpeed(int gearLevel) {
        if (gearLevel == 1) {
            return 0.24;
        } else if (gearLevel == 2) {
            return 0.26;
        } else if (gearLevel == 3) {
            return 0.28;
        } else if (gearLevel == 4) {
            return 0.3;
        } else if (gearLevel == 5) {
            return 0.32;
        } else if (gearLevel == 6) {
            return 0.34;
        } else if (gearLevel == 7) {
            return 0.36;
        } else if (gearLevel == 8) {
            return 0.38;
        } else if (gearLevel == 9) {
            return 0.4;
        }

        return 0.22; //GearLevel.ZERO
    }

    private static double getBaseJump(int gearLevel) {
        if (gearLevel == 1) {
            return 0.85;
        } else if (gearLevel == 2) {
            return 0.9;
        } else if (gearLevel == 3) {
            return 0.95;
        } else if (gearLevel == 4) {
            return 1.0;
        } else if (gearLevel == 5) {
            return 1.05;
        } else if (gearLevel == 6) {
            return 1.1;
        } else if (gearLevel == 7) {
            return 1.15;
        } else if (gearLevel == 8) {
            return 1.2;
        } else if (gearLevel == 9) {
            return 1.25;
        }

        return 0.8; //GearLevel.ZERO
    }
}
