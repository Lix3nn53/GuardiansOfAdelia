package io.github.lix3nn53.guardiansofadelia.Items.list.eggs;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.PetCompanion;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.Companion;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Companions {

    public static ItemStack get(Companion companion, ItemTier tier, int reqLevel, int itemID) {

        PetCompanion petCompanion = new PetCompanion(companion, tier, "", Material.STONE_HOE, companion.getEggCustomModelData(), reqLevel, itemID);

        return petCompanion.getItemStack();
    }
}
