package io.github.lix3nn53.guardiansofadelia.utilities;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class PacketLimitter {
    public static HashMap<UUID, Integer> PacketsSent = new HashMap<UUID, Integer>();

    public static HashMap<UUID, Integer> warnings = new HashMap<UUID, Integer>();

    public static HashMap<UUID, Integer> kick = new HashMap<UUID, Integer>();

    private static ProtocolManager protocolManager;

    public static void register() {
        protocolManager = ProtocolLibrary.getProtocolManager();

        new BukkitRunnable() {

            @Override
            public void run() {
                PacketsSent.clear();
            }
        }.runTaskTimerAsynchronously(GuardiansOfAdelia.getInstance(), 53L, 53L);

        new BukkitRunnable() {

            @Override
            public void run() {
                for (UUID uuid : kick.keySet()) {
                    Player p = Bukkit.getPlayer(uuid);
                    p.kickPlayer("Too many packets");
                    warnings.remove(uuid);
                    kick.remove(uuid);
                }
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 53L, 53L);

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

    public static void onQuit(Player p) {
        UUID uuid = p.getUniqueId();
        PacketsSent.remove(uuid);
        warnings.remove(uuid);
        kick.remove(uuid);
    }

    private static boolean packetLimit(Player p) {
        UUID uuid = p.getUniqueId();

        if (PacketsSent.containsKey(uuid)) {
            int sent = PacketsSent.get(uuid) + 1;
            PacketsSent.put(uuid, sent);
            if (sent > 800) {
                p.sendMessage(ChatColor.RED + "Too many packets!");
                if (warnings.containsKey(uuid)) {
                    int warns = warnings.get(uuid);
                    if (warns >= 3) {
                        warnings.put(uuid, (warns + 1));
                        kick.put(uuid, 1);
                    } else {
                        warnings.put(uuid, (warns + 1));
                    }
                } else {
                    warnings.put(uuid, 1);
                }
                return true;
            }
        } else {
            PacketsSent.put(uuid, 1);
        }
        return false;
    }
}
