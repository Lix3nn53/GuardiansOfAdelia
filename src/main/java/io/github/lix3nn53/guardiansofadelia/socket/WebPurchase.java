package io.github.lix3nn53.guardiansofadelia.socket;

public class WebPurchase {

    private final String productId;
    private final int payment;
    private final String minecraftUsername;

    public WebPurchase(String productId, int payment, String minecraftUsername) {
        this.productId = productId;
        this.payment = payment;
        this.minecraftUsername = minecraftUsername;
    }

    public String getProductId() {
        return productId;
    }

    public int getPayment() {
        return payment;
    }

    public String getMinecraftUsername() {
        return minecraftUsername;
    }
}
