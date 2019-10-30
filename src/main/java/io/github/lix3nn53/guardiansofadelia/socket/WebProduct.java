package io.github.lix3nn53.guardiansofadelia.socket;

import org.bukkit.inventory.ItemStack;

public class WebProduct {

    private int productId;
    private int credits;
    private ItemStack itemStack;

    public WebProduct(int productId, int credits, ItemStack itemStack) {
        this.productId = productId;
        this.credits = credits;
        this.itemStack = itemStack;
    }

    public int productId() {
        return productId;
    }

    public int getCost() {
        return credits;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
