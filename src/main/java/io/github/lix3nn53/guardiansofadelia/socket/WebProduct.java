package io.github.lix3nn53.guardiansofadelia.socket;

import org.bukkit.inventory.ItemStack;

public class WebProduct {

    private final int cost;
    private final ItemStack itemStack;

    public WebProduct(int cost, ItemStack itemStack) {
        this.cost = cost;
        this.itemStack = itemStack;
    }
}
