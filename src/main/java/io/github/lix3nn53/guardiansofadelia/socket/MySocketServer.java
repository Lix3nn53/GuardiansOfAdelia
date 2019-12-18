package io.github.lix3nn53.guardiansofadelia.socket;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;

public class MySocketServer {

    private final Configuration config = new Configuration();
    private final SocketIOServer server;

    public MySocketServer(int port) {
        this.config.setHostname("localhost");
        this.config.setPort(9092);

        this.server = new SocketIOServer(this.config);

        this.server.addEventListener("purchase", WebProduct.class, (socketIOClient, webProduct, ackRequest) -> {
            webProduct.getProductId();

            socketIOClient.sendEvent("chatevent", "data");
        });
    }

    public void start() throws InterruptedException {
        GuardiansOfAdelia.getInstance().getLogger().info("Listening on port " + config.getPort());
        this.server.start();
    }

    public void stop() {
        GuardiansOfAdelia.getInstance().getLogger().info("Listening on port " + config.getPort());
        this.server.stop();
    }
}
