package io.github.lix3nn53.guardiansofadelia.bungeelistener.web;

public class WebResponse {

    private boolean success;
    private String msg;
    private String minecraftUuid;
    private String minecraftUsername;
    private int productId;

    public WebResponse() {
    }

    public WebResponse(boolean success, String msg, String minecraftUuid, String minecraftUsername, int productId) {
        this.success = success;
        this.msg = msg;
        this.minecraftUuid = minecraftUuid;
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

    public String getMinecraftUuid() {
        return minecraftUuid;
    }

    public void setMinecraftUuid(String minecraftUuid) {
        this.minecraftUuid = minecraftUuid;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getMinecraftUsername() {
        return minecraftUsername;
    }

    public void setMinecraftUsername(String minecraftUsername) {
        this.minecraftUsername = minecraftUsername;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WebResponse{");
        sb.append("success=").append(success);
        sb.append(", msg='").append(msg).append('\'');
        sb.append(", minecraftUuid='").append(minecraftUuid).append('\'');
        sb.append(", minecraftUsername='").append(minecraftUsername).append('\'');
        sb.append(", productId=").append(productId);
        sb.append('}');
        return sb.toString();
    }
}
