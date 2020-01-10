package io.github.lix3nn53.guardiansofadelia;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class PluginChannelListener implements PluginMessageListener {

    private static HashMap<Player, String> responses = new HashMap<>();

    @Override
    public synchronized void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }

        Bukkit.getLogger().info("BUNGEE MESSAGE");

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
        try {
            String subchannel = in.readUTF();

            String input = in.readUTF();

            Bukkit.getLogger().info("subchannel: " + subchannel);
            Bukkit.getLogger().info("input: " + input);

            responses.put(player, input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized String get(Player p, String subchannel, List<String> args) {  // here you can add parameters (e.g. String table, String column, ...)
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
            String o = responses.get(p);
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