package io.github.lix3nn53.guardiansofadelia.socket;

public class WebResponse {

    private final String msg;
    private final String minecraftUsername;
    private final String productSelection;

    public WebResponse(String msg, String minecraftUsername, String productSelection) {
        this.msg = msg;
        this.minecraftUsername = minecraftUsername;
        this.productSelection = productSelection;
    }
}
