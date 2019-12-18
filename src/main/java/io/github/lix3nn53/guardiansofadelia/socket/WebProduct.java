package io.github.lix3nn53.guardiansofadelia.socket;

import org.bukkit.inventory.ItemStack;

public class WebProduct {

    private int productId;
    private int credits;
    private ItemStack itemStack;

    public WebProduct() {
    }

    public WebProduct(int productId, int credits, ItemStack itemStack) {
        this.productId = productId;
        this.credits = credits;
        this.itemStack = itemStack;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
