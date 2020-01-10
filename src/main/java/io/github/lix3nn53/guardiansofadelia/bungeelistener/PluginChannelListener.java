package io.github.lix3nn53.guardiansofadelia.bungeelistener;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.web.WebPurchase;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.web.WebResponse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PluginChannelListener implements PluginMessageListener {

    private static HashMap<Player, Object> responses = new HashMap<>();

    @Override
    public synchronized void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }

        Bukkit.getLogger().info("BUNGEE MESSAGE");

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
        try {
            String subchannel = in.readUTF();
            if (subchannel.equalsIgnoreCase("webPurchase")) {
                int productId = Integer.parseInt(in.readUTF());
                int payment = Integer.parseInt(in.readUTF());
                String minecraftUsername = in.readUTF();

                WebPurchase webPurchase = new WebPurchase(productId, payment, minecraftUsername);
                GuardiansOfAdelia.getInstance().getLogger().info(webPurchase.toString());
                WebResponse webResponse = RequestHandler.onPurchase(webPurchase);
                GuardiansOfAdelia.getInstance().getLogger().info(webResponse.toString());

                List<String> responseToSend = new ArrayList<>();
                boolean responseSuccess = webResponse.isSuccess();
                String responseMsg = webResponse.getMsg();
                String responseMinecraftUsername = webResponse.getMinecraftUsername();
                int responseProductId = webResponse.getProductId();

                responseToSend.add(String.valueOf(responseSuccess));
                responseToSend.add(responseMsg);
                responseToSend.add(responseMinecraftUsername);
                responseToSend.add(String.valueOf(responseProductId));

                sendToBungeeCord(player, "webPurchaseResponse", responseToSend);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized Object get(Player p, String subchannel, List<String> args) {  // here you can add parameters (e.g. String table, String column, ...)
        sendToBungeeCord(p, subchannel, args);

        int i = 0;
        while (!responses.containsKey(p)) {
            try {
                wait(100L);
            } catch (InterruptedException e) {
                return null;
            }

            p.sendMessage("i: " + i);
            if (i > 30) break;
            i++;
        }

        if (responses.containsKey(p)) {
            Object o = responses.get(p);
            responses.remove(p);
            return o;
        }

        return null;
    }

    public void sendToBungeeCord(Player p, String subchannel, List<String> args) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF(subchannel);
            for (String arg : args) {
                out.writeUTF(arg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.sendPluginMessage(GuardiansOfAdelia.getInstance(), "BungeeCord", b.toByteArray());
    }

}