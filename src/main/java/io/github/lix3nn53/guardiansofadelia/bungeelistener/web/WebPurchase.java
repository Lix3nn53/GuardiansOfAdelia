package io.github.lix3nn53.guardiansofadelia.bungeelistener.web;

public class WebPurchase {

    private int productId;
    private int payment;
    private String minecraftUuid;

    public WebPurchase() {
    }

    public WebPurchase(int productId, int payment, String minecraftUuid) {
        this.productId = productId;
        this.payment = payment;
        this.minecraftUuid = minecraftUuid;
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

    public String getMinecraftUuid() {
        return minecraftUuid;
    }

    public void setMinecraftUuid(String minecraftUuid) {
        this.minecraftUuid = minecraftUuid;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WebPurchase{");
        sb.append("productId=").append(productId);
        sb.append(", payment=").append(payment);
        sb.append(", minecraftUuid='").append(minecraftUuid).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
