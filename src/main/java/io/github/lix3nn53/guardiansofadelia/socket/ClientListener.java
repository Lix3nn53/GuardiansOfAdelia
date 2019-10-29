package io.github.lix3nn53.guardiansofadelia.socket;

import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientListener extends Thread {

    private final Socket clientSocket;

    ClientListener(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            RequestHandler requestHandler = new RequestHandler(out);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                requestHandler.onRequest(inputLine);
            }

            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
