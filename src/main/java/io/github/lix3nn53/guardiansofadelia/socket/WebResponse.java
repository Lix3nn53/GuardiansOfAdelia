package io.github.lix3nn53.guardiansofadelia.socket;

public class WebResponse {

    private boolean success;
    private String msg;
    private String minecraftUsername;
    private int productId;

    public WebResponse() {
    }

    public WebResponse(boolean success, String msg, String minecraftUsername, int productId) {
        this.success = success;
        this.msg = msg;
        this.minecraftUsername = minecraftUsername;
        this.productId = productId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMinecraftUsername() {
        return minecraftUsername;
    }

    public void setMinecraftUsername(String minecraftUsername) {
        this.minecraftUsername = minecraftUsername;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WebResponse{");
        sb.append("success=").append(success);
        sb.append(", msg='").append(msg).append('\'');
        sb.append(", minecraftUsername='").append(minecraftUsername).append('\'');
        sb.append(", productId=").append(productId);
        sb.append('}');
        return sb.toString();
    }
}
