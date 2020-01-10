package io.github.lix3nn53.guardiansofadelia.bungeelistener.web;

public class WebPurchase {

    private int productId;
    private int payment;
    private String minecraftUsername;

    public WebPurchase() {
    }

    public WebPurchase(int productId, int payment, String minecraftUsername) {
        this.productId = productId;
        this.payment = payment;
        this.minecraftUsername = minecraftUsername;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getMinecraftUsername() {
        return minecraftUsername;
    }

    public void setMinecraftUsername(String minecraftUsername) {
        this.minecraftUsername = minecraftUsername;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WebPurchase{");
        sb.append("productId=").append(productId);
        sb.append(", payment=").append(payment);
        sb.append(", minecraftUsername='").append(minecraftUsername).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
