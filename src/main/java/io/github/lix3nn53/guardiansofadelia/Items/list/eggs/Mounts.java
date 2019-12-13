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
            return 1.25;
        } else if (gearLevel.equals(GearLevel.TWO)) {
            return 1.3;
        } else if (gearLevel.equals(GearLevel.THREE)) {
            return 1.35;
        } else if (gearLevel.equals(GearLevel.FOUR)) {
            return 1.4;
        } else if (gearLevel.equals(GearLevel.FIVE)) {
            return 1.45;
        } else if (gearLevel.equals(GearLevel.SIX)) {
            return 1.5;
        } else if (gearLevel.equals(GearLevel.SEVEN)) {
            return 1.55;
        } else if (gearLevel.equals(GearLevel.EIGHT)) {
            return 1.6;
        } else if (gearLevel.equals(GearLevel.NINE)) {
            return 1.65;
        }

        return 1.2; //GearLevel.ZERO
    }

    private static double getBaseJump(GearLevel gearLevel) {
        if (gearLevel.equals(GearLevel.ONE)) {
            return 1.25;
        } else if (gearLevel.equals(GearLevel.TWO)) {
            return 1.3;
        } else if (gearLevel.equals(GearLevel.THREE)) {
            return 1.35;
        } else if (gearLevel.equals(GearLevel.FOUR)) {
            return 1.4;
        } else if (gearLevel.equals(GearLevel.FIVE)) {
            return 1.45;
        } else if (gearLevel.equals(GearLevel.SIX)) {
            return 1.5;
        } else if (gearLevel.equals(GearLevel.SEVEN)) {
            return 1.55;
        } else if (gearLevel.equals(GearLevel.EIGHT)) {
            return 1.6;
        } else if (gearLevel.equals(GearLevel.NINE)) {
            return 1.65;
        }

        return 1.2; //GearLevel.ZERO
    }
}
