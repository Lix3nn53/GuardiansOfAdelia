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
        int damage = 10; //GearLevel.ZERO

        if (gearLevel.equals(GearLevel.ONE)) {
            damage = 15;
        } else if (gearLevel.equals(GearLevel.TWO)) {
            damage = 15;
        } else if (gearLevel.equals(GearLevel.THREE)) {
            damage = 15;
        } else if (gearLevel.equals(GearLevel.FOUR)) {
            damage = 15;
        } else if (gearLevel.equals(GearLevel.FIVE)) {
            damage = 15;
        } else if (gearLevel.equals(GearLevel.SIX)) {
            damage = 15;
        } else if (gearLevel.equals(GearLevel.SEVEN)) {
            damage = 15;
        } else if (gearLevel.equals(GearLevel.EIGHT)) {
            damage = 15;
        } else if (gearLevel.equals(GearLevel.NINE)) {
            damage = 15;
        }

        return damage;
    }

    private static double getBaseSpeed(GearLevel gearLevel) {
        double damage = 10; //GearLevel.ZERO

        if (gearLevel.equals(GearLevel.ONE)) {
            damage = 15;
        } else if (gearLevel.equals(GearLevel.TWO)) {
            damage = 15;
        } else if (gearLevel.equals(GearLevel.THREE)) {
            damage = 15;
        } else if (gearLevel.equals(GearLevel.FOUR)) {
            damage = 15;
        } else if (gearLevel.equals(GearLevel.FIVE)) {
            damage = 15;
        } else if (gearLevel.equals(GearLevel.SIX)) {
            damage = 15;
        } else if (gearLevel.equals(GearLevel.SEVEN)) {
            damage = 15;
        } else if (gearLevel.equals(GearLevel.EIGHT)) {
            damage = 15;
        } else if (gearLevel.equals(GearLevel.NINE)) {
            damage = 15;
        }

        return damage;
    }

    private static double getBaseJump(GearLevel gearLevel) {
        double damage = 10; //GearLevel.ZERO

        if (gearLevel.equals(GearLevel.ONE)) {
            damage = 15;
        } else if (gearLevel.equals(GearLevel.TWO)) {
            damage = 15;
        } else if (gearLevel.equals(GearLevel.THREE)) {
            damage = 15;
        } else if (gearLevel.equals(GearLevel.FOUR)) {
            damage = 15;
        } else if (gearLevel.equals(GearLevel.FIVE)) {
            damage = 15;
        } else if (gearLevel.equals(GearLevel.SIX)) {
            damage = 15;
        } else if (gearLevel.equals(GearLevel.SEVEN)) {
            damage = 15;
        } else if (gearLevel.equals(GearLevel.EIGHT)) {
            damage = 15;
        } else if (gearLevel.equals(GearLevel.NINE)) {
            damage = 15;
        }

        return damage;
    }
}
