package io.github.lix3nn53.guardiansofadelia.Items.list.consumables;

import io.github.lix3nn53.guardiansofadelia.Items.consumables.BuffScroll;
import io.github.lix3nn53.guardiansofadelia.Items.consumables.BuffType;
import org.bukkit.inventory.ItemStack;

public class BuffScrolls {

    public static ItemStack getItemStack(BuffType type, int potionLevel) {
        BuffScroll buffScroll = new BuffScroll(type.getSkillCode(), potionLevel, type.getMaterial(), type.getName(), type.getDescribtion(), type.getDuration());
        return buffScroll.getItemStack();
    }
}
