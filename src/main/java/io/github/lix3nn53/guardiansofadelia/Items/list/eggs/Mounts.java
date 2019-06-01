package io.github.lix3nn53.guardiansofadelia.Items.list.eggs;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.PetMount;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.Mount;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Mounts {

    public static ItemStack get(Mount mount, ItemTier tier, int reqLevel, int itemID) {

        PetMount petCompanion = new PetMount(mount, tier, "", Material.STONE_HOE, mount.getEggCustomModelData(), reqLevel, itemID);

        return petCompanion.getItemStack();
    }
}
