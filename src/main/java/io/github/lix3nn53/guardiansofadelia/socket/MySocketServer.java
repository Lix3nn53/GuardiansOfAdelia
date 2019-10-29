package io.github.lix3nn53.guardiansofadelia.socket;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;

import java.io.IOException;
import java.net.ServerSocket;

public class MySocketServer {

    private final ServerSocket serverSocket;

    public MySocketServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        GuardiansOfAdelia.getInstance().getLogger().info("Listening on port " + serverSocket.getLocalPort());
        while (true)
            new ClientListener(serverSocket.accept()).start();
    }

    public void stop() throws IOException {
        serverSocket.close();
    }
}
