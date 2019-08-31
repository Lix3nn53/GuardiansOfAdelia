package io.github.lix3nn53.guardiansofadelia.utilities;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class PacketLimitter {
    private static final HashMap<Player, Integer> packetsSent = new HashMap<>();

    private static final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
    private static final int PACKET_LIMIT = 553;

    public static void register() {

        new BukkitRunnable() {

            @Override
            public void run() {
                packetsSent.clear();
            }
        }.runTaskTimerAsynchronously(GuardiansOfAdelia.getInstance(), 100L, 100L);

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.ABILITIES) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.ADVANCEMENTS) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.ARM_ANIMATION) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.AUTO_RECIPE) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                if (e.getPacketType().isClient()) {
                    Player p = e.getPlayer();
                    if (packetLimit(p)) {
                        e.setCancelled(true);
                    }
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.BLOCK_DIG) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.BLOCK_PLACE) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.BOAT_MOVE) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.CHAT) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.CLIENT_COMMAND) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.CLOSE_WINDOW) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.CUSTOM_PAYLOAD) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.ENCHANT_ITEM) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.ENTITY_ACTION) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.FLYING) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.HELD_ITEM_SLOT) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.KEEP_ALIVE) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.LOOK) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.POSITION) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.POSITION_LOOK) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.RECIPE_DISPLAYED) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.RESOURCE_PACK_STATUS) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.SET_CREATIVE_SLOT) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.SETTINGS) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.SPECTATE) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.STEER_VEHICLE) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.TAB_COMPLETE) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.TELEPORT_ACCEPT) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.TRANSACTION) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.UPDATE_SIGN) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.VEHICLE_MOVE) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.WINDOW_CLICK) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (packetLimit(p)) {
                    e.setCancelled(true);
                }
            }
        });
    }

    private static boolean packetLimit(Player player) {
        if (packetsSent.containsKey(player)) {
            int sent = packetsSent.get(player) + 1;
            packetsSent.put(player, sent);
            if (sent > PACKET_LIMIT) {
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        player.kickPlayer(ChatColor.RED + "You are sending too many packets!");
                    }
                }.runTask(GuardiansOfAdelia.getInstance());
                return true;
            }
        } else {
            packetsSent.put(player, 1);
        }
        return false;
    }
}
