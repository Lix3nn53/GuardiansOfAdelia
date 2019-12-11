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
}
