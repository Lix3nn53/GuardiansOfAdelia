package io.github.lix3nn53.guardiansofadelia.Items.list.consumables;

import io.github.lix3nn53.guardiansofadelia.Items.consumables.Consumable;
import io.github.lix3nn53.guardiansofadelia.Items.consumables.PotionType;
import org.bukkit.inventory.ItemStack;

public class Potions {

    public static ItemStack getItemStack(PotionType type, int potionLevel) {
        Consumable consumable = new Consumable(type.getSkillCode(), potionLevel, type.getMaterial(), type.getName(), type.getDescribtion());
        return consumable.getItemStack();
    }

}
